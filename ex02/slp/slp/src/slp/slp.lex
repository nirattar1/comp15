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
CLASS_ID        = [A-Z][A-Za-z_0-9]*
COMMENT			= "/*"(.|{LineTerminator})*("*/") | "//".*{LineTerminator}
UNFINISHED_COMMENT = "/*"|"*/" 

//Default rule - shortest, should match when any other didn't
//also handles unfinished comments
MYDEFAULT = .|{LineTerminator}


%%

{COMMENT}			{/* just skip what was found, do nothing */ } 
"+"					{ return new Token(yyline, yytext(), sym.PLUS);}
					
"="                 { return new Token(yyline, yytext(), sym.ASSIGN);}
"boolean"           { return new Token(yyline, yytext(), sym.BOOLEAN);}
"break"           	{ return new Token(yyline, yytext(), sym.BREAK);}
"class"          	{ return new Token(yyline, yytext(), sym.CLASS);}

","          		{ return new Token(yyline, yytext(), sym.COMMA);}
"continue"          { return new Token(yyline, yytext(), sym.CONTINUE);}
"."           		{ return new Token(yyline, yytext(), sym.DOT);}
"/"					{ return new Token(yyline, yytext(), sym.DIV);}
"=="         		{ return new Token(yyline, yytext(), sym.EQUAL);}
"extends"     		{ return new Token(yyline, yytext(), sym.EXTENDS);}
"else"     			{ return new Token(yyline, yytext(), sym.ELSE);}
"false"           	{ return new Token(yyline, yytext(), sym.FALSE);}
">"           		{ return new Token(yyline, yytext(), sym.GT);}
">="           		{ return new Token(yyline, yytext(), sym.GE);}

"if"           		{ return new Token(yyline, yytext(), sym.IF);}
"int"           	{ return new Token(yyline, yytext(), sym.INT);}
"&&"           	{ return new Token(yyline, yytext(), sym.LAND);}
"["           	{ return new Token(yyline, yytext(), sym.LB);}
"("           	{ return new Token(yyline, yytext(), sym.LPAREN);}
"{"           	{ return new Token(yyline, yytext(), sym.LCBR);}
"length"           	{ return new Token(yyline, yytext(), sym.LENGTH);}
"new"           	{ return new Token(yyline, yytext(), sym.NEW);}
"!"           	{ return new Token(yyline, yytext(), sym.LNEG);}
"||"           	{ return new Token(yyline, yytext(), sym.LOR);}
"<"           	{ return new Token(yyline, yytext(), sym.LT);}
"<="           	{ return new Token(yyline, yytext(), sym.LE);}
"-"           	{ return new Token(yyline, yytext(), sym.MINUS);}
"%"           	{ return new Token(yyline, yytext(), sym.MOD);}
"*"           	{ return new Token(yyline, yytext(), sym.MULT);}
"!="           		{ return new Token(yyline, yytext(), sym.NEQUAL);}
"null"           	{ return new Token(yyline, yytext(), sym.NULL);}
"]"          	 	{ return new Token(yyline, yytext(), sym.RB);}
"}"          	 	{ return new Token(yyline, yytext(), sym.RCBR);}
"return"       	 	{ return new Token(yyline, yytext(), sym.RETURN);}
")" 	      	 	{ return new Token(yyline, yytext(), sym.RPAREN);}
";" 	      	 	{ return new Token(yyline, yytext(), sym.SEMI);}
"static" 			{ return new Token(yyline, yytext(), sym.STATIC);}
"string" 			{ return new Token(yyline, yytext(), sym.STRING);}
{QuotedString}		{
						return new Token(yyline, yytext(), sym.QUOTE, new String(yytext()));
					}
"this" 				{ return new Token(yyline, yytext(), sym.THIS);}
"true" 				{ return new Token(yyline, yytext(), sym.TRUE);}
"void" 				{ return new Token(yyline, yytext(), sym.VOID);}
"while"				{ return new Token(yyline, yytext(), sym.WHILE);}

//"readi" 	{ return new Token(yyline, yytext(), sym.READI); }
//"print" 	{ return new Token(yyline, yytext(), sym.PRINT); }

{INTEGER}			{	return new Token(yyline, "NUMBER", sym.NUMBER, new Integer(yytext())); }   
{CLASS_ID}		  	
			{
				return new Token(yyline, yytext(), sym.CLASS_ID, new String(yytext()));
			}

{IDENTIFIER}		{

						return new Token(yyline, "VAR", sym.VAR, yytext());
					}

{WhiteSpace}		{ /* just skip what was found, do nothing */ }





{MYDEFAULT}	{
	//if default rule was matched, report error.
	System.err.println((1+yyline)+": Lexical error: illegal character '"+ yytext()+"'"); 
	System.exit(0);
}

{UNFINISHED_COMMENT} {
	System.err.println((1+yyline)+": Lexical error: Unfinished comment."); 
	System.exit(0);

}

//"//".*{LineTerminator} { }
//. 			{ throw new RuntimeException("Illegal character at line " + (yyline+1) + " : '" + yytext() + "'"); }

<<EOF>> 	{ return new Token(yyline, "EOF", sym.EOF); }