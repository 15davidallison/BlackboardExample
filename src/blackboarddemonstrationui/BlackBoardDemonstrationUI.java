/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackboarddemonstrationui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BlackBoardDemonstrationUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UICreatedFXMLDoc.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        

//        // test acceleration run
//        for (int i = 0; i < 10; i++) {
//
//                // list of activities to be performed i times
//                cont.afr.updateVal();
//                cont.pps.accelerate();
//                cont.rpm.updateVal();
//                // halfway through the acceleration run, simulate a knock event
//                if (i == 5) {
//                        cont.ks.causeDetonationEvent();
//                }
//
//                // prioritize and execute these activities based on the knowledge they yield
//                while (cont.selectKS() != null) {
//                        cont.executeKS();
//                }
//        }
//        // test deceleration run
//        for (int i = 0; i < 10; i++) {
//
//                // list of activities to be performed i times
//                cont.afr.updateVal();
//                cont.pps.decelerate();			
//                cont.rpm.updateVal();
//
//                // prioritize and execute these activities based on the knowledge they yield
//                while (cont.selectKS() != null) {
//                        cont.executeKS();
//                }	
//        }
    }
    
}
