/**
 * 
 */
package slp;

/**
 * @author NAttar
 * an expression class for "new" command.
 * will hold information about what type of object to create. 
 */

public class NewClassInstance extends Expr {

	public String _class_id = null;
	
	
	/**
	 * @param name
	 */
	public NewClassInstance(int line, String class_id) {
		super(line);
		_class_id = class_id;
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
		return "new instance creation: " + _class_id;
	}	
}
