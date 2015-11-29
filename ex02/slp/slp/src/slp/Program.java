package slp;

import java.util.ArrayList;
import java.util.List;

public class Program extends ASTNode {

	
	List<Class> classList;

	public Program(List<Class> classList) {
		
		this.classList = classList;
	}
	public Program() {
		
		this.classList = new ArrayList<Class>();;
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

