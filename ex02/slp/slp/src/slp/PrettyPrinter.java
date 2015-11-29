package slp;

/**
 * Pretty-prints an SLP AST.
 */
public class PrettyPrinter implements Visitor {
	protected final ASTNode root;

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 */
	public PrettyPrinter(ASTNode root) {
		this.root = root;
	}

	/**
	 * Prints the AST with the given root.
	 */
	public void print() {
		root.accept(this);
	}

	// public void visit(PrintStmt stmt) {
	// System.out.print("print(");
	// stmt.expr.accept(this);
	// System.out.print(");");
	// }

	public void visit(AssignStmt stmt) {
		stmt._assignTo.accept(this);
		System.out.print("=");
		stmt._assignValue.accept(this);
		System.out.print(";");
	}

	public void visit(Expr expr) {

		if (expr instanceof BinaryOpExpr) {
			BinaryOpExpr e = ((BinaryOpExpr) expr);
			System.out.print(e.getLine() + ": " + e.op);
			e.lhs.accept(this);
			e.rhs.accept(this);
		} else if (expr instanceof CallStatic) {
			CallStatic call = (CallStatic) expr;
			System.out.print("Call to static method: " + call._methodId
					+ "in class: " + call._classId);
			for (Expr f : call._arguments) {
				f.accept(this);
			}
		} else if (expr instanceof CallVirtual) {

		} else if (expr instanceof ExprLength) {

		} else if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			System.out.print("Boolean literal:" + e.value);
		} else if (expr instanceof LiteralNull) {

		} else if (expr instanceof LiteralNumber) {
			LiteralNumber e = ((LiteralNumber) expr);
			System.out.print(e.value);
		} else if (expr instanceof LiteralString) {

		} else if (expr instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) expr);
			System.out.println("Reference to array:");
			e._exprArr.accept(this);
			System.out.println();
			e._exprSub.accept(this);
			// System.out.println();
		} else if (expr instanceof LocationExpressionMember) {

		} else if (expr instanceof LocationId) {
			LocationId e = (LocationId) expr;
			System.out.print("Reference to variable: "+e.name);
		} else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			System.out.print(e.op);
			e.operand.accept(this);

		} else {
			throw new UnsupportedOperationException(
					"Unexpected visit of Expr abstract class");
		}
	}

	@Override
	public void visit(FieldMethodList fieldMethodList) {
		// for (FieldMethod fm : fieldMethodList.fieldsmethods) {
		FieldMethod fm;
		for (int i = fieldMethodList.fieldsmethods.size() - 1; i >= 0; i--) {
			fm = fieldMethodList.fieldsmethods.get(i);
			if (fm instanceof Field) {
				((Field) fm).accept(this);
			}
			if (fm instanceof Method) {
				((Method) fm).accept(this);
			}
		}

	}

	@Override
	public void visit(FormalsList formalsList) {
		for (Formal f : formalsList.formals) {
			f.accept(this);
		}

	}

	@Override
	public void visit(Formal formal) {
		formal.type.accept(this);
		if (formal.frmName != null) {
			formal.frmName.accept(this);
			System.out.println();
		}

	}

	@Override
	public void visit(TypeArray array) {
		System.out.println("Primitive Data Type: 1-dimensional array of "
				+ array._typeName);

	}

	@Override
	public void visit(Method method) {
		if (method.isStatic) {
			System.out.println("Declaration of static method: "
					+ method.f.frmName.name);
		} else {
			System.out.println("Declaration of virtual method: "
					+ method.f.frmName.name);
		}

		method.frmls.accept(this);
		method.stmt_list.accept(this);

	}

	@Override
	public void visit(Field field) {

		for (LocationId v : field.idList) {
			System.out.print("Declaration of field: ");
			v.accept(this);
		}
		System.out.println();
		field.type.accept(this);

	}

	@Override
	public void visit(Class class1) {
		if (class1._extends != null) {
			System.out.println("Declaration of class:" + class1._className
					+ " Extends" + class1._extends);
		} else {
			System.out.println("Declaration of class: " + class1._className);
		}
		class1.fieldMethodList.accept(this);

	}

	@Override
	public void visit(Program program) {
		for (Class c : program.classList) {
			c.accept(this);
			// System.out.println();
		}

	}

	@Override
	public void visit(Type type) {
		System.out.println("Primitive Data Type: " + type._typeName);
	}

	@Override
	public void visit(PrintStmt stmt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Stmt stmt) {
		if (stmt instanceof StmtBreak) {
			//StmtBreak s = (StmtBreak) stmt;
			System.out.println("Break statement");
		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			System.out.println("While statement: ");
			s._condition.accept(this);
			s._commands.accept(this);
		} else if (stmt instanceof StmtContinue) {

		} else if (stmt instanceof StmtDeclareVar) {
			StmtDeclareVar s = (StmtDeclareVar) stmt;
			System.out.println("Declaration of a local variable: " + s._id
					+ " , with initial value ");
			s._type.accept(this);
			if (s._value != null) {
				s._value.accept(this);
			}

		} else if (stmt instanceof StmtIf) {
			System.out.println("If statement");
			StmtIf s = (StmtIf) stmt;
			s._commands.accept(this);
			if (s._commandsElse != null) {
				System.out.println("Else statement");
				s._commandsElse.accept(this);
			}
		} else if (stmt instanceof StmtList) {
			System.out.println("Block of statements:");
			StmtList sl = (StmtList) stmt;
			for (Stmt s : sl.statements) {
				s.accept(this);
				System.out.println();
			}

		} else {
			throw new UnsupportedOperationException(
					"Unexpected visit of Stmt  abstract class");
		}

	}

	@Override
	public void visit(VarExpr varExpr) {
		System.out.println("Paremeter:" + varExpr.name);
	}

}
