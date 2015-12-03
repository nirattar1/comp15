package slp;

/**
 * Pretty-prints an SLP AST.
 */
public class TypeChecker implements PropagatingVisitor<Type, Type> {
	protected final ASTNode root;

	// holds the depth while traversing the tree
	private int depth = 0;

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 */
	public TypeChecker(ASTNode root) {
		this.root = root;
		System.out.println("\nstarted dfs");
		System.out.println(root.accept(this, new Type (0,"Program"))._typeName);

	}

	public Type visit(Expr expr, Type t) {
		Type t1, t2;
		if (expr instanceof BinaryOpExpr) {
			BinaryOpExpr e = ((BinaryOpExpr) expr);
			t1 = visit(e.lhs, null);
			t2 = visit(e.rhs, null);
			System.out.println(t1._typeName);
			System.out.println(t2._typeName);
			if (t1 == t2) {
				System.out.println(t1._typeName);
				System.out.println(t2._typeName);
				return t1;
			} else {
				System.out.println("Error in line " + e.line
						+ ": Illegal type casting");
			}
		}

		// call
		else if (expr instanceof Call) {

			if (expr instanceof CallStatic) {
				CallStatic call = (CallStatic) expr;
				System.out.print("Call to static method: " + call._methodId
						+ ", in class: " + call._classId);

				depth += 2;
				for (Expr f : call._arguments) {
					f.accept(this, t);
				}
				depth -= 2;
			} else if (expr instanceof CallVirtual) {

				CallVirtual call = (CallVirtual) expr;
				System.out.print("Call to virtual method: " + call._methodId);

				// write "external scope" if this is an instance call.
				if (call._instanceExpr != null) {
					System.out.print(", in external scope");
					depth += 2;
					call._instanceExpr.accept(this, t);
					depth -= 2;
				}

				depth += 2;
				// visit parameters
				for (Expr f : call._arguments) {
					f.accept(this, t);
				}
				depth -= 2;
			}
		}

		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			System.out.print("Reference to array length");
			depth += 2;
			e._expr.accept(this, t);
			depth -= 2;
		} else if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			System.out.print("Boolean literal: " + e.value);
			return new Type (e.line, "boolean");
		} else if (expr instanceof LiteralNull) {
			System.out.print("Null literal");
			return new Type (expr.line, "null");
		} else if (expr instanceof LiteralNumber) {
			LiteralNumber e = ((LiteralNumber) expr);
			System.out.print("Integer literal: " + e.value);
			return new Type (e.line, "int");
		} else if (expr instanceof LiteralString) {
			LiteralString e = (LiteralString) expr;
			System.out.print("String literal: " + e.value);
			return new Type (e.line, "String");
		} else if (expr instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) expr);
			System.out.print("Reference to array");
			depth += 2;
			e._exprArr.accept(this, t);
			e._exprSub.accept(this, t);
			depth -= 2;

		} else if (expr instanceof LocationExpressionMember) {
			LocationExpressionMember e = (LocationExpressionMember) expr;
			System.out.print("Reference to variable: " + e.member);
			System.out.print(", in external scope");
			depth += 2;
			e.expr.accept(this, t);
			depth -= 2;
		} else if (expr instanceof LocationId) {
			LocationId e = (LocationId) expr;
			System.out.print("Reference to variable: " + e.name);
		} else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;
			System.out.print(e.op.humanString());
			depth += 2;
			e.operand.accept(this, t);
			depth -= 2;
		} else if (expr instanceof NewClassInstance) {
			System.out.print("Instantiation of class: ");
			NewClassInstance instance = (NewClassInstance) expr;
			System.out.print(instance._class_id);
		} else if (expr instanceof NewArray) {
			System.out.print("Array allocation");
			NewArray newArr = (NewArray) expr;

			depth += 2;
			// print array type
			newArr._type.accept(this, t);
			// print array size expression
			newArr._arrSizeExpr.accept(this, t);
			depth -= 2;
		} else {
			throw new UnsupportedOperationException(
					"Unexpected visit of Expr abstract class");
		}
		return t;
	}

	@Override
	public Type visit(FieldMethodList fieldMethodList, Type t) {

		FieldMethod fm;
		for (int i = fieldMethodList.fieldsmethods.size() - 1; i >= 0; i--) {
			depth += 2;
			fm = fieldMethodList.fieldsmethods.get(i);
			if (fm instanceof Field) {
				((Field) fm).accept(this, t);
			}
			if (fm instanceof Method) {
				((Method) fm).accept(this, t);
			}
			depth -= 2;
		}
		return t;
	}

	@Override
	public void visit(FormalsList formalsList) {

		for (Formal f : formalsList.formals) {
			indent(formalsList);
			System.out.print("Parameter: " + f.frmName);
			depth += 2;
			f.type.accept(this, t);
			depth -= 2;

		}

	}

	@Override
	public void visit(Formal formal) {

		// print parameter name
		if (formal.frmName != null) {
			formal.frmName.accept(this, t);
			depth += 2; // indent
		}

		// print its type
		formal.type.accept(this, t);

		if (formal.frmName != null) {
			depth -= 2; // remove indent
		}

	}

	@Override
	public void visit(TypeArray array) {
		indent(array);
		System.out.print("Primitive data type: 1-dimensional array of "
				+ array._typeName);

	}

	@Override
	public Type visit(Method method, Type t) {


		if (method.isStatic) {
			System.out.print("Declaration of static method: ");
		} else {
			System.out.print("Declaration of virtual method: ");
		}

		// print return type
		
		//method.f.accept(this, t);
		
		// depth += 2;
		
//		method.frmls.accept(this, t);
		
		method.stmt_list.accept(this, t);
		// depth -= 2;
		return t;

	}

	@Override
	public Type visit(Field field, Type t) {

		// print field names.
		for (VarExpr v : field.idList) {
			System.out.print("Declaration of field: ");
			v.accept(this, t);
		}

		// print type.
		depth += 2;
		field.type.accept(this, t);
		depth -= 2;
		return t;

	}

	@Override
	public Type visit(Class class1, Type t) {
		
		if (class1._extends != null) {
			System.out.print("Declaration of class:" + class1._className
					+ " Extends" + class1._extends);
		} else {
			System.out.print("Declaration of class: " + class1._className);
		}

		class1.fieldMethodList.accept(this, t);
		return t;
	}

	@Override
	public Type visit(Program program, Type t) {

		for (Class c : program.classList) {
			c.accept(this, t);

		}
		return t;

	}

	@Override
	public Type visit(Type type, Type t) {
		if (type.isPrimitive) {
			System.out.print("Primitive data type: " + type._typeName);
		} else {
			System.out.print("User-defined data type: " + type._typeName);
		}
		return t;
	}

	// assign statement
	public Type visit(AssignStmt stmt, Type t) {
		System.out.print("Assignment statement");
		stmt._assignTo.accept(this, t);
		stmt._assignValue.accept(this, t);
		return t;
	}

	// general statement
	@Override
	public Type visit(Stmt stmt, Type t) {
		System.out.println("stmt visit");
		// call statement
		if (stmt instanceof CallStatement) {
			System.out.print("Method call statement");
			depth += 2;
			((CallStatement) stmt)._call.accept(this, t);
			depth -= 2;
		}
		// break statement
		else if (stmt instanceof StmtBreak) {
			System.out.print("Break statement");
		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			System.out.print("While statement");
			depth += 2;
			s._condition.accept(this, t);
			if (s._commands instanceof StmtList) {

				System.out.print("Block of statements");
			}
			s._commands.accept(this, t);
			depth -= 2;
		} else if (stmt instanceof StmtContinue) {
			System.out.print("Continue statement");
		} else if (stmt instanceof StmtDeclareVar) {
			StmtDeclareVar s = (StmtDeclareVar) stmt;
			boolean isValue = (s._value != null);
			System.out.print("Declaration of local variable: " + s._id);
			// print value if exists
			if (isValue) {
				System.out.print(", with initial value");
			}

			depth += 2;
			// print the type
			s._type.accept(this, t);

			// print value if exists
			if (isValue) {
				s._value.accept(this, t);
			}
			depth -= 2;

		}

		else if (stmt instanceof StmtIf) {
			System.out.print("If statement");
			StmtIf s = (StmtIf) stmt;
			depth += 2;
			// print condition
			s._condition.accept(this, t);
			// print commands
			if (s._commands instanceof StmtList) {

				System.out.print("Block of statements");
			}
			s._commands.accept(this, t);
			if (s._commandsElse != null) {
				System.out.println("Else statement");
				s._commandsElse.accept(this, t);
			}
			depth -= 2;
		}

		else if (stmt instanceof StmtList) {

			StmtList sl = (StmtList) stmt;
			for (Stmt s : sl.statements) {
				s.accept(this, t);
			}
			return t;
			
		} else if (stmt instanceof ReturnExprStatement) {

			System.out.print("Return statement, with return value");
			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;
			depth += 2;
			returnExp.accept(this, t);
			depth -= 2;
		} else if (stmt instanceof ReturnVoidStatement) {
			System.out.print("Return statement (void value).");
		} else {
			throw new UnsupportedOperationException(
					"Unexpected visit of Stmt  abstract class");
		}
		return t;
	}

	@Override
	public Type visit(VarExpr varExpr, Type t) {

		System.out.print(varExpr.name);
		return t;

	}


}
