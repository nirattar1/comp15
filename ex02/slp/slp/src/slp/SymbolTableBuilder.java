package slp;

/**
 * Pretty-prints an SLP AST.
 */
public class SymbolTableBuilder implements PropagatingVisitor<Integer, Void> {

	protected final ASTNode root;

	private SymbolTableImpl symbolTable = new SymbolTableImpl();

	public TypeTableImpl typeTable = new TypeTableImpl();

	private boolean _checkInitialized = true;

	// holds the depth while traversing the tree

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 * @throws SemanticException
	 */
	public SymbolTableBuilder(ASTNode root) throws SemanticException {
			
		this.root = root;
		System.out.println("\nstarted dfs - SymbolTableBuilder");
		root.accept(this, 0);
		System.out.println("Finished SymbolTableBuilder");
		SymbolTableImpl.debugs.append(typeTable.toString() + "\n" + symbolTable.toString());
		SymbolTableImpl.printToDebugFile();
		symbolTable.checkNoOrphans(typeTable);
	}

	@Override
	public Void visit(Program program, Integer scope) throws SemanticException {

		for (Class c : program.classList) {
			c.accept(this, scope);

		}
		return null;

	}

	@Override
	public Void visit(Class class1, Integer scope) throws SemanticException {

		// class is derived
		if (class1._extends != null) {
			System.out.println("Declaration of class:" + class1._className + " Extends" + class1._extends);
			// check base class was already declared.
			if (!typeTable.checkExist(class1._extends)) {
				throw new SemanticException("cannot derive from undefined class " + class1._extends);
			}
		}
		// class is independent
		else {
			System.out.println("Declaration of class: " + class1._className);
		}

		// visit all components of the class.
		class1.fieldMethodList.accept(this, scope + 1);

		// class declaration was complete. need to add it to the type table.
		// build a type from the class given.
		Type t = new Type(class1);
		typeTable.addType(class1._className, t);

		// close the class scope.
		symbolTable.deleteScope(scope + 1);

		return null;
	}

