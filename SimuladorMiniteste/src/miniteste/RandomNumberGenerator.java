package miniteste;

import java.util.List;
import java.util.ArrayList;

public class RandomNumberGenerator {
	
	private int seed;	
	private int multiplier;
	private int increment;
	private int mod;
	
	private List<Integer> values;

	
	public RandomNumberGenerator(int seed) {
		this.seed = seed;
		this.multiplier = 1013904223;
		this.increment = 1664525;
		this.mod = (int) Math.pow(2, 32);

		this.values = new ArrayList<Integer>();
	}
	
	public List<Integer> multiplicativeCongruential(int lowerBound, int upperBound, int multiplier, int incremental, int mod) {
		values = new ArrayList<Integer>();
		values.add(seed);
		if (multiplier < this.mod && incremental < mod) {
			for (int i = 0; i < (upperBound - lowerBound) + 1; i++) {
				int previous = values.get(i);
				int xn = ((multiplier*previous)+increment) % mod;
				
				values.add(xn);
			}
		}
		return values;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
	
}
