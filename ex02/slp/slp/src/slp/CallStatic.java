package slp;
import java.util.List;

	
public class CallStatic extends Call {
private final String _classId;
private final String _methodId;
private List <Expr> _arguments;

	public CallStatic(String classId, String methodId, List<Expr> args) 
	{
		this._classId = classId;
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

}
