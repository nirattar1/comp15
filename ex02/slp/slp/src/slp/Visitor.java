package slp;

/** An interface for AST visitors.
 */
public interface Visitor {
	public void visit(StmtList stmts);
	public void visit(Stmt stmt);
	public void visit(PrintStmt stmt);
	public void visit(AssignStmt stmt);
	public void visit(Expr expr);
	public void visit(FieldMethodList fieldMethodList);
	public void visit(FormalsList formalsList);
	public void visit(Formal formal);
	public void visit(TypeArray array);
	public void visit(Method method);
	public void visit(Field field);
	public void visit(Class class1);
	public void visit(Program program);
	public void visit(Type type);
}