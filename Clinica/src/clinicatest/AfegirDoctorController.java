/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Days;
import model.Doctor;
import model.ExaminationRoom;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author guicado
 */
public class AfegirDoctorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Doctor doctor;
    private ArrayList<ExaminationRoom> llistaHabitacions;


    @FXML
    private TextField pin;
    @FXML
    private TextField nom;
    @FXML
    private TextField cognom;
    @FXML
    private TextField telf;
    @FXML
    private GridPane dias;
    @FXML
    private ChoiceBox<Integer> horaIni;
    @FXML
    private ChoiceBox<Integer> minIni;
    @FXML
    private ChoiceBox<Integer> horaFi;
    @FXML
    private ChoiceBox<Integer> minFi;
    @FXML
    private ChoiceBox<Habitacio> habitacio;
    private final String CSS_COLOR_FONDO = "-fx-background-color:   #B3E5FC";
    private final String CSS_COLOR_SELECCION = "-fx-background-color: #81d4fa";
    private final String CSS_RED_BORDER = "-fx-border-color: #f44336;";
    @FXML
    private ImageView foto;
    @FXML
    private Text textoObligatorio;
    @FXML
    private Label telfError;
    @FXML
    private Label errorMaximHores;
    @FXML
    private Text detalles;
    private boolean hiHaFoto = false;
    private boolean totsCamps = false;
    public void setLlistaHabitacions(ArrayList<ExaminationRoom> llistaHabitacions) {
        this.llistaHabitacions = llistaHabitacions;
        

        ArrayList<Habitacio> llistaHabitacionsMostrables = new ArrayList<>();
        for(int i = 0 ;i < llistaHabitacions.size(); i++){
            llistaHabitacionsMostrables.add(new Habitacio(llistaHabitacions.get(i)));
        }
        habitacio.getItems().removeAll(habitacio.getItems());
        habitacio.getItems().addAll(llistaHabitacionsMostrables);
        //System.out.println("aaaaaaaaaaaaaaaa"+habitacio.getItems());
        habitacio.valueProperty().addListener(e -> {
            detalles.setText("Equipamiento: "+habitacio.getValue().examinationRoomAssociated().getEquipmentDescription());
                    
                    ;});
        
        
//        for(int i = 0; i < habitacio.getItems().size(); i++){
//            Tooltip tt = new Tooltip("Equipamiento: "+habitacio.getItems().get(i).examinationRoomAssociated().getEquipmentDescription());
//            habitacio.setTooltip(tt);
//        }


    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    private boolean afegir = false;

    public boolean getAfegir() {
        return afegir;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        horaIni.getItems().removeAll(horaIni.getItems());
        horaIni.getItems().addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        horaIni.setValue(8);
        
        minIni.getItems().removeAll(minIni.getItems());
        minIni.getItems().addAll(00, 15, 30, 45);
        minIni.setValue(0);
        
        horaFi.getItems().removeAll(horaFi.getItems());
        horaFi.getItems().addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        horaFi.setValue(8);
        
        minFi.getItems().removeAll(minFi.getItems());
        minFi.getItems().addAll(15, 30, 45);
        minFi.setValue(15);
                //-------------
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
            textoObligatorio.setVisible(false);
            if (!newVal.matches("\\d*")) {
                telf.setText(newVal.replaceAll("[^\\d]", ""));
                telfError.setVisible(true);
            }
            else{ telfError.setVisible(false);}
        });
        habitacio.valueProperty().addListener((observable, oldVal, newVal) -> {
            habitacio.setStyle("");
            textoObligatorio.setVisible(false);
        });
        horaIni.valueProperty().addListener((observable, oldVal, newVal) -> {
            horaIni.setStyle("");
            textoObligatorio.setVisible(false);
            int vDef = horaFi.getValue();
            
            horaFi.getItems().removeAll(horaFi.getItems());
            int hora = horaIni.getValue();
            if(minIni.getValue() == 45){ hora++; }
            while(vDef < hora){vDef++;}
            horaFi.setValue(vDef);
            for(int i = hora; i <= 20; i++){
                horaFi.getItems().add(i);
            }
            if(horaIni.getValue() != horaFi.getValue()){
                if(horaFi.getValue() != 20){
                    Integer aux = minFi.getValue();
                    //System.out.println("Numero " +aux);

                    minFi.getItems().removeAll(minFi.getItems());
                    minFi.getItems().addAll(0, 15, 30, 45);
                    minFi.setValue(aux);
                }
                else{
                    minFi.getItems().removeAll(minFi.getItems());
                    minFi.getItems().addAll(0);
                    minFi.setValue(0);
                }
            }
            if(horaIni.getValue() == horaFi.getValue()){
                //if(minFi.getItems().get(0) <= minIni.getValue()){
                    Integer aux = minFi.getValue();
                    minFi.getItems().removeAll(minFi.getItems());
                    boolean vAsignado = false;
                    for(int i = minIni.getValue()+15; i <= 45; i+=15){
                        //System.out.println("!");
                        if(i == aux){ vAsignado=true;}
                        minFi.getItems().add(i);
                    }
                    if(vAsignado == false){ aux = minFi.getItems().get(0);}
                    minFi.setValue(aux);
                //}
            }
            errorMaximHores.setVisible(false);
        });
        horaFi.valueProperty().addListener((observable, oldVal, newVal) -> {
            horaFi.setStyle("");
            textoObligatorio.setVisible(false);
            //.out.println("No deberia");
            if(horaFi.getValue() == null){ horaFi.setValue(horaIni.getValue()+1);}
            if(horaFi.getValue() == 20){ 
                minFi.getItems().removeAll(minFi.getItems());
                minFi.getItems().add(0);
                minFi.setValue(0);
            }
            else if(horaFi.getValue() == horaIni.getValue()){
                if(minFi.getValue()<= minIni.getValue()){
                    minFi.getItems().removeAll(minFi.getItems());
                    minFi.setValue(minIni.getValue()+ 15);
                    for(int i = minIni.getValue()+15; i <= 45; i+=15){
                        minFi.getItems().add(i);
                    }
                }
            }
                else{   
                Integer aux = minFi.getValue();
                minFi.getItems().removeAll(minFi.getItems());
                minFi.getItems().addAll(0, 15, 30, 45);
                minFi.setValue(aux);
            }
            errorMaximHores.setVisible(false);
        });
        minIni.valueProperty().addListener((observable, oldVal, newVal) -> {
            minIni.setStyle("");
            textoObligatorio.setVisible(false);
            if(horaFi.getValue() == horaIni.getValue()){
                
                if(minIni.getValue() == 45){
                    horaFi.getItems().remove(horaFi.getValue());
                    horaFi.setValue(horaFi.getValue()+1);
                    minFi.getItems().removeAll(minFi.getItems());
                    if(horaFi.getValue() != 20){ minFi.getItems().addAll(0, 15, 30, 45);}
                    else{ minFi.getItems().add(0);}
                    minFi.setValue(0);
                }
                
                else{
                    Integer aux = minFi.getValue();
                    minFi.getItems().removeAll(minFi.getItems());
                    boolean vAsignado = false;
                    for(int i = minIni.getValue()+15; i <= 45; i+=15){
                        if(i == aux){ vAsignado=true;}
                        minFi.getItems().add(i);
                    }
                    if(vAsignado == false){ aux = minFi.getItems().get(0);}
                    minFi.setValue(aux);
                    
                }
            }
            else if(horaFi.getItems().get(0)-1 == horaIni.getValue() && minIni.getValue() != 45){ 
                        //minFi.getItems().add(0);
                    //System.out.println("hola");
                        horaFi.getItems().add(0, horaIni.getValue());
                    }
            else{
                Integer aux = minFi.getValue();
                minFi.getItems().removeAll(minFi.getItems());
                minFi.getItems().addAll(0, 15, 30, 45);
                minFi.setValue(aux);
            }
            errorMaximHores.setVisible(false);
        });
        minFi.valueProperty().addListener((observable, oldVal, newVal) -> {
            minFi.setStyle("");
            textoObligatorio.setVisible(false);
//            if(minFi.getValue() == 45){
//                if(horaFi.getValue() == horaIni.getValue()){
//                    Integer b = horaFi.getValue();
//                    horaFi.getItems().remove(b);
//                    horaFi.setValue(b+1);
//                }
//            }
            errorMaximHores.setVisible(false);
            if(minFi.getValue() == null){ minFi.setValue(0);}
        });
        ObservableList<Node> days = dias.getChildren();
        for(int i = 1; i < days.size(); i+=2){
            if(days.get(i) instanceof CheckBox) {
            CheckBox cell = ((CheckBox)days.get(i));
            cell.setOnMouseClicked(e -> {
                for(int j = 0; j < days.size(); j+=2){
                    days.get(j).setStyle("");
                }
                textoObligatorio.setVisible(false);
            });
            }
            
        }
