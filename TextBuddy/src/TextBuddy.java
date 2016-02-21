/**
 * 
 * Assumptions: 
		1. Only .txt files are allowed, and user must specify a .txt file. If it doesn't exist, the program creates it.
		2. The file saves after each user action.
		3. User is allowed to add null text. 
		4. Delete operations are only valid on indexed lines.
		5. Valid commands are: add "sample string", delete N (N is index of existing string), clear, display and exit
 * 
 */

	
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class TextBuddy {
	private static final String MESSAGE_WELCOME = "Welcome to textbuddy. %1$s is ready for use \ncommand:";
	private static final String MESSAGE_STRING_ADDED = "added to %1s: \"%2s\"";
	private static final String MESSAGE_DELETED = "deleted from %1s: \"%2s\"";
	private static final String MESSAGE_INVALID_ARGUMENTS = "invalid arguments";
	private static final String MESSAGE_EMPTY_FILE = "%1s is empty";
	private static final String MESSAGE_RETRIEVE_RECORD = "%1s.%2s\n";
	private static final String MESSAGE_CLEAR_CONTENTS = "all content deleted from %1s";
	private static final String MESSAGE_OUT_OF_BOUNDS = "out of bounds";
	private static final String MESSAGE_WRONG_FILE_TYPE = "wrong file type provided";
	private static final String MESSAGE_FILE_SORTED = "%1s was sorted correctly";
	private static final String MESSAGE_FILE_NOT_SORTED = "%1s was not sorted correctly";


	
	private String fileName;
	private String userCommand;
	
	private ArrayList<String> textRecords;

	private Scanner systemInput;	
	
	private File savedFile;
	
	public static void main(String[] args) throws Exception {
		String fileName = checkParams(args);
		TextBuddy textBuddyHelper = new TextBuddy(fileName);
		textBuddyHelper.printWelcomeMessage(fileName);
		textBuddyHelper.restoreExistingFile();
		textBuddyHelper.readCommand();
	}
	
	//Constructor instantiates TextBuddy class and creates new ArrayList and File
	public TextBuddy(String fileName) {
		this.fileName = fileName;
		this.systemInput = new Scanner(System.in);
		textRecords = new ArrayList<String>();
		savedFile = new File(fileName);
	}
	
	public void printWelcomeMessage(String fileName){
		System.out.print(String.format(MESSAGE_WELCOME, fileName));	
	}
	
	//checkParams method verifies that the input file is type .txt and has correct number of arguments
	public static String checkParams(String[] args) throws UnsupportedOperationException {
		if (args.length == 1 && args[0].endsWith(".txt")) {
			return args[0];
		} else {
			throw new UnsupportedOperationException(MESSAGE_WRONG_FILE_TYPE);
		}
	}
	
	//restoreExistingFile method restores a file from memory if it already exists
	//else, it creates a new file
	public void restoreExistingFile() throws IOException {
		if (savedFile.exists()) {
			try (BufferedReader reader = new BufferedReader (new FileReader(savedFile))) {
				for (String lineContents; (lineContents = reader.readLine()) != null;) {
					textRecords.add(lineContents);
				}
			}
		} else {
			savedFile.createNewFile();
		}
	}
	
	//readCommand method checks what command the user entered and calls the appropriate helper methods 
	public void readCommand() throws IOException {		
		do { 
			userCommand = systemInput.nextLine();
			String[] userInput = userCommand.split("\\s+");
			
			if (userCommand.startsWith("add")) {
				addText(userInput);
			} else if (userCommand.startsWith("delete")) {
				deleteLine(userInput, fileName);
			} else if (userCommand.startsWith("display")) {
				displayFile();
			} else if (userCommand.startsWith("clear")) {
				clearFile();
			} else if (userCommand.startsWith("exit")) {
				exitProgram();
			} else if (userCommand.startsWith("sort")) {
				sortAlphabetically();
			} 
		
			saveContents();
			
		} while(!userCommand.startsWith("exit"));	
	}
	
	public void sortAlphabetically() {
		if (textRecords.size() > 0){
			Collections.sort(textRecords);
			System.out.println(String.format(MESSAGE_FILE_SORTED, fileName));
		} else { 
			System.out.println(String.format(MESSAGE_FILE_NOT_SORTED, fileName));
		}		
	}
	
	public void addText(String [] userInput) {	
		String addedString = "";
		for (int i = 1; i < userInput.length; i++) {
			addedString += userInput[i];
			if (i != userInput.length-1) {
				addedString += " ";
			}
		}
		textRecords.add(addedString);
		System.out.println(String.format(MESSAGE_STRING_ADDED, fileName, addedString));
	}
	
	public void deleteLine(String[] userInput, String fileName) throws NumberFormatException {	
		if (userInput.length != 2 ) {
			System.out.println(MESSAGE_INVALID_ARGUMENTS);
		} else {
			try { 
				int index = Integer.parseInt(userInput[1]);
				if (index < 1 || index>textRecords.size()) {
					System.out.println(MESSAGE_OUT_OF_BOUNDS);
				} else {
					String targetLine = textRecords.get(index-1);
					System.out.println(String.format(MESSAGE_DELETED, fileName, targetLine));
					textRecords.remove(index-1);
				}	
			} catch(NumberFormatException error) {
				System.out.println(MESSAGE_INVALID_ARGUMENTS);
			}		
		}
	}
	
	public void displayFile() {
		if (textRecords.size() == 0) {
			System.out.println(String.format(MESSAGE_EMPTY_FILE, fileName));
		} 
		
		for (int i = 0; i < textRecords.size(); i++) {
			int itemNumber = i+1; 
			String itemContents = textRecords.get(i);
			System.out.println(String.format(MESSAGE_RETRIEVE_RECORD, itemNumber, itemContents));
		}
	}
	
	public void clearFile() {	
		textRecords.clear();
		System.out.println(String.format(MESSAGE_CLEAR_CONTENTS, fileName));
	}
	
	public void exitProgram() {
		systemInput.close();
	}
	
	//saveContents method saves the data after each user action  
	public void saveContents() throws IOException {
		try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
			for(String record : textRecords) {
				writer.println(record);
			}
		} catch (IOException error) {
			error.printStackTrace();
		}
	}
	
	public ArrayList<String> getContents(){
		return textRecords;
	}
	
}

