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
		
		//add library "type" containing all the functions.
		AddICLibrary();
		
	}

	
	/**
	 * will add the class "Library" to type table.
	 */
	private void AddICLibrary ()
	{

		//prepare all the functions.
		FieldMethodList fml = new FieldMethodList(0, null);
		
		Type type_string = new Type (0,"string");
		Type type_void = new Type (0, "void");
		Type type_int = new Type (0, "int");
		Type type_boolean = new Type (0, "boolean");
		
		//println
		Formal meth_decl = new Formal(0, type_void, new VarExpr(0, "println"));
		FormalsList frmls = new FormalsList(0, 
				new Formal (0, type_string, new VarExpr(0, "s")));
		Method m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);
		
		//print
		meth_decl = new Formal(0, type_void, new VarExpr(0, "print"));
		frmls = new FormalsList(0, 
				new Formal (0, type_string, new VarExpr(0, "s")));
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);

		//printi
		meth_decl = new Formal(0, type_void, new VarExpr(0, "printi"));
		frmls = new FormalsList(0, 
				new Formal (0, type_int, new VarExpr(0, "i")));
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);

		//printb
		meth_decl = new Formal(0, type_void, new VarExpr(0, "printb"));
		frmls = new FormalsList(0, 
				new Formal (0, type_boolean, new VarExpr(0, "b")));
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);

		//readi
		meth_decl = new Formal(0, type_int, new VarExpr(0, "readi"));
		frmls = new FormalsList(0);
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);

		//readln
		meth_decl = new Formal(0, type_string, new VarExpr(0, "readln"));
		frmls = new FormalsList(0);
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);

		//eof
		meth_decl = new Formal(0, type_boolean, new VarExpr(0, "eof"));
		frmls = new FormalsList(0);
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);

		//stoi
		meth_decl = new Formal(0, type_int, new VarExpr(0, "stoi"));
		frmls = new FormalsList(0, 
				new Formal (0, type_string, new VarExpr(0, "s")));
		frmls.addFrml(new Formal(0, type_int, new VarExpr(0, "n")));
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);

		//itos
		meth_decl = new Formal(0, type_string, new VarExpr(0, "itos"));
		frmls = new FormalsList(0, 
				new Formal (0, type_int, new VarExpr(0, "i")));
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);
		
		
		//
		
		//
		
		//random
		meth_decl = new Formal(0, type_int, new VarExpr(0, "random"));
		frmls = new FormalsList(0, 
				new Formal (0, type_int, new VarExpr(0, "i")));
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);
	
		//time
		meth_decl = new Formal(0, type_int, new VarExpr(0, "time"));
		frmls = new FormalsList(0);
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);
	
		//exit
		meth_decl = new Formal(0, type_void, new VarExpr(0, "exit"));
		frmls = new FormalsList(0, 
				new Formal (0, type_int, new VarExpr(0, "i")));
		m = new Method(0, meth_decl, frmls, null);
		m.isStatic = true;
		fml.addFieldMethod(m);	
		
		
		//finished adding all. make it a type.
		Class lib = new Class (0, "Library", fml);
		
		
		//will be transformed to type.
		Type libType = new Type (lib);
		
		addType("Library", libType);
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

		SymbolTableImpl.debugs.append(this.toString());
	}

	@Override
	public boolean checkSubTypes(String sub, String sup) {
		boolean found = false;
		
		//when sub is of type "null" (the string "null").
		//- consider a subtype from every type.
		if (sub!=null && sub.equals("null"))
		{
			return true;
		}
		
		System.out.println(sub + " "+ sup);
		boolean t1Array=sub.endsWith("[]");
		boolean t2Array=sup.endsWith("[]");
		System.out.println(t1Array + " "+ t2Array);

		if ( t1Array != t2Array){
						return false;
		}
		if (t1Array==t2Array && t1Array==true){
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
