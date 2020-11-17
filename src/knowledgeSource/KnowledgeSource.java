package knowledgeSource;
import blackboard.*;

public abstract class KnowledgeSource {
	private Blackboard bb;
	protected int priority;
	protected String sourceType;
	protected double currentVal;
	
	public KnowledgeSource(Blackboard bb) {
		this.bb = bb;
	}
	
	protected void updateBb() {
		BbNode node = new BbNode(priority, currentVal, sourceType);
		bb.update(node);
	}
}
