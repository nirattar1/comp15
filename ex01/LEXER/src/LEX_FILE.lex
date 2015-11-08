/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/***************************/
/* AUTHOR: OREN ISH SHALOM */
/***************************/
/**/
/*************/
/* USER CODE */
/*************/
   
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/
      
%%
   
/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable (1+yyline) */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column
    
/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup
   
/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied letter to letter into the Lexer class code.                */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{   
    /*********************************************************************************/
    /* Create a new java_cup.runtime.Symbol with information about the current token */
    /*********************************************************************************/
    private Symbol symbol(int type)               {return new Symbol(type, (1+yyline), yycolumn);}
    private Symbol symbol(int type, Object value) {return new Symbol(type, (1+yyline), yycolumn, value);}
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
INTEGER			= 0 | [1-9][0-9]*
IDENTIFIER		= [A-Za-z_][A-Za-z_0-9]*   
QuotedString	= \"([^\"\\]|\\\\|\\\")*\"
CLASS_ID        = [A-Z][A-Za-z_0-9]*
LETTER 			= [a-zA-Z_0-9!" " ";" "." "?" \" "'"]
COMMENT			= "/*" {LETTER}* "*/"  |  "//" {LETTER}* {LineTerminator} 
 

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/
   
/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/
   
<YYINITIAL> {
{COMMENT}			{/* just skip what was found, do nothing */ } 
"+"					{ System.out.println((1+yyline)+": PLUS");     return symbol(sym.PLUS);}
					
"="                 { System.out.println((1+yyline)+": ASSIGN");    return symbol(sym.ASSIGN);}
"boolean"           { System.out.println((1+yyline)+": BOOLEAN");    return symbol(sym.BOOLEAN);}
"break"           	{ System.out.println((1+yyline)+ ": BREAK");    return symbol(sym.BREAK);}
"class"          	{ System.out.println((1+yyline)+ ": CLASS");    return symbol(sym.CLASS);}
{CLASS_ID}		  	{
						System.out.print((1+yyline)+": CLASS_ID(");
						System.out.print(yytext());
						System.out.println(") ");
						return symbol(sym.CLASS_ID, new String(yytext()));
					}
","          		{ System.out.println((1+yyline)+": COMMA");    return symbol(sym.COMMA);}
"continue"          { System.out.println((1+yyline) +": CONTINUE");    return symbol(sym.CONTINUE);}
"."           		{ System.out.println((1+yyline) +": DOT");    return symbol(sym.DOT);}
"/"					{ System.out.println((1+yyline) +": DIVIDE ");    return symbol(sym.DIVIDE);}
"=="         		{ System.out.println((1+yyline) +": EQUAL");    return symbol(sym.EQUAL);}
"extends"     		{ System.out.println((1+yyline)+ ": EXTENDS");    return symbol(sym.EXTENDS);}
"else"     			{ System.out.println((1+yyline) +": ELSE");    return symbol(sym.ELSE);}
"false"           	{ System.out.println((1+yyline)+ ": FALSE");    return symbol(sym.FALSE);}
">"           		{ System.out.println((1+yyline)+": GT");    return symbol(sym.GT);}
">="           		{ System.out.println((1+yyline)+": GTE");    return symbol(sym.GTE);}

"if"           		{ System.out.println((1+yyline) +": IF");    return symbol(sym.IF);}
"int"           	{ System.out.println((1+yyline)+": INT");    return symbol(sym.INT);}
"&&"           	{ System.out.println((1+yyline)+": LAND");    return symbol(sym.LAND);}
"["           	{ System.out.println((1+yyline)+": LB");    return symbol(sym.LB);}
"("           	{ System.out.println((1+yyline)+": LP");    return symbol(sym.LP);}
"{"           	{ System.out.println((1+yyline)+": LBCR");    return symbol(sym.LCBR);}
"length"           	{ System.out.println((1+yyline)+": LENGTH");    return symbol(sym.LENGTH);}
"new"           	{ System.out.println((1+yyline)+": NEW");    return symbol(sym.NEW);}
"!"           	{ System.out.println((1+yyline)+": LNEG");    return symbol(sym.LNEG);}
"||"           	{ System.out.println((1+yyline)+": LOR");    return symbol(sym.LOR);}
"<"           	{ System.out.println((1+yyline)+": LT");    return symbol(sym.LT);}
"<="           	{ System.out.println((1+yyline)+": LTE");    return symbol(sym.LTE);}
"-"           	{ System.out.println((1+yyline)+": MINUS");    return symbol(sym.MINUS);}
"%"           	{ System.out.println((1+yyline)+": MOD");    return symbol(sym.MOD);}
"*"           	{ System.out.println((1+yyline)+": MULTIPLY");    return symbol(sym.MULTIPLY);}
"!="           		{ System.out.println((1+yyline) +": NEQUAL");    return symbol(sym.NEQUAL);}
"null"           	{ System.out.println((1+yyline) +": NULL");    return symbol(sym.NULL);}
"]"          	 	{ System.out.println((1+yyline) + ": RB");    return symbol(sym.RB);}
"}"          	 	{ System.out.println((1+yyline) +": RCBR");    return symbol(sym.RCBR);}
"return"       	 	{ System.out.println((1+yyline)+": RETURN");    return symbol(sym.RETURN);}
")" 	      	 	{ System.out.println((1+yyline)+": RP");    return symbol(sym.RP);}
";" 	      	 	{ System.out.println((1+yyline)+": SEMI");    return symbol(sym.SEMI);}
"static" 			{ System.out.println((1+yyline) +": STATIC");	return symbol(sym.STATIC);}
"string" 			{ System.out.println((1+yyline) +": STRING");	return symbol(sym.STRING);}
{QuotedString}		{
						System.out.print((1+yyline) +": QUOTE(");
						System.out.print(yytext());
						System.out.println(")");
						return symbol(sym.QUOTE, new String(yytext()));
					}
"this" 				{ System.out.println((1+yyline) +": THIS");	return symbol(sym.THIS);}
"true" 				{ System.out.println((1+yyline) +": TRUE");	return symbol(sym.TRUE);}
"void" 				{ System.out.println((1+yyline) +": VOID");	return symbol(sym.VOID);}
"while"				{ System.out.println((1+yyline) +": WHILE"); return symbol(sym.WHILE);}
{INTEGER}			{
						System.out.print((1+yyline) +": INTEGER(");
						System.out.print(yytext());
						System.out.println(") ");
						return symbol(sym.NUMBER, new Integer(yytext()));
					}   
{IDENTIFIER}		{
						System.out.print((1+yyline) +": ID(");
						System.out.print(yytext());
						System.out.println(") ");
						return symbol(sym.ID, new String(yytext()));
					}
{WhiteSpace}		{ /* just skip what was found, do nothing */ }

<<EOF>> 			{System.out.print((1+yyline) +": EOF");	System.exit(0);}
}
