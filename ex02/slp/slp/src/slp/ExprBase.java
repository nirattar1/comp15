/**
 * 
 */
package slp;

/**
 * @author NAttar
 *
 */
public class ExprBase extends Expr {

	/**
	 * 
	 */
	public ExprBase() {
		// TODO Auto-generated constructor stub
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

}
