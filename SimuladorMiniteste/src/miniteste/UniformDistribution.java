package miniteste;

import java.util.ArrayList;
import java.util.List;

public class UniformDistribution {
	
	private RandomNumberGenerator rng;
	private List<Integer> uniformDist;
	
	private int lowerBound;
	private int upperBound;
	
	private int mod;
	private int seed;
	private int multiplier;
	private int increment;
	
	public UniformDistribution(int lowerBound, int upperBound, int seed, int multiplier, int increment) {
		
		this.rng = new RandomNumberGenerator(seed);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.seed = seed;
		this.multiplier = multiplier;
		this.increment = increment;
		this.mod = (upperBound - lowerBound) + 1;
		this.uniformDist = rng.multiplicativeCongruential(multiplier, increment, mod);
		
	}

	public List<Integer> getUniformDist() {
		return uniformDist;
	}

	public void setUniformDist(List<Integer> uniformDist) {
		this.uniformDist = uniformDist;
	}
	
	
}
