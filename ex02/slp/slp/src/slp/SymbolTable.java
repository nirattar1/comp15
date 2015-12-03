package slp;

public interface SymbolTable {
 public boolean checkExists (int scope, String name);
 public void addVariable (int scope, String name, Type type);
 
}