//        habitacio.valueProperty().addListener((observable, oldVal, newVal) -> {
////            System.out.println("a");
////            Tooltip tt = new Tooltip("Equipamiento: "+newVal.examinationRoomAssociated().getEquipmentDescription());
////            
////            habitacio.setTooltip(tt);
//        });
        
        pin.textProperty().addListener((observable, oldVal, newVal) -> {
            pin.setStyle("");
            //System.out.println(pin.getText().length());
            textoObligatorio.setVisible(false);
//            try{
//                if(pin.getText().length() < 9){
//                    System.out.println("p");
//                    if(pin.getText().length() == 9 && pin.getText().charAt(8) < 64){
//                        pinError.setVisible(true);
//                        pin.setText(oldVal);
//                    }
//
//                    else if (!newVal.matches("\\d*")) {
//                        pin.setText(newVal.replaceAll("[^\\d]", ""));
//                        pinError.setVisible(true);
//                    }
//                    else{ pinError.setVisible(false);}
//                }
//                else if(pin.getText().length() == 9 && ((pin.getText().charAt(8) > 64 && pin.getText().charAt(8) < 91) ||
//                             (pin.getText().charAt(8) > 96 && pin.getText().charAt(8) < 123))){
//        //                if(pin.getText().length() >= 2 && pin.getText().charAt(1) > 57){
//        //                    pin.setText(oldVal);
//        //                    pinError.setVisible(true);
//        //                }
//                        pinError.setVisible(false);
//                        System.out.println("asa");
//                    }
//                else{ 
//                    System.out.println("pa");
//                    pin.setText(oldVal);
//                    pinError.setVisible(true);
//                }
//                
//            }catch(StringIndexOutOfBoundsException e){
//
//
//            }
            
        });
    }    

    @FXML
    private void aceptarDoctor(ActionEvent event) {
        if(totCorrecte()){
            Image im= new Image("file:src/images/defaultSelected.png");
            //im = scale(im, 20, 20, true);
            doctor.setIdentifier(pin.getText());
            doctor.setName(nom.getText());
            doctor.setSurname(cognom.getText());
            doctor.setTelephon(telf.getText());
            doctor.setPhoto(im);
            ArrayList<Days> res = new ArrayList<Days>();
            ObservableList<Node> l = dias.getChildren();
            for(int i = 0; i < l.size(); i++){
                if(l.get(i) instanceof CheckBox){
                    if(((CheckBox)l.get(i)).isSelected()){
                        Days d = null;
                        Text t = (Text)l.get(i-1); // i-1 en teoría es lo de arriba del checkbox
                        switch(t.getText()){
                            case "L": d = Days.Monday; break;
                            case "M": d = Days.Tuesday; break;
                            case "X": d = Days.Wednesday; break;
                            case "J": d = Days.Thursday; break;
                            case "V": d = Days.Friday; break;
                            case "S": d = Days.Saturday; break;
                            case "D": d = Days.Sunday;
                        }
                        res.add(d);
                    }
                }
            }

            doctor.setVisitDays(res);
            doctor.setVisitStartTime(LocalTime.of(horaIni.getValue(), minIni.getValue()));
            doctor.setVisitEndTime(LocalTime.of(horaFi.getValue(), minFi.getValue()));
            doctor.setExaminationRoom(habitacio.getValue().examinationRoomAssociated());
            if (!hiHaFoto) {
               // System.out.println("asasas");
                doctor.setPhoto(im);
            } else {
                doctor.setPhoto(foto.getImage());
            }
            
            afegir = true;
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATION
            alert.setTitle("Operación realizada correctamente");
            alert.setHeaderText("Se ha añadido el doctor con éxito");
            // ó null si no queremos cabecera
            alert.showAndWait();
            ((Stage) telf.getScene().getWindow()).close();
        }
        else if (!totsCamps){ textoObligatorio.setVisible(true);}
    }

    @FXML
    private void cancelarDoctor(ActionEvent event) {
        ((Stage) telf.getScene().getWindow()).close();
    }
    
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
        totsCamps = false;
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
        if(habitacio.getValue() == null){
            habitacio.setStyle(CSS_RED_BORDER);
            res = false;
        }
        if(horaIni.getValue() == null){
            horaIni.setStyle(CSS_RED_BORDER);
            res = false;
        }
        if(horaFi.getValue() == null){
            horaFi.setStyle(CSS_RED_BORDER);
            res = false;
        }
        if(minIni.getValue() == null){
            minIni.setStyle(CSS_RED_BORDER);
            res = false;
        }
        if(minFi.getValue() == null){
            minFi.setStyle(CSS_RED_BORDER);
            res = false;
        }
        ObservableList<Node> days = dias.getChildren();
        int cont  = 0;
        for(int i = 1; i < days.size(); i+=2){
            if(days.get(i) instanceof CheckBox) {
            if(!((CheckBox)days.get(i)).isSelected()){
                cont++;
            }
            }
        }
        //System.out.println(cont);
        if(cont == 7){
           // System.out.println(dias.getChildren().get(0));
            for(int i = 0; i < dias.getChildren().size(); i+=2){
                if(dias.getChildren().get(i) instanceof Text){
                    ((Text)dias.getChildren().get(i)).setStyle("-fx-fill: #f44336");
                }
            }
            //dias.setStyle(CSS_RED_BORDER + "-fx-border-width: 2;");
            res = false;
        }
        int minutsTreballats = 0; //Maximo: 8 * 60
        minutsTreballats = (horaFi.getValue() -horaIni.getValue()) * 60 + (minFi.getValue() - minIni.getValue());
        if(minutsTreballats > 8*60){
            errorMaximHores.setVisible(true);
            if(res){ totsCamps = true;  }
            res = false;
        }
        return res;
    }
    
}


