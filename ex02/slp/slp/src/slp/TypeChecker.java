package slp;

import java.util.ArrayList;
import java.util.List;

/**
 * Pretty-prints an SLP AST.
 */
public class TypeChecker implements PropagatingVisitor<Integer, Type> {

	protected final ASTNode root;

	private SymbolTable symbolTable = new SymbolTableImpl();

	public TypeTable typeTable = null;

	private boolean _checkInitialized = true;

	private String _currentClassName = null;

	private Method _currentMethod = null;

	private int _currentMethodReturnBalance = 0;

	// holds the depth while traversing the tree

	/**
	 * Constructs a printin visitor from an AST.
	 * 
	 * @param root
	 *            The root of the AST.
	 * @throws SemanticException
	 */
	public TypeChecker(ASTNode root, TypeTable tt) throws SemanticException {
		this.root = root;
		this.typeTable = tt;
		// start traverse tree.
		root.accept(this, 0);
		SymbolTableImpl.printToDebugFile();

	}

	@Override
	public Type visit(Program program, Integer scope) throws SemanticException {

		for (Class c : program.classList) {
			c.accept(this, scope);

		}
		return null;

	}

	@Override
	public Type visit(Class class1, Integer scope) throws SemanticException {

		// save an indication of current class name,
		// for further traversal inside class .
		_currentClassName = class1._className;

		// visit all components of the class.
		class1.fieldMethodList.accept(this, scope + 1);

		// close the class scope.
		symbolTable.deleteScope(scope + 1);
		_currentClassName = null;

		return null;
	}

	@Override
	public Type visit(Field field, Integer scope) throws SemanticException {
		// traverse tree but do nothing

		for (VarExpr v : field.idList) {
			v.accept(this, scope);
			// no use of adding to type table
			// (was already done in previous pass).
		}

		field.type.accept(this, scope);
		return null;

	}

	@Override
	public Type visit(FieldMethodList fieldMethodList, Integer scope) throws SemanticException {

		FieldMethod fm;
		for (int i = fieldMethodList.fieldsmethods.size() - 1; i >= 0; i--) {
			fm = fieldMethodList.fieldsmethods.get(i);
			if (fm instanceof Field) {
				((Field) fm).accept(this, scope);
			}
			if (fm instanceof Method) {
				((Method) fm).accept(this, scope);
			}
		}
		return null;
	}

	@Override
	public Type visit(FormalsList formalsList, Integer scope) throws SemanticException {
		// traverse tree but do nothing

		for (Formal f : formalsList.formals) {
			f.type.accept(this, scope);
		}
		return null;
	}

	@Override
	public Type visit(Formal formal, Integer scope) throws SemanticException {
		// traverse tree but do nothing (been checked in previous stage)
		if (formal.frmName != null) {
			formal.frmName.accept(this, scope);
		}

		formal.type.accept(this, scope);

		return null;

	}

