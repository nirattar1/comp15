package slp;

public class StmtWhile extends Stmt {
	
	//the condition to evaluate
	private Expr _condition;
	
	//the code to perform if condition turned to be true
	private Stmt _commands;
	

	//while constructor . 
	public StmtWhile(Expr cond, Stmt commands) {
		this._condition = cond;
		this._commands = commands;
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
