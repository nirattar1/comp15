package slp;

/** A base class for AST nodes for expressions.
 */
public abstract class Expr extends ASTNode {
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	
	public Type _type = null;

	public Expr (int line) 
	{
		super (line);
		_type = null;

	}
	
	public abstract void accept(Visitor visitor) throws SemanticException;
	
	public abstract String toString ();

	

}