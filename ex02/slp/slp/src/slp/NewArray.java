/**
 * 
 */
package slp;

/**
 * @author NAttar
 * an expression class for "new" command.
 * will hold information about what type of object to create. 
 */

public class NewArray extends Expr {

	//a number of objects to create.
	//will allow array creation "new Integer[5]"
	public Expr _arrSizeExpr = null;
	public Type _type = null;
	
	
	/**
	 * @param name
	 */
	public NewArray(int line, Type type) {
		super(line);
		type = _type;
	}
	
	//constructor for new array instance.
	public NewArray(int line, Type type, Expr arraySizeExpr) {
		super(line);
		_type = type;
		_arrSizeExpr = arraySizeExpr;
	}
	
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}
	
	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 * @throws SemanticException 
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) throws SemanticException {
		return visitor.visit(this, context);
	}	
	
	
	public String toString() {
		return "new instance creation: " + _type;
	}	
}
