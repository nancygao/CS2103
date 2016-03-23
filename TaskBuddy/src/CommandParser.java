import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.*; 


public class CommandParser {
	
	public enum commandType {
		ADD, DISPLAY, UPDATE, DELETE, EXIT
	}
	
	private String userInput;
	private String[] inputSplit;
	private static com.joestelmach.natty.Parser parser;
	
	public CommandParser() { 
		CommandParser.initialize();
	}
	
	public static void initialize() {
		CommandParser.parser = new com.joestelmach.natty.Parser(
				TimeZone.getDefault());
		parser.parse("");
	}
		
	public Task addParser(String userInput, String[] inputSplit) {
		Task userToDo = new Task();
		
		//natty library date parser 
		List<DateGroup> groups = parser.parse(userInput);
		
		if (!groups.isEmpty()) {
			
			List<Date> dates = groups.get(0).getDates();
			Collections.sort(dates);
			
			//event with specific time
			if (dates.size() == 1) {
				userToDo.setEventDateTime(dates.get(0));
			}
			
			//event with separate start/end times
			if (dates.size() == 2) {
				userToDo.setStartDateTime(dates.get(0));
				userToDo.setEndDateTime(dates.get(1));
				
				System.out.println(userToDo.getStartDateTime());
				System.out.println(userToDo.getEndDateTime());

			}
		} 
	
		String payload = "";
		for (int i=1; i<inputSplit.length; i++) {
			payload += " " + inputSplit[i];			
		}
		userToDo.setPayload(payload);

		return userToDo;
	}

	public int indexParser(String[] inputSplit) {
		int index = Integer.parseInt(inputSplit[1]);
		return index;
	}
	
	public String updateParser(String[] inputSplit) {
		String payload = "";
		for (int i=2; i<inputSplit.length; i++) {
			payload += " " + inputSplit[i];			
		}
		return payload;
	}
	
	public void exitParser() {
		//unfinished
		
	}
	

  }
