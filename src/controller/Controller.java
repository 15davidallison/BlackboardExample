package controller;
import blackboard.*;
import io.*;
import knowledgeSource.*;
import java.util.HashSet;

/**
 * @author David Allison
 * Controller in the Blackboard pattern, simulates a car's ECU.
 * Knowledge Sources are left public to allow for simulated sensor updates.
 */
public class Controller {
	
	private static Controller pointer;
	
	private Blackboard bb;
	private final double targetAFR = 14.7;
	
	// Output Components:
	private FuelInjector injector;
	private Throttle throttle;
	
	// Knowledge Sources:
	public EngineRPMSensor rpm;
	public AirFuelRatioSensor afr;
	public KnockSensor ks;
	public PedalPositionSensor pps;
	
	// Local Knowledge Containers
	private BbNode nextNode;
	private double currentAFR;
	private double currentRPM;
	private double currentPedalPosition;
	
	/**
	 * Private constructor for singleton Controller class, creates all necessary sensors, devices, and blackboard
	 */
	private Controller() {
		bb = new Blackboard();
		injector = new FuelInjector(50);
		throttle = new Throttle(735);
		rpm = new EngineRPMSensor(bb, throttle);
		afr = new AirFuelRatioSensor(bb, throttle, injector);
		ks = new KnockSensor(bb, afr);
		pps = new PedalPositionSensor(bb);
	}
	
	public static Controller getInstance() {
		if (pointer == null) {
			pointer = new Controller();
		}
		return pointer;
	}
	
	/**
	 * Accesses the blackboard and determines the most important information on the board
	 * @return the BbNode with the highest priority on the blackboard
	 */
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
	
	/**
	 * Executes actions based on the most recent node gathered from the blackboard
	 * Node actions are classified based on the node type
	 */
	public void executeKS() {
		if (nextNode != null) {
			switch (nextNode.type()) {
			case "AFR": // Air Fuel Ratio Sensor has reported a new value
				currentAFR = nextNode.value();
				System.out.println("New AFR reported: " + currentAFR);
				// DO NOT CHANGE THIS SECTION
				if (currentAFR > 14.8) { // too much air, increase fuel or decrease throttle
					if (injector.currentVal() == injector.maxValue) {
						System.out.println("Decreasing throttles to fix lean AFR");
						throttle.less((int)((currentAFR - targetAFR) * (throttle.currentVal()/currentAFR)));
					} else {
						System.out.println("Increasing injectors to fix lean AFR");
						injector.more((int)(((1/targetAFR) - (1/currentAFR)) * currentAFR * injector.currentVal()));
					}
				} else if (currentAFR < 14.6) { // too much fuel, decrease fuel or increase air
					if (injector.currentVal() >= injector.maxValue *.2) { // if injectors are running at >20% power
						System.out.println("Decreasing injectors to fix rich AFR");
						injector.less((int)(-1 * ((1/targetAFR) - (1/currentAFR)) * currentAFR * injector.currentVal()));
					} else {
						System.out.println("Increasing throttle to fix rich AFR");
						throttle.more((int)((targetAFR-currentAFR) * (throttle.currentVal()/currentAFR)));
					}
				} else {
					System.out.println("AFR is great! No change.");
				}
				break;
				
			case "KS": // Knock Sensor has reported a new value
				if (nextNode.value() == 1.0) {
					System.out.println("Knock event detected!");
					// cutting throttle will cause a desired rich AFR condition here
					throttle.less(throttle.maxValue/10);
					break;
				}
				break;
				
			case "RPM": // Engine RPM Sensor has reported a new value
				currentRPM = nextNode.value();
				System.out.println("RPM: " + currentRPM);
				break;
				
			case "GAS": // Pedal Position has been updated
				if (currentPedalPosition < nextNode.value()) { // accelerating
					throttle.more(throttle.maxValue/10);
					injector.more(injector.maxValue/10);
				} else if (currentPedalPosition> nextNode.value()) { // decelerating
					throttle.less(throttle.maxValue/10);
					injector.less(injector.maxValue/10);
				}
				currentPedalPosition = nextNode.value();
				System.out.println("Pedal position: " + currentPedalPosition);
				break;
			}
		} else { // nextNode is null
			System.out.println("Idling, no new engine data to execute on...");
		}
		nextNode = null; // make sure a node is not acted upon twice
	}
}
