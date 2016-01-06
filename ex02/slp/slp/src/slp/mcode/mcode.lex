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
	public int getLineNumber() { return yyline+1+1; }
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
Add 				= "Add "
Sub					= "Sub "
Mul 				= "Mul "
Div					= "Div "
Mod   				= "Mod "
Inc					 ="Inc "
Dec					 ="Dec "
Neg					 ="Neg "
Not					 ="Not "
And					 ="And "
Or 					="Or "
Xor 				= "Xor "
Compare 			= "Compare "
Jump				= "Jump "
JumpTrue			= "JumpTrue "
JumpFalse 			= "JumpFalse "
JumpG				="JumpG "
JumpGE				="JumpGE "
JumpL				="JumpL "
JumpLE				="JumpLE "
Library 			= "Library "
StaticCall			="StaticCall "
VirtualCall			="VirtualCall "
Return				="Return "



%%

{Move} {REG} "," {REG} {System.out.println("move reg,reg");
				 return new IRToken (McodeSym.MOVE_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
				}
{Move} {REG} "," {VAR} {System.out.println("move reg,var");
				return new IRToken (McodeSym.MOVE_REG_VAR, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
 }
 
{Move} {VAR} "," {REG} {System.out.println("move var,reg"); 
				return new IRToken (McodeSym.MOVE_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Move} {NUMBER} "," {REG} {System.out.println("move immediate,var");
				return new IRToken (McodeSym.MOVE_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

 }
{Move} {REG} "," {NUMBER} {System.out.println("move reg,immediate"); 
				return new IRToken (McodeSym.MOVE_REG_IMM, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}
{Move} {NUMBER} "," {VAR} {System.out.println("move immediate,reg"); 
				return new IRToken (McodeSym.MOVE_IMM_VAR, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}

{MoveArray} {REG} "[" {REG} "]," {REG}  {System.out.println("movearray reg[reg], reg");
				return new IRToken (McodeSym.MOVEARRAY_REG__REG_REG, yyline+1, 
				
				yytext().split(" ")[1].split(",")[0].split("\\[")[0],
				yytext().split(" ")[1].split(",")[0].split("\\[")[1].split("\\]")[0],
				yytext().split(" ")[1].split(",")[1]);

 }
{MoveArray} {REG} "[" {NUMBER} "]," {REG}  {System.out.println("movearray reg[immediate],reg");
				return new IRToken (McodeSym.MOVEARRAY_REG__IMM_REG, yyline+1, 
				yytext().split(" ")[1].split(",")[0].split("\\[")[0],
				yytext().split(" ")[1].split(",")[0].split("\\[")[1].split("\\]")[0],
				yytext().split(" ")[1].split(",")[1]);

 }

{MoveArray} {REG} "," {REG} "[" {REG} "]" {System.out.println("movearray reg, reg[immediate]"); 
				return new IRToken (McodeSym.MOVEARRAY_REG_REG__REG, yyline+1, 
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[1].split("\\]")[0]);
}
{MoveArray} {REG} "," {REG} "[" {NUMBER} "]" {System.out.println("movearray reg, reg[immediate]"); 
				return new IRToken (McodeSym.MOVEARRAY_REG_REG__IMM, yyline+1, 
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[1].split("\\]")[0]);
}

{MoveArray} {NUMBER} "," {REG} "[" {REG} "]" {System.out.println("movearray immediate, reg[reg]"); 
				return new IRToken (McodeSym.MOVEARRAY_IMM_REG__REG, yyline+1,
				
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[1].split("\\]")[0]);
}
{MoveArray} {NUMBER} "," {REG} "[" {NUMBER} "]" {System.out.println("movearray immediate, reg[immediate]");
				return new IRToken (McodeSym.MOVEARRAY_IMM_REG__IMM, yyline+1, 
				
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[0],
				yytext().split(" ")[1].split(",")[1].split("\\[")[1].split("\\]")[0]);	
 }

{MoveField} {REG} "." {REG} "," {REG}  {System.out.println("movefield reg.reg,reg");
				return new IRToken (McodeSym.MOVEFIELD_REG___REG_REG, yyline+1, 
				yytext().split(" ")[1].split(",")[0].split("\\.")[0],
				yytext().split(" ")[1].split(",")[0].split("\\.")[1],
				yytext().split(" ")[1].split(",")[1]);
 }
{MoveField} {REG} "." {NUMBER} "," {REG} {System.out.println("movefield reg.immediate, reg"); 
				return new IRToken (McodeSym.MOVEFIELD_REG___IMM_REG, yyline+1, 
				yytext().split(" ")[1].split(",")[0].split("\\.")[0],
				yytext().split(" ")[1].split(",")[0].split("\\.")[1],
				yytext().split(" ")[1].split(",")[1]);
}
{MoveField} {REG} "," {REG} "." {REG} {System.out.println("movefield reg,reg.reg");
				return new IRToken (McodeSym.MOVEFIELD_REG_REG___REG, yyline+1, 
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[1]);

 }
{MoveField} {REG} "," {REG} "." {NUMBER} {System.out.println("movefield reg, reg.immediate"); 
				return new IRToken (McodeSym.MOVEFIELD_REG_REG___IMM, yyline+1, 
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[1]);}
{MoveField} {NUMBER} "," {REG} "." {REG} {System.out.println("movefield immediate,reg.reg"); 
				return new IRToken (McodeSym.MOVEFIELD_IMM_REG___REG, yyline+1, 
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[1]);
}
{MoveField} {NUMBER} "," {REG} "." {NUMBER} {System.out.println("movefield immediate, reg.immediate"); 
				return new IRToken (McodeSym.MOVEFIELD_IMM_REG___IMM, yyline+1, 
				yytext().split(" ")[1].split(",")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[0],
				yytext().split(" ")[1].split(",")[1].split("\\.")[1]);
}

{ArrayLength} {VAR} "," {REG}  {System.out.println("arraylength var,reg"); 
				return new IRToken (McodeSym.ARRAYLENGTH_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}
{ArrayLength} {REG} "," {REG}  {System.out.println("arraylength reg,reg"); 
				return new IRToken (McodeSym.ARRAYLENGTH_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}

{Add} {NUMBER} "," {REG} {System.out.println("add immediate,reg"); 
				return new IRToken (McodeSym.ADD_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}
{Add} {VAR} "," {REG} {System.out.println("add var,reg"); 
				return new IRToken (McodeSym.ADD_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Add} {REG} "," {REG} {System.out.println("MOD reg,reg"); 
				return new IRToken (McodeSym.ADD_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}

{Sub} {NUMBER} "," {REG} {System.out.println("Sub immediate,reg");
				return new IRToken (McodeSym.SUB_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

 }
{Sub} {VAR} "," {REG} {System.out.println("Sub var,reg"); 
				return new IRToken (McodeSym.SUB_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Sub} {REG} "," {REG} {System.out.println("Sub reg,reg"); 
				return new IRToken (McodeSym.SUB_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}

{Mul} {NUMBER} "," {REG} {System.out.println("Mul immediate,reg"); 
				return new IRToken (McodeSym.MUL_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Mul} {VAR} "," {REG} {System.out.println("Mul var,reg");
				return new IRToken (McodeSym.MUL_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
 }
{Mul} {REG} "," {REG} {System.out.println("Mul reg,reg"); 
				return new IRToken (McodeSym.MUL_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Div} {NUMBER} "," {REG} {System.out.println("Div immediate,reg"); 
				return new IRToken (McodeSym.DIV_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Div} {VAR} "," {REG} {System.out.println("Div var,reg"); 
				return new IRToken (McodeSym.DIV_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Div} {REG} "," {REG} {System.out.println("Div reg,reg"); 
				return new IRToken (McodeSym.DIV_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}

{Mod} {NUMBER} "," {REG} {System.out.println("Mod immediate,reg");
				return new IRToken (McodeSym.MOD_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
 }
{Mod} {VAR} "," {REG} {System.out.println("Mod var,reg"); 
				return new IRToken (McodeSym.MOD_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);
}
{Mod} {REG} "," {REG} {System.out.println("Mod reg,reg"); 
				return new IRToken (McodeSym.MOD_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}

{Inc} {REG} {System.out.println("Inc reg");
				return new IRToken (McodeSym.INC, yyline+1, yytext().split(" ")[1]);
 }
{Dec}  {REG} {System.out.println("Dec reg");
				return new IRToken (McodeSym.DEC, yyline+1, yytext().split(" ")[1]);
}
{Neg}  {REG} {System.out.println("Neg reg");
				return new IRToken (McodeSym.NEG, yyline+1, yytext().split(" ")[1]);

 }
{Not}  {REG} {System.out.println("Not reg"); 
				return new IRToken (McodeSym.NOT, yyline+1, yytext().split(" ")[1]);
}

{And} {NUMBER} "," {REG} {System.out.println("And immediate,reg");
				return new IRToken (McodeSym.AND_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

 }
{And} {VAR} "," {REG} {System.out.println("And var,reg"); 
				return new IRToken (McodeSym.AND_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}
{And} {REG} "," {REG} {System.out.println("And reg,reg"); 
				return new IRToken (McodeSym.AND_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}

{Or} {NUMBER} "," {REG} {System.out.println("Or immediate,reg"); 
				return new IRToken (McodeSym.OR_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}
{Or} {VAR} "," {REG} {System.out.println("Or var,reg");
				return new IRToken (McodeSym.OR_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

 }
{Or} {REG} "," {REG} {System.out.println("Or reg,reg"); 
				return new IRToken (McodeSym.OR_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}

{Xor} {NUMBER} "," {REG} {System.out.println("Xor immediate,reg"); 
				return new IRToken (McodeSym.XOR_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}
{Xor} {VAR} "," {REG} {System.out.println("Xor var,reg"); 
				return new IRToken (McodeSym.XOR_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}
{Xor} {REG} "," {REG} {System.out.println("Xor reg,reg"); 
				return new IRToken (McodeSym.XOR_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}

{Compare} {NUMBER} "," {REG} {System.out.println("Compare immediate,reg");
				return new IRToken (McodeSym.COMPARE_IMM_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

 }
{Compare} {VAR} "," {REG} {System.out.println("Compare var,reg");
				return new IRToken (McodeSym.COMPARE_VAR_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

 }
{Compare} {REG} "," {REG} {System.out.println("Compare reg,reg"); 
				return new IRToken (McodeSym.COMPARE_REG_REG, yyline+1, yytext().split(" ")[1].split(",")[0], yytext().split(" ")[1].split(",")[1]);

}

{Jump} {Label}   {System.out.println("Jump label"); 
				return new IRToken (McodeSym.JUMP, yyline+1, yytext().split(" ")[1]);
}
{JumpTrue} {Label}   {System.out.println("JumpTrue label"); 
				return new IRToken (McodeSym.JUMPTRUE, yyline+1, yytext().split(" ")[1]);

}
{JumpFalse} {Label}   {System.out.println("JumpFalse label");
				return new IRToken (McodeSym.JUMPFALSE, yyline+1, yytext().split(" ")[1]);
 }
{JumpG} {Label}   {System.out.println("JumpG label");
				return new IRToken (McodeSym.JUMPG, yyline+1, yytext().split(" ")[1]);
 }
{JumpGE} {Label}   {System.out.println("JumpGE label");
				return new IRToken (McodeSym.JUMPGE, yyline+1, yytext().split(" ")[1]);
 }
{JumpL} {Label}   {System.out.println("JumpL label"); 
				return new IRToken (McodeSym.JUMPL, yyline+1, yytext().split(" ")[1]);
}
{JumpLE} {Label}   {System.out.println("JumpLE label");
				return new IRToken (McodeSym.JUMPLE, yyline+1, yytext().split(" ")[1]);
 }

//{Library} {Label} 


{Label} ":"    	{ return new IRToken(McodeSym.LABEL, yyline+1, yytext()); }



\n 				{}
\r 				{}
{Comment}       {                                                                        }
{Whitespace}    {                                                                        }
"//".*{newline} { }
. 				{ throw new RuntimeException("Illegal character at line " + (yyline+1+1) + " : '" + yytext() + "'"); }
<<EOF>> 		{ 				return new IRToken (McodeSym.EOF, yyline+1, null);
 }
 
 //commented code is old code that may be thrown out
/*"Move" 			{ return new IRToken(yyline+1, yytext(), McodeSym.MOVE); }
"MoveArray" 	{ return new IRToken(yyline+1, yytext(), McodeSym.MOVEARRAY); }
"ArrayLength" 	{ return new IRToken(yyline+1, yytext(), McodeSym.ARRAYLENGTH); }
"MoveField" 	{ return new IRToken(yyline+1, yytext(), McodeSym.MOVEFIELD); }
"Add" 			{ return new IRToken(yyline+1, yytext(), McodeSym.ADD); }
"Sub" 			{ return new IRToken(yyline+1, yytext(), McodeSym.SUB); }
"Mul" 			{ return new IRToken(yyline+1, yytext(), McodeSym.MUL); }
"Div" 			{ return new IRToken(yyline+1, yytext(), McodeSym.DIV); }
"Mod" 			{ return new IRToken(yyline+1, yytext(), McodeSym.MOD); }
"Inc" 			{ return new IRToken(yyline+1, yytext(), McodeSym.INC); }
"Dec" 			{ return new IRToken(yyline+1, yytext(), McodeSym.DEC); }
"Neg" 			{ return new IRToken(yyline+1, yytext(), McodeSym.NEG); }
"Not" 			{ return new IRToken(yyline+1, yytext(), McodeSym.NOT); }
"And" 			{ return new IRToken(yyline+1, yytext(), McodeSym.AND); }
"Or" 			{ return new IRToken(yyline+1, yytext(), McodeSym.OR); }
"Xor" 			{ return new IRToken(yyline+1, yytext(), McodeSym.XOR); }
"Compare"		{ return new IRToken(yyline+1, yytext(), McodeSym.COMPARE); }
"Jump" 			{ return new IRToken(yyline+1, yytext(), McodeSym.JUMP); }
"JumpTrue" 		{ return new IRToken(yyline+1, yytext(), McodeSym.JUMPTRUE); }
"JumpFalse"		{ return new IRToken(yyline+1, yytext(), McodeSym.JUMPFALSE); }
"JumpG" 		{ return new IRToken(yyline+1, yytext(), McodeSym.JUMPG); }
"JumpGE"		{ return new IRToken(yyline+1, yytext(), McodeSym.JUMPGE); }
"JumpL" 		{ return new IRToken(yyline+1, yytext(), McodeSym.JUMPL); }
"JumpLE"		{ return new IRToken(yyline+1, yytext(), McodeSym.JUMPLE); }
"VirtualCall"	{ return new IRToken(yyline+1, yytext(), McodeSym.VIRTUALLCALL); }
"StaticCall" 	{ return new IRToken(yyline+1, yytext(), McodeSym.STATICCALL); }
"Library" 		{ return new IRToken(yyline+1, yytext(), McodeSym.LIBRARY); }
"Return" 		{ return new IRToken(yyline+1, yytext(), McodeSym.RETURN); }
{NUMBER} 		{ return new IRToken(yyline+1, "NUMBER", McodeSym.NUMBER, new Integer(yytext())); }
{VAR} 			{ return new IRToken(yyline+1, "VAR", McodeSym.VAR, yytext()); }
{REG} 			{ return new IRToken(yyline+1, "REG", McodeSym.REG, yytext()); }
{String}        { return new IRToken(yyline+1, "String", McodeSym.STRING, yytext()); }
*/
 