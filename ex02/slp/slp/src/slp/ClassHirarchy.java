package slp;

public interface ClassHirarchy {

	boolean checkExist(String className);
	
	void createClass(String className);
	
	void createClass(String className, String baseClass);
	
	Class getClassObject(String className);
}
