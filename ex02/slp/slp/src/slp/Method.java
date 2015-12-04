package slp;

import java.util.List;

public class Method extends FieldMethod {

	Formal f;		//return type of method
	public boolean isStatic;
	StmtList stmt_list;
	FormalsList frmls;

	

	public Method(int line, Formal f,  FormalsList frmls, StmtList stmt_list) {
		super(line);
		this.f=f;
		this.stmt_list = stmt_list;
		this.frmls = frmls;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) throws SemanticException {
		return visitor.visit(this, context);
	}

}
