package slp;

//implements from class "Type".
//just that this class represents an array of that type.

public class TypeArray extends Type {
	
	//the type of the array members.
	public final Type _type;
	

	public TypeArray(Type type) {
		//call the base constructor.
		super(type._typeName);
		
		//save an indication that this is the type
		_type = type;
		
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}

}
