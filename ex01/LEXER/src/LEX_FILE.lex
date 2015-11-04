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
CLASS_ID        = (class)[" "]+[A-Z][A-Za-z_0-9]* 

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
"+"					{ System.out.print(" "+ yyline + "PLUS ");      return symbol(sym.PLUS);}
"*"					{ System.out.print("TIMES ");     return symbol(sym.TIMES);}
"="                 { System.out.print("ASSIGN ");    return symbol(sym.ASSIGN);}
"boolean"           { System.out.print("BOOLEAN ");    return symbol(sym.BOOLEAN);}
"break"           	{ System.out.print("BREAK ");    return symbol(sym.BREAK);}
"class"          	{ System.out.print("CLASS ");    return symbol(sym.CLASS);}
{CLASS_ID}		  	{
						System.out.print("CLASS_ID(");
						System.out.print(yytext());
						System.out.print(") ");
						return symbol(sym.CLASS_ID, new String(yytext()));
					}
","          		{ System.out.print("COMMA ");    return symbol(sym.COMMA);}
"continue"          { System.out.print("CONTINUE ");    return symbol(sym.CONTINUE);}
"."           		{ System.out.print("DOT ");    return symbol(sym.DOT);}
"/"					{ System.out.print("DIVIDE ");    return symbol(sym.DIVIDE);}
"=="         		{ System.out.print("EQUAL ");    return symbol(sym.EQUAL);}
"extends"     		{ System.out.print("EXTENDS ");    return symbol(sym.EXTENDS);}
"else"     			{ System.out.print("ELSE ");    return symbol(sym.ELSE);}
"false"           	{ System.out.print("FALSE ");    return symbol(sym.FALSE);}
">"           		{ System.out.print("GT ");    return symbol(sym.GT);}
">="           		{ System.out.print("GTE ");    return symbol(sym.GTE);}

"!="           		{ System.out.print("NEQUAL ");    return symbol(sym.NEQUAL);}
"null"           	{ System.out.print("NULL ");    return symbol(sym.NULL);}
"]"          	 	{ System.out.print("RB ");    return symbol(sym.RB);}
"}"          	 	{ System.out.print("RCBR ");    return symbol(sym.RCBR);}
"return"       	 	{ System.out.print("RETURN ");    return symbol(sym.RETURN);}
")" 	      	 	{ System.out.print("RP ");    return symbol(sym.RP);}
"(" 	      	 	{ System.out.print("LP ");    return symbol(sym.LP);}
";" 	      	 	{ System.out.print("SEMI ");    return symbol(sym.SEMI);}
"static" 			{ System.out.print("STATIC");	return symbol(sym.STATIC);}
"string" 			{ System.out.print("STRING");	return symbol(sym.STRING);}
{QuotedString}		{
						System.out.print("QUOTE(");
						System.out.print(yytext());
						System.out.print(") ");
						return symbol(sym.QUOTE, new String(yytext()));
					}
"this" 				{ System.out.print("THIS");	return symbol(sym.THIS);}
"true" 				{ System.out.print("TRUE");	return symbol(sym.TRUE);}
"void" 				{ System.out.print("VOID");	return symbol(sym.VOID);}
"while"				{ System.out.print("WHILE"); return symbol(sym.WHILE);}
{INTEGER}			{
						System.out.print("INT(");
						System.out.print(yytext());
						System.out.print(") ");
						return symbol(sym.NUMBER, new Integer(yytext()));
					}   
{IDENTIFIER}		{
						System.out.print("ID(");
						System.out.print(yytext());
						System.out.print(") ");
						return symbol(sym.ID, new String(yytext()));
					}
{WhiteSpace}		{ /* just skip what was found, do nothing */ }   

}