	@Override
	public Void visit(Field field, Integer scope) throws SemanticException {
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
	public Void visit(FieldMethodList fieldMethodList, Integer scope) throws SemanticException {

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
	public Void visit(FormalsList formalsList, Integer scope) throws SemanticException {

		for (Formal f : formalsList.formals) {
			System.out.println("Parameter: " + f.frmName);
			f.type.accept(this, scope);

		}
		return null;
	}

	@Override
	public Void visit(Formal formal, Integer scope) throws SemanticException {

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
	public Void visit(Stmt stmt, Integer scope) throws SemanticException {
		// System.out.println("stmt visit");

		// Assign statement
		if (stmt instanceof AssignStmt) {
			AssignStmt s = (AssignStmt) stmt;
			System.out.println("Assignment statement");

			// go into 1st location, doesn't need be initialized.
			_checkInitialized = false;
			s._assignTo.accept(this, scope);
			System.out.println("t1 finished");

			// continue, remember to check initialized values.
			_checkInitialized = true;
			s._assignValue.accept(this, scope);

			// if (s._assignValue instanceof LocationId && !((VVariable)
			// symbolTable.getVariable(scope,
			// ((LocationId) s._assignValue).name)).isInitialized) {
			// throw new SemanticException("Trying to assign uninitialized value
			// of "
			// + ( (VVariable) symbolTable.getVariable(scope, ((LocationId)
			// s._assignValue).name) ).name
			// + " in line: "
			// + stmt.line);
			// }
			if (s._assignValue instanceof NewArray) {
				if (s._assignTo instanceof LocationId) {
					((VArray) symbolTable.getVariable(scope, ((LocationId) s._assignTo).name)).isInitialized = true;
				}
//				else if (s._assignTo instanceof LocationExpressionMember){
//					((VArray) typeTable.getFieldOfInstance(
//							((LocationExpressionMember)s._assignTo).expr, 
//							((LocationExpressionMember)s._assignTo).member)).isInitialized=true;
//				}

			} else if (s._assignValue instanceof NewClassInstance) {
				((VVariable) symbolTable.getVariable(scope, ((LocationId) s._assignTo).name)).isInitialized = true;
			}
			System.out.println("finished assignment stmt");
		}

		else if (stmt instanceof CallStatement) {
			System.out.println("Method call statement");
			((CallStatement) stmt)._call.accept(this, scope);
		}

		else if (stmt instanceof StmtIf) {
			System.out.println("If statement");
			StmtIf s = (StmtIf) stmt;

			// print condition
			s._condition.accept(this, scope);

			// print commands
			if (s._commands instanceof StmtList) {
				System.out.println("Block of statements");
			}

			// commands - may be a list of statements.
			s._commands.accept(this, scope);

			// visit 'else' part if exists.
			if (s._commandsElse != null) {
				System.out.println("Else statement");
				// commandsElse - may be a list of statements.
				s._commandsElse.accept(this, scope);
			}

		}

		else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			System.out.println("While statement");
			s._condition.accept(this, scope);
			if (s._commands instanceof StmtList) {
				System.out.println("Block of statements");
			}
			s._commands.accept(this, scope);
			return null;
		}

		// break statement
		else if (stmt instanceof StmtBreak)

		{
			System.out.println("Break statement");

		} else if (stmt instanceof StmtContinue)

		{
			System.out.println("Continue statement");
		}

		// code block (list of statements).
		else if (stmt instanceof StmtList) {
			System.out.println("stmtlist start");

			StmtList sl = (StmtList) stmt;

			// opening scope.
			for (Stmt s : sl.statements) {
				System.out.println(s.line);
				s.accept(this, scope + 1);
			}
			System.out.println("stmt list end");

			// closing scope.
			symbolTable.deleteScope(scope + 1);
		}

		else if (stmt instanceof ReturnExprStatement) {
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
			if (s._type instanceof TypeArray) {
				if (!symbolTable.addVariable(scope, new VArray(s._id, scope, s._type, isValue))) {
					throw new SemanticException("Error: duplicate array var name at line " + s.line);
				}
			} else {
				if (!symbolTable.addVariable(scope, new VVariable(s._id, scope, s._type, isValue))) {
					throw new SemanticException("Error: duplicate variable name at line " + s.line);
				}
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

	public Void visit(Expr expr, Integer scope) throws SemanticException {

		if (expr instanceof BinaryOpExpr) {

			BinaryOpExpr e = ((BinaryOpExpr) expr);
			visit(e.lhs, scope);
			visit(e.rhs, scope);

		}

		// call
		else if (expr instanceof Call) {
			// do nothing in this stage for calls.
		} else if (expr instanceof ExprThis) {
			ExprThis e = (ExprThis) expr;
			System.out.print("This identifier.");

		} else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			System.out.println("Reference to array length");
			e._expr.accept(this, scope);

		}

		// Literals
		else if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			System.out.println("Boolean literal: " + e.value);
		} else if (expr instanceof LiteralNull) {
			System.out.println("Null literal");
		} else if (expr instanceof LiteralNumber) {
			LiteralNumber e = ((LiteralNumber) expr);
			System.out.println("Integer literal: " + e.value);
		} else if (expr instanceof LiteralString) {
			LiteralString e = (LiteralString) expr;
			System.out.println("String literal: " + e.value);
		}

		else if (expr instanceof Location) {
			return visit((Location) expr, scope);
		}

		else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			System.out.println(e.op.humanString());

			// continue evaluating.
			e.operand.accept(this, scope);

		}

		else if (expr instanceof NewClassInstance) {
			System.out.println("Instantiation of class: ");
			NewClassInstance instance = (NewClassInstance) expr;
			System.out.println(instance._class_id);
		} else if (expr instanceof NewArray) {
			System.out.println("Array allocation");

			NewArray newArr = (NewArray) expr;

			newArr._arrSizeExpr.accept(this, scope);

		} else {
			throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
		}
		return null;
	}

	public Void visit(Location loc, Integer scope) throws SemanticException {
		// location expressions.
		// will throw on access to location before it is initialized.
		if (loc instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) loc);
			System.out.println("Reference to array");
			// validate the reference to array will be checked for
			// initialization.
			_checkInitialized = true;
			e._exprArr.accept(this, scope);

			// validate subscript expression will be checked for initialization.
			_checkInitialized = true;
			e._exprSub.accept(this, scope);

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

			// check that symbol exists in symbol table.
			// defer these checks (existence, initialization) to type checker.
			// (after types definition is complete).
			// the reason is that a field can be referred to inside the function
			// (here), while its declaration is only after the function decl.

			System.out.println("Reference to variable: " + e.name);

		}

		return null;
	}

	@Override
	public Void visit(TypeArray array) {
		System.out.println("Primitive data type: 1-dimensional array of " + array._typeName);
		return null;
	}

	@Override
	public Void visit(Method method, Integer scope) throws SemanticException {

		if (method.isStatic) {
			System.out.println("Declaration of static method: ");
		} else {
			System.out.println("Declaration of virtual method: ");
		}
		if (!symbolTable.addVariable(scope, new VMethod(method.returnVar.frmName.name, scope, method.returnVar.type))) {
			throw new SemanticException("Error: duplicate variable name at line " + method.line);
		}
		// print return type
		// method.f.accept(this, scope);

		for (Formal f : method.frmls.formals) {
			System.out.println(f.type);
			if (!symbolTable.addVariable(scope + 1, new VVariable(f.frmName.name, scope + 1, f.type, true))) {
				throw new SemanticException("Error: duplicate variable name at line " + method.line);
			}
		}
		method.stmt_list.accept(this, scope);
		// scope's variables will be deleted in the end of stmtlist!

		return null;

	}

	@Override
	public Void visit(Type type, Integer scope) {
		if (type.isPrimitive) {
			System.out.println("Primitive data type: " + type._typeName);
		} else {
			System.out.println("User-defined data type: " + type._typeName);
		}
		return null;
	}

	@Override
	public Void visit(TypeArray array, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(VarExpr varExpr, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

}
