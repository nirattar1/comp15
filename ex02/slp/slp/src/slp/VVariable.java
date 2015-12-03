package slp;

public class VVariable extends VSymbol {
	Type type;

	public VVariable(String name, int scope, Type type) {
		super(name, scope);
		this.type = type;
	}

	@Override
	public String toString() {
		return "VVariable [type=" + type + ", name=" + name + ", scope=" + scope + "]";
	}


	
}
