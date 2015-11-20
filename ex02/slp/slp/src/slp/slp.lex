package slp;

import java_cup.runtime.*;

%%

%cup
%class Lexer
%type Token
%line
%scanerror RuntimeException

%{
	public int getLineNumber() { return yyline+1; }
%}


/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
INTEGER			= 0 | [1-9][0-9]*
IDENTIFIER		= [A-Za-z_][A-Za-z_0-9]*   
QuotedString	= \" ( [^\"\\] | \\\\ | \\\" | \\t | \\n)* \"
//CLASS_ID        = [A-Z][A-Za-z_0-9]*
//COMMENT			= "/*"(.|{LineTerminator})*("*/") | "//".*{LineTerminator}
UNFINISHED_COMMENT = "/*"|"*/" 

//Default rule - shortest, should match when any other didn't
//also handles unfinished comments
//MYDEFAULT = .|{LineTerminator}


%%

//{COMMENT}			{/* just skip what was found, do nothing */ } 
"+"					{ System.out.println((1+yyline)+": PLUS");     return new Token(yyline, yytext(), sym.PLUS);}
					
"="                 { System.out.println((1+yyline)+": ASSIGN");    return new Token(yyline, yytext(), sym.ASSIGN);}
"boolean"           { System.out.println((1+yyline)+": BOOLEAN");    return new Token(yyline, yytext(), sym.BOOLEAN);}
"break"           	{ System.out.println((1+yyline)+ ": BREAK");    return new Token(yyline, yytext(), sym.BREAK);}
"class"          	{ System.out.println((1+yyline)+ ": CLASS");    return new Token(yyline, yytext(), sym.CLASS);}

","          		{ System.out.println((1+yyline)+": COMMA");    return new Token(yyline, yytext(), sym.COMMA);}
"continue"          { System.out.println((1+yyline) +": CONTINUE");    return new Token(yyline, yytext(), sym.CONTINUE);}
"."           		{ System.out.println((1+yyline) +": DOT");    return new Token(yyline, yytext(), sym.DOT);}
"/"					{ System.out.println((1+yyline) +": DIV ");    return new Token(yyline, yytext(), sym.DIV);}
"=="         		{ System.out.println((1+yyline) +": EQUAL");    return new Token(yyline, yytext(), sym.EQUAL);}
"extends"     		{ System.out.println((1+yyline)+ ": EXTENDS");    return new Token(yyline, yytext(), sym.EXTENDS);}
"else"     			{ System.out.println((1+yyline) +": ELSE");    return new Token(yyline, yytext(), sym.ELSE);}
"false"           	{ System.out.println((1+yyline)+ ": FALSE");    return new Token(yyline, yytext(), sym.FALSE);}
">"           		{ System.out.println((1+yyline)+": GT");    return new Token(yyline, yytext(), sym.GT);}
">="           		{ System.out.println((1+yyline)+": GE");    return new Token(yyline, yytext(), sym.GE);}

