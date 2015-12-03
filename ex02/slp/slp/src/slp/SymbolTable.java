package slp;

public interface SymbolTable {
	public boolean checkExists(int scope, String name);

	public boolean addVariable(int scope, VSymbol symbol);
	public boolean checkAvailable(Integer scope, String name);
	public Type getVariableType (int scope, String name);
}
