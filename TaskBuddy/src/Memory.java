import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Memory {
	
	private File savedFile;
	private ArrayList<Task> tempRecord;

	//singleton design pattern
	private static Memory memory = new Memory();
	private Memory() {
		this.savedFile = new File("memory.txt");
		this.tempRecord = new ArrayList<Task>();
	}
	
	public void writeToMemory()  throws IOException {
		try (PrintWriter writer = new PrintWriter("memory.txt", "UTF-8")) {
			for(Task record : tempRecord) {
				writer.println(record);
			}
		} catch (IOException error) {
			error.printStackTrace();
		}
	}
	
	public void addToDoItem(Task item) {
		tempRecord.add(item);
	}
	
	public void deleteToDoItem(int index) {
		int indexToBeDeleted = -1;
		for (Task t: tempRecord) {
			if (t.getId() == index) {
				
				indexToBeDeleted = tempRecord.indexOf(t);
				
			}
		}
		
		if (indexToBeDeleted>=0) {
			tempRecord.remove(indexToBeDeleted);
		}
	}
	
	public void updateToDoItem(int updateId, String updatedPayload) {
		int indexToBeUpdated = -1;
		for (Task t: tempRecord) {
			if (t.getId() == updateId) {
				
				indexToBeUpdated = tempRecord.indexOf(t);
			}
 		}
		
		if (indexToBeUpdated >= 0) {
			tempRecord.get(indexToBeUpdated).setPayload(updatedPayload);;
		}
	}
	public static Memory getMemory() throws IOException {
		memory.restoreExistingFile();
		return memory;
	}
	
	public void restoreExistingFile() throws IOException {
		if (savedFile.exists()) {
			try (BufferedReader reader = new BufferedReader (new FileReader(savedFile))) {
				for (String lineContents; (lineContents = reader.readLine()) != null;) {
					//tempRecord.add(lineContents);
				}
			}
		} else {
			savedFile.createNewFile();
		}
	} 
	
	public String showMemory() {
		String currentView = "";
		for (Task t: tempRecord) {
			currentView += t.getId() + ". " + t.getPayload() + "\n";
		}
		return currentView;
	}

}
