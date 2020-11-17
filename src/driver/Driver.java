package driver;
import blackboard.*;
import controller.*;
import io.*;
import knowledgeSource.*;

/**
 * @author David Allison
 * Car Simulator class, shows use of the Blackboard pattern
 */
public class Driver {
	public static void main(String[] args) {
		// create a singleton Controller
		Controller cont = Controller.getInstance();
		
		// test acceleration run
		for (int i = 0; i < 10; i++) {
			
			// list of activities to be performed i times
			cont.afr.updateVal();
			cont.pps.accelerate();
			cont.rpm.updateVal();
			// halfway through the acceleration run, simulate a knock event
			if (i == 5) {
				cont.ks.causeDetonationEvent();
			}
			
			// prioritize and execute these activities based on the knowledge they yield
			while (cont.selectKS() != null) {
				cont.executeKS();
			}
		}
		// test deceleration run
		for (int i = 0; i < 10; i++) {
		
			// list of activities to be performed i times
			cont.afr.updateVal();
			cont.pps.decelerate();			
			cont.rpm.updateVal();
			
			// prioritize and execute these activities based on the knowledge they yield
			while (cont.selectKS() != null) {
				cont.executeKS();
			}	
		}
		

	}
}
