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

newline = [\n\r]
VAR=[a-zA-Z]+
NUMBER=[0-9]+

%%

"+" 		{ return new Token(yyline, yytext(), sym.PLUS); }
"-" 		{ return new Token(yyline, yytext(), sym.MINUS); }
"*" 		{ return new Token(yyline, yytext(), sym.MULT); }
"/" 		{ return new Token(yyline, yytext(), sym.DIV); }
"(" 		{ return new Token(yyline, yytext(), sym.LPAREN); }
")" 		{ return new Token(yyline, yytext(), sym.RPAREN); }
";" 		{ return new Token(yyline, yytext(), sym.SEMI); }
"=" 		{ return new Token(yyline, yytext(), sym.ASSIGN); }
"<" 		{ return new Token(yyline, yytext(), sym.LT); }
">" 		{ return new Token(yyline, yytext(), sym.GT); }
"<=" 		{ return new Token(yyline, yytext(), sym.LE); }
">=" 		{ return new Token(yyline, yytext(), sym.GE); }
"&&" 		{ return new Token(yyline, yytext(), sym.LAND); }
"||" 		{ return new Token(yyline, yytext(), sym.LOR); }
"readi" 	{ return new Token(yyline, yytext(), sym.READI); }
"print" 	{ return new Token(yyline, yytext(), sym.PRINT); }
{NUMBER} 	{ return new Token(yyline, "NUMBER", sym.NUMBER, new Integer(yytext())); }
{VAR} 		{ return new Token(yyline, "VAR", sym.VAR, yytext()); }
\n 			{}
\r 			{}
" "			{}
"//".*{newline} { }
. 			{ throw new RuntimeException("Illegal character at line " + (yyline+1) + " : '" + yytext() + "'"); }
<<EOF>> 	{ return new Token(yyline, "EOF", sym.EOF); }