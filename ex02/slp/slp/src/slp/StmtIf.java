package slp;

public class StmtIf extends Stmt {
	
	//the condition to evaluate
	public Expr _condition;
	
	//the code to perform if condition turned to be true
	public Stmt _commands;
	
	//the code to perform if condition turned to be false ('else' part)
	public Stmt _commandsElse;	

	//if with no 'else' part.
	public StmtIf(int line, Expr cond, Stmt commands) {
		super (line);
		this._condition = cond;
		this._commands = commands;
		this._commandsElse = null;
	}

	//if with an 'else'.
	public StmtIf(int line, Expr cond, Stmt commands, Stmt commands_else) {
		super(line);
		this._condition = cond;
		this._commands = commands;
		this._commandsElse = commands_else;
	}


	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 * @throws SemanticException 
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) throws SemanticException {
		return visitor.visit(this, context);
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}
	
	

}
