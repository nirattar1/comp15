package slp;

/**
 * Pretty-prints an SLP AST.
 */
public class TypeChecker implements PropagatingVisitor<Integer, Type> {
	protected final ASTNode root;

	SymbolTableImpl symbolTable = new SymbolTableImpl();
	// holds the depth while traversing the tree
	private int depth = 0;

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 */
	public TypeChecker(ASTNode root) {
		this.root = root;
		System.out.println("\nstarted dfs");
		System.out.println(root.accept(this, 0)._typeName);

	}

	public Type visit(Expr expr, Integer scope) {
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
				System.out.print("Call to static method: " + call._methodId + ", in class: " + call._classId);

				depth += 2;
				for (Expr f : call._arguments) {
					f.accept(this, scope);
				}
				depth -= 2;
			} else if (expr instanceof CallVirtual) {

				CallVirtual call = (CallVirtual) expr;
				System.out.print("Call to virtual method: " + call._methodId);

				// write "external scope" if this is an instance call.
				if (call._instanceExpr != null) {
					System.out.print(", in external scope");
					depth += 2;
					call._instanceExpr.accept(this, scope);
					depth -= 2;
				}

				depth += 2;
				// visit parameters
				for (Expr f : call._arguments) {
					f.accept(this, scope);
				}
				depth -= 2;
			}
		}

		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			System.out.print("Reference to array length");
			depth += 2;
			e._expr.accept(this, scope);
			depth -= 2;
		} else if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			System.out.print("Boolean literal: " + e.value);
			return new Type(e.line, "boolean");
		} else if (expr instanceof LiteralNull) {
			System.out.print("Null literal");
			return new Type(expr.line, "null");
		} else if (expr instanceof LiteralNumber) {
			LiteralNumber e = ((LiteralNumber) expr);
			System.out.print("Integer literal: " + e.value);
			return new Type(e.line, "int");
		} else if (expr instanceof LiteralString) {
			LiteralString e = (LiteralString) expr;
			System.out.print("String literal: " + e.value);
			return new Type(e.line, "String");
		} else if (expr instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) expr);
			System.out.print("Reference to array");
			depth += 2;
			e._exprArr.accept(this, scope);
			e._exprSub.accept(this, scope);
			depth -= 2;

		} else if (expr instanceof LocationExpressionMember) {
			LocationExpressionMember e = (LocationExpressionMember) expr;
			System.out.print("Reference to variable: " + e.member);
			System.out.print(", in external scope");
			depth += 2;
			e.expr.accept(this, scope);
			depth -= 2;
		} else if (expr instanceof LocationId) {
			LocationId e = (LocationId) expr;
			if (!symbolTable.checkAvailable(scope, e.name)){
				System.out.println("Error at line " + e.line + ": Undefined variable");
				System.exit(0);
			}
			System.out.print("Reference to variable: " + e.name);
			return symbolTable.getVariableType(scope, e.name);
			
		} else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			System.out.print(e.op.humanString());
			depth += 2;
			e.operand.accept(this, scope);
			depth -= 2;
		} else if (expr instanceof NewClassInstance) {
			System.out.print("Instantiation of class: ");
			NewClassInstance instance = (NewClassInstance) expr;
			System.out.print(instance._class_id);
		} else if (expr instanceof NewArray) {
			System.out.print("Array allocation");
			NewArray newArr = (NewArray) expr;

			depth += 2;
			// print array type
			newArr._type.accept(this, scope);
			// print array size expression
			newArr._arrSizeExpr.accept(this, scope);
			depth -= 2;
		} else {
			throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
		}
		return null;
	}

	@Override
	public Type visit(FieldMethodList fieldMethodList, Integer scope) {

		FieldMethod fm;
		for (int i = fieldMethodList.fieldsmethods.size() - 1; i >= 0; i--) {
			depth += 2;
			fm = fieldMethodList.fieldsmethods.get(i);
			if (fm instanceof Field) {
				((Field) fm).accept(this, scope);
			}
			if (fm instanceof Method) {
				((Method) fm).accept(this, scope);
			}
			depth -= 2;
		}
		return null;
	}

	@Override
	public void visit(FormalsList formalsList) {

		for (Formal f : formalsList.formals) {
			indent(formalsList);
			System.out.print("Parameter: " + f.frmName);
			depth += 2;
			f.type.accept(this, scope);
			depth -= 2;

		}

	}

	@Override
	public Type visit(Formal formal, Integer scope) {

		// print parameter name
		if (formal.frmName != null) {
			formal.frmName.accept(this, scope);
		}

		// print its type
		formal.type.accept(this, scope);

		if (!symbolTable.addVariable(scope, new VVariable(formal.frmName.name, scope, formal.type))) {
			System.out.println("Error: duplicate variable name at line " + formal.line);
			System.exit(0);
		}
		return null;

	}

	@Override
	public void visit(TypeArray array) {
		System.out.print("Primitive data type: 1-dimensional array of " + array._typeName);

	}

	@Override
	public Type visit(Method method, Integer scope) {

		if (method.isStatic) {
			System.out.print("Declaration of static method: ");
		} else {
			System.out.print("Declaration of virtual method: ");
		}
		if (!symbolTable.addVariable(scope, new VMethod(method.f.frmName.name, scope, method.f.type))) {
			System.out.println("Error: duplicate variable name at line " + method.line);
			System.exit(0);
		}
		// print return type

		// method.f.accept(this, scope);

		// depth += 2;

		// method.frmls.accept(this, scope);

		method.stmt_list.accept(this, scope );
		// depth -= 2;
		return null;

	}

	@Override
	public Type visit(Field field, Integer scope) {

		// print field names.
		for (VarExpr v : field.idList) {
			System.out.print("Declaration of field: ");
			v.accept(this, scope);
			System.out.println(field.type == null);
			if (!symbolTable.addVariable(scope, new VVariable(v.name, scope, field.type))) {
				System.out.println("Error: duplicate variable name at line " + field.line);
				System.exit(0);
			}

		}

		// print type.
		field.type.accept(this, scope);
		return null;

	}

	@Override
	public Type visit(Class class1, Integer scope) {

		if (class1._extends != null) {
			System.out.print("Declaration of class:" + class1._className + " Extends" + class1._extends);
		} else {
			System.out.print("Declaration of class: " + class1._className);
		}

		class1.fieldMethodList.accept(this, scope+1);
		return null;
	}

	@Override
	public Type visit(Program program, Integer scope) {

		for (Class c : program.classList) {
			c.accept(this, scope);

		}
		return null;

	}

	@Override
	public Type visit(Type type, Integer scope) {
		if (type.isPrimitive) {
			System.out.print("Primitive data type: " + type._typeName);
		} else {
			System.out.print("User-defined data type: " + type._typeName);
		}
		return null;
	}

	// assign statement
	public Type visit(AssignStmt stmt, Integer scope) {
		System.out.print("Assignment statement");
		Type t1 = stmt._assignTo.accept(this, scope);
		
		Type t2 = stmt._assignValue.accept(this, scope);
		if (t2==null){
		System.out.println("t2 finished");}
		if (t1.equals(t2)){
			System.out.println("equals");
			return null;
		} else {
			System.out.println("Assign type error at line " + stmt.line);
			System.exit(0);
		}
		return null;
	}

	// general statement
	@Override
	public Type visit(Stmt stmt, Integer scope) {
		System.out.println("stmt visit");
		// call statement
		if (stmt instanceof CallStatement) {
			System.out.print("Method call statement");
			depth += 2;
			((CallStatement) stmt)._call.accept(this, scope);
			depth -= 2;
		}
		// break statement
		else if (stmt instanceof StmtBreak) {
			System.out.print("Break statement");
		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			System.out.print("While statement");
			depth += 2;
			s._condition.accept(this, scope);
			if (s._commands instanceof StmtList) {

				System.out.print("Block of statements");
			}
			s._commands.accept(this, scope);
			depth -= 2;
		} else if (stmt instanceof StmtContinue) {
			System.out.print("Continue statement");
		} else if (stmt instanceof StmtDeclareVar) {
			StmtDeclareVar s = (StmtDeclareVar) stmt;
			boolean isValue = (s._value != null);
			System.out.print("Declaration of local variable: " + s._id);
			// print value if exists
			if (isValue) {
				System.out.print(", with initial value");
			}
			if (!symbolTable.addVariable(scope, new VVariable(s._id, scope, s._type))) {
				System.out.println("Error: duplicate variable name at line " + s.line);
				System.exit(0);
			}
			depth += 2;
			// print the type
			s._type.accept(this, scope);

			// print value if exists
			if (isValue) {
				s._value.accept(this, scope);
			}
			depth -= 2;

		}

		else if (stmt instanceof StmtIf) {
			System.out.print("If statement");
			StmtIf s = (StmtIf) stmt;
			depth += 2;
			// print condition
			s._condition.accept(this, scope);
			// print commands
			if (s._commands instanceof StmtList) {

				System.out.print("Block of statements");
			}
			s._commands.accept(this, scope);
			if (s._commandsElse != null) {
				System.out.println("Else statement");
				s._commandsElse.accept(this, scope);
			}
			depth -= 2;
		}

		else if (stmt instanceof StmtList) {

			StmtList sl = (StmtList) stmt;
			for (Stmt s : sl.statements) {
				s.accept(this, scope + 1);
			}
			return null;

		} else if (stmt instanceof ReturnExprStatement) {

			System.out.print("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;
			depth += 2;
			returnExp.accept(this, scope);
			depth -= 2;
		} else if (stmt instanceof ReturnVoidStatement) {
			System.out.print("Return statement (void value).");
		} else {
			throw new UnsupportedOperationException("Unexpected visit of Stmt  abstract class");
		}
		return null;
	}

	@Override
	public Type visit(VarExpr varExpr, Integer scope) {

		System.out.print(varExpr.name);
		return null;

	}

	@Override
	public Type visit(StmtList stmts, Integer d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(FormalsList mthds, Integer d) {
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
