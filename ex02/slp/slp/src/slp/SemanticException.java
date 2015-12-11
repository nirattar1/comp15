package slp;

public class SemanticException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String err_msg;
	public SemanticException(String err_msg) {
		super();
		this.err_msg = err_msg;
		System.err.println(err_msg);
		//SymbolTableBuilder.debugs.append(SymbolTableBuilder.typeTable.toString());
		SymbolTableBuilder.printToDebugFile();;
		System.err.print("Debug file created");
	}
	
}
