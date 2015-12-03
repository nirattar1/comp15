/**
 * 
 */
package slp;

/**
 * @author NAttar
 *
 */
public abstract class Location extends Expr {

	public Location (int line)
	{
		super(line);
	}
	public abstract void accept(Visitor visitor) ;
	
	public abstract <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) ;
}
