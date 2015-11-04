   
import java.io.*;

import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{    
		Lexer p =null;
		try
		{
			p=new Lexer(new FileReader(argv[0]));
    	
			Symbol token = p.next_token();
			while (token.sym != sym.EOF)
			{
				token = p.next_token();
				System.out.println();
			}
		}
			     
		catch (Exception e)
		{
			e.printStackTrace();
			if(p != null){
			System.out.println(p.yytext());
			}
		}
	}
}


