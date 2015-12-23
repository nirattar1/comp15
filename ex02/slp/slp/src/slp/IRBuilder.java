package slp;

import java.util.*;
import java.util.zip.CheckedInputStream;

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

	private int _currentRegister1 = 1;
	private int _currentRegister2 = 2;

	// holds the depth while traversing the tree

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 * @throws SemanticException
	 */
	public IRBuilder(ASTNode root, TypeTable tt) throws SemanticException 
	{
		this.root = root;
		this.typeTable = tt;
		// System.out.println("\nstarted dfs - TypeChecker");
		// start traverse tree.
		root.accept(this, 0);
		// SymbolTableImpl.printToDebugFile();
		System.out.println(output);

	}

	@Override
	public LIRResult visit(Program program, Integer scope) throws SemanticException {

		for (Class c : program.classList) {

			c.accept(this, scope);

		}
		return null;

	}

	@Override
	public LIRResult visit(Class class1, Integer scope) throws SemanticException {

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
		class1.fieldMethodList.accept(this, scope + 1);

		// close the class scope.
		symbolTable.deleteScope(scope + 1);
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
	public LIRResult visit(FieldMethodList fieldMethodList, Integer scope) throws SemanticException {

		FieldMethod fm;
		for (int i = fieldMethodList.fieldsmethods.size() - 1; i >= 0; i--) {
			fm = fieldMethodList.fieldsmethods.get(i);
			if (fm instanceof Field) {
				((Field) fm).accept(this, scope);
			}
			if (fm instanceof Method) {
				((Method) fm).accept(this, scope);
			}
		}
		return null;
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

	
	public LIRResult visit (AssignStmt stmt, Integer regCount) throws SemanticException
	{		
		AssignStmt s = (AssignStmt) stmt;

		// generate code for left hand side.
		LIRResult resultLeft = s._assignTo.accept(this, regCount);

		// generate code for right hand side.
		//(update register count).
		LIRResult resultRight = s._assignValue.accept(this, resultLeft.get_regCount());

		//call the right data transfer function to deal with the assignment.
		
		//local vars.
		//Move R2, mylocal1
		if (s._assignTo instanceof LocationId)
		{
			String str = "Move ";
			str += resultRight.get_regName();  		//register where value was saved.
			str += ",";
			
			//note: resultLeft contains the field where value is saved.
			// i.e. "R1.3"
			str += resultLeft.get_regName();
			str += "\n";
			
			//write output.
			output.append(str);
		}
				
		//fields.
		//MoveField R2, R1.3
		else if (s._assignTo instanceof LocationExpressionMember)
		{
			String str = "MoveField ";
			str += resultRight.get_regName();  		//register where value was saved.
			str += ",";
			
			//note: resultLeft contains the field where value is saved.
			// i.e. "R1.3"
			str += resultLeft.get_regName();
		}
	
		//array elements
		
		
		

		//update caller based on right! (computed last) 
		return new LIRResult (RegisterType.REGTYPE_TEMP_SIMPLE, 
				null , resultRight.get_regCount());

	}
	
	// general statement
	@Override
	public LIRResult visit(Stmt stmt, Integer regCount) throws SemanticException {
		// System.out.println("stmt visit");

		// Assign statement
		if (stmt instanceof AssignStmt) 
		{
			return visit((AssignStmt) stmt, regCount);

		}

		else if (stmt instanceof CallStatement) {
			// System.out.println("Method call statement");
			((CallStatement) stmt)._call.accept(this, regCount);
		}

		else if (stmt instanceof StmtIf) {
			// System.out.println("If statement");
			StmtIf s = (StmtIf) stmt;

			// print condition
			s._condition.accept(this, regCount);

			// print commands
			if (s._commands instanceof StmtList) {
				// System.out.println("Block of statements");
			}

			s._commands.accept(this, regCount);
			if (s._commandsElse != null) {
				// System.out.println("Else statement");
				s._commandsElse.accept(this, regCount);
			}

		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			// System.out.println("While statement");
			s._condition.accept(this, regCount);
			if (s._commands instanceof StmtList) {

				// System.out.println("Block of statements");
			}
			s._commands.accept(this, regCount);
			return null;
		}

		// break statement
		else if (stmt instanceof StmtBreak)

		{
			// System.out.println("Break statement");

		} else if (stmt instanceof StmtContinue)

		{
			// System.out.println("Continue statement");
		}

		else if (stmt instanceof StmtList) {

			StmtList sl = (StmtList) stmt;
			Type temp, r = null;

			Integer lastCount = regCount;
			for (Stmt s : sl.statements) 
			{
				LIRResult res = s.accept(this, lastCount);
				lastCount = res.get_regCount();
			}


			// System.out.println(r == null);
			return null;
		}

		else if (stmt instanceof ReturnExprStatement) {

			// System.out.println("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;

			returnExp.accept(this, regCount);

		}

		else if (stmt instanceof ReturnVoidStatement) {
			// System.out.println("Return statement (void value).");
			if (!_currentMethod.returnVar.type._typeName.equals("void")) {
			}

		} 
		else if (stmt instanceof StmtDeclareVar) 
		{
			StmtDeclareVar s = (StmtDeclareVar) stmt;
			boolean isValue = (s._value != null);
			// System.out.println("Declaration of local variable: " + s._id);
			// print value if exists
			if (isValue) 
			{
				// System.out.println(", with initial value");
			}

			// print the type
			s._type.accept(this, regCount);
			Type t1 = s._type;

			// print value if exists
			if (isValue) 
			{
				s._value.accept(this, regCount);

				// // check primitive types.
				// if (t1.isPrimitive || t2.isPrimitive) {
				// // check that both are primitive and of the same type
				// if (t1.isPrimitive && t2.isPrimitive
				// && t1._typeName.equals(t2._typeName)) {
				// return null;
				// } else {
				// }
				// }

				// // check for types
				// if (typeTable.checkSubTypes(t2._typeName, t1._typeName)) {
				// // System.out.println("t2 inherits from t1");
				// return null;
				// }
			}
		}

		
		//default action, return nothing special and continue with count.
		return new LIRResult (RegisterType.REGTYPE_TEMP_SIMPLE, 
				null , regCount);

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

	public LIRResult visit(Expr expr, Integer scope) throws SemanticException {

		if (expr instanceof BinaryOpExpr) {
			LIRResult t1, t2;

			BinaryOpExpr e = ((BinaryOpExpr) expr);
			t1 = visit(e.lhs, scope);
			t2 = visit(e.rhs, scope);

			switch (e.op) {
			case DIV:
				break;
			case MINUS:
				break;
			case MULT:
				break;
			case PLUS:
				break;
			case LT:
				break;
			case GT:
				break;
			case LE:
				break;
			case GE:
				break;
			case LAND:
				break;
			case LOR:
				break;
			case EQUAL:

			default:
			}
			// System.out.println(t1._typeName);
			// System.out.println(t2._typeName);

			// type checks and inference.

			// System.out.println(t1._typeName);
			// System.out.println(t2._typeName);

			// infer and return the type from the 2 types and operator.
			// may throw exceptions on inappropriate types.
			return null;

		}

		// call
		else if (expr instanceof Call) {
			return visit((Call) expr, scope);
		}

		// "this" expression
		else if (expr instanceof ExprThis) {
			// return typeTable.getType(_currentClassName);
		}

		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			// System.out.println("Reference to array length");
			e._expr.accept(this, scope);

			// array length is considered as int.
			// return new Type(e.line, "int");
		}

		// Literals
		else if (expr instanceof Literal) {
			return visit((Literal) expr, scope);

		}

		else if (expr instanceof Location) {
			return visit((Location) expr, scope);
		}

		else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			// System.out.println(e.op.humanString());

			// continue evaluating.
			e.operand.accept(this, scope);

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

			newArr._arrSizeExpr.accept(this, scope);

			// print array type
			newArr._type.accept(this, scope);
			newArr._type._typeName += "[]";
			// return newArr._type;
		}
		return null;
	}

	public LIRResult visit(Call cl, Integer scope) throws SemanticException {

		if (cl instanceof CallStatic) {
			CallStatic call = (CallStatic) cl;
			// System.out.println("Call to static method: " + call._methodId +
			// ", in class: " + call._classId);

			// check that class has a static method with this name.
			MethodBase m = typeTable.getMethod(call._classId, call._methodId);
			// check method exist and static
			if (m == null || !m.isStatic) {
			}

			// evaluate arguments.
			// prepare a list for arguments types in this call.
			List<Type> argsTypes = new ArrayList<Type>();
			for (Expr f : call._arguments) {
				// resolve each argument's type , and push it to list.
				f.accept(this, scope);
			}

			// check validity of arguments for call.
			// (will throw if invalid).
			call.checkValidArgumentsForCall(m, argsTypes, typeTable);

			return null;
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
				call._instanceExpr.accept(this, scope);

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
				f.accept(this, scope);
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

		if (loc instanceof LocationArrSubscript) 
		{
			LocationArrSubscript e = ((LocationArrSubscript) loc);
			// System.out.println("Reference to array");

			e._exprArr.accept(this, regCount);

			// TODO do this better.
			// // drop the [] from type name to return the actual type.
			// String basicTypeName = arr._typeName.substring(0,
			// arr._typeName.length() - 2);
			//
			// Type basicType = new Type(arr.line, basicTypeName);
			// // on non primitive type, get the type info from Type table.
			// if (basicType != null && !basicType.isPrimitive) {
			// basicType = typeTable.getType(basicTypeName);
			// }

			// validate subscript expression will be checked for initialization.
			_checkInitialized = true;
			// Type sub = e._exprSub.accept(this, scope);

			// validate both types exist, and that subscript is int type.
			// if (sub == null) {
			// // System.out.println("sub=null");
			// }
			// if (arr == null) {
			// } else if (!sub._typeName.equals("int")) {
			// }
			// return basicType;
		}

		
		//return a reference to the object with the field access.
		else if (loc instanceof LocationExpressionMember) 
		{
			
			//save object reference, compute offset of field.
			
			
			// access to member of object instance.
			// example: m.x (where m is of type Moshe).
			// will type checks.

			LocationExpressionMember l = (LocationExpressionMember) loc;
			// System.out.println("Reference to variable: " + l.member);
			// System.out.println(", in external scope");

			// we need to check that reference was initialized.
			// (the member was already init by default.)
			_checkInitialized = true;
			// get the type of instance.
			l.expr.accept(this, regCount);

			// get the full type from the typetable.
//			instanceType = typeTable.getType(instanceType._typeName);

			// we need to check that instance has this field name,
			// (or super class has it).
			// then return its type.
//			Field memberField = typeTable.getFieldOfInstance(instanceType._typeName, l.member);
//			if (memberField != null) {
//				return memberField.type;
//			} else {
//			}
		}

		else if (loc instanceof LocationId) 
		{
			LocationId l = (LocationId) loc;

			// check that symbol exists in current scope in symbol table.
			//if (l.name != null && symbolTable.checkAvailable(scope, l.name)) 
			//{

			// TODO distinguish between scope variable and "this" fields..
			// return the type from the current scope.
			
			//TODO need distinguish between local and argument ?
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, l.name, regCount);
			
			//}

//			// not in symbol table,
//			// but it is a field in this class or its superclass.
//			Field memberField = typeTable.getFieldOfInstance(_currentClassName, l.name);
//			if (memberField != null) {
//				// return memberField.type;
//			}

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

		output.append(_currentClassName + "_" + method.returnVar.frmName.name + ": \n");

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
