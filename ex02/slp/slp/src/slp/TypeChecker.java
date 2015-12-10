package slp;

/**
 * Pretty-prints an SLP AST.
 */
public class TypeChecker implements PropagatingVisitor<Integer, Type> {

	protected final ASTNode root;

	SymbolTableImpl symbolTable = new SymbolTableImpl();

	TypeTableImpl typeTable = new TypeTableImpl();

	private boolean _checkInitialized = true;

	// holds the depth while traversing the tree

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 * @throws SemanticException
	 */
	public TypeChecker(ASTNode root) throws SemanticException {
		this.root = root;
		System.out.println("\nstarted dfs");
		System.out.println("if null then semantic check OK: " + root.accept(this, 0));

	}

	@Override
	public Type visit(Program program, Integer scope) throws SemanticException {

		for (Class c : program.classList) {
			c.accept(this, scope);

		}
		return null;

	}

	@Override
	public Type visit(Class class1, Integer scope) throws SemanticException {

		if (class1._extends != null) {
			System.out.println("Declaration of class:" + class1._className + " Extends" + class1._extends);
		} else {
			System.out.println("Declaration of class: " + class1._className);
		}

		// visit all components of the class.
		class1.fieldMethodList.accept(this, scope + 1);

		// class declaration was complete. need to add it to the type table.

		// build a type from the class given.
		Type t = new Type(class1);
		typeTable.addType(class1._className, t);

		return null;
	}

	@Override
	public Type visit(Field field, Integer scope) throws SemanticException {
		// declaration of a field
		for (VarExpr v : field.idList) {
			System.out.println("Declaration of field: ");
			v.accept(this, scope);
			System.out.println(field.type == null);
			if (!symbolTable.addVariable(scope, new VVariable(v.name, scope, field.type, false))) {
				throw (new SemanticException("Error: duplicate variable name at line " + field.line));
			}

		}

		// print type.
		field.type.accept(this, scope);
		return null;

	}

