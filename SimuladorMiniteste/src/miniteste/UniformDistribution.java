package miniteste;

import java.util.ArrayList;
import java.util.List;

public class UniformDistribution {
	
	private RandomNumberGenerator rng;
	private List<Integer> uniformDist;
	
	private int lowerBound;
	private int upperBound;
	
	private int seed;

	
	public UniformDistribution(int lowerBound, int upperBound, int seed, int multiplier, int incremental) {
		
		this.seed = seed;
		this.rng = new RandomNumberGenerator(seed);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		
		int mod = (upperBound - lowerBound) + 1;
		
		this.uniformDist = rng.mixedMethod(lowerBound, upperBound, multiplier, incremental, mod);
		
	}

	public List<Integer> getUniformDist() {
		return uniformDist;
	}

	public void setUniformDist(List<Integer> uniformDist) {
		this.uniformDist = uniformDist;
	}
	
	
}
