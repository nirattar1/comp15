package slp;

public abstract class MethodBase extends FieldMethod {

	public Formal returnVar;
	public boolean isStatic;
	protected FormalsList frmls;

	public MethodBase(int line) {
		super(line);
	}



}