package slp;

public class Class extends ASTNode {

	String _className = null;
	String _extends = null;
	FieldMethodList fieldMethodList;
	
	public Class(int line, String _className, String _extends, FieldMethodList fieldMethodList) {
		super(line);
		this._className = _className;
		this._extends = _extends;
		this.fieldMethodList = fieldMethodList;
	}

	public Class(int line, String _className,FieldMethodList fieldMethodList) {
		super(line);
		this._className = _className;
		this.fieldMethodList = fieldMethodList;
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
