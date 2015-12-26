package slp;
import java.util.List;

	
public class CallStatic extends Call {
public final String _classId;
public final String _methodId;
public CallStatic(int line, String classId, String methodId, List<Expr> args) 
	{
		super(line);
		this._classId = classId;
		this._methodId = methodId;
		this._arguments = args;
	}


	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 * @throws SemanticException 
	 */
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


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
