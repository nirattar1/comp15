package slp.mcode;

import java_cup.runtime.Symbol;

/**
 * Adds line number and name information to scanner symbols.
 */
public class IRToken extends Symbol {
	private final int line;
	private final String name;
	String reg1 = null;
	String reg2 = null;
	String reg3 = null;

	public IRToken(int sym_num, int line, String name, String reg1,
			String reg2, String reg3) {
		this(sym_num, line, name, reg1, reg2);
		this.reg3 = reg3;

	}

	public IRToken(int sym_num, int line, String name, String reg1, String reg2) {
		this(sym_num, line, name, reg1);

		this.reg2 = reg2;
	}

	public IRToken(int sym_num, int line, String name, String reg1) {
		super(sym_num);
		this.line = line;
		this.name = name;
		this.reg1 = reg1;

	}

	public String toString() {
		String val = value != null ? "(" + value + ")" : "";
		return name + val;
	}

	public int getLine() {
		return line;
	}
}