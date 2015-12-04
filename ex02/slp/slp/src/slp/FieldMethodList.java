package slp;

import java.util.List;
import java.util.ArrayList;

/** An AST node for a list of methods.
 */
public class FieldMethodList extends ASTNode {
	public final List<FieldMethod> fieldsmethods = new ArrayList<FieldMethod>();
	
	public FieldMethodList(int line,  FieldMethod fldmthd) {
		super(line);
		fieldsmethods.add(fldmthd);
		
	}
	

	/** Adds a statement to the tail of the list.
	 * 
	 * @param mthd A program statement.
	 */
	public void addFieldMethod(FieldMethod fldmthd) {
		fieldsmethods.add(fldmthd);
	}

	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws SemanticException 
	 */
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
}