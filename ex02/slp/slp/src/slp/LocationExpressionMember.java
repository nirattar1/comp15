/**
 * 
 */
package slp;

/**
 * @author NAttar
 *
 */
public class LocationExpressionMember extends Location {

	private final Expr expr;
	private final String member;
	
	public LocationExpressionMember (int line, Expr e, String m)
	{
		super(line);
		expr = e ;
		member = m ;
	}
	
	/* (non-Javadoc)
	 * @see slp.Expr#accept(slp.Visitor)
	 */
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see slp.ASTNode#accept(slp.PropagatingVisitor, java.lang.Object)
	 */
	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() 
	{
		return expr.toString() + " . " + member;
	}	
	
}
