package slp;

public class Type extends ASTNode {

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

	/////////////////////////
	//type inference rules.//
	/////////////////////////
	public static Type TypeInferBinary (Type t1, Type t2, Operator op)
	{
		
		//int arithmetic operators
		if (t1._typeName.equals("int") && t2._typeName.equals("int"))
		{
			if (op==Operator.PLUS || op==Operator.MINUS || op==Operator.DIV
				|| op==Operator.MULT || op==Operator.MOD)
			{
				return new Type (0, "int");
			}
		}

		// object comparison == , !=   
		// returns boolean.
		//TODO: add check for type inheritance.
		if (op==Operator.EQUAL || op==Operator.NEQUAL )
		{
			return new Type (0, "boolean");
		}
		
		
		//string concatenation
		if (t1._typeName.equals("string") && t2._typeName.equals("string")
				 && op==Operator.PLUS)
		{
			return new Type (0, "string");
		}		
		
		//int comparison
		//returns boolean
		if (t1._typeName.equals("int") && t2._typeName.equals("int"))
		{
			if (op==Operator.GT || op==Operator.GE || op==Operator.LT
				|| op==Operator.LE)
			{
				return new Type (0, "boolean");
			}
		}
		
		
		//logic operators
		//returns boolean
		if (t1._typeName.equals("boolean") && t2._typeName.equals("boolean"))
		{
			if (op==Operator.LAND || op==Operator.LOR)
			{
				return new Type (0, "boolean");
			}
		}
		
		//default 
		return null;
	}
	
	//infer type for unary operations.
	public static Type TypeInferUnary (Type t1, Operator op)
	{
		//unary minus.
		if (t1._typeName.equals("int") && op==Operator.MINUS)
		{
			return new Type (0, "int");
		}

		//unary negation.
		if (t1._typeName.equals("boolean") && op==Operator.LNEG)
		{
			return new Type (0, "boolean");
		}
		
		return null;
	}
	
	public boolean equals (Type other) {
		if (other._typeName.contentEquals(_typeName)==true){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Type [_typeName=" + _typeName + ", isPrimitive=" + isPrimitive + "]";
	}

}
