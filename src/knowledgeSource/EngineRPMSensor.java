package knowledgeSource;

import blackboard.*;
import io.*;

public class EngineRPMSensor extends KnowledgeSource implements EngineSource {
	private Throttle throttle;
	private final double maxRPMs = 7000;

	public EngineRPMSensor(Blackboard bb, Throttle throttle) {
		super(bb);
		super.priority = 5;
		super.sourceType = "RPM";
		this.throttle = throttle;
	}

	public void updateVal() {
		currentVal = ((double)throttle.currentVal()/(double)throttle.maxValue) * maxRPMs;
		super.updateBb();
	}
	
}
