package slp;

import java_cup.runtime.Symbol;

/** Adds line number and name information to scanner symbols.
 */
public class Token extends Symbol {
	private final int line;
	private final String name;

	public Token(int line, String name, int id, Object value) {
		super(id, ++line, 0, value);
		this.name = name;
		this.line = line;
	}
	
	public Token(int line, String name, int id) {
		super(id, ++line, 0);
		this.name = name;
		this.line = line;
	}
	
	public String toString() {
		String val = value != null ? "(" + value + ")" : "";
		return name +  val;
	}
	
	public int getLine() {
		return line;
	}
}