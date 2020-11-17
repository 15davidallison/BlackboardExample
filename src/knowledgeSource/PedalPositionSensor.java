package knowledgeSource;

import blackboard.Blackboard;

public class PedalPositionSensor extends KnowledgeSource {
	public PedalPositionSensor(Blackboard bb) {
		super(bb);
		super.priority = 6;
		super.sourceType = "GAS";
		currentVal = 0;
	}
	
	public void accelerate() {
		if (currentVal + 10 <= 100) {
			currentVal += 10;
		} else {
			currentVal = 100;
		}
		super.updateBb();
	}

	public void decelerate() {
		if (currentVal - 10 >= 0) {
			currentVal -= 10;
		} else {
			currentVal = 0;
		}
		super.updateBb();
	}

}
