package slp;

public class VVariable extends VSymbol {
	Type type;
	boolean isInitialized;


	public VVariable(String name, int scope, Type type, boolean isInitialized) {
		super(name, scope);
		this.type = type;
		this.isInitialized = isInitialized;
	}

	@Override
	public String toString() {
		return "VVariable [type=" + type + ", name=" + name + ", scope=" + scope + "]";
	}


	
}
