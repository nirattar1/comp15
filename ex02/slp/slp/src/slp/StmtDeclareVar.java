package slp;

public class StmtDeclareVar extends Stmt {
	
	//the type of variable.
	private Type _type;
	
	//the variable id.
	private String _id;
	
	//(optional) the value to init this variable. .
	private Expr _value = null;	

	//declare with no value
	public StmtDeclareVar(Type type, String id) {
		this._type = type;
		this._id = id;
		this._value = null;
	}

	//declare with init value.
	public StmtDeclareVar(Type type, String id, Expr init_val) {
		this._type = type;
		this._id = id;
		this._value = init_val;
	}

	public String toString ()
	{
		String s;
		s += "Type: " + _type;
		s += ", Id: " + _id;
		if (_value!=null)
		{
			s += ", Init value: " + _value;
		}
		return s;
	}
	
	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