	// general statement
	@Override
	public Type visit(Stmt stmt, Integer scope) throws SemanticException {
		// System.out.println("stmt visit");

		// Assign statement
		if (stmt instanceof AssignStmt) {
			AssignStmt s = (AssignStmt) stmt;
			// System.out.println("Assignment statement");

			// go into 1st location, doesn't need be initialized.
			_checkInitialized = false;
			Type t1 = s._assignTo.accept(this, scope);
			// System.out.println("t1 finished");

			// update symbol table that value was initialized.
			if (s._assignTo instanceof LocationId) {
				symbolTable.setInitialized(scope, ((LocationId) s._assignTo).name);
			}

			// evaluate right side, remember to check initialized values.
			_checkInitialized = true;
			Type t2 = s._assignValue.accept(this, scope);

			// not supposed to happen in actual runtime that t1/t2 is null
			// but we prefer throwing an exception to be on the safe side
			if (t1 == null || t2 == null) {
				throw new SemanticException(
						"Assign type error at line " + stmt.line + " type 1: " + t1 + " type 2: " + t2);
			}

			if (t1.isPrimitive || t2.isPrimitive) {
				// check that both are primitive and of the same type
				if (t1.isPrimitive && t2.isPrimitive && t1._typeName.equals(t2._typeName)) {
					return null;
				} else {
					throw new SemanticException("Assign type error at line " + stmt.line + " type 1: " + t1._typeName
							+ " type 2: " + t2._typeName);
				}
			} else if (typeTable.checkSubTypes(t2._typeName, t1._typeName)) {
				System.out.println("t2 inherits from t1");
				return null;

			}

			else {
				throw new SemanticException("Assign type error at line " + stmt.line + " type 1: " + t1._typeName
						+ " type 2: " + t2._typeName);
			}
		}

		else if (stmt instanceof CallStatement) {
			((CallStatement) stmt)._call.accept(this, scope);
		}

		else if (stmt instanceof StmtIf) {
			StmtIf s = (StmtIf) stmt;

			// get condition type
			Type cond = s._condition.accept(this, scope);

			// check that condition is of type boolean.
			if (cond == null || !cond.isPrimitive || !cond._typeName.equals("boolean")) {
				throw new SemanticException("Error: 'if' condition is not of type boolean. " + "at line " + s.line);
			}

			// traverse commands

			s._commands.accept(this, scope);
			if (s._commandsElse != null) {
				s._commandsElse.accept(this, scope);
			}

		} else if (stmt instanceof StmtWhile) {
			StmtWhile s = (StmtWhile) stmt;
			Type cond = s._condition.accept(this, scope);
			if (cond == null || !cond.isPrimitive || !cond._typeName.equals("boolean")) {
				throw new SemanticException("Error: 'while' condition is not of type boolean. " + "at line " + s.line);
			}

			Type t = s._commands.accept(this, scope);
			if (t == null) {
				return t;
			} else if (t._typeName.equals("BREAK") || t._typeName.equals("CONTINUE")) {
				return null;
			}
			return t;
		} else if (stmt instanceof StmtBreak) {
			return new Type(stmt.line, "BREAK");
		} else if (stmt instanceof StmtContinue) {
			return new Type(stmt.line, "CONTINUE");
		} else if (stmt instanceof StmtList) {

			StmtList sl = (StmtList) stmt;
			Type temp, r = null;

			// opening scope.
			for (Stmt s : sl.statements) {
				temp = s.accept(this, scope + 1);
				if (temp != null) {
					if (temp._typeName.equals("BREAK") || temp._typeName.equals("CONTINUE")) {
						r = temp;
					}
				}
			}

			// closing scope.
			symbolTable.deleteScope(scope + 1);

			return r;
		}

		else if (stmt instanceof ReturnExprStatement) {

			Expr returnExp = ((ReturnExprStatement) stmt)._exprForReturn;

			Type t = returnExp.accept(this, scope);
			if (!t._typeName.equals(_currentMethod.returnVar.type._typeName)) {
				if (!typeTable.checkSubTypes(t._typeName, _currentMethod.returnVar.type._typeName)) {
					throw new SemanticException(stmt.line + ": incorrect return type: " + t._typeName
							+ " Expected type: " + _currentMethod.returnVar.type._typeName);
				}
			}

			// decrease number of needed return statements .
			// (on a returning function)
			if (!_currentMethod.IsReturnVoid()) {
				_currentMethodReturnBalance--;
			}
		}

		else if (stmt instanceof ReturnVoidStatement) {
			// System.out.println("Return statement (void value).");
			if (!_currentMethod.returnVar.type._typeName.equals("void")) {
				throw new SemanticException(stmt.line + ": incorrect return type: void Expected type: "
						+ _currentMethod.returnVar.type._typeName);
			}

		} else if (stmt instanceof StmtDeclareVar) {
			StmtDeclareVar s = (StmtDeclareVar) stmt;
			boolean isValue = (s._value != null);

			if (s._type instanceof TypeArray) {
				s._type.isPrimitive = false;
				if (!symbolTable.addVariable(scope, new VArray(s._id, scope, s._type, isValue))) {
					throw new SemanticException("Error: duplicate array var name at line " + s.line);
				}

			} else {
				if (!symbolTable.addVariable(scope, new VVariable(s._id, scope, s._type, isValue))) {
					throw new SemanticException("Error: duplicate variable name at line " + s.line);
				}
			}

			// print the type
			s._type.accept(this, scope);
			Type t1 = s._type;

			// print value if exists
			if (isValue) {
				Type t2 = s._value.accept(this, scope);

				// check primitive types.
				if (t1.isPrimitive || t2.isPrimitive) {
					// check that both are primitive and of the same type
					if (t1.isPrimitive && t2.isPrimitive && t1._typeName.equals(t2._typeName)) {
						return null;
					} else {
						throw new SemanticException("Assign type error at line " + stmt.line + " type 1: "
								+ t1._typeName + " type 2: " + t2._typeName);
					}
				}

				// check for types
				if (typeTable.checkSubTypes(t2._typeName, t1._typeName)) {
					// System.out.println("t2 inherits from t1");
					return null;
				} else {
					throw new SemanticException("Assign type error at line " + stmt.line + " type 1: " + t1._typeName
							+ " type 2: " + t2._typeName);
				}
			}
		} else	{
			throw new UnsupportedOperationException("Unexpected visit of Stmt  abstract class");
		}
		return null;

	}

