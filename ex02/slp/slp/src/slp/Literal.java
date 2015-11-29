package slp;

public abstract class Literal extends Expr{
	
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract String toString ();
	
}
