package ua.edu.lnu.unitest.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.edu.lnu.unitest.parser.cl.CommandLine;

/**
 * Парсер Криминальное право Украины (особенная часть)
 * 
 * @author Kostyantyn Drobyazko
 * 
 */
public class CpuParser extends TemplateParser {

	/**
	 * Questions counter
	 */
	private int counter;
	/**
	 * Pattern for question. First character must be a letter
	 */
	private final static String QUESTION_PATTERN = "^[А-Яа-я]";
	/**
	 * Pattern for answers. First character must be a digit with bracket
	 */
	private final static String ANSWER_PATTERN = "^[\\d]\\s*\\)";
	/**
	 * Pattern for code line. First character in line with code must be '#'
	 */
	private final static String CODE_PATTERN = "^[\\#][\\d+]";

	public CpuParser() {

		super();
		new CommandLine(this);
	}

	@Override
	public void parseText(StringBuilder inText, StringBuilder outText) {

		int start = 0;
		int end = 0;
		// int lineCounter = 0;
		boolean f_question = false;
		boolean f_answer = false;
		// boolean f_codeline = false;

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

		while (inText.length() > 0) {
			end = inText.indexOf("\n"); // Find end of line
			line = inText.substring(start, end); // Get line

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

					head = "№ " + (getStartIndex() + counter) + ", "
							+ getVersion() + ", " + getChapter() + ", "
							+ getLevel() + ", " + getType() + ", " + getTime()
							+ "\n";
					outText.append(head);
					outText.append(line);
					outText.append("\n");

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
							outText.append(answer + "\n");
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

			inText.delete(start, end + 1); // Delete processed string
		}
	}

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

	public static void main(String[] args) {

		new CpuParser();
	}

}
