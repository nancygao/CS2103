import java.util.Date;

public class Task {
	
	public enum eventType {
		FLOATING_TASK, RECURRING_TASK, DEADLINE_TASK, EVENT_TASK
	}
	
	private Date eventDateTime;
	
	private Date deadlineDateTime;
	
	private Date startDateTime;
	private Date endDateTime;
	
	private static int idTick=1;
	private int id;
	
	private String payload;
	
	public Task() {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.payload = payload;
		this.id = Task.idTick++;
	}
	
	public void setStartDateTime(Date start) {
		startDateTime = start;
	}
	
	public Date getStartDateTime() {
		return startDateTime;
	}
	
	public void setEventDateTime(Date event) {
		eventDateTime = event;
	}
	
	public Date getEventDateTime() {
		return eventDateTime;
	}
	
	public void setEndDateTime(Date end) {
		endDateTime = end;
	}
	
	public Date getEndDateTime() {
		return endDateTime;
	}
	
	public void setId(int index) {
		id = index;
	}
	
	public int getId() {
		return id;
	}
	
	public void setPayload(String content) {
		payload = content;
	}
	
	public String getPayload() {
		return payload;
	}
	
}

