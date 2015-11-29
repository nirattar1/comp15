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
	
	public abstract String toString (); 

}
