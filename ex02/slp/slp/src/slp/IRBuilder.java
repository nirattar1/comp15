package slp;

import java.util.*;

import slp.LIRResult.RegisterType;

/**
 * Pretty-prints an SLP AST.
 */
public class IRBuilder implements PropagatingVisitor<Integer, LIRResult> {

	protected final ASTNode root;

	private SymbolTable symbolTable = new SymbolTableImpl();

	public TypeTable typeTable = null;

	private boolean _checkInitialized = true;

	private String _currentClassName = null;

	private Method _currentMethod = null;

	private StringBuffer output = new StringBuffer();
	
	//made for counting labels of temporary labels (for Logical operations)
	private int _tempLabel = 0;
	
	//made for counting labels of loops
	private int _tempLoopLabel = 0;

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 * @throws SemanticException
	 */
	public IRBuilder(ASTNode root, TypeTable tt) throws SemanticException {
		this.root = root;
		this.typeTable = tt;
		// System.out.println("\nstarted dfs - TypeChecker");
		// start traverse tree.
		root.accept(this, 0);
		// SymbolTableImpl.printToDebugFile();
		System.out.println(output);

	}

	@Override
	public LIRResult visit(Program program, Integer regCount) throws SemanticException {

		for (Class c : program.classList) {

			c.accept(this, regCount);

		}
		return null;

	}

	@Override
	public LIRResult visit(Class class1, Integer regCount) throws SemanticException {

		if (class1._extends != null) {
			// System.out.println("Declaration of class:" + class1._className +
			// " Extends" + class1._extends);
		} else {
			// System.out.println("Declaration of class: " + class1._className);
		}

		// save an indication of current class name,
		// for further traversal inside class .
		_currentClassName = class1._className;

		// visit all components of the class.
		class1.fieldMethodList.accept(this, regCount);

		_currentClassName = null;

		return null;
	}

	@Override
	public LIRResult visit(Field field, Integer scope) throws SemanticException {
		// declaration of a field
		for (VarExpr v : field.idList) {
			// System.out.println("Declaration of field: ");
			v.accept(this, scope);
			// System.out.println(field.type == null);

			// no use of adding to type table
			// (was already done in previous pass).
			// if (!symbolTable.addVariable(scope, new VVariable(v.name, scope,
			// field.type, false))) {
			// throw (new SemanticException("Error: duplicate variable name at
			// line " + field.line));
			// }

		}

		// print type.
		field.type.accept(this, scope);
		return null;

	}

	@Override
	public LIRResult visit(FieldMethodList fieldMethodList, Integer regCount) throws SemanticException {
		LIRResult r1 = new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R" + regCount, regCount);
		FieldMethod fm;
		for (int i = fieldMethodList.fieldsmethods.size() - 1; i >= 0; i--) {
			fm = fieldMethodList.fieldsmethods.get(i);
			if (fm instanceof Field) {
				r1 = ((Field) fm).accept(this, regCount);
			}
			if (fm instanceof Method) {
				r1 = ((Method) fm).accept(this, regCount);
			}
		}
		return r1;
	}

	@Override
	public LIRResult visit(FormalsList formalsList, Integer scope) throws SemanticException {

		for (Formal f : formalsList.formals) {
			// System.out.println("Parameter: " + f.frmName);
			f.type.accept(this, scope);

		}
		return null;
	}

	@Override
	public LIRResult visit(Formal formal, Integer scope) throws SemanticException {

		// print parameter name
		if (formal.frmName != null) {
			formal.frmName.accept(this, scope);
		}

		// print its type
		formal.type.accept(this, scope);

		return null;

	}

