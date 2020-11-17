package knowledgeSource;
import blackboard.*;
import io.*;

public class AirFuelRatioSensor extends KnowledgeSource implements EngineSource {
	private Throttle throttle;
	private FuelInjector injector;

	public AirFuelRatioSensor(Blackboard bb, Throttle throttle, FuelInjector injector) {
		super(bb);
		super.priority = 5;
		super.sourceType = "AFR";
		this.throttle = throttle;
		this.injector = injector;
	}
	public void updateVal() {
		currentVal = (double)throttle.currentVal()/(double)injector.currentVal();
		super.updateBb();
	}
}
