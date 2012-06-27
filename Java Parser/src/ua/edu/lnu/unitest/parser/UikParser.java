package ua.edu.lnu.unitest.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.edu.lnu.unitest.parser.cl.CommandLine;

/**
 * Парсер История украинской культуры
 * 
 * @author Kostyantyn Drobyazko
 * 
 */
public class UikParser extends TemplateParser {

	private int counter;
	/**
	 * Pattern for question. First character must be a letter
	 */
	protected final static String QUESTION_PATTERN = "^[А-Яа-я]";
	/**
	 * Pattern for answers. First character must be a digit with bracket
	 */
	protected final static String ANSWER_PATTERN = "^[\\d]\\s*\\)";
	/**
	 * Pattern of correct answer
	 */
	protected final static String CODE_PATTERN_CORRECT = "[\\+\\s*\\n]$";
	/**
	 * Pattern of incorrect answer
	 */
	protected final static String CODE_PATTERN_INCORRECT = "[\\-\\s*\\n]$";

	public UikParser() {
		super();
		new CommandLine(this);
	}

	@Override
	public void parseText(StringBuilder inText, StringBuilder outText) {

		int start = 0;
		int end = 0;

		boolean f_question = false;
		boolean f_answer = false;

		String line = null;
		String head = null;

		Matcher q_matcher = null;
		Matcher a_matcher = null;

		ArrayList<String> answers = new ArrayList<String>(10);

		// Preparing patterns
		Pattern q_pattern = Pattern.compile(QUESTION_PATTERN,
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Pattern a_pattern = Pattern.compile(ANSWER_PATTERN,
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

		while (inText.length() > 0) {
			end = inText.indexOf("\n"); // Find end of line
			line = inText.substring(start, end); // Get line

			q_matcher = q_pattern.matcher(line);
			a_matcher = a_pattern.matcher(line);

			if (q_matcher.find()) {
				if (f_answer == true) {
					// Found new question. Write down previous question to
					// outBuffer
					writeAnswers(answers, outText);
					f_answer = false;
				}

				head = "\n№ " + (getStartIndex() + counter) + ", "
						+ getVersion() + ", " + getChapter() + ", "
						+ getLevel() + ", " + getType() + ", " + getTime()
						+ "\n";
				outText.append(head);
				outText.append(line);
				outText.append("\n");

				counter++;

			} else if (a_matcher.find()) {

				f_answer = true;
				// Delete 'x)'
				// line = line.replaceAll(ANSWER_PATTERN, "").trim();
				processAnswer(answers, line);

			} else {
				System.err
						.println(ERROR_INCORRECT_FORMAT + "at line:\n" + line);
				break;
			}
			// Delete processed string
			inText.delete(start, end + 1);
		}

	}

	/**
	 * Decode answer to correct form
	 * 
	 * @param answers
	 * @param answer
	 */
	protected void processAnswer(ArrayList<String> answers, String answer) {

		Pattern correctAnswer = Pattern.compile(CODE_PATTERN_CORRECT,
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

		Pattern incorrectAnswer = Pattern.compile(CODE_PATTERN_INCORRECT,
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

		Matcher correct = correctAnswer.matcher(answer);
		Matcher incorrect = incorrectAnswer.matcher(answer);
		// Matcher answerMatcher = a_pattern.matcher(answer);

		if (correct.find()) {
			// System.out.println("Found correct: "+ "+ " + answer.replace("+",
			// ""));

			// delete '+' sign
			answer = answer.replace("+", "");
			// add '+' at begin of the string
			answer = answer.replaceFirst(ANSWER_PATTERN, "+ ");

		} else if (incorrect.find()) {
			// System.out.println("Found incorrect: " + answer);

			// add '-' at begin of the string
			answer = answer.replaceFirst(ANSWER_PATTERN, "- ");

			answer = answer.replaceFirst(CODE_PATTERN_INCORRECT, "");

		} else {
			System.out.println("Unknown: " + answer);
			return;
		}

		// answer = answer.replaceFirst(ANSWER_PATTERN, "");

		answers.add(answer);

	}

	/**
	 * Write answers to out buffer
	 * 
	 * @param answers
	 * @param out
	 */
	protected void writeAnswers(ArrayList<String> answers, StringBuilder out) {

		for (String answer : answers) {

			out.append(answer);
			out.append("\n");
		}
		answers.clear();
	}

	public static void main(String[] args) {

		new UikParser();

	}
}
