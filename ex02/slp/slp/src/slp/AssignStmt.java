package slp;

/**
 * An AST node for assignment statements.
 */
public class AssignStmt extends Stmt {
	public final Location _assignTo;
	public final Expr _assignValue;

	public AssignStmt(int line, Location assignTo, Expr val) {
		super (line);
		this._assignTo = assignTo;
		this._assignValue = val;
	}

	/**
	 * Accepts a visitor object as part of the visitor pattern.
	 * 
	 * @param visitor
	 *            A visitor.
	 * @throws SemanticException 
	 */
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 * @throws SemanticException 
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) throws SemanticException {
		return visitor.visit(this, context);
	}
}