	public LIRResult visit(AssignStmt stmt, Integer regCount) throws SemanticException {
		AssignStmt s = (AssignStmt) stmt;

		// generate code for left hand side.
		LIRResult resultLeft = s._assignTo.accept(this, regCount);

		// generate code for right hand side.
		// (update register count).
		LIRResult resultRight = s._assignValue.accept(this, resultLeft.get_regCount());

		// call the right data transfer function to deal with the assignment.

		// local vars.
		// Move R2, mylocal1
		if (s._assignTo instanceof LocationId) {
			String str = "Move ";
			str += resultRight.get_regName(); // register where value was saved.
			str += ",";

			// TODO bug - when right side is a field ! need to use movefield
			// instead of move
			str += resultLeft.get_regName();
			str += "\n";

			// write output.
			output.append(str);
		}

		// fields.
		// MoveField R2, R1.3
		else if (s._assignTo instanceof LocationExpressionMember) {
			String str = "MoveField ";
			str += resultRight.get_regName(); // register where value was saved.
			str += ",";

			// note: resultLeft contains the field where value is saved.
			// i.e. "R1.3"
			str += resultLeft.get_regName();
			str += "\n";

			// write output.
			output.append(str);

		}

		else if (s._assignTo instanceof LocationArrSubscript) {
			String str = "";
			if (resultRight.get_regType() == resultLeft.get_regType()) {

				str = "Move " + resultRight.get_regName() + ",R" + (resultRight.get_regCount() + 1) + "\nMoveArray R"
						+ (resultRight.get_regCount() + 1) + "," + resultLeft.get_regName() + "\n";

			} else {
				str = "MoveArray ";
				str += resultRight.get_regName(); // register where value was
													// saved.
				str += ",";
				str += resultLeft.get_regName() + "\n";

			}
			output.append(str);
		}

		// update caller based on right! (computed last)
		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R" + resultRight.get_regCount(),
				resultRight.get_regCount());

	}

	public LIRResult visit(StmtDeclareVar stmt, Integer regCount) throws SemanticException {

		StmtDeclareVar s = stmt;
		boolean isValue = (s._value != null);

		// print value if exists
		if (isValue) {

			// TODO need distinguish between types ?? (array etc.)

			// generate code for right hand side.
			// (update register count).
			LIRResult resultRight = s._value.accept(this, regCount);

			// update for later use.
			regCount = resultRight.get_regCount();

			String str = "Move ";
			str += resultRight.get_regName(); // register where value was saved.
			str += ",";

			// put the result in the new variable in memory.
			str += s._id;
			str += "\n";

			// write output.
			output.append(str);
		}

		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R" + regCount, regCount);

	}

	// general statement
	@Override
	public LIRResult visit(Stmt stmt, Integer regCount) throws SemanticException {
		// System.out.println("stmt visit");

		// Assign statement
		if (stmt instanceof AssignStmt) {
			return visit((AssignStmt) stmt, regCount);
		}

		if (stmt instanceof CallStatement) {
			// System.out.println("Method call statement");
			((CallStatement) stmt)._call.accept(this, regCount);
		}

		else if (stmt instanceof StmtIf) {
			// System.out.println("If statement");
			StmtIf s = (StmtIf) stmt;

			// print out condition code
			LIRResult r1 = s._condition.accept(this, regCount);

			// print out condition handling
			output.append("Compare 0," + r1.get_regName() + "\n");
			output.append("JumpT _Else_" + ++_tempLabel + "\n");
			// print out commands for if condition was true

			r1 = s._commands.accept(this, r1.get_regCount());

			output.append("Jump _If_" + _tempLabel + "_End\n");

			// print out commands for if condition was false
			output.append("_Else_" + _tempLabel + ":\n");
			output.append("Move 1," + r1.get_regName() + "\n");
			if (s._commandsElse != null) {
				r1 = s._commandsElse.accept(this, r1.get_regCount());
			}
			output.append("_If_" + _tempLabel + "_End:\n\n");

			return r1;
		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;

			// print out condition code
			LIRResult r1 = s._condition.accept(this, regCount);
			LIRResult r2 = new LIRResult(r1);
			// print out condition handling
			output.append("_Loop" + ++_tempLoopLabel + "_Start:\n");
			output.append("Compare 0," + r1.get_regName() + "\n");
			output.append("JumpF _Loop" + _tempLoopLabel + "_End\n");

			// print out loop commands code
			r2 = s._commands.accept(this, r2.get_regCount());
			// jump to loop start
			output.append("Jump _Loop" + _tempLoopLabel + "_Start\n");
			// loop end label
			output.append("_Loop" + _tempLoopLabel + "_End:\n\n");
			_tempLoopLabel--;
			// return LIRResult of last command in loop
			return r2;
		}

		// break statement
		else if (stmt instanceof StmtBreak)

		{
			output.append("Jump _Loop" + _tempLoopLabel + "_End\n\n");
			// System.out.println("Break statement");

		} else if (stmt instanceof StmtContinue){
			output.append("Jump _Loop" + _tempLoopLabel + "_Start\n\n");
			// System.out.println("Continue statement");
		}

		else if (stmt instanceof StmtList) {

			StmtList sl = (StmtList) stmt;
			LIRResult res = new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R" + regCount, regCount);

			Integer lastCount = regCount;
			for (Stmt s : sl.statements) {
				res = s.accept(this, lastCount);
				lastCount = res.get_regCount();
			}

			return res;
		}

		else if (stmt instanceof ReturnExprStatement) 
		{

			// System.out.println("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;
			LIRResult resultReg = returnExp.accept(this, regCount);
			
			//print a return statement with the register result
			String str = "Return ";
			str += resultReg.get_regName();
			str += "\n";
			output.append(str);
			
			return resultReg;
		}

		else if (stmt instanceof ReturnVoidStatement) {
			// System.out.println("Return statement (void value).");
			output.append("Return 9999");
		}

		// Declaration of local variable
		// similar to assignment.
		else if (stmt instanceof StmtDeclareVar) {

			return visit((StmtDeclareVar) stmt, regCount);
		}

		// default action, return nothing special and continue with count.
		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R" + regCount, regCount);

	}

	/**
	 * build code for this literal. It will be just a temp name where the value
	 * will be stored.
	 * 
	 * @param expr
	 * @param scope
	 * @return
	 * @throws SemanticException
	 */
	public LIRResult visit(Literal expr, Integer regCount) throws SemanticException {

		if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			// System.out.println("Boolean literal: " + e.value);
			String strLitValue = (e.value == true) ? "1" : "0";
			String resultName = "R" + (++regCount);
			output.append("Move " + strLitValue + "," + resultName + "\n");
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, resultName, regCount);
		}

		else if (expr instanceof LiteralNull) {
			// nul reference is just a zero.
			String resultName = "R" + (++regCount);
			output.append("Move 0," + resultName + "\n");
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, resultName, regCount);
		}

		else if (expr instanceof LiteralNumber) {
			// prepare a move command: store the literal inside a temp.
			// then return it.
			LiteralNumber e = ((LiteralNumber) expr);
			String resultName = "R" + (++regCount);
			output.append("Move " + Integer.toString(e.value) + "," + resultName + "\n");

			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, resultName, regCount);
		}

		// string literals need separate global storage.
		else if (expr instanceof LiteralString) {
			LiteralString e = (LiteralString) expr;

			// TODO not implemented.

			return null;
		}

		return null;
	}

	public LIRResult visit(Expr expr, Integer regCount) throws SemanticException {

		if (expr instanceof BinaryOpExpr) {
			LIRResult r1, r2;

			BinaryOpExpr e = ((BinaryOpExpr) expr);
			r1 = visit(e.lhs, regCount);
			r2 = visit(e.rhs, r1.get_regCount());

			switch (e.op) {
			case DIV:
				output.append("Div " + r2.get_regName() + "," + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r1.get_regName(), r2.get_regCount());
			case MINUS:
				output.append("Sub " + r2.get_regName() + "," + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r1.get_regName(), r2.get_regCount());
			case MULT:
				output.append("Mul " + r2.get_regName() + "," + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r1.get_regName(), r2.get_regCount());
			case PLUS:
				output.append("Add " + r2.get_regName() + "," + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r1.get_regName(), r2.get_regCount());
			case LT:
				output.append("Compare " + r1.get_regName() + "," + r2.get_regName() + "\n");
				output.append("JumpL _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());
			case GT:
				output.append("Compare " + r1.get_regName() + "," + r2.get_regName() + "\n");
				output.append("JumpG _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());
			case LE:
				output.append("Compare " + r1.get_regName() + "," + r2.get_regName() + "\n");
				output.append("JumpLE _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());
			case GE:
				output.append("Compare " + r1.get_regName() + "," + r2.get_regName() + "\n");
				output.append("JumpGE _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());

			case EQUAL:
				output.append("Compare " + r1.get_regName() + "," + r2.get_regName() + "\n");
				output.append("JumpF _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());

			case NEQUAL:
				output.append("Compare " + r1.get_regName() + "," + r2.get_regName() + "\n");
				output.append("JumpT _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());

			case LAND:
				output.append("And " + r1.get_regName() + "," + r2.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());
			case LOR:
				output.append("Or " + r1.get_regName() + "," + r2.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r2.get_regName(), r2.get_regCount());
			case MOD:
				output.append("Mod " + r2.get_regName() + "," + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r1.get_regName(), r2.get_regCount());

			default:
			}

			return null;

		}

		// call
		else if (expr instanceof Call) {
			return visit((Call) expr, regCount);
		}

		// "this" expression
		else if (expr instanceof ExprThis) {
			// return typeTable.getType(_currentClassName);
		}

		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			// System.out.println("Reference to array length");
			e._expr.accept(this, regCount);

			// array length is considered as int.
			// return new Type(e.line, "int");
		}

		// Literals
		else if (expr instanceof Literal) {
			return visit((Literal) expr, regCount);

		}

		else if (expr instanceof Location) {
			return visit((Location) expr, regCount);
		}

		else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;

			// continue evaluating.
			LIRResult r1 = e.operand.accept(this, regCount);
			switch (e.op) {
			case LNEG:
				output.append("Not " + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r1.get_regName(), r1.get_regCount());
			case MINUS:
				output.append("Neg " + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, r1.get_regName(), r1.get_regCount());
			default:

			}
			// infer type
			// return Type.TypeInferUnary(t1, e.op);
		}

		else if (expr instanceof NewClassInstance) {
			// System.out.println("Instantiation of class: ");
			NewClassInstance instance = (NewClassInstance) expr;
			// System.out.println(instance._class_id);

			// check that type table has this type and return it .
			if (!typeTable.checkExist(instance._class_id)) {
			} else {
				// return typeTable.getType(instance._class_id);
			}
		} else if (expr instanceof NewArray) {
			// System.out.println("Array allocation");

			NewArray newArr = (NewArray) expr;

			LIRResult arrSize = newArr._arrSizeExpr.accept(this, regCount);
			output.append("__allocateArray(R" + arrSize.get_regCount() + ")\n");
			return arrSize;
		}
		return null;
	}

	public LIRResult visit(Call cl, Integer regCount) throws SemanticException 
	{

		if (cl instanceof CallStatic) {
			CallStatic call = (CallStatic) cl;


			// get the static method with this name - validity checked in typechecker.
			MethodBase m = typeTable.getMethod(call._classId, call._methodId);
			
			//prepare function name.
			String str = "StaticCall " + call._classId + "." + call._methodId + "(";
			
			//prepare pairs of argument-value.
			List <Formal> formals = m.frmls.formals;
			
			int i=0;
			int lastCount = regCount;
			for (Formal f : formals)
			{
				//comma starting from argument 1.
				if (i>0) {str += ",";};
				
				str += f.frmName.name;
				str += "=";
				
				//compute the value
				List <Expr> argsExprList = call._arguments;
				LIRResult argVal = argsExprList.get(i).accept(this, lastCount);

				//append the place where the value is stored.
				str += argVal.get_regName();
				
				//update for next iteration
				lastCount = argVal.get_regCount();
				i++;
			}
			
			//make a register for result.
			lastCount++;
			String outReg = "R"+lastCount;			
			str += ")," + outReg + "\n";
			
			//finally output
			output.append(str);
			
			
			return new LIRResult (RegisterType.REGTYPE_TEMP_SIMPLE, outReg, lastCount);
		}

		else if (cl instanceof CallVirtual) {

			CallVirtual call = (CallVirtual) cl;
			// System.out.println("Call to virtual method: " + call._methodId);
			MethodBase m = null;
			Type instanceType = null; // the type of the caller.

			// instance (external) call.
			if (call._instanceExpr != null) {
				// resolve the type of the instance.
				// System.out.println(", in external scope");
				call._instanceExpr.accept(this, regCount);

				// check that instance class has a virtual method with this
				// name.
				m = typeTable.getMethod(instanceType._typeName, call._methodId);
			} else {
				// this is not an instance call.
				// caller is "this".
				instanceType = typeTable.getType(_currentClassName);
				m = typeTable.getMethod(_currentClassName, call._methodId);
			}

			// check method exist and virtual
			if (m == null || m.isStatic) {
			}

			// evaluate arguments.
			// prepare a list for arguments types in this call.
			List<Type> argsTypes = new ArrayList<Type>();
			for (Expr f : call._arguments) {
				// resolve each argument's type , and push it to list.
				f.accept(this, regCount);
			}

			// check validity of arguments for call.
			// (will throw if invalid).
			call.checkValidArgumentsForCall(m, argsTypes, typeTable);

			// return the method's return type.
			return null;

		}

		return null;

	}

	public LIRResult visit(Location loc, Integer regCount) throws SemanticException {
		// location expressions.
		// will throw on access to location before it is initialized.

		if (loc instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) loc);
			// System.out.println("Reference to array");

			LIRResult arr = e._exprArr.accept(this, regCount);

			LIRResult sub = e._exprSub.accept(this, arr.get_regCount());

			return new LIRResult(RegisterType.REGTYPE_TEMP_ARRAYSUB, arr.get_regName() + "[" + sub.get_regName() + "]",
					sub.get_regCount());
		}

		// object field access. (external scope)
		// will first evaluate the object instance and then get the field.
		// responsible to return a full name of register and field number.
		// i.e. R1.3
		else if (loc instanceof LocationExpressionMember) {

			LocationExpressionMember l = (LocationExpressionMember) loc;

			// compute the object instance.
			LIRResult instance = l.expr.accept(this, regCount);

			// compute offset of field.
			int offset = l.expr._type.getFieldIROffset(l.member);

			String fullFieldName = instance.get_regName() + "." + offset;

			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, fullFieldName, instance.get_regCount() + 1);

		}

		else if (loc instanceof LocationId) {
			LocationId l = (LocationId) loc;

			// check that symbol exists in current scope in symbol table.
			// if (l.name != null && symbolTable.checkAvailable(scope, l.name))
			// {

			// TODO distinguish between scope variable and "this" fields..
			// return the type from the current scope.

			// TODO make this only for objects?
			// create a temporary for the instance.
			// i.e. Move student1, Rxxx
			String tempName = "R" + (++regCount);
			String cmd = "Move " + l.name + "," + tempName + "\n";
			output.append(cmd);

			// TODO need distinguish between local and argument ?
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, tempName, regCount);

			// }

			// // not in symbol table,
			// // but it is a field in this class or its superclass.
			// Field memberField =
			// typeTable.getFieldOfInstance(_currentClassName, l.name);
			// if (memberField != null) {
			// // return memberField.type;
			// }

			// if reached here - reference to variable not found anywhere.

		}

		return null;

	}

	@Override
	public LIRResult visit(TypeArray array) {
		// System.out.println("Primitive data type: 1-dimensional array of " +
		// array._typeName);
		return null;

	}

	@Override
	public LIRResult visit(Method method, Integer scope) throws SemanticException {

		_currentMethod = method;

		if (method.isStatic) {
			// System.out.println("Declaration of static method: ");
		} else {
			// System.out.println("Declaration of virtual method: ");
		}

		//there is always one main in program - special naming for IR.
		String methodIRName;
		if (method.returnVar.frmName.name.equals("main"))
		{
			methodIRName = "_ic_main";
		}
		else
		{
			methodIRName = _currentClassName + "_" + method.returnVar.frmName.name;
		}
		
		//print method name.
		output.append("\n" + methodIRName + ": \n");

		if (!symbolTable.addVariable(scope, new VMethod(method.returnVar.frmName.name, scope, method.returnVar.type))) {
		}

		for (Formal f : method.frmls.formals) {
			// System.out.println(f.type);
			if (!symbolTable.addVariable(scope + 1, new VVariable(f.frmName.name, scope + 1, f.type, true))) {
			}
		}

		// go into method body
		method.stmt_list.accept(this, scope);

		// scope's variables will be deleted in the end of stmtlist!

		return null;

	}

	@Override
	public LIRResult visit(Type type, Integer scope) {
		if (type.isPrimitive) {
			// System.out.println("Primitive data type: " + type._typeName);
		} else {
			// System.out.println("User-defined data type: " + type._typeName);
		}
		return null;
	}

	@Override
	public LIRResult visit(TypeArray array, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LIRResult visit(VarExpr varExpr, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

}
