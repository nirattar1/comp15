package slp;

import java.io.*;
import java_cup.runtime.*;

/** The entry point of the SLP (Straight Line Program) application.
 *
 */
public class Main {
	private static boolean printtokens = false;
	
	/** Reads an SLP and pretty-prints it.
	 * 
	 * @param args Should be the name of the file containing an SLP.
	 */
	public static void main(String[] args) throws Exception {
		try {
			if (args.length == 0) {
				System.out.println("Error: Missing input file argument!");
				printUsage();
				System.exit(-1);
			}
			if (args.length == 2) {
				if (args[1].equals("-printtokens")) {
					printtokens = true;
				}
				else {
					printUsage();
					System.exit(-1);
				}
			}
			
			// Parse the input file
			FileReader txtFile = new FileReader(args[0]);
			Lexer scanner = new Lexer(txtFile);
			Parser parser = new Parser(scanner);
			parser.printTokens = printtokens;
			
			Symbol parseSymbol = parser.parse();
			System.out.println("Parsed " + args[0] + " successfully!");
			System.out.println();
			System.out.println("Abstract Syntax Tree:" + args[0]);
			ASTNode root = (ASTNode) parseSymbol.value;
			
			// Pretty-print the program to System.out
			PrettyPrinter printer = new PrettyPrinter(root);
			printer.print();
			
			//make a symbol table builder .
			//it also will build a type table.
			SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder(root);
			
			//make a type checker based on the built type table.
			TypeChecker tchecker=new TypeChecker(root, symbolTableBuilder.typeTable);

			//if reached here, passed without semantic errors.
			System.out.println("Success - no semantic errors.");
			IRBuilder irbuilder=new IRBuilder(root, symbolTableBuilder.typeTable);
			
			StringBuffer out = irbuilder.getOutput();
			//write to console
			System.out.println(out);
			
			//write to file
			FileWriter fileWriter = new FileWriter ("output.lir");
			fileWriter.write(out.toString());
			fileWriter.close();
			
//			// Interpret the program
//			SLPEvaluator evaluator = new SLPEvaluator(root);
//			evaluator.evaluate();
		} 
		catch (SemanticException e)
		{
			System.out.println(e.toString());
		}
		catch (Exception e) {
			System.out.print(e);
			throw e;
		}
	}
	
	/** Prints usage information about this application to System.out
	 */
	public static void printUsage() {
		System.out.println("Usage: slp file [-printtokens]");
	}
}