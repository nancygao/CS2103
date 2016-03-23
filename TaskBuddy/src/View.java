
public class View {
	
	//singleton design pattern 
	private static View view = new View();	
	private View() {
		
	}
	
	public static View getView() {
		return view;
	}
	
	public void displayAddToDo(Task item) {
		System.out.println("Task added: " + item.getId() + ". " 
							+ item.getPayload());		
	}
	
	public void displayUpdatetoDo(int index) {
		System.out.println("Task updated: item " + index);
	}
	
	public void displayDeleteToDo(int index) {
		System.out.println("Task deleted: item " + index);
	}
	
	public void displayToUser(String show) {
		System.out.println(show);
	}

}

