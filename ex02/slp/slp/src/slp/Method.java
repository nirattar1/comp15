package slp;

import java.util.List;

public class Method extends FieldMethod {

	Formal f;
	public boolean isStatic;
	List<Stmt> stmt_list;
	FormalsList frmls;

	

	public Method(Formal f,  FormalsList frmls, List<Stmt> stmt_list) {
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
