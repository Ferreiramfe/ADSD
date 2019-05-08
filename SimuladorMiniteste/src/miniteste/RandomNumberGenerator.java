package miniteste;

import java.util.List;
import java.util.ArrayList;

public class RandomNumberGenerator {
	
	private int seed;	
	private List<Integer> values;
	
	
	public RandomNumberGenerator(int seed) {
		this.seed = seed;
		this.values = new ArrayList<Integer>();
	}
	
	public List<Integer> multiplicativeCongruential(int multiplier, int increment, int mod) {
		values = new ArrayList<Integer>();
		values.add(this.seed);
		
		for (int i = 0; i < mod - 1; i++) {
			int previous = values.get(i);
			int xn = ((multiplier * previous) + increment) % mod;
			
			values.add(xn);
		}
		
		return values;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
	
}
