package slp;

public class Class extends ASTNode {

		String _className = null; 
		
		public Class(String _className) {
			_className = new String (_className);
		}

		@Override
		public void accept(Visitor visitor) {
			// TODO Auto-generated method stub

		}

		@Override
		public <DownType, UpType> UpType accept(PropagatingVisitor<DownType, UpType> visitor, DownType context) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	
}
