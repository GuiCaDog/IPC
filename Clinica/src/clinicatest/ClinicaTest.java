/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import DBAccess.ClinicDBAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author guicado
 */
public class ClinicaTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader miloader = new FXMLLoader((getClass().getResource("FXMLDocument.fxml")));
        
        Parent root =  miloader.load();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        ClinicDBAccess clinicDBAccess = ((FXMLDocumentController) miloader.getController()).getClinic();
        stage.setOnCloseRequest((WindowEvent event) -> {
            //System.out.println("adsjhf");
            Alert alert = new Alert(Alert.AlertType.INFORMATION); 
            alert.setTitle(clinicDBAccess.getClinicName()); alert.setHeaderText("Guardando datos en DB"); 
            alert.setContentText("\t La aplicación esta guardando la información \n en la base de datos. Esto puede tomar unos \n minutos"); 
            alert.show(); 
            clinicDBAccess.saveDB();
        });
        stage.setScene(scene);
        stage.setTitle("Clínica de atención primaria");
        stage.getIcons().add(new Image("images/appIcon.png"));
        //stage.show();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
