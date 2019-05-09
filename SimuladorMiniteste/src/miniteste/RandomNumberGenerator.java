package miniteste;

import java.util.List;
import java.util.ArrayList;

public class RandomNumberGenerator {
	
	private int seed;	
	private List<Integer> uniformDist;

	
	public RandomNumberGenerator(int seed) {
		this.seed = seed;
		this.uniformDist = new ArrayList<Integer>();
	}
	
	public List<Integer> mixedMethod(int lowerBound, int upperBound, int multiplier, int incremental, int mod) {
		List<Integer> values = new ArrayList<Integer>();
		uniformDist.add(seed);
		values.add(seed);
		if (multiplier < mod && incremental < mod) {
			for (int i = 0; i < mod - 1; i++) {
				int previous = values.get(i);
				int xn = ((multiplier*previous)+incremental) % mod;
				if (xn >= lowerBound && xn < upperBound) {	
					uniformDist.add(xn);
				}
				values.add(xn);
			}
			
		}
		return uniformDist;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
	
}
