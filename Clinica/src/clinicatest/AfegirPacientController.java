/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class AfegirPacientController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Patient pacient;
    private boolean afegir = false;
    private boolean hiHaFoto = false;
    private Text confirmacionPaciente;
    @FXML
    private ImageView foto;
    private final String CSS_COLOR_FONDO = "-fx-background-color:   #B3E5FC";
    private final String CSS_COLOR_SELECCION = "-fx-background-color: #81d4fa";
    private final String CSS_RED_BORDER = "-fx-border-color: red;";
    @FXML
    private Text textoObligatorio;
    @FXML
    private Label telfError;
    public boolean getAfegir() {
        return afegir;
    }
    @FXML
    private TextField pin;
    @FXML
    private TextField nom;
    @FXML
    private TextField cognom;
    @FXML
    private TextField telf;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pin.textProperty().addListener((observable, oldVal, newVal) -> {
            pin.setStyle("");
            textoObligatorio.setVisible(false);
        });
        nom.textProperty().addListener((observable, oldVal, newVal) -> {
            nom.setStyle("");
            textoObligatorio.setVisible(false);
        });
        cognom.textProperty().addListener((observable, oldVal, newVal) -> {
            cognom.setStyle("");
            textoObligatorio.setVisible(false);
        });
        telf.textProperty().addListener((observable, oldVal, newVal) -> {
            telf.setStyle("");
            //System.out.println(telf.getText());
            textoObligatorio.setVisible(false);
            if (!newVal.matches("\\d*")) {
                telf.setText(newVal.replaceAll("[^\\d]", ""));
                telfError.setVisible(true);
            }
            else{ telfError.setVisible(false);}
        });
        
    }    
    
    public void setPacient (Patient p) {
        pacient = p;
    }

    @FXML
    private void aceptarPaciente(ActionEvent event) throws InterruptedException {
//        Image im= new Image("file:src/images/defaultSelected.png");
//        foto.setImage(im);
        if(totCorrecte()){
            Image im= new Image("file:src/images/defaultSelected.png");
            //im = scale(im, 20, 20, true);
            pacient.setIdentifier(pin.getText());
            pacient.setName(nom.getText());
            pacient.setSurname(cognom.getText());
            pacient.setTelephon(telf.getText());
    //        File f = new File("../images/defaultSelected.png");
    //        Image imageDefault = null;
    //        try {
    //            imageDefault = new Image(f.toURI().toURL().toString());
    //        } catch (MalformedURLException e) {
    //        }
            if (!hiHaFoto) {
                //System.out.println("asasas");
                pacient.setPhoto(im);
            } else {
                pacient.setPhoto(foto.getImage());
            }
            

            afegir = true;
            //confirmacionPaciente.setText("Paciente añadido con éxito.");
            //Thread.sleep(2000);
            Alert alert = new Alert(AlertType.INFORMATION);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATION
            alert.setTitle("Operación realizada correctamente");
                    
            alert.setHeaderText("Se ha añadido el paciente con éxito");
            // ó null si no queremos cabecera
            alert.showAndWait();

            ((Stage) telf.getScene().getWindow()).close();
        }
        else{ textoObligatorio.setVisible(true);}
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) telf.getScene().getWindow()).close();
    }
    
//    public Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
//    ImageView imageView = new ImageView(source);
//    imageView.setSmooth(true);
//    imageView.setPreserveRatio(preserveRatio);
//    imageView.setFitWidth(targetWidth);
//    imageView.setFitHeight(targetHeight);
//    return imageView.snapshot(null, null);
//}

    @FXML
    private void ratoliNoSobreText(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setStyle(CSS_COLOR_FONDO);
    }

    @FXML
    private void ratoliSobreText(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setStyle(CSS_COLOR_SELECCION);
    }

    @FXML
    private void obtindreFoto(ActionEvent event) throws MalformedURLException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar una imagen");
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add(".jpg"); extensions.add(".png");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todas las imágenes", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        File file = fileChooser.showOpenDialog(telf.getScene().getWindow());
        if(file!=null){ 
            String ruta = file.toURI().toURL().toString();
            foto.setImage(new Image(ruta));
            hiHaFoto = true;
        }
    }
    
    private boolean totCorrecte(){
        boolean res = true;
        if(pin.getText().equals("")){
            pin.setStyle(CSS_RED_BORDER);
            res = false;
        }
        if(nom.getText().equals("")){
            nom.setStyle(CSS_RED_BORDER);
            res = false;
        }
        if(cognom.getText().equals("")){
            cognom.setStyle(CSS_RED_BORDER);
            res = false;
        }
        if(telf.getText().equals("")){
            telf.setStyle(CSS_RED_BORDER);
            res = false;
        }
        return res;
    }
    
}
