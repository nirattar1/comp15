package slp;

import java.util.ArrayList;
import java.util.List;

public class Type extends ASTNode {

	
	//the name of this type.
	public String _typeName = null;
	
	//the name of the supertype of this type (if exists).
	public String _superName = null;

	//considered primitive: int, boolean, string, void.
	
	//TODO what is difference between null and void?
	public boolean isPrimitive;
	
	public boolean wasDeclared=false;
	
	//a list of all fields defined.
	public List<Field> _fields = new ArrayList<Field>();
	
	public List<MethodBase> _methods = new ArrayList<MethodBase>();
			
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
			this.wasDeclared=true;
		}else{
			this.isPrimitive=false;
		}
	}

	//a constructor from "class" object.
	//the idea is to build a type that is just a summary of the class implementation.
	//will hold the names of methods, fields, name of superclass etc.
	public Type (Class cl)
	{
		super(cl.line);	//has no real meaning
		
		//name
		_typeName = cl._className;
		
		//superclass name
		_superName = cl._extends;
		
		//iterate through class's fields and methods (without implementations).
		//copy them to the constructed Type object.
		for (FieldMethod f : cl.fieldMethodList.fieldsmethods) 
		{

			//add field.
			if (f instanceof Field) 
			{
				Field field = (Field) f;
				for (VarExpr v : field.idList )
				{
					//ignore id list (used for AST).
					//each id will be a separate field in Type. 
					List<VarExpr> list = new ArrayList<VarExpr>();
					list.add(v);
					Field simpleField = 
							new Field(field.line, field.type, list);
					this._fields.add(simpleField);
				}
				
			}
			
			//add method.
			else if (f instanceof Method) 
			{
				System.out.println("adding method " + ( (MethodBase) f).returnVar.frmName.name);
				this._methods.add((MethodBase) f);
			}
			else if (f==null){
				System.out.println("f is null");
			}
		}
	}
	
	
	/**
	 * checks if this type has a member called "memberName".
	 * @return
	 */
	public boolean hasField (String memberName)
	{
		//iterate through all fields. return when found list of name.
		for (Field f : _fields)
		{
			if (f.idList.get(0).name.equals(memberName))
			{
				return true;
			}
		}
		
		//field not found
		return false;
	}
	
	/**
	 * will return a field in the fields list of this type.
	 * @param memberName
	 * @return
	 */
	public Field getField (String memberName)
	{
		//iterate through all fields. return it.
		for (Field f : _fields)
		{
			if (f.idList.get(0).name.equals(memberName))
			{
				return f;
			}
		}
		
		//field not found
		return null;
	}
	
	public MethodBase getMethod (String MethodName)
	{
		//iterate through all methods. return it.
		for (MethodBase m : _methods)
		{
			if (m.returnVar.frmName.name.equals(MethodName))
			{
				return m;
			}
		}
		
		//field not found
		return null;	
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
	public static Type TypeInferBinary (Type t1, Type t2, Operator op, TypeTable tt) throws SemanticException
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
		if (op==Operator.EQUAL || op==Operator.NEQUAL )
		{
			
			//primitive types.
			if (t1.isPrimitive || t2.isPrimitive)
			{
				//check that both are primitive and of the same type
				if (t1.isPrimitive && t2.isPrimitive 
						&& t1._typeName.equals(t2._typeName))
				{
					return new Type (0, "boolean");
				}
				else
				{
					//one primitive, one not, different types of primitive.
					throw new SemanticException("comparison between non-compatible types: "
							+ t1._typeName+" and "+ t2._typeName );
				}
			}

			//both are not primitive.
			
			//check for type inheritance.
			if (tt.checkSubTypes(t1._typeName, t2._typeName)
						||	tt.checkSubTypes(t2._typeName, t1._typeName))
			{
				//one inherits from other, good.
				return new Type (0, "boolean");
			}
			else
			{
				//not inherit from each other. cannot compare
				throw new SemanticException("comparison between non-compatible types: "
						+ t1._typeName+" and "+ t2._typeName );
			}
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
		
		
		//if reached here then it's a problem . couldn't find compatible operation 
		//for 2 types.
		
		throw new SemanticException("operation \"" + op.humanString() + 
				"\" between non-compatible types: " + t1._typeName+" and "+ t2._typeName );

		
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
		return "Type [_typeName=" + _typeName + ", _superName=" + _superName + ", isPrimitive=" + isPrimitive
				+ ", wasDeclared=" + wasDeclared + ", fields=" + _fields + "]";
	}



}
