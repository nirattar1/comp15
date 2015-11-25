package slp;

public class Type extends TypeVoid {

	String _typeName = null; 
	
	public Type(String typeName) {
		_typeName = new String (typeName);
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}

}
