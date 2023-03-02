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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Sesion;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class AfegirSesioController implements Initializable {
    @FXML
    private TextField codigoSesion;
    private SesionTipo sesion;
    private final String CSS_RED_BORDER = "-fx-border-color: #ff5722; -fx-border-width:2;";
    @FXML
    private TextField sEjer;
    @FXML
    private TextField sCal;
    @FXML
    private ChoiceBox<Integer> numEjercicios;
    @FXML
    private ChoiceBox<Integer> numRepeticiones;
    @FXML
    private TextField sDesEjer;
    @FXML
    private TextField sDesCirc;
    @FXML
    private Label mensajeError;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numEjercicios.getItems().removeAll(numEjercicios.getItems());
        numEjercicios.getItems().addAll(1,2,3,4,5,6,7,8, 9, 10, 11, 12, 13, 14, 15);
        
        numRepeticiones.getItems().removeAll(numRepeticiones.getItems());
        numRepeticiones.getItems().addAll(1,2,3,4,5,6,7,8, 9, 10, 11, 12, 13, 14, 15);
        
        codigoSesion.textProperty().addListener((observable, oldVal, newVal) -> {
            codigoSesion.setStyle("");
            mensajeError.setVisible(false);
        });
        limitarText(sEjer, 3);
        sEjer.textProperty().addListener((observable, oldVal, newVal) -> {
            sEjer.setStyle("");
            mensajeError.setVisible(false);
        });
        limitarText(sCal, 3);
        sCal.textProperty().addListener((observable, oldVal, newVal) -> {
            sCal.setStyle("");
            mensajeError.setVisible(false);
        });
        
        numEjercicios.valueProperty().addListener((observable, oldVal, newVal) -> {
            numEjercicios.setStyle("");
            mensajeError.setVisible(false);
        });
        
        numRepeticiones.valueProperty().addListener((observable, oldVal, newVal) -> {
            numRepeticiones.setStyle("");
            mensajeError.setVisible(false);
        });
        limitarText(sDesEjer, 3);
        sDesEjer.textProperty().addListener((observable, oldVal, newVal) -> {
            sDesEjer.setStyle("");
            mensajeError.setVisible(false);
        });
        limitarText(sDesCirc, 3);
        sDesCirc.textProperty().addListener((observable, oldVal, newVal) -> {
            sDesCirc.setStyle("");
            mensajeError.setVisible(false);
        });
    }    

    @FXML
    private void aceptar(ActionEvent event) {
        if(totCorrecte()){
            sesion.setCodigo(codigoSesion.getText());
            
            sesion.setNum_ejercicios(numEjercicios.getValue());
            sesion.setNum_circuitos(numRepeticiones.getValue());
            //System.out.println("aaa");
            sesion.setT_calentamiento(Integer.parseInt(sCal.getText()));
            sesion.setT_ejercicio(Integer.parseInt(sEjer.getText()));
            sesion.setD_ejercicio(Integer.parseInt(sDesEjer.getText()));
            sesion.setD_circuito(Integer.parseInt(sDesCirc.getText()));
            //System.out.println("aaa");
            ((Stage) codigoSesion.getScene().getWindow()).close();
        }
        else{
            mensajeError.setVisible(true);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) codigoSesion.getScene().getWindow()).close();
    }
    
    public void setSesionTipo(SesionTipo s){
        sesion = s;
    }
    private boolean totCorrecte(){
        boolean res = true;
        if(codigoSesion.getText().equals("")){
            res = false;
            codigoSesion.setStyle(CSS_RED_BORDER);
        }
        if(sCal.getText().equals("")){
            res = false;
            sCal.setStyle(CSS_RED_BORDER);
        }
        if(sEjer.getText().equals("")){
            res = false;
            sEjer.setStyle(CSS_RED_BORDER);
        }
        if(sDesEjer.getText().equals("")){
            res = false;
            sDesEjer.setStyle(CSS_RED_BORDER);
        }
        if(sDesCirc.getText().equals("")){
            res = false;
            sDesCirc.setStyle(CSS_RED_BORDER);
        }
        if(numEjercicios.getValue() == null){
            res = false;
            numEjercicios.setStyle(CSS_RED_BORDER);
        }
        if(numRepeticiones.getValue() == null){
            res = false;
            numRepeticiones.setStyle(CSS_RED_BORDER);
        }
        return res;
    }
    
     public static void limitarText(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    if (newValue.length() > maxLength)
                        tf.setText(oldValue.replaceAll("[^\\d.]", ""));
                    else tf.setText(newValue.replaceAll("[^\\d.]", ""));
                }
            } catch (Exception e) {}
        });
    }
}
