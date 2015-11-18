package slp;

/** An enumeration containing all the operation types in the SLP language.
 */
public enum Operator {
	MINUS, PLUS, MULT, DIV, LT, GT, LE, GE, LAND, LOR;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
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
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}