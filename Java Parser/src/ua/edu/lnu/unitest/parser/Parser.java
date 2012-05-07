package ua.edu.lnu.unitest.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

	private String inFileName;
	private String outFileName;

	private StringBuilder inTest = new StringBuilder();

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
			br = new BufferedReader(new FileReader(inFileName));
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + this.inFileName
					+ "\" doesn't exist ");
			return false;
		}

		String str = null;
		try {
			while (true) {
				str = br.readLine();
				if (str == null)
					break;

				inTest.append(str);
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

	}

	public static void main(String[] args) {

		Parser parser = new Parser();

		parser.parse();

	}

}
