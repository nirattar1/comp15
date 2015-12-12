package slp;

import java.util.*;

public class SymbolTableImpl implements SymbolTable 
{

	public HashMap<String, List<VSymbol>> map = new HashMap<String, List<VSymbol>>();

	public SymbolTableImpl() {

	}

	public boolean checkExists(int scope, String name) {
		// map is empty
		if (map.get(name) == null)
			return false;
		else {
			for (VSymbol s : map.get(name)) {
				if (s.scope == scope) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean addVariable(int scope, VSymbol symbol) 
	{
		if (checkExists(scope, symbol.name) == true)
			return false;
		else {
			// check if name exists in map on other scopes
			List<VSymbol> l = map.get(symbol.name);
			if (l == null) {
				l = new LinkedList<VSymbol>();
			}
			l.add(symbol);
			map.put(symbol.name, l);
			SymbolTableBuilder.debugs.append(this.toString());
			return true;
		}

	}

	public boolean checkAvailable(Integer scope, String name) {
		// map is empty
		if (map.get(name) == null)
			return false;

		else {
			for (VSymbol s : map.get(name)) {

				if (s.scope <= scope) {
					return true;
				}
			}
		}
		return false;
	}

	public VSymbol getVariable(int scope, String name) {
		if (!checkAvailable(scope, name)) {
			return null;
		} else {
			VSymbol temp = new VSymbol("", 0);
			for (VSymbol s : map.get(name)) {

				if ((temp.scope < s.scope) && (s.scope <= scope)) {
					temp = s;
				}
			}
			return temp;
		}
	}

	public Type getVariableType(int scope, String name) {
		VSymbol temp = getVariable(scope, name);
		if (temp != null)
			return ((VVariable) temp).type;
		else
			return null;

	}

	@Override
	public boolean checkInitialized(Integer scope, String name) {
		if (map.get(name) == null)
			return false;

		else {
			for (VSymbol s : map.get(name)) {

				if (s.scope <= scope) {
					return ((VVariable) s).isInitialized;
				}
			}
		}
		return false;
	}

	/** 
	 * find member in symbol table and set it to initialized.
	 */
	public void setInitialized (Integer scope, String name)
	{
		if (map.get(name) == null)
			return;

		else 
		{
			for (VSymbol s : map.get(name)) 
			{

				if (s.scope == scope && s instanceof VVariable) 
				{
					((VVariable) s).isInitialized = true;
					return;
				}
			}
		}

	}
	
	@Override
	public void deleteScope(int scope)
	{
		//iterate through all keys (names of symbols).
		Set<String> keys = new HashSet<String> (map.keySet());
		for (String key : keys)
		{
			//for each key extract its VSymbol list . 
			List<VSymbol> list = map.get(key);
			if (list!=null)
			{			
				//then remove the ones with this scope.
				for (VSymbol v : list)
				{
					if (v!=null && v.scope==scope)
					{
						//remove from list.
						list.remove(v);
					}
				}
				
				//if the list is empty then remove this key
				if (list.isEmpty())
				{
					map.remove(key);
				}
			}
		}
		
		
	}
	
	
	public boolean checkNoOrphans(TypeTable tTable) throws SemanticException {
		for (List<VSymbol> l : map.values()) {
			for (VSymbol v : l) {
				if (v instanceof VVariable) {
					Type t = ((VVariable) v).type;
					if (!t.wasDeclared) {
						if (tTable.checkExist(t._typeName)==false){
							throw new SemanticException("Type " +t._typeName + " was not properly declared");
						}else{
							t.wasDeclared=true;
						}
						
					}
				}
			}
		}
		return true;
	}

	public String toString() {
		StringBuffer result = new StringBuffer("Symbol Table: \n");

		for (Map.Entry<String, List<VSymbol>> e : map.entrySet()) {
			result.append(e.toString() + "\n");
		}
		return result.toString();
	}

}
