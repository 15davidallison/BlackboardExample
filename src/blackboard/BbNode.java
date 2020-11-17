package blackboard;

public class BbNode {
	private int priority;
	private double value;
	private String dataType;
	
	public BbNode(int p, double v, String type) {
		if (p > 10 || p < 0) {
			throw new IllegalArgumentException("Priority is not valid!");
		}
		priority = p;
		value = v;
		dataType = type;
	}
	
	public int priority() {
		return priority;
	}
	
	public double value() {
		return value;
	}
	
	public String type() {
		return dataType;
	}
}
