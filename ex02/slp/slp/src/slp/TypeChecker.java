package slp;

/**
 * Pretty-prints an SLP AST.
 */
public class TypeChecker implements PropagatingVisitor<Integer, Type> {
	protected final ASTNode root;

	SymbolTableImpl symbolTable = new SymbolTableImpl();
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

		class1.fieldMethodList.accept(this, scope + 1);
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

		// call statement
		if (stmt instanceof StmtDeclareVar) {
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

		} else if (stmt instanceof CallStatement) {
			System.out.println("Method call statement");
			((CallStatement) stmt)._call.accept(this, scope);
		} else if (stmt instanceof StmtIf) {
			System.out.println("If statement");
			StmtIf s = (StmtIf) stmt;
			// print condition
			s._condition.accept(this, scope);
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
			if (t==null){
				return t;
			}
			else if (t._typeName.equals("BREAK") || t._typeName.equals("CONTINUE")) {
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
			System.out.println(r==null);
			return r;
		} else if (stmt instanceof ReturnExprStatement) {

			System.out.println("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;
			returnExp.accept(this, scope);
		} else if (stmt instanceof ReturnVoidStatement)

		{
			System.out.println("Return statement (void value).");
		} else

		{
			throw new UnsupportedOperationException("Unexpected visit of Stmt  abstract class");
		}
		return null;

	}

	public Type visit(Expr expr, Integer scope) throws SemanticException {
		Type t1, t2;
		if (expr instanceof BinaryOpExpr) {
			BinaryOpExpr e = ((BinaryOpExpr) expr);
			t1 = visit(e.lhs, scope);
			t2 = visit(e.rhs, scope);
			System.out.println(t1._typeName);
			System.out.println(t2._typeName);
			if (t1 == t2) {
				System.out.println(t1._typeName);
				System.out.println(t2._typeName);
				return t1;
			} else {
				System.out.println("Error in line " + e.line + ": Illegal type casting");
			}
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
		} else if (expr instanceof LiteralBoolean) {
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
			return new Type(e.line, "String");
		} else if (expr instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) expr);
			System.out.println("Reference to array");
			e._exprArr.accept(this, scope);
			e._exprSub.accept(this, scope);

		} else if (expr instanceof LocationExpressionMember) {
			LocationExpressionMember e = (LocationExpressionMember) expr;
			System.out.println("Reference to variable: " + e.member);
			System.out.println(", in external scope");
			e.expr.accept(this, scope);
		} else if (expr instanceof LocationId) {
			LocationId e = (LocationId) expr;
			if (!symbolTable.checkAvailable(scope, e.name)) {
				System.out.println("Error at line " + e.line + ": Undefined variable");
				System.exit(0);
			}
			System.out.println("Reference to variable: " + e.name);
			return symbolTable.getVariableType(scope, e.name);

		} else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			System.out.println(e.op.humanString());
			e.operand.accept(this, scope);
		} else if (expr instanceof NewClassInstance) {
			System.out.println("Instantiation of class: ");
			NewClassInstance instance = (NewClassInstance) expr;
			System.out.println(instance._class_id);
		} else if (expr instanceof NewArray) {
			System.out.println("Array allocation");
			NewArray newArr = (NewArray) expr;

			// print array type
			newArr._type.accept(this, scope);
			// print array size expression
			newArr._arrSizeExpr.accept(this, scope);
		} else {
			throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
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

	// assign statement
	public Type visit(AssignStmt stmt, Integer scope) throws SemanticException {
		System.out.println("Assignment statement");
		Type t1 = stmt._assignTo.accept(this, scope);
		System.out.println("t1 finished");
		Type t2 = stmt._assignValue.accept(this, scope);
		if (t2 == null) {
			System.out.println("t2 finished");
		}
		if (stmt._assignValue instanceof LocationId &&
				!((VVariable)symbolTable.getVariable(scope,
						((LocationId)stmt._assignValue).name)).isInitialized){
			throw new SemanticException("Trying to assign uninitialized value of " +
					((VVariable)symbolTable.getVariable(scope,
							((LocationId)stmt._assignValue).name))+ "in line: " + stmt.line);
				}
		if (t1.equals(t2)) {
			System.out.println("equals");
			return null;
		} else {
			throw new SemanticException("Assign type error at line " + stmt.line);
		}
	}

	@Override
	public Type visit(VarExpr varExpr, Integer scope) {

		System.out.println(varExpr.name);
		return null;

	}

	@Override
	public Type visit(StmtList stmts, Integer d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(LiteralNumber expr, Integer d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(UnaryOpExpr expr, Integer d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(BinaryOpExpr expr, Integer d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(TypeArray array, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

}
