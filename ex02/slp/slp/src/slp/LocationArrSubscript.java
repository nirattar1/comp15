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
	
	public LocationArrSubscript (Expr arr, Expr sub)
	{
		_exprArr = arr ;
		_exprSub = sub ;
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
		return _exprArr.toString() + " [ " + _exprSub.toString() + " ]";
	}	
}
