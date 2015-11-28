package slp;

public class CallStatement extends Stmt {
	private Call _call;
	
	
	public CallStatement(Call c) {
		this._call = c;
	}
	
	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}

}
