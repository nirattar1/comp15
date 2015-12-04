package slp;

public class StmtDeclareVar extends Stmt {
	
	//the type of variable.
	public Type _type;
	
	//the variable id.
	public String _id;
	
	//(optional) the value to init this variable. .
	public Expr _value = null;	

	//declare with no value
	public StmtDeclareVar(int line, Type type, String id) {
		super (line);
		this._type = type;
		this._id = id;
		this._value = null;
	}

	//declare with init value.
	public StmtDeclareVar(int line, Type type, String id, Expr init_val) {
		super(line);
		this._type = type;
		this._id = id;
		this._value = init_val;
	}

	public String toString ()
	{
		String s = "";
		s += "Type: " + _type;
		s += ", Id: " + _id;
		if (_value!=null)
		{
			s += ", Init value: " + _value;
		}
		return s;
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
