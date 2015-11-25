package slp;

public class Method extends FieldMethod {

	Formal f;
	public boolean isStatic;
	StmtList stmt_list;
	FormalsList frmls;

	

	public Method(String _methodName,Formal f, StmtList stmt_list, FormalsList frmls) {
		this.f=f;
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
