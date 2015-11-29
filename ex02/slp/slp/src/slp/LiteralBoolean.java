/**
 * 
 */
package slp;

/**
 * @author NAttar
 *
 */
public class LiteralBoolean extends Literal 
{

	public final boolean value;
	
	public LiteralBoolean(int line, boolean value) 
	{
		super(line);
		this.value = value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
