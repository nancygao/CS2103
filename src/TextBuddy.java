import java.util.*;
import java.lang.Throwable;
import java.io.PrintWriter;
import java.io.File;


public class TextBuddy {
	static ArrayList<String> textRecord = new ArrayList<String>();
	private static String file_name;
	
	public static void main(String[] args) throws Exception{
		TextBuddy TB = new TextBuddy();
		file_name = formatParams(args);
		
		System.out.println("Welcome to textbuddy. " + file_name + " is ready for use \ncommand:");	
		
		//scan user input until exit
		Scanner sc = new Scanner(System.in);	
		String userCommand = null;
		
		do {
			userCommand = sc.nextLine();
			String[] userInput = userCommand.split("\\s+");
			
			if (userCommand.startsWith("add")){
				String addedString = "";
				for (int i=1; i<userInput.length;i++){
					addedString += userInput[i] ;
					if(i!=userInput.length-1) addedString += " ";
				}
				addText(addedString);
			}
			
			else if(userCommand.startsWith("delete")){
				deleteLine(userInput);
			}
			else if(userCommand.startsWith("display")){
				displayFile();
			}
			else if(userCommand.startsWith("clear")){
				clearFile();
			}
			else if(userCommand.equals("exit")){
				exitProgram();
			}
		}while(!userCommand.startsWith("exit"));
	}
		
	public static String formatParams(String[] args) throws Exception{
		if(args.length == 1){
			if(args[0].endsWith(".txt")){
				return args[0];
			}
			else{
				throw new Exception("Wrong extension.");
			}
		}else{
			throw new Exception("No .txt file provided.");
		}
	}
	
	public static void addText(String addedString){		
		textRecord.add(addedString);
		System.out.println("added to "+ file_name + ": \"" + addedString + "\"");
	}
	
	public static void deleteLine(String[] userInput){
		int index = Integer.parseInt(userInput[1]);
		if(index<1||index>textRecord.size()){
			System.out.println("Out of bounds.");
		}else{
			System.out.println("deleted from "+ file_name + ": \"" + textRecord.get(index-1) + "\"");
			textRecord.remove(index-1);
		}			
	}
	
	public static void displayFile(){
		if (textRecord.size()==0){
			System.out.println(file_name+ " is empty");
		}
		for(int i=0;i<textRecord.size();i++){
			System.out.println(i+1 + ". " + textRecord.get(i) + "\n");
		}
	}
	
	public static void clearFile(){	
		textRecord.clear();
		System.out.println("all content deleted from "+ file_name);		
	}
	
	public static void exitProgram(){
		File file = new File(file_name);
	    PrintWriter printWriter = new PrintWriter(file);
	    printWriter.println("I\"M HERE!!!!");
	    printWriter.close();	
	}
}
