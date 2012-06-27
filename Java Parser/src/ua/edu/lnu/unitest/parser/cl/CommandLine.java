package ua.edu.lnu.unitest.parser.cl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ua.edu.lnu.unitest.parser.TemplateParser;

public class CommandLine implements Runnable {

	private static final String INCORRECT_FILE_EXTENSION = "*** Incorrect file extension";
	private static final String BYE = "*** Bye!!!";
	private static final String INPUT_FILE = "*** Input file: ";
	private static final String VERSION = "*** Variant: ";
	private static final String INCORRECT_ARGS = "*** Incorrect args";
	private static final String TIME = "*** Time: ";
	private static final String TYPE = "*** Type: ";
	private static final String LEVEL = "*** Level:";
	private static final String CHAPTER = "*** Chapter: ";
	private static final String START_INDEX = "*** Initial index: ";
	private static final String UNKNOWN_COMMAND = "***Unknown command";
	
	private TemplateParser parser;

	public CommandLine(TemplateParser parser) {

		this.parser = parser;
		// Start thread
		new Thread(this).start();
	}

	@Override
	public void run() {

		String command = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("***Parser Commad Line***");

		while (!Thread.currentThread().isInterrupted()) {

			try {
				System.out.print("$> ");
				command = in.readLine();
				executeCommand(command);
			} catch (IOException e) {
				System.err.println("IO Error. Program will be termainated");
			}
		}
	}

	private void executeCommand(String command) {

		if (command == null)
			return;
		// Conver command to lover case
		command = command.toLowerCase().trim();
		// Separate args from command
		String[] c = command.split(" ");

		switch (c[0]) {
		case "chapter":
			doChapter(c);
			break;

		case "exit":
			doExit();
			break;
			
		case "file":
			doFile(c);
			break;

		case "help":
			doHelp();
			break;

		case "level":
			doLevel(c);
			break;

		case "parse":
			doParse();
			break;

		case "props":
			doProps();
			break;

		case "separator":
			doSeparator(c);
			break;

		case "sindex":
			doSindex(c);
			break;

		case "time":
			doTime(c);
			break;

		case "type":
			doType(c);
			break;
		
		case "version":
			doVariant(c);
			break;
		default:
			System.out.println(UNKNOWN_COMMAND);
		}
	}

	private void doFile(String[] c) {
		 
		if (c.length == 1) {
			System.out.println(INPUT_FILE + parser.getInFileName());		
		} else if (c.length == 2) {
			
			if (!(c[1].endsWith(".txt"))) {
				System.out.flush();
				System.err.println(INCORRECT_FILE_EXTENSION);
			} else {
				parser.setInFileName(c[1]);
			}
			
		} else {
			System.out.println(INCORRECT_ARGS);
		}
	}

	// Executes command TYPE or TYPE <ARG>
	private void doType(String[] c) {

		int value = 0;

		if (c.length == 1) {
			System.out.println(TYPE + parser.getType());
		} else if (c.length == 2) {
			try {
				value = Integer.parseInt(c[1], 10);
				parser.setType(value);

			} catch (NumberFormatException e) {
				System.out.println(INCORRECT_ARGS);
			}

		} else {
			System.out.println(UNKNOWN_COMMAND);
		}
	}

	// Executes command TIME or TIME <ARG>
	private void doTime(String[] c) {

		int value = 0;

		if (c.length == 1) {
			System.out.println(TIME + parser.getTime());
		} else if (c.length == 2) {
			try {
				value = Integer.parseInt(c[1], 10);
				parser.setTime(value);

			} catch (NumberFormatException e) {
				System.out.println(INCORRECT_ARGS);
			}

		} else {
			System.out.println(UNKNOWN_COMMAND);
		}
	}

	// Executes command SINDEX or SINDEX <ARG>
	private void doSindex(String[] c) {

		int value = 0;

		if (c.length == 1) {
			System.out.println(START_INDEX + parser.getStartIndex());
		} else if (c.length == 2) {
			try {
				value = Integer.parseInt(c[1], 10);
				parser.setStartIndex(value);

			} catch (NumberFormatException e) {
				System.out.println(INCORRECT_ARGS);
			}

		} else {
			System.out.println(UNKNOWN_COMMAND);
		}
	}

	// Stub
	private void doSeparator(String[] c) {

	}

	// Executes command VARIANT or VARIANT <ARG>
	private void doVariant(String[] c) {

		int value = 0;

		if (c.length == 1) {
			System.out.println(VERSION + parser.getVersion());
		} else if (c.length == 2) {
			try {
				value = Integer.parseInt(c[1], 10);
				parser.setVersion(value);

			} catch (NumberFormatException e) {
				System.out.println(INCORRECT_ARGS);
			}

		} else {
			System.out.println(UNKNOWN_COMMAND);
		}
	}

	// Shows all properties
	private void doProps() {

		System.out.println(INPUT_FILE + parser.getInFileName());
		System.out.println(START_INDEX + parser.getStartIndex());
		System.out.println(VERSION + parser.getVersion());
		System.out.println(CHAPTER + parser.getChapter());
		System.out.println(LEVEL + parser.getLevel());
		System.out.println(TYPE + parser.getType());
		System.out.println(TIME + parser.getTime());
	}

	private void doParse() {

		parser.parse();
	}

	private void doLevel(String[] c) {

		int value = 0;

		if (c.length == 1) {
			System.out.println(LEVEL + parser.getLevel());
		} else if (c.length == 2) {
			try {
				value = Integer.parseInt(c[1], 10);
				parser.setLevel(value);

			} catch (NumberFormatException e) {
				System.out.println(INCORRECT_ARGS);
			}

		} else {
			System.out.println(UNKNOWN_COMMAND);
		}
	}

	private void doExit() {

		System.out.println(BYE);
		Thread.currentThread().interrupt();
	}

	private void doChapter(String[] c) {

		int value = 0;

		if (c.length == 1) {
			System.out.println(CHAPTER + parser.getChapter());
		} else if (c.length == 2) {
			try {
				value = Integer.parseInt(c[1], 10);
				parser.setChapter(value);

			} catch (NumberFormatException e) {
				System.out.println(INCORRECT_ARGS);
			}

		} else {
			System.out.println(UNKNOWN_COMMAND);
		}
	}

	/**
	 * Show help message
	 */
	private void doHelp() {

		System.out.println("\n\tCHAPTER \t\t- show chapter");
		System.out.println("\n\tCHAPTER <val> \t\t- set chapter");

		System.out.println("\tLEVEL \t\t- show level");
		System.out.println("\tLEVEL <val> \t\t- set level");

		System.out.println("\tTYPE \t\t- show type");
		System.out.println("\tTYPE <val> \t\t- set type");

		System.out.println("\tTIME \t- show time");
		System.out.println("\tTIME <val> \t- set time");

		System.out.println("\tSINDEX \t- show start index");
		System.out.println("\tSINDEX <val> \t- set start index");
		
		System.out.println("\tVERSION \t- show version");
		System.out.println("\tVERSION <val> \t- set version");

		System.out.println("\tPROPS \t\t- prints all properties");
		System.out.println("\tHELP \t\t- prints this message");
		System.out.println("\tPARSE \t\t- start parsing");
		System.out
				.println("\tEXIT \t\t- stop server, free collection and exit");
	}
}
