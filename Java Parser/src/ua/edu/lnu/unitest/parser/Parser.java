package ua.edu.lnu.unitest.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.*;

/**
 * Parser for Ohrenda's test
 * Question must be formatted like this:
 * <p i>This is a question:
 * <br>1) this is first answer;
 * <br>2) this is second answer;
 * <br>3) this is third answer;
 * <br>#23
 * @author KD
 *
 */
public class Parser {
	/**
	 * Name of input file
	 */
	private String inFileName;
	/**
	 * Name of output file
	 */
	private String outFileName;
	/**
	 * Var to store input text
	 */
	private StringBuilder inTest = new StringBuilder();
	/**
	 * Var to store formatted text
	 */
	private StringBuilder outTest = new StringBuilder();

	public Parser() {

		inFileName = "input.txt";
		outFileName = "output.txt";
	}

	public Parser(String in, String out) {

		inFileName = in;
		outFileName = out;
	}

	/**
	 * Open input text file and read it to memory
	 */
	private boolean openInputFile() {

		BufferedReader br = null;

		try {
//			br = new BufferedReader(new FileReader(inFileName));
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(inFileName), "UTF-16"));
			
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + this.inFileName
					+ "\" doesn't exist ");
			return false;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String str = null;
		try {
			while (true) {
				str = br.readLine();
				if (str == null)
					break;
				if (str.length()!=0) {
					inTest.append(str);
					inTest.append("\n");
				}
				
			}
		} catch (IOException e) {
			System.out
					.println("Error reading file \"" + this.inFileName + "\"");
			return false;
		}
		return true;
	}

	/**
	 * Write parsed tests to file
	 */
	private int writeToFile(StringBuilder sb) {

		return 0;
	}

	public void parse() {

		if (!openInputFile()) {
			System.exit(1);
		}
		
//		System.out.println("Input:");
//		System.out.println(inTest.toString());
		
		String string = inTest.toString();
		System.out.println(string.trim());
		
		Pattern p = Pattern.compile("\\p{L}*");

		Matcher m = p.matcher(string);
		
		System.out.println(m.matches());
		
		

	}

	public static void main(String[] args) {

		Parser parser = new Parser("input2.txt", "");

		parser.parse();

	}

}
