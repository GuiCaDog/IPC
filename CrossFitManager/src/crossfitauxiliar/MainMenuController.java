/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitauxiliar;

import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Gym;

/**
 * FXML Controller class
 *
 * @author guicado
 */
public class MainMenuController implements Initializable {

    @FXML
    private StackPane sesionStack;
    private AccesoBD baseDades;
    private Gym gymActual;
    private Scene anteriorScene;
    @FXML
    private VBox gr;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        baseDades = AccesoBD.getInstance();
        //System.out.println(baseDades.getGym().getTiposSesion());
        gymActual = baseDades.getGym();

    }    

    @FXML
    private void mouseExitedArea(MouseEvent event) {
         sesionStack.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void mouseEnteredArea(MouseEvent event) {
        sesionStack.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    private void mouseClickedSesion(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/ConfiguracioSesio.fxml"));

        Parent root =  loader.load();  
        Scene sesio = new Scene(root);
        anteriorScene = sesionStack.getScene();
        ((Stage)sesionStack.getScene().getWindow()).setScene(sesio);
        ((ConfiguracioSesioController)loader.getController()).setGym(gymActual);
        ((ConfiguracioSesioController)loader.getController()).setAnteriorScene(anteriorScene);
    }

    @FXML
    private void mouseClickedGrupos(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/Grups.fxml"));

        Parent root =  loader.load();  
        Scene sesio = new Scene(root);
        anteriorScene = sesionStack.getScene();
        ((Stage)sesionStack.getScene().getWindow()).setScene(sesio);
        //System.out.println(gymActual);
        ((GrupsController)loader.getController()).setGym(gymActual);
        ((GrupsController)loader.getController()).setAnteriorScene(anteriorScene);
    }

    
    public AccesoBD getBaseDades(){
        return baseDades;
    }

    @FXML
    private void ex(MouseEvent event) {
        sesionStack.getScene().setCursor(Cursor.DEFAULT);
        VBox v = (VBox)event.getSource();
        v.setStyle("-fx-background-color:transparent;");
    }

    @FXML
    private void ent(MouseEvent event) {
        sesionStack.getScene().setCursor(Cursor.HAND);
        VBox v = (VBox)event.getSource();
        
        v.setStyle("-fx-background-color:#81d4fa;");
        
        
    }
}
