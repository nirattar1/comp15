package slp;

public class StmtWhile extends Stmt {
	
	//the condition to evaluate
	public Expr _condition;
	
	//the code to perform if condition turned to be true
	public Stmt _commands;
	

	//while constructor . 
	public StmtWhile(int line, Expr cond, StmtList commands) {
		super (line);
		this._condition = cond;
		this._commands = new StmtList(line, commands);
	}
	public StmtWhile(int line, Expr cond, Stmt commands) {
		super (line);
		this._condition = cond;
		this._commands = commands;
	}
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
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
	
}
