package miniteste;

import java.util.List;

public class UniformDistribution {
	
	private RandomNumberGenerator rng;
	private List<Integer> uniformDist;
	
	public UniformDistribution(int lowerBound, int upperBound, int seed, int multiplier, int incremental) {
		
		this.rng = new RandomNumberGenerator(seed);

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
