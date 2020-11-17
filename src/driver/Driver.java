package driver;
import blackboard.*;
import controller.*;
import io.*;
import knowledgeSource.*;

public class Driver {
	public static void main(String[] args) {
		Blackboard bb = Blackboard.getInstance();
		Controller cont = new Controller(bb);
		
		for (int i = 0; i < 10; i++) {
			
			cont.afr.updateVal();
			
			cont.selectKS();
			cont.configureKS();
			cont.executeKS();
			
			cont.pps.accelerate();
			
			cont.selectKS();
			cont.configureKS();
			cont.executeKS();
			
			cont.rpm.updateVal();
			
			cont.selectKS();
			cont.configureKS();
			cont.executeKS();
		}
		

	}
}