	public Type visit(Expr expr, Integer scope) throws SemanticException {

		if (expr instanceof BinaryOpExpr) {
			Type t1, t2;

			BinaryOpExpr e = ((BinaryOpExpr) expr);
			t1 = visit(e.lhs, scope);
			t2 = visit(e.rhs, scope);

			// type checks and inference.

			// System.out.println(t1._typeName);
			// System.out.println(t2._typeName);

			// infer and return the type from the 2 types and operator.
			// may throw exceptions on inappropriate types.
			Type t = Type.TypeInferBinary(t1, t2, e.op, this.typeTable);
			e._type = t;
			return t;

		}

		// call
		else if (expr instanceof Call) {
			Type t = visit((Call) expr, scope);
			expr._type = t;
			return t;
		}

		// "this" expression
		else if (expr instanceof ExprThis) {
			// current method is static - cannot call this
			if (_currentMethod != null && _currentMethod.isStatic) {
				throw new SemanticException("cannot call \"this\" identifier from static scope.", expr.line);
			}

			// current method is virtual - resolve the type.
			Type t = typeTable.getType(_currentClassName);
			expr._type = t;
			return t;
		}

		else if (expr instanceof ExprLength) {
			ExprLength e = (ExprLength) expr;
			// System.out.println("Reference to array length");
			Type arr=e._expr.accept(this, scope);
			
			//check that e is really an array
			if (!arr._typeName.endsWith("[]")){
				throw new SemanticException(e.line + ": Error: Can't get length of a non-array variable");
				
			}

			// array length is considered as int.
			Type t = new Type(e.line, "int");
			expr._type = t;
			return t;
		}

		// Literals
		else if (expr instanceof LiteralBoolean) {
			LiteralBoolean e = ((LiteralBoolean) expr);
			// System.out.println("Boolean literal: " + e.value);
			Type t = new Type(e.line, "boolean");
			expr._type = t;
			return t;
		} else if (expr instanceof LiteralNull) {
			// System.out.println("Null literal");
			Type t = new Type(expr.line, "null");
			expr._type = t;
			return t;
		} else if (expr instanceof LiteralNumber) {
			LiteralNumber e = ((LiteralNumber) expr);
			// System.out.println("Integer literal: " + e.value);
			Type t = new Type(e.line, "int");
			expr._type = t;
			return t;
		} else if (expr instanceof LiteralString) {
			LiteralString e = (LiteralString) expr;
			// System.out.println("String literal: " + e.value);
			Type t = new Type(e.line, "string");
			expr._type = t;
			return t;
		}

		else if (expr instanceof Location) {
			Type t = visit((Location) expr, scope);

			// save the type inside AST.
			if (t != null && !t.isPrimitive) {
				// if not primitive, resolve the full type
				expr._type = typeTable.getType(t._typeName);
			} else {
				// primitive
				expr._type = t;
			}
			return t;
		}

		else if (expr instanceof UnaryOpExpr) {
			UnaryOpExpr e = (UnaryOpExpr) expr;

			// continue evaluating.
			Type t1 = e.operand.accept(this, scope);

			// infer type
			Type t = Type.TypeInferUnary(t1, e.op);
			expr._type = t;
			return t;
		}

		else if (expr instanceof NewClassInstance) {
			NewClassInstance instance = (NewClassInstance) expr;

			// check that type table has this type and return it .
			if (!typeTable.checkExist(instance._class_id)) {
				throw new SemanticException("unknown type: " + instance._class_id, expr.line);
			} else {
				Type t = typeTable.getType(instance._class_id);
				expr._type = t;
				return t;
			}
		} else if (expr instanceof NewArray) {
			// System.out.println("Array allocation");

			NewArray newArr = (NewArray) expr;

			Type size = newArr._arrSizeExpr.accept(this, scope);
			if (size == null) {
				throw new SemanticException("Illegal size for array", expr.line);
			} else if (!size._typeName.equals("int")) {
				throw new SemanticException("Subscript of array isn't an int", expr.line);
			}
			
			//array types are not considered primitive anymore
			newArr._type._typeName += "[]";
			newArr._type.isPrimitive = false;
			
			Type t = newArr._type;
			expr._type = t;
			return t;
		} else {
			throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
		}

	}

