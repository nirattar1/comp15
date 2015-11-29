package slp;
import java.util.List;

	
public class CallVirtual extends Call {
public final Expr _instanceExpr;
public final String _methodId;
public List <Expr> _arguments;

	//object instance virtual call
	public CallVirtual(int line, Expr instanceExpr, String methodId, List<Expr> args) 
	{
		super(line);
		this._instanceExpr = instanceExpr;
		this._methodId = methodId;
		this._arguments = args;
	}
	
	//virtual call inside a class impl. (no instance).
	public CallVirtual(int line, String methodId, List<Expr> args) 
	{
		super(line);
		this._instanceExpr = null; //TODO give "this" expression
		this._methodId = methodId;
		this._arguments = args;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}



}
