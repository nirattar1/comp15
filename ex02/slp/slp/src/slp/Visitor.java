package slp;

/** An interface for AST visitors.
 */
public interface Visitor {
	public void visit(Stmt stmt) throws SemanticException;
	public void visit(Expr expr) throws SemanticException;
	public void visit(FieldMethodList fieldMethodList) throws SemanticException;
	public void visit(FormalsList formalsList) throws SemanticException;
	public void visit(Formal formal) throws SemanticException;
	public void visit(TypeArray array) throws SemanticException;
	public void visit(Method method) throws SemanticException;
	public void visit(Field field) throws SemanticException;
	public void visit(Class class1) throws SemanticException;
	public void visit(Program program) throws SemanticException;
	public void visit(Type type) throws SemanticException;
	public void visit(VarExpr varExpr) throws SemanticException;
}
