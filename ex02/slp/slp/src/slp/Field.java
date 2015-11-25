package slp;

import java.util.List;

public class Field extends FieldMethod{
	Type type;
	List<VarExpr> idList;
	
	
	public Field(Type type, List<VarExpr> idList) {
		this.type = type;
		this.idList = idList;
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
