package slp;

public interface SymbolTable {
	public boolean checkExists(int scope, String name);

	public boolean addVariable(int scope, VSymbol symbol);
	public boolean checkAvailable(Integer scope, String name);
	public VSymbol getVariable(int scope, String name);
	public Type getVariableType (int scope, String name);
	public boolean checkInitialized (Integer scope, String name);
	
	/** 
	 * delete/close scope from symbol table.
	 * will delete all the entries for in that scope.
	 * is used (for example) when block scope has ended.
	 * @param scope
	 */
	public void deleteScope(int scope);
}
