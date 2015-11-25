package slp;

public class Method extends FieldMethod {

	String _methodName = null;
	TypeVoid typeVoid;
	boolean isStatic;
	StmtList stmt_list;
	FormalsList frmls;

	

	public Method(String _methodName, TypeVoid typeVoid, boolean isStatic, StmtList stmt_list, FormalsList frmls) {
		this._methodName = _methodName;
		this.typeVoid = typeVoid;
		this.isStatic = isStatic;
		this.stmt_list = stmt_list;
		this.frmls = frmls;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}

}
