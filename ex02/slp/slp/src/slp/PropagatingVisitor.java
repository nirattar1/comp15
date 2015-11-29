package slp;

/** An interface for a propagating AST visitor.
 * The visitor passes down objects of type <code>DownType</code>
 * and propagates up objects of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType,UpType> {
	public UpType visit(StmtList stmts, DownType d);
	public UpType visit(FieldMethodList mthds, DownType d);
	public UpType visit(FormalsList mthds, DownType d);
	public UpType visit(Stmt stmt, DownType d);
	public UpType visit(AssignStmt stmt, DownType d);
	public UpType visit(Formal frml, DownType d);
	public UpType visit(Expr expr, DownType d);
	public UpType visit(LocationId expr, DownType d);
	public UpType visit(LiteralNumber expr, DownType d);
	public UpType visit(UnaryOpExpr expr, DownType d);
	public UpType visit(BinaryOpExpr expr, DownType d);
	public UpType visit(TypeArray array, DownType context);
	public UpType visit(Method method, DownType context);
	public UpType visit(Field field, DownType context);
	public UpType visit(Class class1, DownType context);
	public UpType visit(Program program, DownType context);
	public UpType visit(Type type, DownType context);
	public UpType visit(VarExpr type, DownType context);
}