	@Override
	public Type visit(FieldMethodList fieldMethodList, Integer scope) throws SemanticException {

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
	public Type visit(FormalsList formalsList, Integer scope) throws SemanticException {

		for (Formal f : formalsList.formals) {
			System.out.println("Parameter: " + f.frmName);
			f.type.accept(this, scope);

		}
		return null;
	}

	@Override
	public Type visit(Formal formal, Integer scope) throws SemanticException {

		// print parameter name
		if (formal.frmName != null) {
			formal.frmName.accept(this, scope);
		}

		// print its type
		formal.type.accept(this, scope);

		if (!symbolTable.addVariable(scope, new VVariable(formal.frmName.name, scope, formal.type, true))) {
			throw new SemanticException("Error: duplicate variable name at line " + formal.line);
		}
		return null;

	}

	// general statement
	@Override
	public Type visit(Stmt stmt, Integer scope) throws SemanticException {
		// System.out.println("stmt visit");

		// Assign statement
		if (stmt instanceof AssignStmt) {
			AssignStmt s = (AssignStmt) stmt;
			System.out.println("Assignment statement");

			// go into 1st location, doesn't need be initialized.
			_checkInitialized = false;
			Type t1 = s._assignTo.accept(this, scope);
			System.out.println("t1 finished");

			// continue, remember to check initialized values.
			_checkInitialized = true;
			Type t2 = s._assignValue.accept(this, scope);
			if (t2 == null) {
				System.out.println("t2 finished");
			}
			if (s._assignValue instanceof LocationId && !((VVariable) symbolTable.getVariable(scope,
					((LocationId) s._assignValue).name)).isInitialized) {
				throw new SemanticException("Trying to assign uninitialized value of "
						+ ((VVariable) symbolTable.getVariable(scope, ((LocationId) s._assignValue).name)) + "in line: "
						+ stmt.line);
			} else if (s._assignValue instanceof NewArray) {
				((VArray) symbolTable.getVariable(scope, ((LocationId) s._assignTo).name)).isInitialized = true;
			}
			if (t1.equals(t2)) {
				System.out.println("equals");
				return null;
			} else {
				throw new SemanticException("Assign type error at line " + stmt.line);
			}
		}

		else if (stmt instanceof CallStatement) {
			System.out.println("Method call statement");
			((CallStatement) stmt)._call.accept(this, scope);
		}

		else if (stmt instanceof StmtIf) {
			System.out.println("If statement");
			StmtIf s = (StmtIf) stmt;

			// print condition
			Type cond = s._condition.accept(this, scope);

			// check that condition is of type boolean.
			if (cond == null || !cond.isPrimitive || !cond._typeName.equals("boolean")) {
				throw new SemanticException("Error: 'if' condition is not of type boolean. " + "at line " + s.line);
			}

			// print commands
			if (s._commands instanceof StmtList) {
				System.out.println("Block of statements");
			}

			s._commands.accept(this, scope);
			if (s._commandsElse != null) {
				System.out.println("Else statement");
				s._commandsElse.accept(this, scope);
			}

		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			System.out.println("While statement");
			s._condition.accept(this, scope);
			if (s._commands instanceof StmtList) {

				System.out.println("Block of statements");
			}
			Type t = s._commands.accept(this, scope);
			if (t == null) {
				return t;
			} else if (t._typeName.equals("BREAK") || t._typeName.equals("CONTINUE")) {
				return null;
			}
			return t;
		}

		// break statement
		else if (stmt instanceof StmtBreak)

		{
			System.out.println("Break statement");
			return new Type(stmt.line, "BREAK");

		} else if (stmt instanceof StmtContinue)

		{
			System.out.println("Continue statement");
			return new Type(stmt.line, "CONTINUE");
		}

		else if (stmt instanceof StmtList)

		{

			StmtList sl = (StmtList) stmt;
			Type temp, r = null;
			for (Stmt s : sl.statements) {
				temp = s.accept(this, scope + 1);
				if (temp != null) {
					if (temp._typeName.equals("BREAK") || temp._typeName.equals("CONTINUE")) {
						r = temp;
					}
				}
			}
			System.out.println(r == null);
			return r;
		} else if (stmt instanceof ReturnExprStatement) {

			System.out.println("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;
			returnExp.accept(this, scope);
		} else if (stmt instanceof ReturnVoidStatement) {
			System.out.println("Return statement (void value).");
		} else if (stmt instanceof StmtDeclareVar) {
			StmtDeclareVar s = (StmtDeclareVar) stmt;
			boolean isValue = (s._value != null);
			System.out.println("Declaration of local variable: " + s._id);
			// print value if exists
			if (isValue) {
				System.out.println(", with initial value");
			}
			if (!symbolTable.addVariable(scope, new VVariable(s._id, scope, s._type, isValue))) {
				throw new SemanticException("Error: duplicate variable name at line " + s.line);
			}
			// print the type
			s._type.accept(this, scope);
			// print value if exists
			if (isValue) {
				s._value.accept(this, scope);
			}
		} else {
			throw new UnsupportedOperationException("Unexpected visit of Stmt  abstract class");
		}
		return null;

	}

	public Type visit(Expr expr, Integer scope) throws SemanticException {

		if (expr instanceof BinaryOpExpr) {
			Type t1, t2;

			BinaryOpExpr e = ((BinaryOpExpr) expr);
			t1 = visit(e.lhs, scope);
			t2 = visit(e.rhs, scope);
			System.out.println(t1._typeName);
			System.out.println(t2._typeName);

			// type checks and inference.

			System.out.println(t1._typeName);
			System.out.println(t2._typeName);

			// infer and return the type from the 2 types and operator.
			// may throw exceptions on inappropriate types.
			return Type.TypeInferBinary(t1, t2, e.op, this.typeTable);

		}

		// call
		else if (expr instanceof Call) {

			if (expr instanceof CallStatic) {
				CallStatic call = (CallStatic) expr;
				System.out.println("Call to static method: " + call._methodId + ", in class: " + call._classId);

				for (Expr f : call._arguments) {
					f.accept(this, scope);
				}
			} else if (expr instanceof CallVirtual) {

				CallVirtual call = (CallVirtual) expr;
				System.out.println("Call to virtual method: " + call._methodId);

				// write "external scope" if this is an instance call.
				if (call._instanceExpr != null) {
					System.out.println(", in external scope");
					call._instanceExpr.accept(this, scope);
				}

				// visit parameters
				for (Expr f : call._arguments) {
					f.accept(this, scope);
				}
			}
		}

		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			System.out.println("Reference to array length");
			e._expr.accept(this, scope);

			// array length is considered as int.
			return new Type(e.line, "int");
		}

		// Literals
		else if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			System.out.println("Boolean literal: " + e.value);
			return new Type(e.line, "boolean");
		} else if (expr instanceof LiteralNull) {
			System.out.println("Null literal");
			return new Type(expr.line, "null");
		} else if (expr instanceof LiteralNumber) {
			LiteralNumber e = ((LiteralNumber) expr);
			System.out.println("Integer literal: " + e.value);
			return new Type(e.line, "int");
		} else if (expr instanceof LiteralString) {
			LiteralString e = (LiteralString) expr;
			System.out.println("String literal: " + e.value);
			return new Type(e.line, "string");
		}

		else if (expr instanceof Location) {
			return visit((Location) expr, scope);
		}

		else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			System.out.println(e.op.humanString());

			// continue evaluating.
			Type t1 = e.operand.accept(this, scope);

			// infer type
			return Type.TypeInferUnary(t1, e.op);
		}

