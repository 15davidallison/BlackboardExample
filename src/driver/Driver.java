package driver;
import blackboard.*;
import controller.*;
import io.*;
import knowledgeSource.*;

public class Driver {
	public static void main(String[] args) {
		Blackboard bb = Blackboard.getInstance();
		Controller cont = new Controller(bb);
		
		// test acceleration run
		for (int i = 0; i < 10; i++) {
			
			// list of activities to be performed i times
			cont.afr.updateVal();
			cont.pps.accelerate();
			cont.rpm.updateVal();
			
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
