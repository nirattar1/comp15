package slp;

public class ReturnExprStatement extends Stmt{

	private Expr _exprForReturn;
	
	public ReturnExprStatement(Expr e) {
		this._exprForReturn = e;
	}
	
	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}

}
