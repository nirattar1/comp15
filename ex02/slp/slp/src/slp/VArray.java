package slp;

public class VArray extends VVariable {


	public VArray(String name, int scope, Type type, boolean isInitialized, int length) {
		super(name, scope, type, isInitialized);
		this.length = length;
	}

	int length;
	
	
}
