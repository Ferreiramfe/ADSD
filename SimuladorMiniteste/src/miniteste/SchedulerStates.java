package miniteste;

public enum SchedulerStates {
	
	FREE(0),
	BUSY(1);
	
	private int schedulerState;
	
	SchedulerStates(int schedulerState) {
		this.schedulerState = schedulerState;
	}
	
	public int getSchedulerState() {
		return schedulerState;
	}
}
