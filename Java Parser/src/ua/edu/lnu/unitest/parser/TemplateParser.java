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

/**
 * 
 * @author Kostyantyn Drobyazko
 * 
 */
public abstract class TemplateParser {

	protected static final String FILE_ENCODING = "WINDOWS-1251";

	// Messages
	protected static final String ERROR_NUMBER_OF_ANSWERS_MISTMACH = "Number of correct answers doesn't match to number of answers";
	protected static final String ERROR_INCORRECT_CODES = "Incorrect codes";
	protected static final String ERROR_READ_FILE = "Error while reading file";
	protected static final String ERROR_INCORRECT_FILE_EXTENSION = "Incorrect file extension";
	protected static final String ERROR_INCORRECT_VALUE = "Incorrect value";
	protected static final String ERROR_READ_INPUT_PARAMS = "Error reading input params. The program will be terminated";
	protected static final String ERROR_WRITING_TO_FILE = "Error writing to file";
	protected static final String ERROR_UNKNOWN_ENCODING = "Unknown encoding";
	protected static final String ERROR_FILE_DIDN_T_CREATED = "File didn't created";
	protected static final String ERROR_INCORRECT_FORMAT = "Incorrect formatting";
	protected static final String ERROR_ANSWERS_MISSED = "Answers are missed";

	// End of messages

	private char separator;
	/**
	 * Input file name
	 */
	private String inFileName;
	/**
	 * Output file name
	 */
	private String outFileName;
	/**
	 * Start index. From this index will begin count of questions
	 */
	private int startIndex;
	/**
	 * Number of chapter
	 */
	private int chapter;
	/**
	 * Number of level
	 */
	private int level;
	/**
	 * The time given to solve task
	 */
	private int time;
	/**
	 * Number of quesiont's variant
	 */
	private int variant;
	/**
	 * Type of question
	 */
	private int type;
	/**
	 * Version of test
	 */
	private int version;
	/**
	 * Buffer for input text
	 */
	protected StringBuilder inText;
	/**
	 * Buffer for output text
	 */
	protected StringBuilder outText;

	public TemplateParser() {

		inFileName = "input.txt";
		outFileName = "out_".intern() + inFileName;

		variant = 0;
		chapter = 1;
		level = 1;
		type = 1;
		time = 60;
		version = 1;

		startIndex = 1;
		separator = ')';

		outText = new StringBuilder();
		inText = new StringBuilder();
	}

	// Getters and setters

	public char getSeparator() {
		return separator;
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

	public String getInFileName() {
		return inFileName;
	}

	public void setInFileName(String inFileName) {
		this.inFileName = inFileName;
	}

	public String getOutFileName() {
		return outFileName;
	}

	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getVariant() {
		return variant;
	}

	public void setVariant(int variant) {
		this.variant = variant;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	// End of getters and setters

	/**
	 * <b>Template method</b>
	 * <p>
	 * readFile(StringBuilder)<br>
	 * parseText(StringBuilder, StringBuilder)<br>
	 * writeFile(StringBuilder)
	 */
	public void parse() {

		readFile(inText, inFileName);
		parseText(inText, outText);
		writeFile(outText, outFileName);

	}

	/**
	 * Method for implementing parsing algorithm
	 * 
	 * @param inText
	 *            input text
	 * @param outText
	 *            output text
	 * 
	 */
	public abstract void parseText(StringBuilder inText, StringBuilder outText);

	/**
	 * <b>Part of algorithm</b><br>
	 * Opens input file and reads it to buffer
	 * 
	 * @param inText
	 *            input buffer
	 * @param inFileName
	 *            input file
	 * @return <b>true</b> if file was opened and red successfully or
	 *         <b>false</b> if were errors during processing
	 */
	protected boolean readFile(StringBuilder inText, String inFileName) {

		if (!checkArgument(inText))
			throw new NullPointerException("Buffer for incomming text is null");

		if (!checkArgument(inFileName))
			throw new NullPointerException("Input file's name is null");

		BufferedReader br = null;
		String line = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(inFileName)), FILE_ENCODING));

			while (true) {
				line = br.readLine();

				if (line == null)
					break;

				line = line.trim();

				if (line.isEmpty())
					continue;

				inText.append(line);
				inText.append("\n".intern());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + this.inFileName
					+ "\" doesn't exist ");
			return false;
		} catch (UnsupportedEncodingException e) {
			System.out.println(ERROR_UNKNOWN_ENCODING);
			return false;
		} catch (IOException e) {
			System.out.println(ERROR_READ_FILE);
			return false;
		}
		try {
			br.close();
		} catch (IOException e) {
			// Do nothing
		}

		return true;
	}

	/**
	 * <b>Part of algorithm</b><br>
	 * Writes content of output buffer to file
	 * 
	 * @param outText
	 *            output buffer
	 * @param outFileName
	 *            name of output file
	 */
	protected void writeFile(StringBuilder outText, String outFileName) {

		if (!checkArgument(outText) || !checkArgument(outFileName))
			throw new NullPointerException("outText of outFileName is null");

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(outFileName)), FILE_ENCODING));

			bw.write(outText.toString());
			bw.close();

		} catch (FileNotFoundException e) {
			System.out.println(ERROR_WRITING_TO_FILE);
		} catch (UnsupportedEncodingException e) {
			System.err.println(ERROR_UNKNOWN_ENCODING);
		} catch (IOException e) {
			System.err.println(ERROR_WRITING_TO_FILE);
		}
	}

	/**
	 * <b>Helper method</b> <br>
	 * Get <b>int</b> value from user and parses it to int
	 * 
	 * @param br
	 *            BufferedReader
	 * @param msg
	 * @return <b>0</b> if was pressed <b>Enter</b>
	 * @throws IOException
	 */
	protected int getIntVal(BufferedReader br, String msg) {

		if (!checkArgument(br) || !checkArgument(msg))
			throw new NullPointerException("br or msg is null");

		String in = null;

		while (true) {

			System.out.print(msg);
			try {

				in = br.readLine();
				if (in.length() == 0)
					return 0;

				return Integer.parseInt(in, 10);

			} catch (NumberFormatException e) {
				System.out.println(ERROR_INCORRECT_VALUE);
				continue;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * <b>Helper method</b><br>
	 * 
	 * Prints value of all properties<br>
	 * <i>If some properties were added, this method should be overridden</i>
	 */
	protected void showProps() {

		System.out.println("Chapter: " + chapter);
		System.out.println("Start index: " + startIndex);
		System.out.println("Level: " + level);

		System.out.println("Input file: " + inFileName);
		System.out.println("Output file: " + outFileName);
	}

	/**
	 * <b>Helper method</b><br>
	 * Checks if argument is null
	 * 
	 * @param arg
	 * @return <b>true</b> if not or <b>false</b> if argument is null
	 */
	protected boolean checkArgument(Object arg) {
		if (arg != null)
			return true;
		else
			return false;
	}

	public static class ParserException extends Exception {

		private static final long serialVersionUID = 1L;

		public ParserException(String string) {
			super(string);
		}
	}

}
