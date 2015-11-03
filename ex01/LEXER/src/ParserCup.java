
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Wed Nov 04 00:35:55 IST 2015
//----------------------------------------------------


/** CUP v0.11a beta 20060608 generated parser.
  * @version Wed Nov 04 00:35:55 IST 2015
  */
public class ParserCup extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public ParserCup() {super();}

  /** Constructor which sets the default scanner. */
  public ParserCup(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public ParserCup(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\030\000\002\002\013\000\002\002\004\000\002\003" +
    "\003\000\002\003\005\000\002\004\003\000\002\004\005" +
    "\000\002\005\003\000\002\005\005\000\002\006\010\000" +
    "\002\007\003\000\002\007\003\000\002\007\006\000\002" +
    "\007\005\000\002\007\004\000\002\007\005\000\002\007" +
    "\005\000\002\007\005\000\002\007\005\000\002\007\011" +
    "\000\002\010\005\000\002\010\005\000\002\010\005\000" +
    "\002\011\003\000\002\012\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\075\000\004\004\004\001\002\000\004\031\007\001" +
    "\002\000\004\002\006\001\002\000\004\002\000\001\002" +
    "\000\044\005\uffeb\006\uffeb\007\uffeb\010\uffeb\012\uffeb\013" +
    "\uffeb\014\uffeb\016\uffeb\017\uffeb\020\uffeb\021\uffeb\022\uffeb" +
    "\023\uffeb\024\uffeb\025\uffeb\026\uffeb\027\uffeb\001\002\000" +
    "\010\005\uffff\016\uffff\020\uffff\001\002\000\006\005\013" +
    "\016\012\001\002\000\004\031\007\001\002\000\004\031" +
    "\007\001\002\000\006\006\ufffb\016\ufffb\001\002\000\006" +
    "\006\071\016\070\001\002\000\004\017\017\001\002\000" +
    "\004\031\007\001\002\000\006\016\012\020\021\001\002" +
    "\000\004\021\022\001\002\000\014\011\030\017\024\024" +
    "\026\031\007\032\023\001\002\000\040\006\uffea\007\uffea" +
    "\010\uffea\012\uffea\013\uffea\014\uffea\016\uffea\020\uffea\021" +
    "\uffea\022\uffea\023\uffea\024\uffea\025\uffea\026\uffea\027\uffea" +
    "\001\002\000\014\011\030\017\024\024\026\031\007\032" +
    "\023\001\002\000\040\006\ufff8\007\ufff8\010\ufff8\012\ufff8" +
    "\013\ufff8\014\ufff8\016\ufff8\020\ufff8\021\ufff8\022\ufff8\023" +
    "\ufff8\024\ufff8\025\ufff8\026\ufff8\027\ufff8\001\002\000\014" +
    "\011\030\017\024\024\026\031\007\032\023\001\002\000" +
    "\042\006\ufff7\007\ufff7\010\ufff7\012\ufff7\013\ufff7\014\ufff7" +
    "\016\ufff7\017\057\020\ufff7\021\ufff7\022\ufff7\023\ufff7\024" +
    "\ufff7\025\ufff7\026\ufff7\027\ufff7\001\002\000\014\011\030" +
    "\017\024\024\026\031\007\032\023\001\002\000\016\006" +
    "\ufff9\016\ufff9\024\033\025\032\026\035\027\034\001\002" +
    "\000\014\011\030\017\024\024\026\031\007\032\023\001" +
    "\002\000\014\011\030\017\024\024\026\031\007\032\023" +
    "\001\002\000\014\011\030\017\024\024\026\031\007\032" +
    "\023\001\002\000\014\011\030\017\024\024\026\031\007" +
    "\032\023\001\002\000\040\006\ufff2\007\ufff2\010\ufff2\012" +
    "\ufff2\013\ufff2\014\ufff2\016\ufff2\020\ufff2\021\ufff2\022\ufff2" +
    "\023\ufff2\024\ufff2\025\ufff2\026\ufff2\027\ufff2\001\002\000" +
    "\040\006\ufff1\007\ufff1\010\ufff1\012\ufff1\013\ufff1\014\ufff1" +
    "\016\ufff1\020\ufff1\021\ufff1\022\ufff1\023\ufff1\024\ufff1\025" +
    "\ufff1\026\ufff1\027\ufff1\001\002\000\040\006\ufff0\007\ufff0" +
    "\010\ufff0\012\ufff0\013\ufff0\014\ufff0\016\ufff0\020\ufff0\021" +
    "\ufff0\022\ufff0\023\ufff0\024\ufff0\025\ufff0\026\035\027\034" +
    "\001\002\000\040\006\ufff3\007\ufff3\010\ufff3\012\ufff3\013" +
    "\ufff3\014\ufff3\016\ufff3\020\ufff3\021\ufff3\022\ufff3\023\ufff3" +
    "\024\ufff3\025\ufff3\026\035\027\034\001\002\000\020\021" +
    "\051\022\052\023\053\024\033\025\032\026\035\027\034" +
    "\001\002\000\004\012\044\001\002\000\014\011\030\017" +
    "\024\024\026\031\007\032\023\001\002\000\014\013\046" +
    "\024\033\025\032\026\035\027\034\001\002\000\014\011" +
    "\030\017\024\024\026\031\007\032\023\001\002\000\014" +
    "\014\050\024\033\025\032\026\035\027\034\001\002\000" +
    "\040\006\uffef\007\uffef\010\uffef\012\uffef\013\uffef\014\uffef" +
    "\016\uffef\020\uffef\021\uffef\022\uffef\023\uffef\024\uffef\025" +
    "\uffef\026\uffef\027\uffef\001\002\000\014\011\030\017\024" +
    "\024\026\031\007\032\023\001\002\000\014\011\030\017" +
    "\024\024\026\031\007\032\023\001\002\000\014\011\030" +
    "\017\024\024\026\031\007\032\023\001\002\000\014\012" +
    "\uffec\024\033\025\032\026\035\027\034\001\002\000\014" +
    "\012\uffed\024\033\025\032\026\035\027\034\001\002\000" +
    "\014\012\uffee\024\033\025\032\026\035\027\034\001\002" +
    "\000\014\011\030\017\024\024\026\031\007\032\023\001" +
    "\002\000\022\007\ufffd\010\ufffd\016\ufffd\020\ufffd\024\033" +
    "\025\032\026\035\027\034\001\002\000\006\016\062\020" +
    "\063\001\002\000\014\011\030\017\024\024\026\031\007" +
    "\032\023\001\002\000\040\006\ufff6\007\ufff6\010\ufff6\012" +
    "\ufff6\013\ufff6\014\ufff6\016\ufff6\020\ufff6\021\ufff6\022\ufff6" +
    "\023\ufff6\024\ufff6\025\ufff6\026\ufff6\027\ufff6\001\002\000" +
    "\022\007\ufffc\010\ufffc\016\ufffc\020\ufffc\024\033\025\032" +
    "\026\035\027\034\001\002\000\040\006\ufff4\007\ufff4\010" +
    "\ufff4\012\ufff4\013\ufff4\014\ufff4\016\ufff4\020\ufff4\021\ufff4" +
    "\022\ufff4\023\ufff4\024\ufff4\025\ufff4\026\ufff4\027\ufff4\001" +
    "\002\000\014\020\067\024\033\025\032\026\035\027\034" +
    "\001\002\000\040\006\ufff5\007\ufff5\010\ufff5\012\ufff5\013" +
    "\ufff5\014\ufff5\016\ufff5\020\ufff5\021\ufff5\022\ufff5\023\ufff5" +
    "\024\ufff5\025\ufff5\026\ufff5\027\ufff5\001\002\000\004\031" +
    "\007\001\002\000\014\011\030\017\024\024\026\031\007" +
    "\032\023\001\002\000\006\010\073\016\062\001\002\000" +
    "\014\011\030\017\024\024\026\031\007\032\023\001\002" +
    "\000\006\007\075\016\062\001\002\000\004\002\001\001" +
    "\002\000\006\006\ufffa\016\ufffa\001\002\000\010\005\ufffe" +
    "\016\ufffe\020\ufffe\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\075\000\004\002\004\001\001\000\006\003\010\011" +
    "\007\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\011" +
    "\076\001\001\000\010\005\014\006\013\011\015\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\006\003\017\011\007\001\001\000\002\001\001\000\002" +
    "\001\001\000\010\007\030\011\026\012\024\001\001\000" +
    "\002\001\001\000\010\007\065\011\026\012\024\001\001" +
    "\000\002\001\001\000\010\007\064\011\026\012\024\001" +
    "\001\000\002\001\001\000\012\007\041\010\042\011\026" +
    "\012\024\001\001\000\002\001\001\000\010\007\040\011" +
    "\026\012\024\001\001\000\010\007\037\011\026\012\024" +
    "\001\001\000\010\007\036\011\026\012\024\001\001\000" +
    "\010\007\035\011\026\012\024\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\010\007\044\011\026" +
    "\012\024\001\001\000\002\001\001\000\010\007\046\011" +
    "\026\012\024\001\001\000\002\001\001\000\002\001\001" +
    "\000\010\007\055\011\026\012\024\001\001\000\010\007" +
    "\054\011\026\012\024\001\001\000\010\007\053\011\026" +
    "\012\024\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\012\004\060\007\057\011\026\012\024" +
    "\001\001\000\002\001\001\000\002\001\001\000\010\007" +
    "\063\011\026\012\024\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\006\006\075\011\015\001\001\000\012\004\071" +
    "\007\057\011\026\012\024\001\001\000\002\001\001\000" +
    "\012\004\073\007\057\011\026\012\024\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$ParserCup$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$ParserCup$actions(this);
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
    return action_obj.CUP$ParserCup$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$ParserCup$actions {
  private final ParserCup parser;

  /** Constructor */
  CUP$ParserCup$actions(ParserCup parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$ParserCup$do_action(
    int                        CUP$ParserCup$act_num,
    java_cup.runtime.lr_parser CUP$ParserCup$parser,
    java.util.Stack            CUP$ParserCup$stack,
    int                        CUP$ParserCup$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$ParserCup$result;

      /* select the action based on the action number */
      switch (CUP$ParserCup$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // number ::= NUMBER 
            {
              Tnumber RESULT =null;
		int zleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int zright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		String z = (String)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tnumber(z); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("number",8, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // ident ::= ID 
            {
              Tident RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		String n = (String)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tident(n); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("ident",7, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // boolexp ::= exp LEQ exp 
            {
              Tboolexp RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texp l = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp r = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tboolexp(l,'!',r); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("boolexp",6, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // boolexp ::= exp LE exp 
            {
              Tboolexp RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texp l = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp r = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tboolexp(l,'<',r); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("boolexp",6, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // boolexp ::= exp EQ exp 
            {
              Tboolexp RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texp l = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp r = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tboolexp(l,'=',r); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("boolexp",6, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // exp ::= IF boolexp THEN exp ELSE exp FI 
            {
              Texp RESULT =null;
		int bleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).left;
		int bright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).right;
		Tboolexp b = (Tboolexp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).value;
		int tleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).right;
		Texp t = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).right;
		Texp e = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).value;
		 RESULT = new Tifthenelse(b,t,e); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-6)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // exp ::= exp MINUS exp 
            {
              Texp RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texp l = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp r = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Texpinfix(l,'-',r); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // exp ::= exp DIV exp 
            {
              Texp RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texp l = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp r = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Texpinfix(l,'/',r); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // exp ::= exp TIMES exp 
            {
              Texp RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texp l = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp r = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Texpinfix(l,'*',r); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // exp ::= exp PLUS exp 
            {
              Texp RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texp l = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp r = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Texpinfix(l,'+',r); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // exp ::= MINUS exp 
            {
              Texp RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp e = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tuminus(e); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // exp ::= LPAR exp RPAR 
            {
              Texp RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).right;
		Texp e = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).value;
		 RESULT = e; 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // exp ::= ident LPAR explist RPAR 
            {
              Texp RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).right;
		Tident i = (Tident)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).right;
		Texplist e = (Texplist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).value;
		 RESULT = new Tfun(i,e); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // exp ::= ident 
            {
              Texp RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Tident i = (Tident)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = i; 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // exp ::= number 
            {
              Texp RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Tnumber n = (Tnumber)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = n; 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("exp",5, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // dekl ::= ident LPAR parlist RPAR EQ exp 
            {
              Tdekl RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).right;
		Tident i = (Tident)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).value;
		int pleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).right;
		Tparlist p = (Tparlist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp e = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tdekl(i,p,e); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("dekl",4, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // dekllist ::= dekllist COMMA dekl 
            {
              Tdekllist RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Tdekllist l = (Tdekllist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int dleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int dright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Tdekl d = (Tdekl)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tdekllist(l,d); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("dekllist",3, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // dekllist ::= dekl 
            {
              Tdekllist RESULT =null;
		int dleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int dright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Tdekl d = (Tdekl)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tdekllist(d);
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("dekllist",3, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // explist ::= explist COMMA exp 
            {
              Texplist RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Texplist l = (Texplist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp e = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Texplist(l,e); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("explist",2, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // explist ::= exp 
            {
              Texplist RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Texp e = (Texp)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Texplist(e); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("explist",2, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // parlist ::= parlist COMMA ident 
            {
              Tparlist RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).right;
		Tparlist p = (Tparlist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)).value;
		int ileft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Tident i = (Tident)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tparlist(p,i); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("parlist",1, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-2)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // parlist ::= ident 
            {
              Tparlist RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()).right;
		Tident i = (Tident)((java_cup.runtime.Symbol) CUP$ParserCup$stack.peek()).value;
		 RESULT = new Tparlist(i); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("parlist",1, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= program EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).right;
		Tprogram start_val = (Tprogram)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).value;
		RESULT = start_val;
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$ParserCup$parser.done_parsing();
          return CUP$ParserCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // program ::= INPUT parlist FUNCTIONS dekllist OUTPUT explist ARGUMENTS explist END 
            {
              Tprogram RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-7)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-7)).right;
		Tparlist p = (Tparlist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-7)).value;
		int dleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).left;
		int dright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).right;
		Tdekllist d = (Tdekllist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-5)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).right;
		Texplist o = (Texplist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-3)).value;
		int aleft = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).left;
		int aright = ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).right;
		Texplist a = (Texplist)((java_cup.runtime.Symbol) CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-1)).value;
		 RESULT = new Tprogram(p,d,o,a); 
              CUP$ParserCup$result = parser.getSymbolFactory().newSymbol("program",0, ((java_cup.runtime.Symbol)CUP$ParserCup$stack.elementAt(CUP$ParserCup$top-8)), ((java_cup.runtime.Symbol)CUP$ParserCup$stack.peek()), RESULT);
            }
          return CUP$ParserCup$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

