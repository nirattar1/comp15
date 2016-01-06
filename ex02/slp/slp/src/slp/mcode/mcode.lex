package slp.mcode;

import java_cup.runtime.*;

%%

%public
%cup
%class IRLexer
%type IRToken
%line
%scanerror RuntimeException

%{
	public int getLineNumber() { return yyline+1; }
%}


Whitespace          = [ \t\r\n\f]+
newline = [\n\r]
DigitChar           = [0-9]
PosNum              = [1-9]
IdenChar            = [_0-9a-zA-Z]
VAR    				= [a-z]{IdenChar}*
REG					= R{IdenChar}*
Label				= _{IdenChar}+
NUMBER				= [0-9]+
PrintableNotSpecial = [\x20-\x21\x23-\x5B\x5D-\x7E]
Comment         	= ("#" [^\r\n\f]*)
String				= "\"" ({PrintableNotSpecial} | "\\\\" | "\\\"" | "\\t" | "\\n")* "\""
Move 				= "Move "
MoveArray 			= "MoveArray "
MoveField 			= "MoveField "
ArrayLength 		= "ArrayLength "
Add 				= "Add"
Sub					= "Sub"
Mul 				= "Mul"
Div					= "Div"
Mod   				= "Mod"
Inc					 ="Inc"
Dec					 ="Dec"
Neg					 ="Neg"
Not					 ="Not"
And					 ="And"
Or 					="Or"
Xor 				= "Xor"
Compare 			= "Compare"
Jump				= "Jump"
JumpTrue			= "JumpTrue"
JumpFalse 			= "JumpFalse"
JumpG				="JumpG"
JumpGE				="JumpGE"
JumpL				="JumpL"
JumpLE				="JumpLE"
Library 			= "Library"
StaticCall			="StaticCall"
VirtualCall			="VirtualCall"
Return				="Return"



%%

{Move} {REG} "," {REG} {System.out.println("move reg,reg"); }
{Move} {REG} "," {VAR} {System.out.println("move reg,var"); }
{Move} {VAR} "," {REG} {System.out.println("move var,reg"); }
{Move} {NUMBER} "," {REG} {System.out.println("move immediate,var"); }
{Move} {REG} "," {NUMBER} {System.out.println("move reg,immediate"); }
{Move} {NUMBER} "," {VAR} {System.out.println("move immediate,reg"); }

{MoveArray} {REG} "[" {REG} "]," {REG}  {System.out.println("movearray reg[reg], reg"); }
{MoveArray} {REG} "[" {NUMBER} "]," {REG}  {System.out.println("movearray reg[immediate],reg"); }

{MoveArray} {REG} "," {REG} "[" {REG} "]" {System.out.println("movearray reg, reg[immediate]"); }
{MoveArray} {REG} "," {REG} "[" {NUMBER} "]" {System.out.println("movearray reg, reg[immediate]"); }
{MoveArray} {NUMBER} "," {REG} "[" {REG} "]" {System.out.println("movearray immediate, reg[reg]"); }
{MoveArray} {NUMBER} "," {REG} "[" {NUMBER} "]" {System.out.println("movearray immediate, reg[immediate]"); }

{MoveField} {REG} "." {REG} "," {REG}  {System.out.println("movefield reg.reg,reg"); }
{MoveField} {REG} "." {NUMBER} "," {REG} {System.out.println("movefield reg.immediate, reg"); }
{MoveField} {REG} "," {REG} "." {REG} {System.out.println("movefield reg,reg.reg"); }
{MoveField} {REG} "," {REG} "." {NUMBER} {System.out.println("movefield reg, reg.immediate"); }
{MoveField} {NUMBER} "," {REG} "." {REG} {System.out.println("movefield immediate,reg.reg"); }
{MoveField} {NUMBER} "," {REG} "." {NUMBER} {System.out.println("movefield immediate, reg.immediate"); }

{ArrayLength} {VAR} "," {REG}  {System.out.println("arraylength var,reg"); }
{ArrayLength} {REG} "," {REG}  {System.out.println("arraylength reg,reg"); }

{Add} {NUMBER} "," {REG} {System.out.println("add immediate,reg"); }
{Add} {VAR} "," {REG} {System.out.println("add var,reg"); }
{Add} {REG} "," {REG} {System.out.println("add reg,reg"); }

{Sub} {NUMBER} "," {REG} {System.out.println("Sub immediate,reg"); }
{Sub} {VAR} "," {REG} {System.out.println("Sub var,reg"); }
{Sub} {REG} "," {REG} {System.out.println("Sub reg,reg"); }

{Mul} {NUMBER} "," {REG} {System.out.println("Mul immediate,reg"); }
{Mul} {VAR} "," {REG} {System.out.println("Mul var,reg"); }
{Mul} {REG} "," {REG} {System.out.println("Mul reg,reg"); }

{Div} {NUMBER} "," {REG} {System.out.println("Div immediate,reg"); }
{Div} {VAR} "," {REG} {System.out.println("Div var,reg"); }
{Div} {REG} "," {REG} {System.out.println("Div reg,reg"); }

{Mod} {NUMBER} "," {REG} {System.out.println("Mod immediate,reg"); }
{Mod} {VAR} "," {REG} {System.out.println("Mod var,reg"); }
{Mod} {REG} "," {REG} {System.out.println("Mod reg,reg"); }

