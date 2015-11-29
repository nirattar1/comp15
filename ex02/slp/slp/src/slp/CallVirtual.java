package slp;
import java.util.List;

	
public class CallVirtual extends Call {
private final Expr _instanceExpr;
private final String _methodId;
private List <Expr> _arguments;

	//object instance virtual call
	public CallVirtual(Expr instanceExpr, String methodId, List<Expr> args) 
	{
		this._instanceExpr = instanceExpr;
		this._methodId = methodId;
		this._arguments = args;
	}
	
	//virtual call inside a class impl. (no instance).
	public CallVirtual(String methodId, List<Expr> args) 
	{
		this._instanceExpr = null; //TODO give "this" expression
		this._methodId = methodId;
		this._arguments = args;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
