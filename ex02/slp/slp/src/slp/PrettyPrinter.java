package slp;

/** Pretty-prints an SLP AST.
 */
public class PrettyPrinter implements Visitor {
	protected final ASTNode root;

	/** Constructs a printin visitor from an AST.
	 * 
	 * @param root The root of the AST.
	 */
	public PrettyPrinter(ASTNode root) {
		this.root = root;
	}

	/** Prints the AST with the given root.
	 */
	public void print() {
		root.accept(this);
	}
	
	public void visit(StmtList stmts) {
		for (Stmt s : stmts.statements) {
			s.accept(this);
			System.out.println();
		}
	}

	public void visit(Stmt stmt) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt abstract class");
	}
	
	public void visit(PrintStmt stmt) {
		System.out.print("print(");
		stmt.expr.accept(this);
		System.out.print(");");
	}
	
	public void visit(AssignStmt stmt) {
		stmt._assignTo.accept(this);
		System.out.print("=");
		stmt._assignValue.accept(this);
		System.out.print(";");
	}
	
	public void visit(Expr expr) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}	
	
	public void visit(ReadIExpr expr) {
		System.out.print("readi()");
	}	
	
	public void visit(LocationId expr) {
		System.out.print(expr.name);
	}
	
	public void visit(LiteralNumber expr) {
		System.out.print(expr.value);
	}
	
	public void visit(UnaryOpExpr expr) {
		System.out.print(expr.op);
		expr.operand.accept(this);
	}
	
	public void visit(BinaryOpExpr expr) {
		expr.lhs.accept(this);
		System.out.print(expr.op);
		expr.rhs.accept(this);
	}

	@Override
	public void visit(FieldMethodList fieldMethodList) {
		for (FieldMethod fm : fieldMethodList.fieldsmethods) {
			if (fm instanceof Field){
				((Field)fm).accept(this);
			}
			if (fm instanceof Method){
				((Method)fm).accept(this);
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
		formal.frmName.accept(this);
		formal.type.accept(this);
		
	}

	@Override
	public void visit(Array array) {
		array.type.accept(this);
		
	}

	@Override
	public void visit(Method method) {
		if (method.isStatic) {
			System.out.println(" Static");
		}
		method.f.accept(this);
		method.frmls.accept(this);
		method.stmt_list.accept(this);
		
	}

	@Override
	public void visit(Field field) {
		field.type.accept(this);
		for (LocationId v : field.idList) {
			v.accept(this);
		}
		
	}

	@Override
	public void visit(Class class1) {
		System.out.println("Class " + class1._className +" Extends" +  class1._extends);
		class1.fieldMethodList.accept(this);
		
	}
}