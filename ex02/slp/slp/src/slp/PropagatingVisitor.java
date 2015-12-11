package slp;

/** An interface for a propagating AST visitor.
 * The visitor passes down objects of type <code>DownType</code>
 * and propagates up objects of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType,UpType> {
	public UpType visit(FieldMethodList mthds, DownType d) throws SemanticException;
	public UpType visit(FormalsList mthds, DownType d) throws SemanticException;
	public UpType visit(Stmt stmt, DownType d) throws SemanticException;
	public UpType visit(Formal frml, DownType d) throws SemanticException;
	public UpType visit(Expr expr, DownType d) throws SemanticException;
	public UpType visit(TypeArray array, DownType context) throws SemanticException;
	public UpType visit(Method method, DownType context) throws SemanticException;
	public UpType visit(Field field, DownType context) throws SemanticException;
	public UpType visit(Class class1, DownType context) throws SemanticException;
	public UpType visit(Program program, DownType context) throws SemanticException;
	public UpType visit(Type type, DownType context) throws SemanticException;
	public UpType visit(VarExpr varExpr, DownType context);
	Type visit(TypeArray array);
}