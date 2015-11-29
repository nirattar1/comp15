/**
 * 
 */
package slp;

/**
 * @author NAttar
 *
 */
public class LocationArrSubscript extends Location {

	private final Expr _exprArr;
	private final Expr _exprSub;
	
	public LocationArrSubscript (int line, Expr arr, Expr sub)
	{
		super(line);
		_exprArr = arr ;
		_exprSub = sub ;
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
	
	public String toString() 
	{
		return _exprArr.toString() + " [ " + _exprSub.toString() + " ]";
	}	
}
