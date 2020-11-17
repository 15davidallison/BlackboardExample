package knowledgeSource;

import blackboard.*;
import java.util.Random;

public class KnockSensor extends KnowledgeSource implements EngineSource {
	AirFuelRatioSensor afr;

	public KnockSensor(Blackboard bb, AirFuelRatioSensor afr) {
		super(bb);
		super.priority = 1;
		super.sourceType = "KS";
		this.afr = afr;
	}

	public void updateVal() {
		if (afr.currentVal > 14.7) { // if AFR is too lean, randomly cause knock events
			Random rand = new Random();
			currentVal = rand.nextInt(2);
		}
		if (currentVal == 0) {
			super.priority = 1;
		} else { // if knock event occurred, this is high priority
			super.priority = 9;
		}
		super.updateBb();
	}
	
	
	
	
}
