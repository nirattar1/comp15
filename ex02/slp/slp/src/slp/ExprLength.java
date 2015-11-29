package slp;

// a class for ".length" expressions
public class ExprLength extends Expr
{
	private Expr _expr;
	
	public ExprLength (int line, Expr ex)
	{
		super(line);
		_expr = ex;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}