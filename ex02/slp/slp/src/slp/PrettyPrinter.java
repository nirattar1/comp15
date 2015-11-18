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
		stmt.varExpr.accept(this);
		System.out.print("=");
		stmt.rhs.accept(this);
		System.out.print(";");
	}
	
	public void visit(Expr expr) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}	
	
	public void visit(ReadIExpr expr) {
		System.out.print("readi()");
	}	
	
	public void visit(VarExpr expr) {
		System.out.print(expr.name);
	}
	
	public void visit(NumberExpr expr) {
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
}