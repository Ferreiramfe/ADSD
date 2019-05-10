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
			writer = new PrintWriter("output.txt", "UTF-8");
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
		
		writer.close();
	}
	
	public void scheduleStartQueue1(int seconds) {
		event = new Events(NameGenerator.generateName(), PrimaryEvents.ARRIVAL_1, seconds + ud1.getUniformDist().get(index));
		timeLineEvents(event);
		this.index++;
	}
	
	public void scheduleStartQueue2(int seconds) {
		event = new Events(NameGenerator.generateName(), PrimaryEvents.ARRIVAL_2, seconds + ud2.getUniformDist().get(index));
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
			writer.println();
			writer.println("Tipo do Evento: " + nextEvent.getPrimaryEvent() + ", Momento do Evento: " + seconds);
			writer.println("Nome do elemento: " + nextEvent.getEventName());
			if (this.state == SchedulerStates.FREE) {
				busyState(seconds, nextEvent);
			} else {
				this.queue1.add(nextEvent);
			}
			scheduleStartQueue1(seconds);
		}
		else if (nextEvent.getPrimaryEvent().equals(PrimaryEvents.ARRIVAL_2)) {
			writer.println();
			writer.println("Tipo do Evento: " + nextEvent.getPrimaryEvent() + ", Momento do Evento: " + seconds);
			writer.println("Nome do elemento: " + nextEvent.getEventName());
			if (this.state == SchedulerStates.FREE) {
				busyState(seconds, nextEvent);
			} else {
				this.queue2.add(nextEvent);
			}
			scheduleStartQueue2(seconds);
		} 
		else if (nextEvent.getPrimaryEvent().equals(PrimaryEvents.SERVICE_COMPLETION)) {
			writer.println();
			writer.println("Tipo do Evento: " + nextEvent.getPrimaryEvent() + ", Momento do Evento: " + seconds);
			writer.println("Nome do elemento: " + nextEvent.getEventName());
			queueState(seconds);
		}
		writer.println();
		writer.println("Números de Elementos na Fila 1: " + queue1.size());
		writer.print("Elementos na Fila 1:");
		
		String saida1 = "";
		String saida2 = "";
		for (Events event : queue1) {
			saida1 += event.getEventName() + ", ";
		}
		
		writer.print(saida1.replaceAll(", $", ""));
		
		writer.println();
		writer.println("Números de Elementos na Fila 2: " + queue2.size());
		writer.print("Elementos na Fila 2: ");
		
		for (Events event : queue2) {
			saida2 += event.getEventName() + ", ";
		}
		
		writer.print(saida2.replaceAll(", $", ""));
		writer.println();
		writer.println("Elemento no serviço: " + this.element.getEventName());
		
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
	
	public static void main(String[] args) {
		int schedulerTime = 300;
		Scheduler model = new Scheduler(schedulerTime);
		
		model.start();
	}	
}
