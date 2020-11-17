package blackboard;
import java.util.HashSet;

public class Blackboard {
	private static Blackboard pointer;
	private HashSet<BbNode> nodes;
	
	private Blackboard() {
		nodes = new HashSet<BbNode>();
	}
	
	/**
	 * @return the singleton instance of Blackboard
	 */
	public static Blackboard getInstance() {
		if (pointer == null) {
			pointer = new Blackboard();
		}
		return pointer;
	}
	
	public void update(BbNode node) {
		nodes.add(node);
	}
	
	public HashSet<BbNode> access() {
		return nodes;
	}
	
	public boolean remove(BbNode node) {
		return nodes.remove(node);
	}
}
