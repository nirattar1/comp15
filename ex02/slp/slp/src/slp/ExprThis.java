/**
 * 
 */
package slp;

/**
 * @author NAttar
 *
 */
public class ExprThis extends Expr {

	public ExprThis(int line) 
	{
		super(line);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see slp.Expr#accept(slp.Visitor)
	 */
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);

	}

	/* (non-Javadoc)
	 * @see slp.Expr#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see slp.ASTNode#accept(slp.PropagatingVisitor, java.lang.Object)
	 */
	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context)
			throws SemanticException {
		return visitor.visit(this, context);
	}

}