{Inc} {REG} {System.out.println("Inc immediate,reg"); }
{Dec}  {REG} {System.out.println("Dec var,reg"); }
{Neg}  {REG} {System.out.println("Neg reg,reg"); }
{Not}  {REG} {System.out.println("Not var,reg"); }

{And} {NUMBER} "," {REG} {System.out.println("And immediate,reg"); }
{And} {VAR} "," {REG} {System.out.println("And var,reg"); }
{And} {REG} "," {REG} {System.out.println("And reg,reg"); }

{Or} {NUMBER} "," {REG} {System.out.println("Or immediate,reg"); }
{Or} {VAR} "," {REG} {System.out.println("Or var,reg"); }
{Or} {REG} "," {REG} {System.out.println("Or reg,reg"); }

{Xor} {NUMBER} "," {REG} {System.out.println("Xor immediate,reg"); }
{Xor} {VAR} "," {REG} {System.out.println("Xor var,reg"); }
{Xor} {REG} "," {REG} {System.out.println("Xor reg,reg"); }

{Compare} {NUMBER} "," {REG} {System.out.println("Compare immediate,reg"); }
{Compare} {VAR} "," {REG} {System.out.println("Compare var,reg"); }
{Compare} {REG} "," {REG} {System.out.println("Compare reg,reg"); }

{Jump} {Label}   {System.out.println("Jump label"); }
{JumpTrue} {Label}   {System.out.println("JumpTrue label"); }
{JumpFalse} {Label}   {System.out.println("JumpFalse label"); }
{JumpG} {Label}   {System.out.println("JumpG label"); }
{JumpGE} {Label}   {System.out.println("JumpGE label"); }
{JumpL} {Label}   {System.out.println("JumpL label"); }
{JumpLE} {Label}   {System.out.println("JumpLE label"); }

//{Library} {Label} 

/*"Move" 			{ return new IRToken(yyline, yytext(), McodeSym.MOVE); }


"MoveArray" 	{ return new IRToken(yyline, yytext(), McodeSym.MOVEARRAY); }
"ArrayLength" 	{ return new IRToken(yyline, yytext(), McodeSym.ARRAYLENGTH); }
"MoveField" 	{ return new IRToken(yyline, yytext(), McodeSym.MOVEFIELD); }
"Add" 			{ return new IRToken(yyline, yytext(), McodeSym.ADD); }
"Sub" 			{ return new IRToken(yyline, yytext(), McodeSym.SUB); }
"Mul" 			{ return new IRToken(yyline, yytext(), McodeSym.MUL); }
"Div" 			{ return new IRToken(yyline, yytext(), McodeSym.DIV); }
"Mod" 			{ return new IRToken(yyline, yytext(), McodeSym.MOD); }
"Inc" 			{ return new IRToken(yyline, yytext(), McodeSym.INC); }
"Dec" 			{ return new IRToken(yyline, yytext(), McodeSym.DEC); }
"Neg" 			{ return new IRToken(yyline, yytext(), McodeSym.NEG); }
"Not" 			{ return new IRToken(yyline, yytext(), McodeSym.NOT); }
"And" 			{ return new IRToken(yyline, yytext(), McodeSym.AND); }
"Or" 			{ return new IRToken(yyline, yytext(), McodeSym.OR); }
"Xor" 			{ return new IRToken(yyline, yytext(), McodeSym.XOR); }
"Compare"		{ return new IRToken(yyline, yytext(), McodeSym.COMPARE); }
"Jump" 			{ return new IRToken(yyline, yytext(), McodeSym.JUMP); }
"JumpTrue" 		{ return new IRToken(yyline, yytext(), McodeSym.JUMPTRUE); }
"JumpFalse"		{ return new IRToken(yyline, yytext(), McodeSym.JUMPFALSE); }
"JumpG" 		{ return new IRToken(yyline, yytext(), McodeSym.JUMPG); }
"JumpGE"		{ return new IRToken(yyline, yytext(), McodeSym.JUMPGE); }
"JumpL" 		{ return new IRToken(yyline, yytext(), McodeSym.JUMPL); }
"JumpLE"		{ return new IRToken(yyline, yytext(), McodeSym.JUMPLE); }
"VirtualCall"	{ return new IRToken(yyline, yytext(), McodeSym.VIRTUALLCALL); }
"StaticCall" 	{ return new IRToken(yyline, yytext(), McodeSym.STATICCALL); }
"Library" 		{ return new IRToken(yyline, yytext(), McodeSym.LIBRARY); }
"Return" 		{ return new IRToken(yyline, yytext(), McodeSym.RETURN); }
{NUMBER} 		{ return new IRToken(yyline, "NUMBER", McodeSym.NUMBER, new Integer(yytext())); }
{VAR} 			{ return new IRToken(yyline, "VAR", McodeSym.VAR, yytext()); }
{REG} 			{ return new IRToken(yyline, "REG", McodeSym.REG, yytext()); }
{String}        { return new IRToken(yyline, "String", McodeSym.STRING, yytext()); }
*/

{Label}        	{ return new IRToken(yyline, "Label", McodeSym.LABEL, yytext()); }



\n 				{}
\r 				{}
{Comment}       {                                                                        }
{Whitespace}    {                                                                        }
"//".*{newline} { }
. 				{ throw new RuntimeException("Illegal character at line " + (yyline+1) + " : '" + yytext() + "'"); }
<<EOF>> 		{ return new IRToken(yyline, "EOF", McodeSym.EOF); }