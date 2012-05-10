package ua.edu.lnu.unitest.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Parser {

	private char separator;

	private String inFileName;
	private String outFileName;

	private int beginValue;
	private int counter;

	private int chapter;
	private int level;
	private int time;

	// private StringBuilder inTest = new StringBuilder();

	public Parser() {

		inFileName = "input.txt";
		outFileName = "out_".intern() + inFileName;

		chapter = 1;
		beginValue = 1;
		counter = 0;
		level = 1;
		time = 60;

		separator = ')';
	}

	/**
	 * Open input text file
	 * 
	 * @return <b>BufferedReader</b> or <b>null</b> if was errors with file
	 */
	private BufferedReader openInputFile() {

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(inFileName)), "UTF-16"));
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + this.inFileName
					+ "\" doesn't exist ");
			return null;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unknown encoding");
			return null;
		}

		return br;
	}

	/**
	 * Get parameters from user
	 */
	private void getParams() {

		int val;
		String in = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			/**
			 * get input file
			 */
			getFileName(br, "Input file [" + inFileName + "]: ");

			/**
			 * get chapter
			 */
			val = getIntVal(br, "Chapter [" + chapter + "]: ");
			if (val > 0)
				chapter = val;

			/**
			 * get begin value
			 */
			val = getIntVal(br, "Start index [" + beginValue + "]: ");
			if (val > 0)
				beginValue = val;

			/**
			 * get level value
			 */
			val = getIntVal(br, "Level [" + level + "]: ");
			if (val > 0)
				level = val;

		} catch (IOException e) {
			System.err
					.println("Error reading input params. The program will be terminated");
			System.exit(1);
		}
	}

	/**
	 * Get int value from user and parse it to int
	 * 
	 * @param br
	 *            BufferedReader
	 * @param msg
	 * @return <b>0</b> if was pressed <b>Enter</b>
	 * @throws IOException
	 */
	private int getIntVal(BufferedReader br, String msg) throws IOException {

		String in = null;

		while (true) {

			System.out.print(msg);
			in = br.readLine();

			if (in.length() == 0)
				return 0;

			try {

				return Integer.parseInt(in, 10);

			} catch (NumberFormatException e) {
				System.out.println("Incorrect value");
				continue;
			}
		}
	}

	/**
	 * Get name of input file
	 * 
	 * @param br
	 * @return
	 * @throws IOException
	 */
	private void getFileName(BufferedReader br, String msg) throws IOException {

		String in = null;

		while (true) {
			
			System.out.print(msg);
			in = br.readLine();

			if (in != null && in.length() > 0) {

				// Check file's extension
				if (!in.endsWith(".txt")) {
					System.out.println("Incorrect file extension");
					continue;
				}

				this.inFileName = in;
				this.outFileName = "out_" + inFileName;

				return;

			} else
				return;
		}
	}
	
	private void showProps() {
		
		System.out.println("Chapter: " + chapter);
		System.out.println("Start index: " + beginValue);
		System.out.println("Level: " + level);
		
		System.out.println("Input file: " + inFileName);
		System.out.println("Output file: " + outFileName);
	}

	/**
	 * Write parsed tests to file
	 */
	private int writeToFile(StringBuilder sb) {

		return 0;
	}

	public void parseLine() {

	}

	/**
	 * Runs parser
	 */
	public void run() {

		getParams();
		showProps();
		
		openInputFile();
	}

	public static void main(String[] args) {

		Parser parser = new Parser();

		parser.run();

	}

}
