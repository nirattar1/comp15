package slp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class SymbolTableImpl implements SymbolTable {
	public static HashMap<String, List<VSymbol>> map = new HashMap<String, List<VSymbol>>();

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

	public boolean addVariable(int scope, VSymbol symbol) {
		if (checkExists(scope, symbol.name) == true)
			return false;
		else 
		{
			// check if name exists in map on other scopes
			List<VSymbol> l = map.get(symbol.name);
			if (l == null) {
				l = new LinkedList<VSymbol>();
			}
			l.add(symbol);
			map.put(symbol.name, l);
			System.out.println(this.toString());
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
	
	public VSymbol getVariable(int scope, String name){
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
		VSymbol temp=getVariable(scope,name);
		if (temp!=null) return ((VVariable)temp).type;
		else return null;
		
	}

	@Override
	public boolean checkInitialized(Integer scope, String name) {
		if (map.get(name) == null)
			return false;

		else {
			for (VSymbol s : map.get(name)) 
			{

				if (s.scope <= scope) 
				{
					return ((VVariable) s).isInitialized ;
				}
			}
		}
		return false;
	}

	public String toString() {
		StringBuffer result = new StringBuffer("Symbol Table: \n ");

		for (Entry<String, List<VSymbol>> e : map.entrySet()) {
			result.append(e.toString() + "\n");
		}
		return result.toString();
	}

}
