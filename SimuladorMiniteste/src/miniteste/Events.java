package miniteste;

public class Events {
	
	private PrimaryEvents primaryEvent;
	private int eventTime;
	
	public Events (PrimaryEvents primaryEvent, int eventTime) {
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
}
