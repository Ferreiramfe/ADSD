package miniteste;

import java.util.Queue;
import java.util.LinkedList;
import java.io.PrintWriter;

public class Scheduler extends Thread {
	
	private UniformDistribution ud1;
	private UniformDistribution ud2;
	private UniformDistribution udservice;

	private Queue<Events> queue1;
	private Queue<Events> queue2;
	private Queue<Events> timeLine;	
	
	private SchedulerStates state;
	private Events event;
	private Events element;
	
	private int time;
	private int index;
	
	private PrintWriter writer;
	
	public Scheduler (int time) {
				
		this.queue1 = new LinkedList<Events>();
		this.ud1 = new UniformDistribution(1, 12, 11, 7, 5);
		
		this.queue2 = new LinkedList<Events>();
		this.ud2 = new UniformDistribution(1, 4, 3, 1, 3);
		
		this.udservice = new UniformDistribution(2, 6, 5, 1, 3);
		
		this.timeLine = new LinkedList<Events>();
		
		this.state = SchedulerStates.FREE;
		
		scheduleStartQueue1(0);
		scheduleStartQueue2(0);
		
		this.time = time;
		this.index = 0;
		
		try {
			writer = new PrintWriter("output", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		int seconds = 0;
		
		while (seconds < this.time) {
			try {
				this.sleep(1);
				seconds++;
				checkState(seconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void scheduleStartQueue1(int seconds) {
		event = new Events(PrimaryEvents.ARRIVAL_1, seconds + ud1.getUniformDist().get(index));
		timeLineEvents(event);
		this.index++;
	}
	
	public void scheduleStartQueue2(int seconds) {
		event = new Events(PrimaryEvents.ARRIVAL_2, seconds + ud2.getUniformDist().get(index));
		timeLineEvents(event);
		this.index++;
	}
	
	public void scheduleEnd(int seconds, Events event) {
		event.setPrimaryEvent(PrimaryEvents.SERVICE_COMPLETION);
		event.setEventTime(seconds + udservice.getUniformDist().get(index));
		this.element = event;
		timeLineEvents(event);
		this.index++;
	}
	
	public void timeLineEvents(Events event) {
		timeLine.add(event);
		this.index = 0;
	}
	
	public void busyState(int seconds, Events nextEvent) {
		this.state = SchedulerStates.BUSY;
		scheduleEnd(seconds, nextEvent);
	}
	
	public Events nextEvent(int seconds) {
		Events nextEvent = minEventTimeLine();
		if (nextEvent.getEventTime() == seconds) {
			timeLine.remove(nextEvent);
			return nextEvent;
		}
		
		return null;
	}
	
	public Events minEventTimeLine() {
		Events minEvent = timeLine.peek();
		for (Events event : timeLine) {
			if (minEvent.getEventTime() > event.getEventTime()) {
				minEvent = event;
			}
		}
		return minEvent;
	}
	
	public void checkState(int seconds) {
		Events nextEvent = nextEvent(seconds);
		if (nextEvent == null) {
			return;
		}
		
		if (nextEvent.getPrimaryEvent().equals(PrimaryEvents.ARRIVAL_1)) {
			System.out.println("Event: " + nextEvent.getPrimaryEvent() + " Seconds: " + nextEvent.getEventTime());
			if (this.state == SchedulerStates.FREE) {
				busyState(seconds, nextEvent);
			} else {
				this.queue1.add(nextEvent);
			}
			scheduleStartQueue1(seconds);
		}
		else if (nextEvent.getPrimaryEvent().equals(PrimaryEvents.ARRIVAL_2)) {
			System.out.println("Event: " + nextEvent.getPrimaryEvent() + " Seconds: " + nextEvent.getEventTime());
			if (this.state == SchedulerStates.FREE) {
				busyState(seconds, nextEvent);
			} else {
				this.queue2.add(nextEvent);
			}
			scheduleStartQueue2(seconds);
		} 
		else if (nextEvent.getPrimaryEvent().equals(PrimaryEvents.SERVICE_COMPLETION)) {
			System.out.println("Event: " + nextEvent.getPrimaryEvent() + " Seconds: " + nextEvent.getEventTime());
			queueState(seconds);
		}
		System.out.println(this.state);
		System.out.println();
		System.out.println("Elementos na Fila 1: ");
		for (Events event : queue1) {
			System.out.print(event.getEventTime() + " ");
		}
		System.out.println();
		System.out.println("Elementos na Fila 2: ");
		for (Events event : queue2) {
			System.out.print(event.getEventTime() + " ");
		}
		System.out.println();
		System.out.println("Elemento no serviço " + this.element.getEventTime());
		System.out.println();
		checkState(seconds);
	}
	
	public void queueState(int seconds) {
		if (queue1.isEmpty() && queue2.isEmpty()) {
			this.state = SchedulerStates.FREE;
		} else if (!queue1.isEmpty()) {
			Events headEvent = queue1.poll();
			scheduleEnd(seconds, headEvent);
		} else if (!queue2.isEmpty() && queue1.isEmpty()) {
			Events headEvent = queue2.poll();
			scheduleEnd(seconds, headEvent);
		}
	}

	public void printData(int seconds, Events event) {
		if (!event.getPrimaryEvent().equals(PrimaryEvents.SERVICE_COMPLETION)) {
			writer.println("Tipo de evento: Chegada, Momento do evento: " + event.getEventTime());
			writer.println("Elementos na Fila 1: ");
			
			for (Events eventInQueue : queue1) {
				writer.print(eventInQueue + " ");
			}
			
			writer.println("Elementos na Fila 2: ");
			
			for (Events eventInQueue : queue2) {
				writer.print(eventInQueue + " ");
			}
			
			writer.println("Elemento no serviço: ");
			
			if (this.state == SchedulerStates.FREE) {
				writer.print("Nenhum");	
			} else {
				Events elementAtService = this.element;
				writer.print(elementAtService);
			}	
		}
	}
	
	public static void main(String[] args) {
		int schedulerTime = 20;
		Scheduler model = new Scheduler(schedulerTime);
		
		model.start();
	}	
}
