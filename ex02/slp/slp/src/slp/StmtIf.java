package slp;

public class StmtIf extends Stmt {
	
	//the condition to evaluate
	private Expr _condition;
	
	//the code to perform if condition turned to be true
	private Stmt _commands;
	
	//the code to perform if condition turned to be false ('else' part)
	private Stmt _commandsElse;	

	//if with no 'else' part.
	public StmtIf(Expr cond, Stmt commands) {
		this._condition = cond;
		this._commands = commands;
		this._commandsElse = null;
	}

	//if with an 'else'.
	public StmtIf(Expr cond, Stmt commands, Stmt commands_else) {
		this._condition = cond;
		this._commands = commands;
		this._commandsElse = commands_else;
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
