package slp;


public class Method extends MethodBase {

	StmtList stmt_list;
	public Method(int line, Formal f,  FormalsList frmls, StmtList stmt_list) {
		super(line);
		this.returnVar=f;
		this.stmt_list = stmt_list;
		this.frmls = frmls;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) throws SemanticException {
		return visitor.visit(this, context);
	}
}
