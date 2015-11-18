package slp;

/** An interface for a propagating AST visitor.
 * The visitor passes down objects of type <code>DownType</code>
 * and propagates up objects of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType,UpType> {
	public UpType visit(StmtList stmts, DownType d);
	public UpType visit(Stmt stmt, DownType d);
	public UpType visit(PrintStmt stmt, DownType d);
	public UpType visit(AssignStmt stmt, DownType d);
	public UpType visit(Expr expr, DownType d);
	public UpType visit(ReadIExpr expr, DownType d);
	public UpType visit(VarExpr expr, DownType d);
	public UpType visit(NumberExpr expr, DownType d);
	public UpType visit(UnaryOpExpr expr, DownType d);
	public UpType visit(BinaryOpExpr expr, DownType d);
}