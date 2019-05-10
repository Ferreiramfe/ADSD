package miniteste;

public class Events {
	
	private String eventName;
	private PrimaryEvents primaryEvent;
	private int eventTime;
	
	public Events (String eventName, PrimaryEvents primaryEvent, int eventTime) {
		this.eventName = eventName;
		this.primaryEvent = primaryEvent;
		this.eventTime = eventTime;
	}
	
	public PrimaryEvents getPrimaryEvent() {
		return primaryEvent;
	}
	
	public void setPrimaryEvent(PrimaryEvents primaryEvent) {
		this.primaryEvent = primaryEvent;
	}
	
	public void setEventTime(int eventTime) {
		this.eventTime = eventTime;
	}
	
	public int getEventTime() {
		return eventTime;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}
