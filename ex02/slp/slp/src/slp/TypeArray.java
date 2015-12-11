package slp;

//implements from class "Type".
//just that this class represents an array of that type.

public class TypeArray extends Type {

	public TypeArray(int line, Type type) {
		// call the base constructor.
		super(line, type._typeName+"[]");
		this.isPrimitive = type.isPrimitive;
		if (isPrimitive){
			this.wasDeclared=true;
		}

	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context)
			throws SemanticException {
		return visitor.visit(this, context);
	}

}
