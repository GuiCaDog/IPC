/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitauxiliar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Grupo;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class AfegirGrupController implements Initializable {
    @FXML
    private TextField codigoGrupo;
    @FXML
    private TextArea descripcionGrupo;
    private Grupo grup;
    @FXML
    private Label mensajeError;
    private final String CSS_RED_BORDER = "-fx-border-color: #ff5722; -fx-border-width:2;";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codigoGrupo.textProperty().addListener((observable, oldVal, newVal) -> {
            codigoGrupo.setStyle("");
            mensajeError.setVisible(false);
        });
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) codigoGrupo.getScene().getWindow()).close();
    }

    @FXML
    private void aceptar(ActionEvent event) {
        if(!codigoGrupo.getText().equals("")){
            grup.setCodigo(codigoGrupo.getText());
            grup.setDescripcion(descripcionGrupo.getText());
            
            ((Stage) codigoGrupo.getScene().getWindow()).close();
        }
        else{
            mensajeError.setVisible(true);
            codigoGrupo.setStyle(CSS_RED_BORDER);
        }
    }
    
    public void setGrup(Grupo g){
        grup = g;
    }
    
   
}
