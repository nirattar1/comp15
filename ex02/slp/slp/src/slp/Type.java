package slp;

public class Type extends ASTNode implements Comparable<Type>{

	public String _typeName = null; 
	public boolean isPrimitive;
	public Type (int line)
	{
		super(line);
	}
	
	public Type(int line, String typeName) {
		super(line);
		_typeName = new String (typeName);
		if (_typeName.equals("int") || _typeName.equals("string")
				|| _typeName.equals("void") || _typeName.equals("boolean")){
			this.isPrimitive=true;
		}else{
			this.isPrimitive=false;
		}
	}

	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
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
	public int compareTo(Type other) {
		if (other._typeName.contentEquals(_typeName)==true){
			return 0;
		}
		return 1;
	}

}
