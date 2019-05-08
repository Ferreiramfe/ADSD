package miniteste;

public enum PrimaryEvents {
	
	ARRIVAL_1(1),
	ARRIVAL_2(2),
	SERVICE_COMPLETION(3);
	
	private int eventType;
	
	PrimaryEvents(int eventType) {
		this.eventType = eventType;
	}
		
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public int getEventType() {
		return eventType;
	}
	
}
