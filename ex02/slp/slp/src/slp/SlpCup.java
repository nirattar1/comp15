
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Tue Nov 24 18:56:50 IST 2015
//----------------------------------------------------

package slp;

import java_cup.runtime.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Tue Nov 24 18:56:50 IST 2015
  */
public class SlpCup extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public SlpCup() {super();}

  /** Constructor which sets the default scanner. */
  public SlpCup(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public SlpCup(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\024\000\002\004\003\000\002\002\004\000\002\004" +
    "\004\000\002\003\006\000\002\003\007\000\002\002\005" +
    "\000\002\002\005\000\002\002\005\000\002\002\005\000" +
    "\002\002\005\000\002\002\005\000\002\002\005\000\002" +
    "\002\005\000\002\002\005\000\002\002\005\000\002\002" +
    "\004\000\002\002\005\000\002\002\003\000\002\002\005" +
    "\000\002\002\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\054\000\006\023\005\025\004\001\002\000\004\013" +
    "\054\001\002\000\004\010\012\001\002\000\010\002\001" +
    "\023\001\025\001\001\002\000\010\002\011\023\005\025" +
    "\004\001\002\000\010\002\uffff\023\uffff\025\uffff\001\002" +
    "\000\004\002\000\001\002\000\014\005\015\010\016\022" +
    "\020\024\014\025\013\001\002\000\032\004\uffee\005\uffee" +
    "\006\uffee\007\uffee\011\uffee\012\uffee\014\uffee\015\uffee\016" +
    "\uffee\017\uffee\020\uffee\021\uffee\001\002\000\032\004\ufff0" +
    "\005\ufff0\006\ufff0\007\ufff0\011\ufff0\012\ufff0\014\ufff0\015" +
    "\ufff0\016\ufff0\017\ufff0\020\ufff0\021\ufff0\001\002\000\014" +
    "\005\015\010\016\022\020\024\014\025\013\001\002\000" +
    "\014\005\015\010\016\022\020\024\014\025\013\001\002" +
    "\000\030\004\027\005\023\006\026\007\033\011\034\014" +
    "\032\015\035\016\025\017\031\020\024\021\030\001\002" +
    "\000\004\010\021\001\002\000\004\011\022\001\002\000" +
    "\032\004\uffef\005\uffef\006\uffef\007\uffef\011\uffef\012\uffef" +
    "\014\uffef\015\uffef\016\uffef\017\uffef\020\uffef\021\uffef\001" +
    "\002\000\014\005\015\010\016\022\020\024\014\025\013" +
    "\001\002\000\014\005\015\010\016\022\020\024\014\025" +
    "\013\001\002\000\014\005\015\010\016\022\020\024\014" +
    "\025\013\001\002\000\014\005\015\010\016\022\020\024" +
    "\014\025\013\001\002\000\014\005\015\010\016\022\020" +
    "\024\014\025\013\001\002\000\014\005\015\010\016\022" +
    "\020\024\014\025\013\001\002\000\014\005\015\010\016" +
    "\022\020\024\014\025\013\001\002\000\014\005\015\010" +
    "\016\022\020\024\014\025\013\001\002\000\014\005\015" +
    "\010\016\022\020\024\014\025\013\001\002\000\004\012" +
    "\037\001\002\000\014\005\015\010\016\022\020\024\014" +
    "\025\013\001\002\000\032\004\ufff6\005\ufff6\006\ufff6\007" +
    "\ufff6\011\ufff6\012\ufff6\014\ufff6\015\ufff6\016\ufff6\017\ufff6" +
    "\020\ufff6\021\ufff6\001\002\000\010\002\ufffd\023\ufffd\025" +
    "\ufffd\001\002\000\032\004\ufff9\005\ufff9\006\ufff9\007\ufff9" +
    "\011\ufff9\012\ufff9\014\032\015\035\016\025\017\031\020" +
    "\ufff9\021\ufff9\001\002\000\032\004\ufff5\005\ufff5\006\ufff5" +
    "\007\ufff5\011\ufff5\012\ufff5\014\ufff5\015\ufff5\016\ufff5\017" +
    "\ufff5\020\ufff5\021\ufff5\001\002\000\032\004\ufff4\005\ufff4" +
    "\006\ufff4\007\ufff4\011\ufff4\012\ufff4\014\ufff4\015\ufff4\016" +
    "\ufff4\017\ufff4\020\ufff4\021\ufff4\001\002\000\032\004\027" +
    "\005\023\006\026\007\033\011\ufff7\012\ufff7\014\032\015" +
    "\035\016\025\017\031\020\024\021\ufff7\001\002\000\032" +
    "\004\ufffc\005\ufffc\006\026\007\033\011\ufffc\012\ufffc\014" +
    "\032\015\035\016\025\017\031\020\ufffc\021\ufffc\001\002" +
    "\000\032\004\ufffa\005\ufffa\006\ufffa\007\ufffa\011\ufffa\012" +
    "\ufffa\014\032\015\035\016\025\017\031\020\ufffa\021\ufffa" +
    "\001\002\000\032\004\ufff3\005\ufff3\006\ufff3\007\ufff3\011" +
    "\ufff3\012\ufff3\014\ufff3\015\ufff3\016\ufff3\017\ufff3\020\ufff3" +
    "\021\ufff3\001\002\000\032\004\027\005\023\006\026\007" +
    "\033\011\ufff8\012\ufff8\014\032\015\035\016\025\017\031" +
    "\020\ufff8\021\ufff8\001\002\000\032\004\ufffb\005\ufffb\006" +
    "\026\007\033\011\ufffb\012\ufffb\014\032\015\035\016\025" +
    "\017\031\020\ufffb\021\ufffb\001\002\000\030\004\027\005" +
    "\023\006\026\007\033\011\052\014\032\015\035\016\025" +
    "\017\031\020\024\021\030\001\002\000\032\004\ufff1\005" +
    "\ufff1\006\ufff1\007\ufff1\011\ufff1\012\ufff1\014\ufff1\015\ufff1" +
    "\016\ufff1\017\ufff1\020\ufff1\021\ufff1\001\002\000\032\004" +
    "\ufff2\005\ufff2\006\ufff2\007\ufff2\011\ufff2\012\ufff2\014\032" +
    "\015\035\016\025\017\031\020\ufff2\021\ufff2\001\002\000" +
    "\014\005\015\010\016\022\020\024\014\025\013\001\002" +
    "\000\030\004\027\005\023\006\026\007\033\012\056\014" +
    "\032\015\035\016\025\017\031\020\024\021\030\001\002" +
    "\000\010\002\ufffe\023\ufffe\025\ufffe\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\054\000\006\003\005\004\006\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\003\007" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\002" +
    "\016\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\002\052\001\001\000\004\002\050\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\002\047\001\001\000\004\002\046\001\001\000" +
    "\004\002\045\001\001\000\004\002\044\001\001\000\004" +
    "\002\043\001\001\000\004\002\042\001\001\000\004\002" +
    "\041\001\001\000\004\002\040\001\001\000\004\002\037" +
    "\001\001\000\002\001\001\000\004\002\035\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\002\054\001\001\000\002\001\001" +
    "\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$SlpCup$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$SlpCup$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$SlpCup$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


  /** Scan to get the next Symbol. */
  public java_cup.runtime.Symbol scan()
    throws java.lang.Exception
    {

	Token t = lexer.next_token();
	if (printTokens)
		System.out.println(t.getLine() + ":" + t);
	return t; 

    }


	/** Causes the parsr to print every token it reads.
	 * This is useful for debugging.
	 */
	public boolean printTokens;
	
	private Lexer lexer;

	public Parser(Lexer lexer) {
		super(lexer);
		this.lexer = lexer;
	}
	
	public int getLine() {
		return lexer.getLineNumber();
	}
	
	public void syntax_error(Symbol s) {
		Token tok = (Token) s;
		System.out.println("Line " + tok.getLine()+": Syntax error; unexpected " + tok);
	}

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$SlpCup$actions {
  private final SlpCup parser;

  /** Constructor */
  CUP$SlpCup$actions(SlpCup parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$SlpCup$do_action(
    int                        CUP$SlpCup$act_num,
    java_cup.runtime.lr_parser CUP$SlpCup$parser,
    java.util.Stack            CUP$SlpCup$stack,
    int                        CUP$SlpCup$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$SlpCup$result;

      /* select the action based on the action number */
      switch (CUP$SlpCup$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // expr ::= VAR 
            {
              Expr RESULT =null;
		int vleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int vright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		String v = (String)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new VarExpr(v); 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // expr ::= READI LPAREN RPAREN 
            {
              Expr RESULT =null;
		 RESULT = new ReadIExpr(); 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // expr ::= NUMBER 
            {
              Expr RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Integer n = (Integer)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new NumberExpr(n.intValue()); 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // expr ::= LPAREN expr RPAREN 
            {
              Expr RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).right;
		Expr e = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).value;
		 RESULT = e; 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // expr ::= MINUS expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new UnaryOpExpr(e1, Operator.MINUS); 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // expr ::= expr GE expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.GE);
		   System.out.println("Reduced rule e1 >= e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // expr ::= expr LE expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.LE);
		   System.out.println("Reduced rule e1 <= e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // expr ::= expr GT expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.GT);
		   System.out.println("Reduced rule e1 > e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // expr ::= expr LT expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.LT);
		   System.out.println("Reduced rule e1 < e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // expr ::= expr LOR expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.LOR);
		   System.out.println("Reduced rule e1 || e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // expr ::= expr LAND expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.LAND);
		   System.out.println("Reduced rule e1 && e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // expr ::= expr DIV expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.DIV);
		   System.out.println("Reduced rule e1 / e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // expr ::= expr MULT expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.MULT);
		   System.out.println("Reduced rule e1 * e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // expr ::= expr MINUS expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.MINUS);
		   System.out.println("Reduced rule e1 - e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // expr ::= expr PLUS expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		int pleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).right;
		Object p = (Object)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, Operator.PLUS);
		   System.out.println("Reduced rule e1 + e2 for e1=" + e1 + " and e2="+e2);
		 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // stmt ::= PRINT LPAREN expr RPAREN SEMI 
            {
              Stmt RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).right;
		Expr e = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-2)).value;
		 RESULT = new PrintStmt(e); 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("stmt",1, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-4)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // stmt ::= VAR ASSIGN expr SEMI 
            {
              Stmt RESULT =null;
		int vleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-3)).left;
		int vright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-3)).right;
		String v = (String)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-3)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).right;
		Expr e = (Expr)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).value;
		 VarExpr ve = new VarExpr(v); RESULT = new AssignStmt(ve, e); 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("stmt",1, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-3)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // stmt_list ::= stmt_list stmt 
            {
              StmtList RESULT =null;
		int slleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).left;
		int slright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).right;
		StmtList sl = (StmtList)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Stmt s = (Stmt)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 sl.addStmt(s); RESULT = sl; 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("stmt_list",2, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= stmt_list EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).right;
		StmtList start_val = (StmtList)((java_cup.runtime.Symbol) CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)).value;
		RESULT = start_val;
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.elementAt(CUP$SlpCup$top-1)), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$SlpCup$parser.done_parsing();
          return CUP$SlpCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // stmt_list ::= stmt 
            {
              StmtList RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()).right;
		Stmt s = (Stmt)((java_cup.runtime.Symbol) CUP$SlpCup$stack.peek()).value;
		 RESULT = new StmtList(s); 
              CUP$SlpCup$result = parser.getSymbolFactory().newSymbol("stmt_list",2, ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$SlpCup$stack.peek()), RESULT);
            }
          return CUP$SlpCup$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

