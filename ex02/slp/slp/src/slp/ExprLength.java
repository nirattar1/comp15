package slp;

// a class for ".length" expressions
public class ExprLength extends Expr
{
	private Expr _expr;
	
	public ExprLength (Expr ex)
	{
		_expr = ex;
	}
	
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	public void accept(Visitor visitor)
	{
		visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}