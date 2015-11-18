package slp;

/** A base class for AST nodes for expressions.
 */
public abstract class Expr extends ASTNode {
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	public abstract void accept(Visitor visitor);
}