"if"           		{ System.out.println((1+yyline) +": IF");    return new Token(yyline, yytext(), sym.IF);}
"int"           	{ System.out.println((1+yyline)+": INT");    return new Token(yyline, yytext(), sym.INT);}
"&&"           	{ System.out.println((1+yyline)+": LAND");    return new Token(yyline, yytext(), sym.LAND);}
"["           	{ System.out.println((1+yyline)+": LB");    return new Token(yyline, yytext(), sym.LB);}
"("           	{ System.out.println((1+yyline)+": LPAREN");    return new Token(yyline, yytext(), sym.LPAREN);}
"{"           	{ System.out.println((1+yyline)+": LCBR");    return new Token(yyline, yytext(), sym.LCBR);}
"length"           	{ System.out.println((1+yyline)+": LENGTH");    return new Token(yyline, yytext(), sym.LENGTH);}
"new"           	{ System.out.println((1+yyline)+": NEW");    return new Token(yyline, yytext(), sym.NEW);}
"!"           	{ System.out.println((1+yyline)+": LNEG");    return new Token(yyline, yytext(), sym.LNEG);}
"||"           	{ System.out.println((1+yyline)+": LOR");    return new Token(yyline, yytext(), sym.LOR);}
"<"           	{ System.out.println((1+yyline)+": LT");    return new Token(yyline, yytext(), sym.LT);}
"<="           	{ System.out.println((1+yyline)+": LE");    return new Token(yyline, yytext(), sym.LE);}
"-"           	{ System.out.println((1+yyline)+": MINUS");    return new Token(yyline, yytext(), sym.MINUS);}
"%"           	{ System.out.println((1+yyline)+": MOD");    return new Token(yyline, yytext(), sym.MOD);}
"*"           	{ System.out.println((1+yyline)+": MULT");    return new Token(yyline, yytext(), sym.MULT);}
"!="           		{ System.out.println((1+yyline) +": NEQUAL");    return new Token(yyline, yytext(), sym.NEQUAL);}
"null"           	{ System.out.println((1+yyline) +": NULL");    return new Token(yyline, yytext(), sym.NULL);}
"]"          	 	{ System.out.println((1+yyline) + ": RB");    return new Token(yyline, yytext(), sym.RB);}
"}"          	 	{ System.out.println((1+yyline) +": RCBR");    return new Token(yyline, yytext(), sym.RCBR);}
"return"       	 	{ System.out.println((1+yyline)+": RETURN");    return new Token(yyline, yytext(), sym.RETURN);}
")" 	      	 	{ System.out.println((1+yyline)+": RPAREN");    return new Token(yyline, yytext(), sym.RPAREN);}
";" 	      	 	{ System.out.println((1+yyline)+": SEMI");    return new Token(yyline, yytext(), sym.SEMI);}
"static" 			{ System.out.println((1+yyline) +": STATIC");	return new Token(yyline, yytext(), sym.STATIC);}
"string" 			{ System.out.println((1+yyline) +": STRING");	return new Token(yyline, yytext(), sym.STRING);}
{QuotedString}		{
						System.out.print((1+yyline) +": QUOTE(");
						System.out.print(yytext());
						System.out.println(")");
						return new Token(yyline, yytext(), sym.QUOTE, new String(yytext()));
					}
"this" 				{ System.out.println((1+yyline) +": THIS");	return new Token(yyline, yytext(), sym.THIS);}
"true" 				{ System.out.println((1+yyline) +": TRUE");	return new Token(yyline, yytext(), sym.TRUE);}
"void" 				{ System.out.println((1+yyline) +": VOID");	return new Token(yyline, yytext(), sym.VOID);}
"while"				{ System.out.println((1+yyline) +": WHILE"); return new Token(yyline, yytext(), sym.WHILE);}

"readi" 	{ return new Token(yyline, yytext(), sym.READI); }
"print" 	{ return new Token(yyline, yytext(), sym.PRINT); }

{INTEGER}			{	return new Token(yyline, "NUMBER", sym.NUMBER, new Integer(yytext())); }   
{IDENTIFIER}		{

						return new Token(yyline, "VAR", sym.VAR, yytext());
					}
/*{CLASS_ID}		  	{
	System.out.print((1+yyline)+": CLASS_ID(");
	System.out.print(yytext());
	System.out.println(")");
	return new Token(yyline, yytext(), sym.CLASS_ID, new String(yytext()));
}*/
{WhiteSpace}		{ /* just skip what was found, do nothing */ }





/*{MYDEFAULT}	{
	//if default rule was matched, report error.
	System.err.println((1+yyline)+": Lexical error: illegal character '"+ yytext()+"'"); 
	System.exit(0);
}*/

{UNFINISHED_COMMENT} {
	System.err.println((1+yyline)+": Lexical error: Unfinished comment."); 
	System.exit(0);

}

"//".*{LineTerminator} { }
. 			{ throw new RuntimeException("Illegal character at line " + (yyline+1) + " : '" + yytext() + "'"); }

<<EOF>> 	{ return new Token(yyline, "EOF", sym.EOF); }