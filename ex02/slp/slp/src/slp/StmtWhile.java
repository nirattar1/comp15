package slp;

public class StmtWhile extends Stmt {
	
	//the condition to evaluate
	public Expr _condition;
	
	//the code to perform if condition turned to be true
	public StmtList _commands;
	

	//while constructor . 
	public StmtWhile(Expr cond, Stmt commands) {
		this._condition = cond;
		this._commands = new StmtList(commands);
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}	
	
}
