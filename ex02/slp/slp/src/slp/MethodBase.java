package slp;

public abstract class MethodBase extends FieldMethod {

	public Formal returnVar;	//a method's name is inside the return formal
	public boolean isStatic;
	protected FormalsList frmls;

	public MethodBase(int line) {
		super(line);
	}

	public String getName ()
	{
		return returnVar.frmName.name;
	}


}