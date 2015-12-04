package slp;

/** The base class of all AST nodes in this package.
 */
public abstract class ASTNode {
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	public int line;

	
	/**
	 * Construct AST node corresponding to a line number in the original
	 * code. 
	 * Used by subclasses.
	 * @param line - The line number.
	 */

	//TODO remove this!
	protected ASTNode() {
		
	}
	
	protected ASTNode(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}
	
	public abstract void accept(Visitor visitor) throws SemanticException;
	
	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 * @throws SemanticException 
	 */
	public abstract <DownType, UpType> UpType accept (
			PropagatingVisitor<DownType, UpType> visitor, DownType context) throws SemanticException;

	
}