	public Type visit(Call cl, Integer scope) throws SemanticException {

		if (cl instanceof CallStatic) {
			CallStatic call = (CallStatic) cl;

			// check that class has a static method with this name.
			MethodBase m = typeTable.getMethod(call._classId, call._methodId);
			// check method exist and static
			if (m == null || !m.isStatic) {
				throw new SemanticException(
						"static method not found : " + call._methodId + ", for class: " + call._classId, call.line);
			}

			// evaluate arguments.
			// prepare a list for arguments types in this call.
			List<Type> argsTypes = new ArrayList<Type>();
			for (Expr f : call._arguments) {
				// resolve each argument's type , and push it to list.
				Type argType = f.accept(this, scope);
				argsTypes.add(argType);
			}

			// check validity of arguments for call.
			// (will throw if invalid).
			call.checkValidArgumentsForCall(m, argsTypes, typeTable);

			// return the method's return type.
			return m.returnVar.type;
		}

		else if (cl instanceof CallVirtual) {

			CallVirtual call = (CallVirtual) cl;
			// System.out.println("Call to virtual method: " + call._methodId);
			MethodBase m = null;
			Type instanceType = null; // the type of the caller.

			// instance (external) call.
			if (call._instanceExpr != null) {
				// resolve the type of the instance.
				instanceType = call._instanceExpr.accept(this, scope);

				// check that instance class has a virtual method with this
				// name.
				m = typeTable.getMethod(instanceType._typeName, call._methodId);
			} else {
				// this is not an instance call.
				// caller is "this".
				instanceType = typeTable.getType(_currentClassName);
				m = typeTable.getMethod(_currentClassName, call._methodId);
			}

			// check method exist and virtual
			if (m == null || m.isStatic) {
				throw new SemanticException(
						"virtual method not found : " + call._methodId + ", for class: " + instanceType._typeName,
						call.line);
			}

			// evaluate arguments.
			// prepare a list for arguments types in this call.
			List<Type> argsTypes = new ArrayList<Type>();
			for (Expr f : call._arguments) {
				// resolve each argument's type , and push it to list.
				Type argType = f.accept(this, scope);
				argsTypes.add(argType);
			}

			// check validity of arguments for call.
			// (will throw if invalid).
			call.checkValidArgumentsForCall(m, argsTypes, typeTable);

			// return the method's return type.
			return m.returnVar.type;

		}

		return null;

	}

