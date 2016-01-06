package slp.mcode;

import java_cup.runtime.Symbol;

/**
 * Adds line number and name information to scanner symbols.
 */
public class IRToken extends Symbol {
	private final int line;
	String operand1 = null;
	String operand2 = null;
	String operand3 = null;





	public IRToken(int sym_num, int line, String operand1) {
		super(sym_num);
		this.line = line;
		this.operand1 = operand1;
	}

	public IRToken(int sym_num, int line, String operand1, String operand2) {
		this(sym_num, line, operand1);
		this.operand2 = operand2;
	}
	public IRToken(int sym_num, int line, String operand1,
			String operand2, String operand3) {
		this(sym_num, line, operand1, operand2);
		this.operand3 = operand3;

	}

	@Override
	public String toString() {
		return "IRToken [line=" + line + ", operand1=" + operand1 + ", operand2=" + operand2
				+ ", operand3=" + operand3 + "]";
	}

	public int getLine() {
		return line;
	}
}