package slp;

public class Method extends FieldMethod {

		String _methodName = null; 
		boolean isStatic;
		StmtList stmt_list;
		FormalsList frmls;
		
		public Method(String methodName, boolean isStatic, FormalsList frmls, StmtList stmt_list) {
			_methodName = new String (methodName);
			this.isStatic=isStatic;
			this.stmt_list=stmt_list;
			this.frmls=frmls;
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
