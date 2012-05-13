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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.edu.lnu.unitest.parser.cl.CommandLine;
import ua.edu.lnu.unitest.parser.interfaces.Parser;

public class ParserImpl implements Parser {

	// private static final String FILE_ENCODING = "UTF-16";
	private static final String FILE_ENCODING = "WINDOWS-1251";
	private static final String ERROR_NUMBER_OF_ANSWERS_MISTMACH = "Number of correct answers doesn't match to number of answers";
	private static final String ERROR_INCORRECT_CODES = "Incorrect codes";
	private static final String ERROR_READ_FILE = "Error while reading file";
//	private static final String ERROR_INCORRECT_FILE_EXTENSION = "Incorrect file extension";
	private static final String ERROR_INCORRECT_VALUE = "Incorrect value";
//	private static final String ERROR_READ_INPUT_PARAMS = "Error reading input params. The program will be terminated";
	private static final String ERROR_WRITING_TO_FILE = "Error writing to file";
	private static final String ERROR_UNKNOWN_ENCODING = "Unknown encoding";
//	private static final String ERROR_FILE_DIDN_T_CREATED = "File didn't created";
	private static final String ERROR_INCORRECT_FORMAT = "Incorrect formatting";
//	private static final String ERROR_ANSWERS_MISSED = "Answers are missed";

	// Pattern for question. First character must be a letter
	private final static String QUESTION_PATTERN = "^[Р-пр-џ]";

	// Pattern for answers. First character must be a digit with bracket
	private final static String ANSWER_PATTERN = "^[\\d]\\s*\\)";

	// Pattern for code line. First character in line with code must be '#'
	private final static String CODE_PATTERN = "^[\\#][\\d+]";

	private char separator;

	private String inFileName;
	private String outFileName;

	private int startIndex;
	private int counter;

	private int chapter;
	private int level;
	private int time;
	private int version;
	private int type;
	
	@Override
	public char getSeparator() {
		return separator;
	}

	@Override
	public void setSeparator(char separator) {
		this.separator = separator;
	}

	@Override
	public String getInFileName() {
		return inFileName;
	}

	@Override
	public void setInFileName(String inFileName) {
		this.inFileName = inFileName;
	}

	@Override
	public int getStartIndex() {
		return startIndex;
	}

	@Override
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	@Override
	public int getChapter() {
		return chapter;
	}

