/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitauxiliar;

import accesoBD.AccesoBD;
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
 * @author Equipo
 */
public class CrossfitAuxiliar extends Application {
    
    AccesoBD baseDades;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader miloader = new FXMLLoader((getClass().getResource("/models/MainMenu.fxml")));
        Parent root =  miloader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        AccesoBD baseDades = ((MainMenuController) miloader.getController()).getBaseDades();
        stage.setOnCloseRequest((WindowEvent event) -> {
            baseDades.salvar();
        });
        stage.setTitle("Interval Timer para Sala de Crossfit");
        stage.getIcons().add(new Image("resources/appIcon.png"));
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
