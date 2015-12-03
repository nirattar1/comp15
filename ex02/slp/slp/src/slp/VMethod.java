package slp;

public class VMethod extends VSymbol {
	Type returnType;

	public VMethod(String name, int scope, Type returnType) {
		super(name, scope);
		this.returnType = returnType;
	}

	@Override
	public String toString() {
		return "VMethod [returnType=" + returnType + ", name=" + name + ", scope=" + scope + "]";
	}
	
}
