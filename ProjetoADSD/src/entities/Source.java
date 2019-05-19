package entities;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;

public class Source extends Sim_entity {
	
	private Sim_port out;
	private double delay;
	
	public Source(String name) {
		super(name);
		this.delay = delay;
		out = new Sim_port("Out");
		add_port(out);
	}
	
    public void body() {
        for (int i=0; i < 100; i++) {
          // Send the processor a job
          sim_schedule(out, 0.0, 0);
          // Pause
          sim_pause(delay);
        }
      }
}
