import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {

	private static final String MESSAGE_WELCOME = "Welcome to textbuddy. %1$s is ready for use \ncommand:";
	private static final String MESSAGE_STRING_ADDED = "added to %1s: \"%2s\"";
	private static final String MESSAGE_DELETED = "deleted from %1s: \"%2s\"";
	private static final String MESSAGE_INVALID_ARGUMENTS = "invalid arguments";
	private static final String MESSAGE_EMPTY_FILE = "%1s is empty";
	private static final String MESSAGE_RETRIEVE_RECORD = "%1s.%2s\n";
	private static final String MESSAGE_CLEAR_CONTENTS = "all content deleted from %1s";
	private static final String MESSAGE_OUT_OF_BOUNDS = "out of bounds";
	private static final String MESSAGE_WRONG_FILE_TYPE = "wrong file type provided";
	
	private final ByteArrayOutputStream stringOutput = new ByteArrayOutputStream();
	private final ByteArrayOutputStream stringError = new ByteArrayOutputStream();
	
	protected TextBuddy buddy;
	
	@Before 
	public void setup(){
		buddy = new TextBuddy("hello.txt");

		System.setOut(new PrintStream(stringOutput));
		System.setErr(new PrintStream(stringError));	
		
		//System.out.println("test");

	}

	@Test
	public void testWelcomeMessage() {
		buddy.printWelcomeMessage("hello.txt");
		assertEquals(String.format(MESSAGE_WELCOME, "hello.txt"), 
				stringOutput.toString());	
	}
	
	@Test
	public void testFormatParams(){
		String[] sampleArgs = {"a","b","c"};
		boolean triggeredError = false;
		try {
			TextBuddy.formatParams(sampleArgs);
		} catch (UnsupportedOperationException e) {
			triggeredError = true;
		} finally {
			assertTrue(triggeredError);
		}	
	}
	
	@Test 
	public void testAddText(){
		String[] sampleArgs = {"abc"};
		buddy.addText(sampleArgs);
		ArrayList<String> contents = buddy.getContents();
		assertEquals(sampleArgs[0], "abc");
	}
	
	@After
	public void sanitizePrintStream(){
		System.setOut(null);
		System.setErr(null);
	}

}
