package slp;

public class SemanticException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String err_msg;
	int line = 0;
	public SemanticException(String err_msg) {
		super();
		this.err_msg = err_msg;
		//System.err.println(err_msg);
		//SymbolTableImpl.debugs.append(SymbolTableBuilder.typeTable);
		//SymbolTableImpl.printToDebugFile();
		//System.err.print("Debug file created");
	}
	
	public SemanticException(String err_msg, int line) {
		super();
		this.err_msg = err_msg;
		this.line = line;
		//System.err.println(err_msg);
		//SymbolTableImpl.debugs.append(SymbolTableBuilder.typeTable);
		//SymbolTableImpl.printToDebugFile();
		//System.err.print("Debug file created");
	}
	
	public String toString ()
	{
		
		String tmp = "Semantic Error: ";

		if (this.err_msg!=null) 
		{
			if (this.line > 0)
			{
				tmp += "line " + this.line + ": ";
			}
			
			tmp += err_msg;
		}
		return tmp;
	}
	
}
