package entities;

import eduni.simjava.*;

public class Disk extends Sim_entity {
	
	private Sim_port inOne;
	private Sim_port inTwo;
	private Sim_port inThree;
	
	private double delay;
	
	public Disk(String name, double Delay) {
		super(name);
		this.delay = delay;
		
		inOne = new Sim_port("InOne");
		inTwo = new Sim_port("InTwo");
		inThree = new Sim_port("InThree");
		add_port(inOne);
		add_port(inTwo);
		add_port(inThree);
	}
    
	public void body() {
        while (Sim_system.running()) {
          Sim_event e = new Sim_event();
          // Get the next event
          sim_get_next(e);
          // Process the event
          sim_process(delay);
          // The event has completed service
          sim_completed(e);
        }
      }
}
