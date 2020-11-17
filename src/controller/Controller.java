package controller;
import blackboard.*;
import io.*;
import knowledgeSource.*;


import java.util.HashSet;

public class Controller {
	private Blackboard bb;
	private BbNode nextNode;
	private final double targetAFR = 14.7;
	private double currentAFR;
	private double currentRPM;
	private double currentPedalPosition;
	private int posDelta = 0;
	private boolean knockDetected = false;
	
	public FuelInjector injector;
	public Throttle throttle;
	public EngineRPMSensor rpm;
	public AirFuelRatioSensor afr;
	public KnockSensor ks;
	public PedalPositionSensor pps;
	
	public Controller(Blackboard bb) {
		this.bb = bb;
		injector = new FuelInjector(50);
		throttle = new Throttle(735);
		rpm = new EngineRPMSensor(bb, throttle);
		afr = new AirFuelRatioSensor(bb, throttle, injector);
		ks = new KnockSensor(bb, afr);
		pps = new PedalPositionSensor(bb);
	}
	public BbNode selectKS() {
		HashSet<BbNode> nodes = bb.access();
		BbNode highestPriorityNode = null;
		for (BbNode node : nodes) {
			if (highestPriorityNode == null || node.priority() > highestPriorityNode.priority()) {
				highestPriorityNode = node;
			}
		}
		bb.remove(highestPriorityNode);
		nextNode = highestPriorityNode;
		return nextNode;
	}
	
	public void executeKS() {
		if (nextNode != null) {
			switch (nextNode.type()) {
			case "AFR":
				currentAFR = nextNode.value();
				System.out.println("New AFR reported: " + currentAFR);
				if (currentAFR > 14.8) { // too much air, increase fuel or decrease throttle
					if (injector.currentVal() == injector.maxValue) {
						System.out.println("Decreasing throttles to fix high AFR");
						throttle.less((int)((currentAFR - targetAFR) * (throttle.currentVal()/currentAFR)));
					} else {
						System.out.println("Increasing injectors to fix high AFR");
						injector.more((int)(((1/targetAFR) - (1/currentAFR)) * currentAFR * injector.currentVal()));
					}
				} else if (currentAFR < 14.6) { // too much fuel, decrease fuel or increase air
					if (injector.currentVal() >= injector.maxValue *.2) { // if injectors are running at >20% power
						System.out.println("Decreasing injectors to fix low AFR");
						injector.less((int)(-1 * ((1/targetAFR) - (1/currentAFR)) * currentAFR * injector.currentVal())); // decrease fuel
					} else {
						System.out.println("Increasing throttle to fix low AFR");
						throttle.more((int)((targetAFR-currentAFR) * (throttle.currentVal()/currentAFR)));
					}
				} else {
					System.out.println("AFR is great! No change.");
				}
				break;
			case "KS":
				if (nextNode.value() == 1.0) {
					knockDetected = true;
					System.out.println("Knock event detected!");
					throttle.less(throttle.maxValue/10);
					break;
				}
				knockDetected = false;
				break;
			case "RPM":
				currentRPM = nextNode.value();
				System.out.println("RPM: " + currentRPM);
				break;
			case "GAS":
				if (currentPedalPosition < nextNode.value()) {
					throttle.more(throttle.maxValue/10);
					injector.more(injector.maxValue/10);
				} else if (currentPedalPosition> nextNode.value()) {
					throttle.less(throttle.maxValue/10);
					injector.less(injector.maxValue/10);
				}
				currentPedalPosition = nextNode.value();
				System.out.println("Pedal position: " + currentPedalPosition);
				break;
			}
		} else {
			System.out.println("Idling, no new engine data to execute on...");
		}
	}
}
