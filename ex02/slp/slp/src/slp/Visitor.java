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
	public void visit(LocationId expr);
	public void visit(LiteralNumber expr);
	public void visit(UnaryOpExpr expr);
	public void visit(BinaryOpExpr expr);
	public void visit(FieldMethodList fieldMethodList);
	public void visit(FormalsList formalsList);
	public void visit(Formal formal);
	public void visit(Array array);
	public void visit(Method method);
	public void visit(Field field);
	public void visit(Class class1);
}