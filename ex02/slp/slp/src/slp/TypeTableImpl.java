/**
 * 
 */
package slp;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author NAttar
 *
 */

public class TypeTableImpl implements TypeTable {

	private Map<String, Type> _types = null;

	public TypeTableImpl() {
		_types = new HashMap<String, Type>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see slp.TypeTable#checkExist(java.lang.String)
	 */
	@Override
	public boolean checkExist(String className) 
	{
		return (_types.containsKey(className));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see slp.TypeTable#createClass(java.lang.String)
	 */
	@Override
	public void addType(String typeName, Type type) 
	{
		type.wasDeclared = true;
		_types.put(typeName, type);

		SymbolTableBuilder.debugs.append(this.toString());
	}

	@Override
	public boolean checkSubTypes(String sub, String sup) {
		boolean found = false;
		
		if (sub.endsWith("[]") != sup.endsWith("[]")){
			return false;
		}
		if (sub.endsWith("[]") == sup.endsWith("[]") ==true){
			if (sub.substring(0, sub.length()-2).equals(sup.substring(0, sup.length()-2))){
				return true;
			}
			else return false;
		}
		
		
		// if 1 of them doesn't exist in table, no point in searching .
		if (_types.get(sub) == null || _types.get(sup) == null) {
			return false;
		}

		// if both the same class (already checked exist in table)
		// - considered as sub type.
		if (sub.equals(sup)) {
			return true;
		}

		// both are different classes found in table.
		// start from subtype, go up the tree until found "sup".
		Type currType = _types.get(sub);

		while (currType != null) {
			// get father's type, based on father's name.
			Type fatherType = _types.get(currType._superName);

			// if father really exists in table and its name is the target name
			// "sup".
			if (fatherType != null && fatherType._typeName.equals(sup)) {
				return true;
			}

			// didn't find father yet, go up the tree (maybe up to null)
			currType = fatherType;
		}

		// went up the tree and got nada.
		return false;

	}

	
	public Field getFieldOfInstance(String typeName, String memberName)
	{
		//start looking for the field. 
		//if not found , go up the hierarchy until found.
		Type currType = getType(typeName);

		while (currType != null) 
		{

			//check if this type has the field.
			Field f = currType.getField(memberName);
			if (f!=null)
			{
				//we are settled.
				return f;
			}
			
			//didn't find field yet - try going up the father.
			// get father's type, based on father's name.
			currType = getType(currType._superName);

		}
		
		//can be if type not in table, 
		//or doesn't have this field in its hierarchy.
		return null;
	}
	
	
	public MethodBase getMethod(String typeName, String methodName)
	{
		//start looking for the method. 
		//if not found , go up the hierarchy until found.
		Type currType = getType(typeName);

		while (currType != null) 
		{
			//check if this type has the method.
			MethodBase m = currType.getMethod(methodName);
			if (m!=null)
			{
				//we are settled.
				return m;
			}
			
			//didn't find method yet - try going up the father.
			// get father's type, based on father's name.
			currType = getType(currType._superName);
		}
		
		//can be if type not in table, 
		//or doesn't have this method in its hierarchy.
		return null;	
	}

	
	
	public Type getType(String typeName) 
	{
		return _types.get(typeName);
	}

	public String toString() {
		StringBuffer result = new StringBuffer("Type Table: \n");

		for (Entry<String, Type> e : this._types.entrySet()) {
			result.append(e.toString() + "\n");
		}
		return result.toString();
	}

}
