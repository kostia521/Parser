package ua.edu.lnu.unitest.parser.cl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ua.edu.lnu.unitest.parser.Parser;

public class CommandLine implements Runnable {

	Parser parser;
	
	public CommandLine(Parser parser) {
		
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
		
		System.out.println("\tPROPS \t\t- prints all properties");
		System.out.println("\tHELP \t\t- prints this message");
		System.out.println("\tPARSE \t\t- start parsing");
		System.out.println("\tEXIT \t\t- stop server, free collection and exit");
	}

}
