package slp;

public interface TypeTable 
{

	//check if a Type already exists.
	//will be used while defining new class.
	boolean checkExist(String className);
	
	//will receive a type/class name and add it to the table.
	//type may be a complex type, and may hold a name of its supertype.
	void addType(String className, Type type);
	
	
	//will return TRUE if the type named "sub" is a subtype in this type table,
	//of the type named "sup".
	//doesn't have to be a direct child, just a descendant.
	//FALSE otherwise.
	boolean checkSubTypes (String sub, String sup);
	
	Class getClassObject(String className);
}