	public Type visit(Location loc, Integer scope) throws SemanticException {
		// location expressions.
		// will throw on access to location before it is initialized.
		if (loc instanceof LocationArrSubscript) {
			LocationArrSubscript e = ((LocationArrSubscript) loc);

			// return the type of the array.
			Type arr = e._exprArr.accept(this, scope);

			if (arr == null) {
				throw new SemanticException("Incorrect access to array.", e.line);
			}

			// remove [] from type name
			int firstIndex = arr._typeName.indexOf("[");
			int lastIndex = arr._typeName.lastIndexOf("[");
			
			//if trying to subscript a non-array var
			if (firstIndex==-1 || lastIndex == -1){
				throw new SemanticException(e.line + ": Subscript access to a non array variable");
			}
			
			String basicTypeName = arr._typeName.substring(0, lastIndex);
			Type basicType = new Type(arr.line, basicTypeName);

			boolean arrayOfArrays;
			if (firstIndex != lastIndex) {
				arrayOfArrays = true;
				basicType.isPrimitive = false;
			} else {
				arrayOfArrays = false;
			}

			// on non primitive type AND not multiDim array
			// , get the type info from Type table.

			if (basicType != null && !basicType.isPrimitive && !arrayOfArrays) {
				basicType = typeTable.getType(basicTypeName);
			}


			// validate subscript expression will be checked for initialization.
			_checkInitialized = true;
			Type sub = e._exprSub.accept(this, scope);

			if (!sub._typeName.equals("int")) {
				throw new SemanticException("Illegal subscript access to array.", e.line);
			}
			return basicType;
		}

		else if (loc instanceof LocationExpressionMember) {
			// access to member of object instance.
			// example: m.x (where m is of type Moshe).
			// will type checks.

			LocationExpressionMember l = (LocationExpressionMember) loc;

			// we need to check that reference was initialized.
			// (the member was already init by default.)
			_checkInitialized = true;
			// get the type of instance.
			Type instanceType = l.expr.accept(this, scope);

			// get the full type from the typetable.
			instanceType = typeTable.getType(instanceType._typeName);

			// we need to check that instance has this field name,
			// (or super class has it).
			// then return its type.
			Field memberField = typeTable.getFieldOfInstance(instanceType._typeName, l.member);
			if (memberField != null) {
				return memberField.type;
			} else {
				throw new SemanticException(
						"try to access non-existing member \"" + l.member + "\" of class " + instanceType._typeName,
						l.line);
			}
		}

		else if (loc instanceof LocationId) {
			LocationId l = (LocationId) loc;
			// check that symbol exists in current scope in symbol table.
			if (l.name != null && symbolTable.checkAvailable(scope, l.name)) {

				// check initialization if needed.
				if (_checkInitialized && !symbolTable.checkInitialized(scope, l.name)) {
					throw new SemanticException("variable used before initialized: " + l.name, l.line);
				}

				// return the type from the sym.table.
				return symbolTable.getVariableType(scope, l.name);
			}

			// not in symbol table,
			// but it is a field in this class or its superclass.
			Field memberField = typeTable.getFieldOfInstance(_currentClassName, l.name);
			if (memberField != null) {
				return memberField.type;
			}

			// if reached here - reference to variable not found anywhere.
			throw new SemanticException(l.line+": Undefined variable " + l.name  );

		}
		System.out.println("returning NULL");
		return null;

	}

	@Override
	public Type visit(Method method, Integer scope) throws SemanticException {

		_currentMethod = method;

		// update return statement count.
		// 0 - if func is void.
		// 1 - otherwise.
		_currentMethodReturnBalance = method.IsReturnVoid() ? 0 : 1;


		if (!symbolTable.addVariable(scope, new VMethod(method.returnVar.frmName.name, scope, method.returnVar.type))) {
			throw new SemanticException("duplicate variable name: " + method.returnVar.frmName.name, method.line);
		}

		for (Formal f : method.frmls.formals) {
			// System.out.println(f.type);
			if (!symbolTable.addVariable(scope + 1, new VVariable(f.frmName.name, scope + 1, f.type, true))) {
				throw new SemanticException("duplicate variable name: " + f.frmName.name, method.line);
			}
		}

		// go into method body
		Type t = method.stmt_list.accept(this, scope);

		// validate that every branch inside method had a return statement.
		// this check is by count - expecting it to be zeroed.
		// (note: the types of the return statements themselves are already
		// checked- in ReturnStatement)
		if (!_currentMethod.IsReturnVoid() && _currentMethodReturnBalance > 0) {
			throw new SemanticException("method missing return statement in some of its execution paths.", method.line);
		}

		if (t == null) {
			// System.out.println("method finish");
			return null;
		} else if (t._typeName.equals("BREAK")) {
			throw new SemanticException( t.line+": break without while. ");
		} else if (t._typeName.equals("CONTINUE")) {
			throw new SemanticException(t.line + ": continue without while. ");
		}

		// scope's variables will be deleted in the end of stmtlist!

		return null;

	}

	@Override
	public Type visit(Type type, Integer scope) {
		return type;
	}

	@Override
	public Type visit(TypeArray array, Integer context) {
		return array;
	}

	@Override
	public Type visit(VarExpr varExpr, Integer context) {
		// do nothing
		return null;
	}

}
