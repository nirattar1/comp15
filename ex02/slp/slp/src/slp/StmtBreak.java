package slp;

public class StmtBreak extends Stmt {

	public StmtBreak(int line) {
		super (line);
	}

	@Override
	public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		// TODO Auto-generated method stub
		return null;
	}

}
