package slp;

//implements from class "Type".
//just that this class represents an array of that type.

public class TypeArray extends Type {


	public TypeArray(Type type) {
		//call the base constructor.
		super(type._typeName);
		
	
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
