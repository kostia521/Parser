package ua.edu.lnu.unitest.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Parser {

	private static final String ERROR_READ_FILE = "Error while reading file";
	private static final String ERROR_INCORRECT_FILE_EXTENSION = "Incorrect file extension";
	private static final String ERROR_INCORRECT_VALUE = "Incorrect value";
	private static final String ERROR_READ_INPUT_PARAMS = "Error reading input params. The program will be terminated";
	private static final String ERROR_WRITING_TO_FILE = "Error writing to file";
	private static final String ERROR_UNKNOWN_ENCODING = "Unknown encoding";
	private static final String ERROR_FILE_DIDN_T_CREATED = "File didn't created";
	private static final String ERROR_INCORRECT_FORMAT = "Incorrect formatting";
	private static final String ERROR_ANSWERS_MISSED = "Answers are missed";

	private char separator;

	private String inFileName;
	private String outFileName;

	private int beginValue;
	private int counter;

	private int chapter;
	private int level;
	private int time;
	private int variant;
	private int type;

	private StringBuilder outTest = new StringBuilder();

	public Parser() {

		inFileName = "input.txt";
		outFileName = "out_".intern() + inFileName;

		counter = 0;
		variant = 0;
		chapter = 1;
		level = 1;
		type = 1;
		time = 60;

		beginValue = 1;
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
			System.out.println(ERROR_UNKNOWN_ENCODING);
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
			System.err.println(ERROR_READ_INPUT_PARAMS);
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
				System.out.println(ERROR_INCORRECT_VALUE);
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
					System.out.println(ERROR_INCORRECT_FILE_EXTENSION);
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
	private void writeToFile(String str) {

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(outFileName)), "UTF-16"));

			bw.write(str);
			bw.close();

		} catch (FileNotFoundException e) {
			System.out.println(ERROR_FILE_DIDN_T_CREATED);
		} catch (UnsupportedEncodingException e) {
			System.err.println(ERROR_UNKNOWN_ENCODING);
		} catch (IOException e) {
			System.err.println(ERROR_WRITING_TO_FILE);
		}
	}

	/**
	 * Process tests
	 * 
	 * @param br
	 * @param sb
	 */
	private void parse(BufferedReader br, StringBuilder sb) {

		int lineCounter = 0; // Needs to show in which line was found error
		String head = null; // String for question
		String line = null;

		if (br == null || sb == null)
			return;

		ArrayList<String> answers = new ArrayList<String>(); // Array to store
																// answers
		// Loop for processing file
		while (true) {
			try {
				line = br.readLine(); // Read next line

				if (line == null)
					break; // End of stream reached

				lineCounter++; // Increase line counter

				line = line.trim(); // Skip white spaces
				if (line.isEmpty())
					continue;

				head = "¹ " + (beginValue + counter) + ", " + variant + ", "
						+ chapter + ", " + level + ", " + type + ", " + time
						+ "\n";
				sb.append(head); // Write head to buffer
				sb.append(line); // Write question to buffer
				sb.append("\n");

				// Process answers
				char ch;
				while (true) {
					line = br.readLine();

					if (line == null)
						break; // End of stream reached

					line = line.trim(); // Delete white spaces at the begin and
										// end of line
					try {
						ch = line.charAt(0); // Check if line has numbers with
												// correct answers
						if (ch == '#')
							decodeAnswers(line, answers);

					} catch (IndexOutOfBoundsException e) {
						/*
						 * System.err.println(ERROR_INCORRECT_FORMATTING + " #"
						 * + lineCounter);
						 */
						throw new ParserException(ERROR_INCORRECT_FORMAT + " #"
								+ lineCounter);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}

				counter++;

			} catch (IOException e) {
				System.err.println(ERROR_READ_FILE);
				return;
			} catch (ParserException e) {
				System.err.println(e.getMessage());
				break;
			}
		} // end of while

		try {
			br.close();
		} catch (IOException e) {

		}
	}

	/**
	 * Decodes answers from old format to new format
	 * 
	 * @param line
	 *            string with correct answer code
	 * @param answers
	 *            array with answers that should be decoded
	 * @throws ParserException
	 */
	private void decodeAnswers(String line, ArrayList answers)
			throws ParserException {

		if (answers.size() == 0)
			throw new ParserException("No answers were found");
	}

	/**
	 * Runs parser
	 */
	public void run() {

		/**
		 * Input parameters
		 */
		getParams();
		/**
		 * Show inputed parameters
		 */
		// showProps();
		/**
		 * Open input file
		 */
		parse(openInputFile(), outTest);

		/**
		 * Write formated test to file
		 */
		writeToFile(outTest.toString());

	}

	public static void main(String[] args) {

		new Parser().run();

	}

	public static class ParserException extends Exception {

		public ParserException(String string) {
			super(string);
		}

	}

}