	@Override
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}

	// Buffer for input text
	private StringBuilder inTest = new StringBuilder();

	// Buffer for output text
	private StringBuilder outTest = new StringBuilder();

	public ParserImpl() {

		inFileName = "input.txt";
		outFileName = "out_".intern() + inFileName;

		counter = 0;
		version = 0;
		chapter = 1;
		level = 1;
		type = 1;
		time = 60;

		startIndex = 1;
		separator = ')';
		
		new CommandLine(this);
	}

	/**
	 * Open input text file and reads it to buffer
	 * 
	 * @return <b>true</b> if done or <b>false</b> if was errors with file
	 */
	private boolean readFile() {

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

				inTest.append(line);
				inTest.append("\n".intern());
			}
		} catch (FileNotFoundException e) {
			System.out.println("*** File \"" + this.inFileName
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
	 * Get parameters from user
	 */
	

	/**
	 * Get int value from user and parse it to int
	 * 
	 * @param br
	 *            BufferedReader
	 * @param msg
	 * @return <b>0</b> if was pressed <b>Enter</b>
	 * @throws IOException
	 */
/*	private int getIntVal(BufferedReader br, String msg) throws IOException {

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
*/
	/**
	 * Get name of input file
	 * 
	 * @param br
	 * @return
	 * @throws IOException
	 */
/*	private void getFileName(BufferedReader br, String msg) throws IOException {

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
*/
/*
	private void showProps() {

		System.out.println("Chapter: " + chapter);
		System.out.println("Start index: " + startIndex);
		System.out.println("Level: " + level);

		System.out.println("Input file: " + inFileName);
		System.out.println("Output file: " + outFileName);
	}
*/
	/**
	 * Write parsed tests to file
	 */
	private void writeFile() {

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(outFileName)), FILE_ENCODING));

			bw.write(outTest.toString());
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
	 * Process tests
	 * 
	 * @param br
	 * @param sb
	 */
	private void parseTests() {

		int start = 0;
		int end = 0;
//		int lineCounter = 0;
		boolean f_question = false;
		boolean f_answer = false;
//		boolean f_codeline = false;

		String line = null;
		String head = null;

		Matcher q_matcher = null;
		Matcher a_matcher = null;
		Matcher c_matcher = null;

		ArrayList<String> answers = new ArrayList<String>(10);

		// Preparing patterns
		Pattern q_pattern = Pattern.compile(QUESTION_PATTERN,
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Pattern a_pattern = Pattern.compile(ANSWER_PATTERN,
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Pattern c_pattern = Pattern.compile(CODE_PATTERN, Pattern.UNICODE_CASE
				| Pattern.CASE_INSENSITIVE);

		while (inTest.length() > 0) {
			end = inTest.indexOf("\n"); // Find end of line
			line = inTest.substring(start, end); // Get line

			q_matcher = q_pattern.matcher(line);
			a_matcher = a_pattern.matcher(line);
			c_matcher = c_pattern.matcher(line);

			if (q_matcher.find()) {
				if (f_question == true) {
					System.err.println(ERROR_INCORRECT_FORMAT);
					break;
				} else {
					f_question = true;
					// Execute process question line

					head = "Й " + (startIndex + counter) + ", " + version
							+ ", " + chapter + ", " + level + ", " + type
							+ ", " + time + "\n";
					outTest.append(head);
					outTest.append(line);
					outTest.append("\n");

					counter++;
				}
			} else if (a_matcher.find()) {
				if (f_question == false) {
					System.err.println(ERROR_INCORRECT_FORMAT);
					break;
				} else {

					f_answer = true;
					// Replace 'x)' to '-'
					line = a_matcher.replaceFirst("-");
					// Add answer to list of answers
					answers.add(line);
				}
			} else if (c_matcher.find()) {
				if (f_question == false || f_answer == false) {
					System.err.println(ERROR_INCORRECT_FORMAT);
					break;
				} else {
					// Reset flags
					f_question = false;
					f_answer = false;

					try {
						processAnswers(line, answers);

						for (String answer : answers) {
							outTest.append(answer + "\n");
						}

						// Clear buffer with answers
						answers.clear();

					} catch (ParserException e) {
						System.err.println(e.getMessage());
						break;
					}
				}
			} else {
				System.err.println(ERROR_INCORRECT_FORMAT + "at line: " + line);
				break;
			}

			inTest.delete(start, end + 1); // Delete processed string
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
	private void processAnswers(String codes, ArrayList<String> answers)
			throws ParserException {

		// Delete '#'
		codes = codes.substring(1);
		String answer = null;

		// Check if number of answers is equal or less of correct answers
		if (answers.size() <= codes.length())
			throw new ParserException(ERROR_NUMBER_OF_ANSWERS_MISTMACH);

		int start = 0;
		int end = 0;
		int index = 0;

		for (start = 0, end = 1; end <= codes.length(); start++, end++) {
			try {
				// Get number of answer
				index = Integer.parseInt(codes.substring(start, end), 10);
				// Get answer from array
				answer = answers.get(index - 1);
				// Remove array from array
				answers.remove(index - 1);
				// Modify answer to new format
				answer = "+" + answer.substring(1);
				// Insert modified answer to list
				answers.add(index - 1, answer);

			} catch (NumberFormatException e) {
				throw new ParserException(ERROR_INCORRECT_CODES);
			} catch (IndexOutOfBoundsException e) {
				throw new ParserException(ERROR_INCORRECT_CODES);
			}
		}
	}

	/**
	 * Runs parser
	 */
	@Override
	public void parse() {

		if (readFile()) {
			parseTests();
		}

		/**
		 * Write formated test to file
		 */
		writeFile();

	}

	public static void main(String[] args) {

		new ParserImpl();

	}

	public static class ParserException extends Exception {

		private static final long serialVersionUID = 1L;

		public ParserException(String string) {
			super(string);
		}
	}
}
