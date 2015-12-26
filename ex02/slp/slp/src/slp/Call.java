package slp;

import java.util.List;

public abstract class Call extends Expr {

	public List <Expr> _arguments;


	public Call (int line)
	{
		super (line);
	}
	
	
	/**
	 * will check validity of arguments.
	 * @param method
	 * @param argsTypes
	 * @param tt
	 * @throws SemanticException
	 */
	public void checkValidArgumentsForCall 
		(MethodBase method, List<Type> argsTypes, TypeTable tt) 
				throws SemanticException
	{
		//different number of arguments - invalid.
		if (method.frmls.formals.size() != argsTypes.size())
		{
			throw new SemanticException ("mismatching number of arguments. for method call "
					+ method.returnVar.frmName.name);
		}
		
		//check that each given argument is a subtype of the 
		//corresponding formal in method.
		for (int i=0; i<argsTypes.size(); i++)
		{
			Formal formal = method.frmls.formals.get(i);
			Type argType = argsTypes.get(i);
			
			//check for primitive types.
			if (formal.type.isPrimitive || argType.isPrimitive)
			{
				//check that both are primitive and of the same type
				if (formal.type.isPrimitive && argType.isPrimitive 
						&& formal.type._typeName.equals(argType._typeName))
				{
					//both are same primitive of the same type.
					//check next argument.
					continue;
				}
				else
				{
					//one primitive, one not, or different types of primitive.
					throw new SemanticException ("mismatching type for argument "
							+ (i+1)
							+ ", for method call " + method.returnVar.frmName.name
							+ ". expected argument type : " + formal.type._typeName
							+", received argument type: " + argType._typeName);
				}
			}
			
			//not primitive, check is one is subtype of the other.
			if (!tt.checkSubTypes(argType._typeName, formal.type._typeName))
			{
				//found mismatching type.
				throw new SemanticException ("mismatching type for argument "
						+ (i+1)
						+ ", for method call " + method.returnVar.frmName.name
						+ ". expected argument type : " + formal.type._typeName
						+", received argument type: " + argType._typeName);
			}
		}
		
		//passed all checks.
	}
	
}
