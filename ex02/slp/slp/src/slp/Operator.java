package slp;

/** An enumeration containing all the operation types in the SLP language.
 */
public enum Operator {
	MINUS, LNEG, PLUS, MULT, DIV, LT, GT, LE, GE, LAND, LOR, EQUAL, NEQUAL, MOD;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
		case LNEG: return "!";
		case MINUS: return "-";
		case PLUS: return "+";
		case MULT: return "*";
		case DIV: return "/";
		case MOD: return "%";
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
	
	public String humanString ()
	{
		switch (this) 
		{
		case LNEG: return "Logical unary operation: negation";
		case MINUS: return "Mathematical binary operation: subtraction";
		case PLUS: return "Mathematical binary operation: addition";
		case MULT: return "Mathematical binary operation: multiplication";
		case DIV: return "Mathematical binary operation: division";
		case MOD: return "Mathematical binary operation: modulo";
		case LT: return "Logical binary operation: less than";
		case GT: return "Logical binary operation: greater than";
		case LE: return "Logical binary operation: less than or equal to";
		case GE: return "Logical binary operation: greater than or equal to";
		case LAND: return "Logical binary operation: and";
		case LOR: return "Logical binary operation: or";
		case EQUAL: return "Logical binary operation: equality";
		case NEQUAL: return "Logical binary operation: inequality";
		default: throw new RuntimeException("Unexpted value: " + this.name());
			
		}
	}
}