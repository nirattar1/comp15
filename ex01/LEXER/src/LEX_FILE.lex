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
/* The current line number can be accessed with the variable yyline */
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
    private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
    private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}
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
"+"					{ System.out.println(yyline+": PLUS");     return symbol(sym.PLUS);}
					
"="                 { System.out.println(yyline+": ASSIGN");    return symbol(sym.ASSIGN);}
"boolean"           { System.out.println(yyline+": BOOLEAN");    return symbol(sym.BOOLEAN);}
"break"           	{ System.out.println(yyline+ ": BREAK");    return symbol(sym.BREAK);}
"class"          	{ System.out.println(yyline+ ": CLASS");    return symbol(sym.CLASS);}
{CLASS_ID}		  	{
						System.out.print(yyline+": CLASS_ID(");
						System.out.print(yytext());
						System.out.println(") ");
						return symbol(sym.CLASS_ID, new String(yytext()));
					}
","          		{ System.out.println(yyline+": COMMA");    return symbol(sym.COMMA);}
"continue"          { System.out.println(yyline +": CONTINUE");    return symbol(sym.CONTINUE);}
"."           		{ System.out.println(yyline +": DOT");    return symbol(sym.DOT);}
"/"					{ System.out.println(yyline +": DIVIDE ");    return symbol(sym.DIVIDE);}
"=="         		{ System.out.println(yyline +": EQUAL");    return symbol(sym.EQUAL);}
"extends"     		{ System.out.println(yyline+ ": EXTENDS");    return symbol(sym.EXTENDS);}
"else"     			{ System.out.println(yyline +": ELSE");    return symbol(sym.ELSE);}
"false"           	{ System.out.println(yyline+ ": FALSE");    return symbol(sym.FALSE);}
">"           		{ System.out.println(yyline+": GT");    return symbol(sym.GT);}
">="           		{ System.out.println(yyline+": GTE");    return symbol(sym.GTE);}

"if"           		{ System.out.println(yyline +": IF");    return symbol(sym.IF);}
"int"           	{ System.out.println(yyline+": INT");    return symbol(sym.INT);}
"&&"           	{ System.out.println(yyline+": LAND");    return symbol(sym.LAND);}
"["           	{ System.out.println(yyline+": LB");    return symbol(sym.LB);}
"("           	{ System.out.println(yyline+": LP");    return symbol(sym.LP);}
"{"           	{ System.out.println(yyline+": LBCR");    return symbol(sym.LCBR);}
"length"           	{ System.out.println(yyline+": LENGTH");    return symbol(sym.LENGTH);}
"new"           	{ System.out.println(yyline+": NEW");    return symbol(sym.NEW);}
"!"           	{ System.out.println(yyline+": LNEG");    return symbol(sym.LNEG);}
"||"           	{ System.out.println(yyline+": LOR");    return symbol(sym.LOR);}
"<"           	{ System.out.println(yyline+": LT");    return symbol(sym.LT);}
"<="           	{ System.out.println(yyline+": LTE");    return symbol(sym.LTE);}
"-"           	{ System.out.println(yyline+": MINUS");    return symbol(sym.MINUS);}
"%"           	{ System.out.println(yyline+": MOD");    return symbol(sym.MOD);}
"*"           	{ System.out.println(yyline+": MULTIPLY");    return symbol(sym.MULTIPLY);}
"!="           		{ System.out.println(yyline +": NEQUAL");    return symbol(sym.NEQUAL);}
"null"           	{ System.out.println(yyline +": NULL");    return symbol(sym.NULL);}
"]"          	 	{ System.out.println(yyline + ": RB");    return symbol(sym.RB);}
"}"          	 	{ System.out.println(yyline +": RCBR");    return symbol(sym.RCBR);}
"return"       	 	{ System.out.println(yyline+": RETURN");    return symbol(sym.RETURN);}
")" 	      	 	{ System.out.println(yyline+": RP");    return symbol(sym.RP);}
";" 	      	 	{ System.out.println(yyline+": SEMI");    return symbol(sym.SEMI);}
"static" 			{ System.out.println(yyline +": STATIC");	return symbol(sym.STATIC);}
"string" 			{ System.out.println(yyline +": STRING");	return symbol(sym.STRING);}
{QuotedString}		{
						System.out.print("QUOTE(");
						System.out.print(yytext());
						System.out.print(") ");
						return symbol(sym.QUOTE, new String(yytext()));
					}
"this" 				{ System.out.println(yyline +": THIS");	return symbol(sym.THIS);}
"true" 				{ System.out.println(yyline +": TRUE");	return symbol(sym.TRUE);}
"void" 				{ System.out.println(yyline +": VOID");	return symbol(sym.VOID);}
"while"				{ System.out.println(yyline +": WHILE"); return symbol(sym.WHILE);}
{INTEGER}			{
						System.out.print(yyline +": INTEGER(");
						System.out.print(yytext());
						System.out.println(") ");
						return symbol(sym.NUMBER, new Integer(yytext()));
					}   
{IDENTIFIER}		{
						System.out.print(yyline +": ID(");
						System.out.print(yytext());
						System.out.println(") ");
						return symbol(sym.ID, new String(yytext()));
					}
{WhiteSpace}		{ /* just skip what was found, do nothing */ }

<<EOF>> 			{System.out.print(yyline +": EOF");	System.exit(0);}
}
