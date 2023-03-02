/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import DBAccess.ClinicDBAccess;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Days;
import model.Doctor;
import model.ExaminationRoom;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class LlistaDoctorsController implements Initializable {

    private ArrayList<Doctor> llistaDoctors;
    private ArrayList<ExaminationRoom> llistaHabitacions;


    @FXML
    private ListView<Doctor> tablaDoctors;
    @FXML
    private TextField buscadorDoctors;
    @FXML
    private ImageView imagenDoctor;
    private ClinicDBAccess baseDades;

    @FXML
    private Label identi;
    @FXML
    private Label nombr;
    @FXML
    private Label telefo;
    @FXML
    private Label salaCon;
    @FXML
    private Label diasCon;
    @FXML
    private Label horaIniCon;
    @FXML
    private Label horaFiniCon;
    @FXML
    private Label apelli;
    @FXML
    private Button removeD;


    public void setLlistaDoctors(ArrayList<Doctor> llistaDoctors) {
        this.llistaDoctors = llistaDoctors;
    }
    public void setLlistaHabitacions(ArrayList<ExaminationRoom> llistaHabitacions) {
        this.llistaHabitacions = llistaHabitacions;
    }
    
    public void setBaseDades(ClinicDBAccess baseDades) {
        this.baseDades = baseDades;
    }
    private ObservableList<Doctor> observableDoctors;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        buscadorDoctors.textProperty().addListener((observable, oldVal, newVal) -> { 
            String text = buscadorDoctors.getText().toLowerCase();
            ArrayList<Doctor> elVisible = new ArrayList<>();
            for(int i = 0; i < llistaDoctors.size(); i++) {
                String pacientActual = llistaDoctors.get(i).getName()
                                                + " " + llistaDoctors.get(i).getSurname();
                pacientActual = pacientActual.toLowerCase();
                if(pacientActual.contains(text)) {
                    elVisible.add(llistaDoctors.get(i));
                }
            }
            observableDoctors = FXCollections.observableArrayList(elVisible);
            //tablaPacients.setCellFactory(c -> new PersonaListCell());
            tablaDoctors.setItems(observableDoctors);
        });
        //tablaPacients.hoverProperty()
        tablaDoctors.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> { 
                File f = new File("../images/defaultSelected.png");
                if(newVal!=null) {removeD.setDisable(false);} else {removeD.setDisable(true);}
                try{
                    imagenDoctor.setImage(new Image(f.toURI().toURL().toString()));
                } catch(MalformedURLException e){}
                Doctor p = newVal;//tablaPacients.getFocusModel().getFocusedItem();
//                String[] datos = new String[4];
//                datos[0] = p.getIdentifier();
//                datos[1] = p.getName();
//                datos[2] = p.getSurname();
//                datos[3] = p.getTelephon();
//                
//                for(int i = 0; i < 4; i++) {
//                        //System.out.println(datos[i]);
//                        ((Label)datosDoctor.getChildren().get(i)).setText(datos[i]);
//                }
                identi.setText(p.getIdentifier());
                nombr.setText(p.getName());
                apelli.setText(p.getSurname());
                telefo.setText(p.getTelephon());
                salaCon.setText(new Habitacio(p.getExaminationRoom()).toString());
                //((Label)datosDoctor.getChildren().get(4)).setText(new Habitacio(p.getExaminationRoom()).toString());
                ArrayList<String> dies = cambiarFormatDies(p.getVisitDays());
                String diesAMostrar = "";
                for(int i = 0; i < dies.size(); i++){
                    diesAMostrar += dies.get(i) +", ";
                }
                diesAMostrar = diesAMostrar.substring(0, diesAMostrar.length()-2);
//                ((Label)datosDoctor.getChildren().get(5)).setText(diesAMostrar);
//                ((Label)datosDoctor.getChildren().get(6)).setText(p.getVisitStartTime().toString());
//                ((Label)datosDoctor.getChildren().get(7)).setText(p.getVisitEndTime().toString());
                diasCon.setText(diesAMostrar);
                horaIniCon.setText(p.getVisitStartTime().toString());
                horaFiniCon.setText(p.getVisitEndTime().toString());
                imagenDoctor.setImage(p.getPhoto());
            
            });
    }    

    @FXML
    private void afegirDoctor(ActionEvent event) throws IOException{
        Stage stage = new Stage();
       // System.out.println(llistaHabitacions);
        //Parent root  = FXMLLoader.load((getClass().getResource("/vista/FXMLPersona.fxml")));
        FXMLLoader miloader = new FXMLLoader((getClass().getResource("afegirDoctor_1.fxml")));
        Parent root =  miloader.load();        //Parent root  = FXMLLoader.load((getClass().getResource("/vista/FXMLPersona.fxml")));

        //((VistaTablaControlador)miloader.getController()).setLista(datos);
        //System.out.println(miloader.getController().toString());
        Scene scene = new Scene(root);
        Doctor p = new Doctor(null,null,null,null,null,null,null,null,null);
        ((AfegirDoctorController)miloader.getController()).setDoctor(p);
        ((AfegirDoctorController)miloader.getController()).setLlistaHabitacions(llistaHabitacions);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(((AfegirDoctorController)miloader.getController()).getAfegir()){
                addOrdenat(p);
//            observablePacients.add(p);
//            llistaPacients.add(p);
           // System.out.println(llistaDoctors);
        }
    }
    
    public void posarLlistaObservable(){
        observableDoctors = FXCollections.observableArrayList(llistaDoctors);
        tablaDoctors.setCellFactory(c -> new PersonaListCell2());
        tablaDoctors.setItems(observableDoctors); 
    
    }
               public void addOrdenat(Doctor p) {
            String nom = p.getName() + p.getSurname();
            int i = 0;
            while(i < llistaDoctors.size()  && 
                                                nom.compareTo(llistaDoctors.get(i).getName()
                                                + " " + llistaDoctors.get(i).getSurname()
                                                )>0
                    ) 
            { i++;}
            if(i >= llistaDoctors.size()) { llistaDoctors.add(p); 
                                        observableDoctors.add(p);}//major es impossible que siga
            else {
            llistaDoctors.add(i, p);
            observableDoctors.add(i,p);
            }
        }

    @FXML
    private void eliminarDoctor(ActionEvent event) {
        int i = tablaDoctors.getSelectionModel().getSelectedIndex();

        if (i >= 0) {
            Doctor p = observableDoctors.get(i);
            if (!baseDades.hasAppointments(p)) {
                observableDoctors.remove(p);
                llistaDoctors.remove(p);
            } else {
                mostrarDialogError(p);
            }
        }
    }
    
    public void mostrarDialogError(Doctor d){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al eliminar doctor");
                alert.setHeaderText(d.getName() +" " +d.getSurname()+" tiene una cita registrada actualmente.");
                alert.setContentText("No se puede eliminar hasta que esta cita haya sido borrada.");
//                Exception ex = new FileNotFoundException("Detalles del error");
//                StringWriter sw = new StringWriter();
//                PrintWriter pw = new PrintWriter(sw);
//                ex.printStackTrace(pw);
//                String exceptionText = sw.toString();
//                Label label =
//                new Label("Excepción:");
//                TextArea textArea =
//                new TextArea(exceptionText);
//                textArea.setEditable(false);
//                textArea.setWrapText(true);
//                textArea.setMaxWidth(Double.MAX_VALUE);
//                textArea.setMaxHeight(Double.MAX_VALUE);
//                GridPane.setVgrow(textArea,
//                Priority.ALWAYS);
//                GridPane.setHgrow(textArea,
//                Priority.ALWAYS);
//                GridPane expContent = new GridPane();
//                expContent.setMaxWidth(Double.MAX_VALUE);
//                expContent.add(label, 0, 0);
//                expContent.add(textArea, 0, 1);
//                alert.getDialogPane().
//                setExpandableContent(expContent);
                alert.showAndWait();
           }
    
    private ArrayList<String> cambiarFormatDies(ArrayList<Days> a){
        ArrayList<String> res = new ArrayList<>();
        for(int i = 0; i < a.size(); i++){
            String s ="";
            switch(a.get(i)){
                case Monday: s = "Lunes"; break;
                case Tuesday: s = "Martes"; break;
                case Wednesday: s = "Miercoles"; break;
                case Thursday: s = "Jueves"; break;
                case Friday: s = "Viernes"; break;
                case Saturday: s = "Sábado"; break;
                case Sunday: s = "Domingo"; break;
            }
            res.add(s);
        }
        return res;
    }
}






     class PersonaListCell2 extends ListCell<Doctor> {
         private ImageView view = new ImageView();
         private ArrayList<String> colors = new ArrayList<>();
        @Override
        protected void updateItem(Doctor item, boolean empty) {
            super.updateItem(item, empty);
                colors.add("#00bcd4");  colors.add("#673ab7");colors.add("#f44336");colors.add("#ff5722");colors.add("#4caf50");
                colors.add("#009688"); colors.add("#607d8b");colors.add("#795548");colors.add("#ffa726");colors.add("#3f51b5"); colors.add("#e91e63");
            if (item == null || empty) {
                setText(""); setGraphic(null);
            } else {
                view = scale(view, 60, 60, true);
                view.setImage(item.getPhoto());
                VBox fotoDoc = new VBox(view);
                fotoDoc.setMinWidth(70);
                fotoDoc.setMaxWidth(70);
                //fotoDoc.setStyle("-fx-border-width:5; -fx-border-color: #03A9F4; -fx-border-radius: 10;");
                Label doct = new Label();
                doct.setText(item.getName()+"\n" +item.getSurname());
                doct.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;");
                HBox doctor = new HBox(doct);
                HBox.setHgrow(doctor, Priority.ALWAYS);
                doctor.setMargin(doct, new javafx.geometry.Insets(5,5, 5,5));
                doctor.setMinWidth(490);
                //doctor.setMaxWidth(490);
                HBox doctorMesImage = new HBox(fotoDoc, doctor);
                doctorMesImage.setStyle("-fx-border-width:3; -fx-border-color: #455a64; -fx-border-radius: 5;");
                ///////////---------------------------------COLOR A CADA DOCTOR---------------------------------------------------
                               int nDoc = ClinicDBAccess.getSingletonClinicDBAccess().getDoctors().size();
               //int rand = (int) (Math.random()*colors.size());
                //System.out.println(rand);
                //if(!jaRandom)
                //{for(int i = 0; i < rand; i++) {String r = colors.remove(0); colors.add(r); } jaRandom=true;}
               for(int i = 0; i < nDoc; i++){
                   Doctor doo = ClinicDBAccess.getSingletonClinicDBAccess().getDoctors().get(i);
                   String docAct = item.getName()+" " +item.getSurname();
                   if(docAct.contains(doo.getName() + " " + doo.getSurname())) {
                      doctor.setStyle("-fx-background-color:"+ colors.get(i)+";");
                   }
               }
                setGraphic(doctorMesImage);
                //setText(item.getName() + " " + item.getSurname());
            }
        }
        
         public ImageView scale(ImageView imageView, int targetWidth, int targetHeight, boolean preserveRatio) {
             imageView.setSmooth(true);
             imageView.setPreserveRatio(preserveRatio);
             imageView.setFitWidth(targetWidth);
             imageView.setFitHeight(targetHeight);
             return imageView;//.snapshot(null, null);
}
        
         
    }
