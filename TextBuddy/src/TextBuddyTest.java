import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {

	private static final String MESSAGE_WELCOME = "Welcome to textbuddy. %1$s is ready for use \ncommand:";
	private static final String MESSAGE_STRING_ADDED = "added to %1s: \"%2s\"\n";
	private static final String MESSAGE_DELETED = "deleted from %1s: \"%2s\"\n";
	private static final String MESSAGE_INVALID_ARGUMENTS = "invalid arguments";
	private static final String MESSAGE_EMPTY_FILE = "%1s is empty\n";
	private static final String MESSAGE_RETRIEVE_RECORD = "%1s.%2s";
	private static final String MESSAGE_CLEAR_CONTENTS = "all content deleted from %1s\n";
	private static final String MESSAGE_OUT_OF_BOUNDS = "out of bounds";
	private static final String MESSAGE_WRONG_FILE_TYPE = "wrong file type provided";
	private static final String MESSAGE_FILE_SORTED = "%1s was sorted correctly\n";
	private static final String MESSAGE_FILE_NOT_SORTED = "%1s was not sorted correctly";
	private static final String MESSAGE_STRINGS_FOUND = "%1s. %2s\n";
	private static final String MESSAGE_NONE_FOUND = "no strings were found\n";
	
	private final ByteArrayOutputStream stringOutput = new ByteArrayOutputStream();
	private final ByteArrayOutputStream stringError = new ByteArrayOutputStream();
	
	protected TextBuddy buddy;
	
	@Before 
	public void setup(){
		buddy = new TextBuddy("hello.txt");

		System.setOut(new PrintStream(stringOutput));
		System.setErr(new PrintStream(stringError));	
	}

	@Test
	public void testWelcomeMessage() {
		buddy.printWelcomeMessage("hello.txt");
		
		assertEquals(String.format(MESSAGE_WELCOME, "hello.txt"), 
				stringOutput.toString());	
	}
	
	@Test
	public void testFormatParams() {
		String[] sampleArgs = {"a","b","c"};
		
		boolean triggeredError = false;
		
		try {
			TextBuddy.checkParams(sampleArgs);
		} catch (UnsupportedOperationException e) {
			triggeredError = true;
		} finally {
			assertTrue(triggeredError);
		}	
	}
	
	@Test 
	public void testAddText() {
		String[] sampleArgs = {"add","abc"};
		buddy.addText(sampleArgs);
		
		ArrayList<String> contents = buddy.getContents();
		String firstItem = contents.get(0);
		
		assertEquals("abc", firstItem);
		assertEquals(String.format(MESSAGE_STRING_ADDED, "hello.txt", "abc"),
				stringOutput.toString());
	}
	
	@Test
	public void testDeleteLine() {
		String[] sampleAddArgs = {"add","abc"};
		String[] sampleDeleteArgs = {"delete", "1"};
		
		ArrayList<String> contents = buddy.getContents();
		
		buddy.addText(sampleAddArgs);
		buddy.deleteLine(sampleDeleteArgs, "hello.txt");

		boolean wasDeleted = contents.isEmpty();
		
		assertTrue(wasDeleted);
		assertEquals(String.format(MESSAGE_STRING_ADDED, "hello.txt", "abc") 
				+ String.format(MESSAGE_DELETED, "hello.txt", "abc"),
				stringOutput.toString());
		}
	
	@Test
	public void testDisplayFile() {
		buddy.displayFile();
		
		assertEquals(String.format(MESSAGE_EMPTY_FILE, "hello.txt"),
				stringOutput.toString());
		
		String[] sampleAddArgs = {"add","abc"};
		
		buddy.addText(sampleAddArgs);
		stringOutput.reset();

		buddy.displayFile();
		
		assertEquals(String.format(MESSAGE_RETRIEVE_RECORD, 1, "abc"),
				stringOutput.toString().trim());	
	}
	
	@Test
	public void testClearFile() {
		buddy.clearFile();
		assertEquals(String.format(MESSAGE_CLEAR_CONTENTS, "hello.txt"),
				stringOutput.toString());
	}
	
	@Test
	public void testSortAlphabetically() {
		String[] sampleAddArgs = {"add","abc","123","cat"};

		buddy.addText(sampleAddArgs);
		stringOutput.reset();
		
		buddy.sortAlphabetically();
		assertEquals(String.format(MESSAGE_FILE_SORTED, "hello.txt"),
				stringOutput.toString());
		
	}
	
	@Test
	public void testSearchLinesContaining() {
		String[] sampleAddArgs = {"add","abc","123","cat", "catcatcat"};
		String[] sampleSearchArgs = {"search","dog"};

		buddy.addText(sampleAddArgs);
		stringOutput.reset();
		
		buddy.searchLinesContaining(sampleSearchArgs);
		assertEquals(String.format(MESSAGE_NONE_FOUND),
				stringOutput.toString());
	}
	
	@After
	public void sanitizePrintStream(){
		System.setOut(null);
		System.setErr(null);
	}

}
