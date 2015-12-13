package slp;

import java.io.IOException;

/** Evaluates straight line programs.
 */
public class SLPEvaluator implements PropagatingVisitor<Environment, Integer>
{
	protected ASTNode root;

	/** Constructs an SLP interpreter for the given AST.
	 * 
	 * @param root An SLP AST node.
	 */
	public SLPEvaluator(ASTNode root) {
		this.root = root;
	}
	
	/** Interprets the AST passed to the constructor.
	 * @throws SemanticException 
	 */
	public void evaluate() throws SemanticException {
		Environment env = new Environment();
		root.accept(this, env);
	}
	
	public Integer visit(StmtList stmts, Environment env) throws SemanticException {
		for (Stmt st : stmts.statements) {
			st.accept(this, env);
		}
		return null;
	}

	public Integer visit(Stmt stmt, Environment env) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt!");
	}



	public Integer visit(AssignStmt stmt, Environment env) {
		return null;
	}

	public Integer visit(Expr expr, Environment env) {
		throw new UnsupportedOperationException("Unexpected visit of Expr!");
	}

	public Integer visit(LocationId expr, Environment env) {
		return env.get(expr);
	}

	public Integer visit(LiteralNumber expr, Environment env) {
		return new Integer(expr.value);		
		// return expr.value; also works in Java 1.5 because of auto-boxing
	}

	public Integer visit(UnaryOpExpr expr, Environment env) throws SemanticException {
		Operator op = expr.op;
		if (op != Operator.MINUS)
			throw new RuntimeException("Encountered unexpected operator " + op);
		Integer value = expr.operand.accept(this, env);
		return new Integer(- value.intValue());
	}

	public Integer visit(BinaryOpExpr expr, Environment env) throws SemanticException {
		Integer lhsValue = expr.lhs.accept(this, env);
		int lhsInt = lhsValue.intValue();
		Integer rhsValue = expr.rhs.accept(this, env);
		int rhsInt = rhsValue.intValue();
		int result;
		switch (expr.op) {
		case DIV:
			if (rhsInt == 0)
				throw new RuntimeException("Attempt to divide by zero: " + expr);
			result = lhsInt / rhsInt;
			break;
		case MINUS:
			result = lhsInt - rhsInt;
			break;
		case MULT:
			result = lhsInt * rhsInt;
			break;
		case PLUS:
			result = lhsInt + rhsInt;
			break;
		case LT:
			result = lhsInt < rhsInt ? 1 : 0;
			break;
		case GT:
			result = lhsInt > rhsInt ? 1 : 0;
			break;
		case LE:
			result = lhsInt <= rhsInt ? 1 : 0;
			break;
		case GE:
			result = lhsInt >= rhsInt ? 1 : 0;
			break;
		case LAND:
			result = (lhsInt!=0 && rhsInt!=0) ? 1 : 0;
			break;
		case LOR:
			result = (lhsInt!=0 || rhsInt!=0) ? 1 : 0;
			break;
		default:
			throw new RuntimeException("Encountered unexpected operator type: " + expr.op);
		}
		return new Integer(result);
	}

	@Override
	public Integer visit(FieldMethodList mthds, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(FormalsList mthds, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(Formal frml, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer visit(Type type, Environment context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer visit(TypeArray array, Environment context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(TypeArray array) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer visit(Method method, Environment context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(Field field, Environment context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(Class class1, Environment context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(Program program, Environment context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(VarExpr type, Environment context) {
		// TODO Auto-generated method stub
		return null;
	}
}