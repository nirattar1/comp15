package slp;


/**
 * A helper type for LIR translation.	
 * 	It will be propagated up the AST,
 *  during the LIR recursive translation.
 *  Its purpose is to store the type and name of register
 *  where a sub-expression result is saved.
 * @author NAttar
 *
 */
public class LIRResult 
{
	
	public enum RegisterType 
	{
		REGTYPE_TEMP_SIMPLE,	//simple temporary - i.e. result of 2+2 
		REGTYPE_TEMP_ARRAYSUB //temporary for array access Reg[Reg]
	}
		
	private RegisterType 	_regType;
	private String 			_regName;
	private int 			_regCount;
	
	public LIRResult(RegisterType _regType, String _regName, int _regCount) 
	{
		super();
		this._regType = _regType;
		this._regName = _regName;
		this._regCount = _regCount;
	}
	public LIRResult(LIRResult other) 
	{
		super();
		this._regType = other._regType;
		this._regName = other._regName;
		this._regCount = other._regCount;
	}
	
	public RegisterType get_regType() {
		return _regType;
	}
	public void set_regType(RegisterType _regType) {
		this._regType = _regType;
	}
	public String get_regName() {
		return _regName;
	}



	public void set_regName(String _regName) {
		this._regName = _regName;
	}

	
	public int get_regCount() {
		return _regCount;
	}


	public void set_regCount(int _regCount) {
		this._regCount = _regCount;
	}
	
	
}
