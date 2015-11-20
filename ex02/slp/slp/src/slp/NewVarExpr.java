/**
 * 
 */
package slp;

/**
 * @author NAttar
 * an expression class for "new" command.
 * will hold information about what type of object to create. 
 */

public class NewVarExpr extends VarExpr {

	//a number of objects to create.
	//will allow array creation "new Integer[5]"
	private Expr _arrSizeExpr = null;
	
	/**
	 * @param name
	 */
	public NewVarExpr(String name) {
		super(name);
	}
	
	//constructor for new array instance.
	public NewVarExpr(String name, Expr arraySizeExpr) {
		super(name);
		_arrSizeExpr = arraySizeExpr;
	}
	
	public String toString() {
		return "new instance creation: " + name;
	}	
}
