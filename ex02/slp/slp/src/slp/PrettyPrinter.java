package slp;

/**
 * Pretty-prints an SLP AST.
 */
public class PrettyPrinter implements Visitor {
	protected final ASTNode root;

	// holds the depth while traversing the tree
	private int depth = 0;

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 */
	public PrettyPrinter(ASTNode root) {
		this.root = root;
	}

	private void indent(/* StringBuffer output, */ASTNode node) {
		// output.append("\n");
		// for (int i = 0; i < depth; ++i)
		// output.append(" ");
		// if (node != null)
		// output.append(node.getLine() + ": ");

		System.out.print("\n");
		for (int i = 0; i < depth; ++i)
			System.out.print(" ");
		if (node != null)
			System.out.print(node.getLine() + ": ");
	}

	/**
	 * Prints the AST with the given root.
	 * 
	 * @throws SemanticException
	 */
	public void print() throws SemanticException {
		root.accept(this);
	}

	public void visit(Expr expr) throws SemanticException {
		indent(expr);

		if (expr instanceof BinaryOpExpr) {
			BinaryOpExpr e = ((BinaryOpExpr) expr);
			System.out.print(e.op.humanString());
			depth += 2;
			e.lhs.accept(this);
			e.rhs.accept(this);
			depth -= 2;
		}

		// call
		else if (expr instanceof Call) {

			if (expr instanceof CallStatic) {
				CallStatic call = (CallStatic) expr;
				System.out.print("Call to static method: " + call._methodId + ", in class: " + call._classId);

				depth += 2;
				for (Expr f : call._arguments) {
					f.accept(this);
				}
				depth -= 2;
			} else if (expr instanceof CallVirtual) {

				CallVirtual call = (CallVirtual) expr;
				System.out.print("Call to virtual method: " + call._methodId);

				// write "external scope" if this is an instance call.
				if (call._instanceExpr != null) {
					System.out.print(", in external scope");
					depth += 2;
					call._instanceExpr.accept(this);
					depth -= 2;
				}

				depth += 2;
				// visit parameters
				for (Expr f : call._arguments) {
					f.accept(this);
				}
				depth -= 2;
			}
		}

		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			System.out.print("Reference to array length");
			depth += 2;
			e._expr.accept(this);
			depth -= 2;
		} else if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			System.out.print("Boolean literal: " + e.value);
		} else if (expr instanceof LiteralNull) {
			System.out.print("Null literal");
		} else if (expr instanceof LiteralNumber) {
			LiteralNumber e = ((LiteralNumber) expr);
			System.out.print("Integer literal: " + e.value);
		} else if (expr instanceof LiteralString) {
			LiteralString s = (LiteralString) expr;
			System.out.print("String literal: " + s.value);
		} else if (expr instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) expr);
			System.out.print("Reference to array");
			depth += 2;
			e._exprArr.accept(this);
			e._exprSub.accept(this);
			depth -= 2;

		} else if (expr instanceof LocationExpressionMember) {
			LocationExpressionMember e = (LocationExpressionMember) expr;
			System.out.print("Reference to variable: " + e.member);
			System.out.print(", in external scope");
			depth += 2;
			e.expr.accept(this);
			depth -= 2;
		} else if (expr instanceof LocationId) {
			LocationId e = (LocationId) expr;
			System.out.print("Reference to variable: " + e.name);
		} else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			System.out.print(e.op.humanString());
			depth += 2;
			e.operand.accept(this);
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
			newArr._type.accept(this);
			// print array size expression
			newArr._arrSizeExpr.accept(this);
			depth -= 2;
		} else {
			throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
		}
	}

	@Override
	public void visit(FieldMethodList fieldMethodList) throws SemanticException {

		FieldMethod fm;
		for (int i = fieldMethodList.fieldsmethods.size() - 1; i >= 0; i--) {
			depth += 2;
			fm = fieldMethodList.fieldsmethods.get(i);
			if (fm instanceof Field) {
				((Field) fm).accept(this);
			}
			if (fm instanceof Method) {
				((Method) fm).accept(this);
			}
			depth -= 2;
		}

	}

	@Override
	public void visit(FormalsList formalsList) throws SemanticException {

		for (Formal f : formalsList.formals) {
			indent(formalsList);
			System.out.print("Parameter: " + f.frmName);
			depth += 2;
			f.type.accept(this);
			depth -= 2;

		}

	}

	@Override
	public void visit(Formal formal) throws SemanticException {

		// print parameter name
		if (formal.frmName != null) {
			formal.frmName.accept(this);
			depth += 2; // indent
		}

		// print its type
		formal.type.accept(this);

		if (formal.frmName != null) {
			depth -= 2; // remove indent
		}

	}

	@Override
	public void visit(TypeArray array) {
		indent(array);
		System.out.print("Primitive data type: 1-dimensional array of " + array._typeName);

	}

	@Override
	public void visit(Method method) throws SemanticException {

		indent(method);

		if (method.isStatic) {
			System.out.print("Declaration of static method: ");
		} else {
			System.out.print("Declaration of virtual method: ");
		}

		// print return type
		method.f.accept(this);
		// depth += 2;
		method.frmls.accept(this);
		method.stmt_list.accept(this);
		// depth -= 2;

	}

	@Override
	public void visit(Field field) throws SemanticException {
		indent(field);

		// print field names.
		for (VarExpr v : field.idList) {
			System.out.print("Declaration of field: ");
			v.accept(this);
		}

		// print type.
		depth += 2;
		field.type.accept(this);
		depth -= 2;

	}

	@Override
	public void visit(Class class1) throws SemanticException {
		indent(class1);

		if (class1._extends != null) {
			System.out.print("Declaration of class:" + class1._className + " Extends" + class1._extends);
		} else {
			System.out.print("Declaration of class: " + class1._className);
		}

		depth += 2;
		class1.fieldMethodList.accept(this);
		depth -= 2;
	}

	@Override
	public void visit(Program program) throws SemanticException {

		for (Class c : program.classList) {
			c.accept(this);

		}

	}

	@Override
	public void visit(Type type) {
		indent(type);
		if (type.isPrimitive) {
			System.out.print("Primitive data type: " + type._typeName);
		} else {
			System.out.print("User-defined data type: " + type._typeName);
		}
	}

	// general statement
	@Override
	public void visit(Stmt stmt) throws SemanticException {
		if (!(stmt instanceof StmtList)) {
			indent(stmt);
		}
		// assign statement 
		if (stmt instanceof AssignStmt) {
			indent(stmt);
			AssignStmt s=(AssignStmt) stmt;
			System.out.print("Assignment statement");
			depth += 2;
			s._assignTo.accept(this);
			s._assignValue.accept(this);
			depth -= 2;
		}

		// call statement
		else if (stmt instanceof CallStatement) {
			System.out.print("Method call statement");
			depth += 2;
			((CallStatement) stmt)._call.accept(this);
			depth -= 2;
		}
		// break statement
		else if (stmt instanceof StmtBreak) {
			System.out.print("Break statement");
		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			System.out.print("While statement");
			depth += 2;
			s._condition.accept(this);
			if (s._commands instanceof StmtList) {
				indent(s);
				System.out.print("Block of statements");
			}
			s._commands.accept(this);
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

			depth += 2;
			// print the type
			s._type.accept(this);

			// print value if exists
			if (isValue) {
				s._value.accept(this);
			}
			depth -= 2;

		}

		else if (stmt instanceof StmtIf) {
			System.out.print("If statement");
			StmtIf s = (StmtIf) stmt;
			depth += 2;
			// print condition
			s._condition.accept(this);
			// print commands
			if (s._commands instanceof StmtList) {
				indent(s);
				System.out.print("Block of statements");
			}
			s._commands.accept(this);
			if (s._commandsElse != null) {
				System.out.println("Else statement");
				s._commandsElse.accept(this);
			}
			depth -= 2;
		}

		else if (stmt instanceof StmtList) {

			StmtList sl = (StmtList) stmt;
			depth += 2;
			for (Stmt s : sl.statements) {
				s.accept(this);
			}
			depth -= 2;

		} else if (stmt instanceof ReturnExprStatement) {

			System.out.print("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;
			depth += 2;
			returnExp.accept(this);
			depth -= 2;
		} else if (stmt instanceof ReturnVoidStatement) {
			System.out.print("Return statement (void value).");
		} else {
			throw new UnsupportedOperationException("Unexpected visit of Stmt  abstract class");
		}

	}

	@Override
	public void visit(VarExpr varExpr) {

		System.out.print(varExpr.name);

	}

}
