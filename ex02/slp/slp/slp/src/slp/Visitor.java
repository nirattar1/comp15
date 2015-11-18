package slp;

/** An interface for AST visitors.
 */
public interface Visitor {
	public void visit(StmtList stmts);
	public void visit(Stmt stmt);
	public void visit(PrintStmt stmt);
	public void visit(AssignStmt stmt);
	public void visit(Expr expr);
	public void visit(ReadIExpr expr);
	public void visit(VarExpr expr);
	public void visit(NumberExpr expr);
	public void visit(UnaryOpExpr expr);
	public void visit(BinaryOpExpr expr);
}