		else if (expr instanceof NewClassInstance) {
			System.out.println("Instantiation of class: ");
			NewClassInstance instance = (NewClassInstance) expr;
			System.out.println(instance._class_id);
		} else if (expr instanceof NewArray) {
			System.out.println("Array allocation");

			NewArray newArr = (NewArray) expr;

			Type size = newArr._arrSizeExpr.accept(this, scope);
			if (size == null) {
				throw new SemanticException(expr.line + ": Illegal size for array");
			} else if (!size._typeName.equals("int")) {
				throw new SemanticException(expr.line + ": Subscript of array isn't an int");
			}

			// print array type
			return newArr._type.accept(this, scope);
			// print array size expression

		} else {
			throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
		}
		return null;
	}

	public Type visit(Location loc, Integer scope) throws SemanticException {
		// location expressions.
		// will throw on access to location before it is initialized.
		if (loc instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) loc);
			System.out.println("Reference to array");
			// validate the reference to array will be checked for
			// initialization.
			_checkInitialized = true;
			Type arr = e._exprArr.accept(this, scope);

			// validate subscript expression will be checked for initialization.
			_checkInitialized = true;
			Type sub = e._exprSub.accept(this, scope);
			if (sub == null) {
				System.out.println("sub=null");
			}
			if (arr == null) {
				throw new SemanticException(e.line + ": Incorrect access to array");
			} else if (!sub._typeName.equals("int")) {
				throw new SemanticException(e.line + ": Illegal subscript access to array");
			}

			return arr;

		}

		else if (loc instanceof LocationExpressionMember) {
			// access to instance member.
			LocationExpressionMember e = (LocationExpressionMember) loc;
			System.out.println("Reference to variable: " + e.member);
			System.out.println(", in external scope");

			// we need to check that reference was initialized.
			// (the member was already init by default.)
			_checkInitialized = true;
			return e.expr.accept(this, scope);
		}

		else if (loc instanceof LocationId) {
			LocationId e = (LocationId) loc;
			if (!symbolTable.checkAvailable(scope, e.name)) {
				throw new SemanticException("Error at line " + e.line + ": Undefined variable");
			}

			// check initialization if needed.
			if (_checkInitialized && !symbolTable.checkInitialized(scope, e.name)) {
				throw new SemanticException(
						"Error at line " + e.line + ": variable used before initialized: " + e.name);
			}
			System.out.println("Reference to variable: " + e.name);
			return symbolTable.getVariableType(scope, e.name);

		}

		return null;
	}

	@Override
	public void visit(TypeArray array) {
		System.out.println("Primitive data type: 1-dimensional array of " + array._typeName);

	}

	@Override
	public Type visit(Method method, Integer scope) throws SemanticException {

		if (method.isStatic) {
			System.out.println("Declaration of static method: ");
		} else {
			System.out.println("Declaration of virtual method: ");
		}
		if (!symbolTable.addVariable(scope, new VMethod(method.f.frmName.name, scope, method.f.type))) {
			throw new SemanticException("Error: duplicate variable name at line " + method.line);
		}
		// print return type
		// method.f.accept(this, scope);

		Type t = method.stmt_list.accept(this, scope);
		if (t == null) {
			System.out.println("method finish");
			return null;
		} else if (t._typeName.equals("BREAK")) {
			throw new SemanticException("Error: break without while, at line: " + t.line);
		} else if (t._typeName.equals("CONTINUE")) {
			throw new SemanticException("Error: continue without while at line: " + t.line);
		}

		return null;

	}

	@Override
	public Type visit(Type type, Integer scope) {
		if (type.isPrimitive) {
			System.out.println("Primitive data type: " + type._typeName);
		} else {
			System.out.println("User-defined data type: " + type._typeName);
		}
		return null;
	}

	@Override
	public Type visit(TypeArray array, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(VarExpr varExpr, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

}
