package slp;

import java.util.List;
import java.util.ArrayList;

public class FormalsList extends ASTNode{

	/** An AST node for a list of formals.
	 */
		public final List<Formal> formals = new ArrayList<Formal>();
		
		public FormalsList(Formal frml) {
			formals.add(frml);
		}

		/** Adds a statement to the tail of the list.
		 * 
		 * @param frml A program statement.
		 */
		public void addFrml(Formal frml) {
			formals.add(frml);
		}

		/** Accepts a visitor object as part of the visitor pattern.
		 * @param visitor A visitor.
		 */
		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
		
		/** Accepts a propagating visitor parameterized by two types.
		 * 
		 * @param <DownType> The type of the object holding the context.
		 * @param <UpType> The type of the result object.
		 * @param visitor A propagating visitor.
		 * @param context An object holding context information.
		 * @return The result of visiting this node.
		 */
		@Override
		public <DownType, UpType> UpType accept(
				PropagatingVisitor<DownType, UpType> visitor, DownType context) {
			return visitor.visit(this, context);
		}	
	}

