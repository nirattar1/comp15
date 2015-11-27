package slp;

/** An enumeration containing all the operation types in the SLP language.
 */
public enum Operator {
	MINUS, LNEG, PLUS, MULT, DIV, LT, GT, LE, GE, LAND, LOR, EQUAL, NEQUAL;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
		case LNEG: return "!";
		case MINUS: return "-";
		case PLUS: return "+";
		case MULT: return "*";
		case DIV: return "/";
		case LT: return "<";
		case GT: return ">";
		case LE: return "<=";
		case GE: return ">=";
		case LAND: return "&&";
		case LOR: return "||";
		case EQUAL: return "==";
		case NEQUAL: return "!=";
		
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}