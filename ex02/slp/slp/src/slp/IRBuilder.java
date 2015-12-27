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

	private String _currentClassName = null;

	private Method _currentMethod = null;

	private StringBuffer output = new StringBuffer();

	//
	private List<String> _literalValues = new ArrayList<String>();
	private static final String literalStringPrefix = "str_";

	// made for counting labels of temporary labels (for Logical operations)
	private int _tempLabel = 0;

	// made for counting labels of loops
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

	}

	/**
	 * return the output.
	 * 
	 * @return
	 */
	public StringBuffer getOutput() {
		return output;
	}

	@Override
	public LIRResult visit(Program program, Integer regCount)
			throws SemanticException {

		// generate dispatch vectors for all types in program.
		String str = typeTable.getIRAllVirtualTables();
		output.append(str);

		// generate code for all classes in program.
		for (Class c : program.classList) {
			c.accept(this, regCount);
		}

		// generate all string literals found inside program.
		String literalOut = "";
		for (int i = 0; i < _literalValues.size(); i++) {
			literalOut += literalStringPrefix + i;
			literalOut += ": " + "\"" + _literalValues.get(i) + "\"" + "\n";
		}
		// prepend literals list to start of program.
		output.insert(0, literalOut);

		return null;

	}

	@Override
	public LIRResult visit(Class class1, Integer regCount)
			throws SemanticException {

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
	public LIRResult visit(FieldMethodList fieldMethodList, Integer regCount)
			throws SemanticException {
		LIRResult r1 = new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R"
				+ regCount, regCount);
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
		return null;
	}

	@Override
	public LIRResult visit(FormalsList formalsList, Integer scope)
			throws SemanticException {

		for (Formal f : formalsList.formals) {
			// System.out.println("Parameter: " + f.frmName);
			f.type.accept(this, scope);

		}
		return null;
	}

	@Override
	public LIRResult visit(Formal formal, Integer scope)
			throws SemanticException {

		// print parameter name
		if (formal.frmName != null) {
			formal.frmName.accept(this, scope);
		}

		// print its type
		formal.type.accept(this, scope);

		return null;

	}

	public LIRResult visit(AssignStmt stmt, Integer regCount)
			throws SemanticException {
		AssignStmt s = (AssignStmt) stmt;

		// generate code for left hand side.
		LIRResult resultLeft = s._assignTo.accept(this, regCount);

		// generate code for right hand side.
		// (update register count).
		LIRResult resultRight = s._assignValue.accept(this,
				resultLeft.get_regCount());

		// call the right data transfer function to deal with the assignment.

		// local vars.
		// Move R2, mylocal1
		if (s._assignTo instanceof LocationId) {
			String str = "";
			if (s._assignValue instanceof LocationExpressionMember) {
				str += "#__checkNullRef ("
						+ resultRight.get_regName().split("\\.")[0] + ") \n";
				str += "MoveField ";
			} else {
				str += "Move ";
			}

			str += resultRight.get_regName(); // register where value was saved.
			str += ",";
			str += "R" + (resultRight.get_regCount() + 1) + "\n";
			str += "Move R" + (resultRight.get_regCount() + 1) + ",";
			str += ((LocationId) s._assignTo).name;
			str += "\n";

			// write output.
			output.append(str);
		}

		// fields.
		// MoveField R2, R1.3
		else if (s._assignTo instanceof LocationExpressionMember) {
			String str = "";
			str += "#__checkNullRef ("
					+ resultLeft.get_regName().split("\\.")[0] + ")\n";
			str += "MoveField ";
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
			str += "#Library __checkNullRef ("
					+ (resultLeft.get_regName().split("\\["))[0] + ")\n";
			str += "#Library __checkArrayAccess ("
					+ (resultLeft.get_regName().split("\\["))[0]
					+ ","
					+ (resultLeft.get_regName().split("\\["))[1].split("\\]")[0]
					+ ")\n";

			if (resultRight.get_regType() == resultLeft.get_regType()) {
				str += "#Library __checkNullRef ("
						+ (resultRight.get_regName().split("\\["))[0] + ")\n";
				str += "#Library __checkArrayAccess ("
						+ (resultRight.get_regName().split("\\["))[0]
						+ ","
						+ (resultRight.get_regName().split("\\["))[1]
								.split("\\]")[0] + ")\n";
				str += "MoveArray " + resultRight.get_regName() + ",R"
						+ (resultRight.get_regCount() + 1) + "\nMoveArray R"
						+ (resultRight.get_regCount() + 1) + ","
						+ resultLeft.get_regName() + "\n";

			} else {

				str += "MoveArray ";
				str += resultRight.get_regName(); // register where value was
													// saved.
				str += ",";
				str += resultLeft.get_regName() + "\n";

			}
			output.append(str);
		}

		// update caller based on right! (computed last)
		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R"
				+ resultRight.get_regCount(), resultRight.get_regCount());

	}

	// declaration of variable. similar to assignment.
	public LIRResult visit(StmtDeclareVar stmt, Integer regCount)
			throws SemanticException {

		StmtDeclareVar s = stmt;
		boolean isValue = (s._value != null);
		String str = "";

		if (isValue) {
			LIRResult resultRight = s._value.accept(this, regCount);

			// update for later use.
			regCount = resultRight.get_regCount();

			if (s._value instanceof LocationExpressionMember) {
				str += "#__checkNullRef ("
						+ resultRight.get_regName().split("\\.")[0] + ") \n";
				str += "MoveField ";
				str += resultRight.get_regName(); // register where value was saved.
				str += ",";

				// put the result in the new variable in memory.

				str += "R" + (resultRight.get_regCount()) + "\n";
			} 
			
			//Move value into new variable
			str += "Move " + (resultRight.get_regName()) + ",";
			str += s._id;
			str += "\n";

		} else {
			// we have to initialize the newly created variable temporarily in
			// order for the MicroLIR to identify it
			str += "Move 0," + s._id + "\n";
		}
		// write output.
		output.append(str);

		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R" + regCount,
				regCount);

	}

	// general statement
	@Override
	public LIRResult visit(Stmt stmt, Integer regCount)
			throws SemanticException {
		// System.out.println("stmt visit");

		// Assign statement
		if (stmt instanceof AssignStmt) {
			return visit((AssignStmt) stmt, regCount);
		}

		if (stmt instanceof CallStatement) {
			// System.out.println("Method call statement");
			LIRResult callResult = ((CallStatement) stmt)._call.accept(this,
					regCount);
			return new LIRResult(callResult.get_regType(),
					callResult.get_regName(), callResult.get_regCount());
		}

		else if (stmt instanceof StmtIf) {
			// System.out.println("If statement");
			StmtIf s = (StmtIf) stmt;

			// print out condition code
			LIRResult r1 = s._condition.accept(this, regCount);

			// print out condition handling
			output.append("Compare 1," + r1.get_regName() + "\n");
			output.append("JumpFalse _Else_" + ++_tempLabel + "\n");
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
			output.append("JumpFalse _Loop" + _tempLoopLabel + "_End\n");

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

		}

		else if (stmt instanceof StmtContinue) {
			output.append("Jump _Loop" + _tempLoopLabel + "_Start\n\n");
			// System.out.println("Continue statement");
		}

		else if (stmt instanceof StmtList) {

			StmtList sl = (StmtList) stmt;
			LIRResult res = new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R"
					+ regCount, regCount);

			Integer lastCount = regCount;
			for (Stmt s : sl.statements) {
				res = s.accept(this, lastCount); // each statement result will
													// not be null.
				lastCount = res.get_regCount();
			}

			return res;
		}

		else if (stmt instanceof ReturnExprStatement) {

			// System.out.println("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;

			// resolve the expression to return and its expression.
			LIRResult resultReg = returnExp.accept(this, regCount);

			// print a return statement with the register result
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
		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R" + regCount,
				regCount);

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
	public LIRResult visit(Literal expr, Integer regCount)
			throws SemanticException {

		if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			// System.out.println("Boolean literal: " + e.value);
			String strLitValue = (e.value == true) ? "1" : "0";
			String resultName = "R" + (++regCount);
			output.append("Move " + strLitValue + "," + resultName + "\n");
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, resultName,
					regCount);
		}

		else if (expr instanceof LiteralNull) {
			// nul reference is just a zero.
			String resultName = "R" + (++regCount);
			output.append("Move 0," + resultName + "\n");
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, resultName,
					regCount);
		}

		else if (expr instanceof LiteralNumber) {
			// prepare a move command: store the literal inside a temp.
			// then return it.
			LiteralNumber e = ((LiteralNumber) expr);
			String resultName = "R" + (++regCount);
			output.append("Move " + Integer.toString(e.value) + ","
					+ resultName + "\n");

			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, resultName,
					regCount);
		}

		// string literals need separate global storage.
		// in string , no need to move address to new address.
		else if (expr instanceof LiteralString) {
			LiteralString e = (LiteralString) expr;

			// add a new literal value to literal list.
			_literalValues.add(e.value);

			// add a reference to the literal here.
			String regName = literalStringPrefix + (_literalValues.size() - 1);

			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, regName,
					regCount);
		}

		return null;
	}

	public LIRResult visit(Expr expr, Integer regCount)
			throws SemanticException {

		if (expr instanceof BinaryOpExpr) {
			LIRResult r1, r2;

			BinaryOpExpr e = ((BinaryOpExpr) expr);
			r1 = visit(e.lhs, regCount);
			r2 = visit(e.rhs, r1.get_regCount());

			switch (e.op) {
			case DIV:
				output.append("#__checkZero (" + r2.get_regName() + ","
						+ r1.get_regName() + ")\n");
				output.append("Div " + r2.get_regName() + ","
						+ r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r1.get_regName(), r2.get_regCount());
			case MINUS:
				output.append("Sub " + r2.get_regName() + ","
						+ r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r1.get_regName(), r2.get_regCount());
			case MULT:
				output.append("Mul " + r2.get_regName() + ","
						+ r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r1.get_regName(), r2.get_regCount());

			case PLUS:
				// for two strings, call concatenate.
				// i.e. Library __stringCat(str1,R1),R2
				if (e.lhs._type.isStringType() && e.rhs._type.isStringType()) {

					String str = "Library __stringCat(";
					// put arguments
					str += r1.get_regName() + "," + r2.get_regName() + ")";

					// prepare output register.
					int newCount = r2.get_regCount();
					String regName = "R" + (++newCount);
					str += "," + regName + "\n";
					output.append(str);
					return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
							regName, newCount);

				} else {
					// for other cases use normal plus.
					output.append("Add " + r2.get_regName() + ","
							+ r1.get_regName() + "\n\n");
					return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
							r1.get_regName(), r2.get_regCount());
				}

			case LT:
				output.append("Compare " + r1.get_regName() + ","
						+ r2.get_regName() + "\n");
				output.append("JumpL _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());
			case GT:
				output.append("Compare " + r1.get_regName() + ","
						+ r2.get_regName() + "\n");
				output.append("JumpG _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());
			case LE:
				output.append("Compare " + r1.get_regName() + ","
						+ r2.get_regName() + "\n");
				output.append("JumpLE _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());
			case GE:
				output.append("Compare " + r1.get_regName() + ","
						+ r2.get_regName() + "\n");
				output.append("JumpGE _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());

			case EQUAL:
				output.append("Compare " + r1.get_regName() + ","
						+ r2.get_regName() + "\n");
				output.append("JumpTrue _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());

			case NEQUAL:
				output.append("Compare " + r1.get_regName() + ","
						+ r2.get_regName() + "\n");
				output.append("JumpFalse _Temp" + ++_tempLabel + "\n");
				output.append("Move 0," + r2.get_regName() + "\n");
				output.append("Jump _Temp" + _tempLabel + "End\n");
				output.append("_Temp" + _tempLabel + ":\n");
				output.append("Move 1," + r2.get_regName() + "\n");
				output.append("_Temp" + _tempLabel + "End:\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());

			case LAND:
				output.append("And " + r1.get_regName() + ","
						+ r2.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());
			case LOR:
				output.append("Or " + r1.get_regName() + "," + r2.get_regName()
						+ "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r2.get_regName(), r2.get_regCount());
			case MOD:
				output.append("Mod " + r2.get_regName() + ","
						+ r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r1.get_regName(), r2.get_regCount());

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
			String regName = "R" + (++regCount);
			output.append("Move this," + regName + "\n");
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, regName,
					regCount);
		}

		// array length.
		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			// System.out.println("Reference to array length");

			// get register where array is saved.
			LIRResult arrReg = e._expr.accept(this, regCount);

			// runtime check - will check that array is not null.
			output.append("#Library __checkNullRef(" + arrReg.get_regName()
					+ "),Rdummy\n");

			// make a new register for result.
			int newCount = arrReg.get_regCount();
			String resultReg = "R" + (++newCount);
			String cmdLength = "ArrayLength " + arrReg.get_regName() + ","
					+ resultReg + "\n";

			// output
			output.append(cmdLength);

			// return
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, resultReg,
					newCount);
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
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r1.get_regName(), r1.get_regCount());
			case MINUS:
				output.append("Neg " + r1.get_regName() + "\n\n");
				return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						r1.get_regName(), r1.get_regCount());
			default:

			}
			// infer type
			// return Type.TypeInferUnary(t1, e.op);
		}

		else if (expr instanceof NewClassInstance) {

			return visit((NewClassInstance) expr, regCount);

		}

		else if (expr instanceof NewArray) {
			// System.out.println("Array allocation");

			NewArray newArr = (NewArray) expr;
			String s = "";
			LIRResult arrSize = newArr._arrSizeExpr.accept(this, regCount);
			String lengthReg = arrSize.get_regName();
			s += "#Library __checkSize (" + lengthReg + "),Rdummy\n";
			s += "Mul 4," + lengthReg + "\n";
			int arrayPointerReg = arrSize.get_regCount() + 1;
			s += "Library __allocateArray(" + lengthReg + "),R"
					+ arrayPointerReg + "\n";
			s += "Div 4," + lengthReg + "\n";
			int counterReg = arrayPointerReg + 1;
			s += "Move 0,R" + counterReg + "\n";
			s += "_Array_init_" + ++_tempLabel + ":\n";
			s += "Compare R" + counterReg + "," + lengthReg + "\n";
			s += "JumpTrue _Array_init_end_" + _tempLabel + "\n";
			s += "MoveArray 0,R" + arrayPointerReg + "[R" + counterReg + "]\n";
			s += "Inc R" + counterReg + "\n";
			s += "Jump _Array_init_" + _tempLabel + "\n";
			s += "_Array_init_end_" + _tempLabel + ":\n";

			output.append(s);
			// counterRegister, lengthReg
			// can be disposed so we're returning the arrSize LIRResult.
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, "R"
					+ arrayPointerReg, arrayPointerReg);
		}
		return null;
	}

	// new class instance (object).
	// needs to allocate memory.
	public LIRResult visit(NewClassInstance instance, Integer regCount)
			throws SemanticException {
		// System.out.println("Instantiation of class: ");

		// get type info from type table
		Type instanceType = typeTable.getType(instance._class_id);

		// figure out number of fields (included inherited).
		int fieldsSize = (instanceType.getNumFields()) * 4; // memory is 4-byte
															// words.
		fieldsSize += 4; // extra place for virtual table.
		// extra register for allocation result.
		String regName = "R" + (++regCount);

		// call memory allocation .
		String str = "Library __allocateObject(" + fieldsSize + ")," + regName;
		str += "\n";

		// store reference to class's virtual table, in 0th offset.
		// (regardless if object has virtual methods)
		str += "MoveField _DV_" + instance._class_id + "," + regName + ".0";
		str += "\n";

		// write output
		output.append(str);

		// return updated count.
		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, regName,
				regCount);
	}

	private static class PrepareArgumentsResults {
		int regCount;
		String output;

		public PrepareArgumentsResults(int regCount, String output) {
			super();
			this.regCount = regCount;
			this.output = output;
		}

	}

	/**
	 * helper function to create arguments for call.
	 * 
	 * @param call
	 * @param m
	 * @param regCount
	 * @param bIncludeParamName
	 *            - will be used for library calls . (no names printed)
	 * @return
	 * @throws SemanticException
	 */
	private PrepareArgumentsResults PrepareCallArguments(Call call,
			MethodBase m, Integer regCount, boolean bIncludeParamName)
			throws SemanticException {

		String str = "";
		// prepare pairs of argument-value.
		List<Formal> formals = m.frmls.formals;

		int i = 0;
		int lastCount = regCount;
		for (Formal f : formals) {
			// comma starting from argument 1.
			if (i > 0) {
				str += ",";
			}
			;

			// write param name (if needed)
			if (bIncludeParamName) {
				str += f.frmName.name;
				str += "=";
			}

			// compute the value
			List<Expr> argsExprList = call._arguments;
			LIRResult argVal = argsExprList.get(i).accept(this, lastCount);

			// append the place where the value is stored.
			str += argVal.get_regName();

			// update for next iteration
			lastCount = argVal.get_regCount();
			i++;
		}

		return new PrepareArgumentsResults(lastCount, str);
	}

	/**
	 * will generate library calls.
	 * 
	 * @param cl
	 * @param regCount
	 * @return
	 */
	private LIRResult visitLibraryCall(CallStatic cl, Integer regCount)
			throws SemanticException {
		CallStatic call = (CallStatic) cl;

		// get the static method with this name - validity checked in
		// typechecker.
		MethodBase m = typeTable.getMethod(call._classId, call._methodId);

		// prepare library call format.
		String str = "Library __" + call._methodId + "(";

		// prepare arguments
		PrepareArgumentsResults res = PrepareCallArguments(call, m, regCount,
				false);

		// append arguments
		str += res.output;

		// make a register for result.
		int lastCount = res.regCount;
		lastCount++;
		String outReg = "R" + lastCount;
		str += ")," + outReg + "\n";

		// finally output
		output.append(str);

		return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, outReg,
				lastCount);
	}

	public LIRResult visit(Call cl, Integer regCount) throws SemanticException {

		if (cl instanceof CallStatic) {
			CallStatic call = (CallStatic) cl;

			// library calls- handle separately.
			if (call._classId.equals("Library")) {
				return visitLibraryCall(call, regCount);
			}

			// not library - but static call.

			// get the static method with this name - validity checked in
			// typechecker.
			MethodBase m = typeTable.getMethod(call._classId, call._methodId);

			// prepare function name.
			String str = "StaticCall " + "_" + call._classId + "_"
					+ call._methodId + "(";

			// prepare arguments
			PrepareArgumentsResults res = PrepareCallArguments(call, m,
					regCount, true);

			// append arguments
			str += res.output;

			// make a register for result.
			int lastCount = res.regCount;
			lastCount++;
			String outReg = "R" + lastCount;
			str += ")," + outReg + "\n";

			// finally output
			output.append(str);

			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, outReg,
					lastCount);
		}

		else if (cl instanceof CallVirtual) {

			CallVirtual call = (CallVirtual) cl;
			// System.out.println("Call to virtual method: " + call._methodId);
			MethodBase m = null;
			Type instanceType = null;
			LIRResult instanceReg = null;

			// instance (external) call.
			// i.e. VirtualCall R1.1(x=R2),Rdummy
			if (call._instanceExpr != null) {
				// resolve register where this instance is.
				instanceReg = call._instanceExpr.accept(this, regCount);
				regCount = instanceReg.get_regCount();
				output.append("#Library __checkNullRef("
						+ instanceReg.get_regName() + ")\n");

				// resolve method and type of instance.
				instanceType = call._instanceExpr._type;
				m = typeTable.getMethod(instanceType._typeName, call._methodId);

			} else {
				// this is not an instance call.
				// caller is "this".
				// put 'this' in new register.
				String regName = "R" + (++regCount);
				output.append("Move this," + regName + "\n");
				instanceReg = new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
						regName, regCount);

				// get type and method details.
				instanceType = typeTable.getType(_currentClassName);
				m = typeTable.getMethod(_currentClassName, call._methodId);
			}

			// get the offset of method in virtual table.
			int offset = instanceType.getIRVirtualMethodOffset(m.getName(),
					this.typeTable);

			// prepare function call.
			String str = "VirtualCall " + instanceReg.get_regName() + "."
					+ offset + "(";

			// prepare arguments.
			PrepareArgumentsResults res = PrepareCallArguments(call, m,
					regCount, true);

			// append arguments
			str += res.output;

			// make a register for result.
			int lastCount = res.regCount;
			lastCount++;
			String outReg = "R" + lastCount;
			str += ")," + outReg + "\n";

			// append output
			output.append(str);
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, outReg,
					lastCount);

		}

		return null;

	}

	public LIRResult visit(Location loc, Integer regCount)
			throws SemanticException {
		// location expressions.
		// will throw on access to location before it is initialized.

		if (loc instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) loc);
			// System.out.println("Reference to array");

			LIRResult arr = e._exprArr.accept(this, regCount);

			LIRResult sub = e._exprSub.accept(this, arr.get_regCount());

			return new LIRResult(RegisterType.REGTYPE_TEMP_ARRAYSUB,
					arr.get_regName() + "[" + sub.get_regName() + "]",
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
			int offset = l.expr._type.getIRFieldOffset(l.member);

			String fullFieldName = instance.get_regName() + "." + offset;

			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE,
					fullFieldName, instance.get_regCount() + 1);

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
			return new LIRResult(RegisterType.REGTYPE_TEMP_SIMPLE, tempName,
					regCount);

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
	public LIRResult visit(Method method, Integer scope)
			throws SemanticException {

		_currentMethod = method;

		// there is always one main in program - special naming for IR.
		String methodIRName;
		if (method.returnVar.frmName.name.equals("main")) {
			methodIRName = "_ic_main";
		} else {
			methodIRName = "_" + _currentClassName + "_"
					+ method.returnVar.frmName.name;
		}

		// print method name.
		output.append("\n" + methodIRName + ": \n");

		if (!symbolTable.addVariable(scope, new VMethod(
				method.returnVar.frmName.name, scope, method.returnVar.type))) {
		}

		for (Formal f : method.frmls.formals) {
			// System.out.println(f.type);
			if (!symbolTable.addVariable(scope + 1, new VVariable(
					f.frmName.name, scope + 1, f.type, true))) {
			}
		}

		// go into method body
		method.stmt_list.accept(this, scope);
		if (method.returnVar.type._typeName.equals("void")
				&& !method.returnVar.frmName.name.equals("main")) {
			output.append("Return 9999\n");
		}
		if (method.returnVar.frmName.name.equals("main")) {
			output.append("Library __exit(0),Rdummy");
		}
		// scope's variables will be deleted in the end of stmtlist!

		return null;

	}

	@Override
	public LIRResult visit(Type type, Integer scope) {
		return null;
	}

	@Override
	public LIRResult visit(TypeArray array, Integer context) {
		return null;
	}

	@Override
	public LIRResult visit(VarExpr varExpr, Integer context) {
		return null;
	}

}
