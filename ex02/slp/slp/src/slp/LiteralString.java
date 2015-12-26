package slp;

public class LiteralString extends Literal 
{

	public final String value;
	
	public LiteralString(int line, String value) 
	{
		super(line);
		
		//this literal came from lex.
		//need to trim first and last quotes  .
		String tmp = new String (value);
		tmp = tmp.substring(1, tmp.length()-1);
		this.value = tmp;
	}

	@Override
	public String toString() {
		return this.value;
	}
	
}
