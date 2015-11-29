/**
 * 
 */
package slp;

/**
 * @author NAttar
 *
 */
public class LocationExpressionMember extends Location {

	public final Expr expr;
	public final String member;
	
	public LocationExpressionMember (int line, Expr e, String m)
	{
		super(line);
		expr = e ;
		member = m ;
	}
	

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}

	public String toString() 
	{
		return expr.toString() + " . " + member;
	}	
	
}
