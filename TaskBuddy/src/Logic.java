import java.io.IOException;
import java.util.Scanner;

public class Logic {
	private Scanner systemInput;
	
	private Memory model;
	private View view;

	//singleton design pattern
	private static Logic logic = new Logic();
	private Logic() { 
		this.systemInput = new Scanner(System.in);
		
		try {
			this.model = Memory.getMemory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.view = View.getView();
	}
	
	public static Logic getLogic() {
		return logic;
	}
	
	public void readCommand() throws IllegalArgumentException { 
		
		//instantiates Parser 
		CommandParser commandParser = new CommandParser();
		
		while(true) {
			String userInput = systemInput.nextLine();
			
			//split input into array delimited by whitespace
			//command is expected to be first word in user input
			String[] inputSplit = userInput.split("\\s+");
			
			String command = inputSplit[0];
			
			switch (command) {
				case "add":								
					Task item = commandParser.addParser(userInput, inputSplit);
					
					//assert item != null: "item is null!";
					
					model.addToDoItem(item);
					view.displayAddToDo(item);
					break;
					
				case "display":
					String currentDisplay = model.showMemory();
					
					view.displayToUser(currentDisplay);
					break;
					
				case "update":
					int updateId = commandParser.indexParser(inputSplit);
					String updatedPayload = commandParser.updateParser(inputSplit);
					
					model.updateToDoItem(updateId, updatedPayload);
					view.displayUpdatetoDo(updateId);
					
					break;
					
				case "edit":
					int editId = commandParser.indexParser(inputSplit);
					String editedPayload = commandParser.updateParser(inputSplit);
					
					model.updateToDoItem(editId, editedPayload);
					view.displayUpdatetoDo(editId);
					
					break;
					
				case "delete":
					//assuming user deletes by id
					//id should follow 'delete' command
					int deleteIndex = commandParser.indexParser(inputSplit);
					model.deleteToDoItem(deleteIndex);
					view.displayDeleteToDo(deleteIndex);
					break;
					
				case "exit":
					commandParser.exitParser();
					//write to memory
					break;
					
				default:
					//throw new IllegalArgumentException();
					System.out.println("Invalid command, try again.");
			}	
		}
	}	
}
