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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class LlistaPacientsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private ArrayList<Patient> llistaPacients;
    private ObservableList<Patient> observablePacients;
    @FXML
    private ListView<Patient> tablaPacients;
    @FXML
    private TextField buscadorPacients;
    @FXML
    private ImageView imagenPaciente;
    private ClinicDBAccess baseDades;
    @FXML
    private Label ident;
    @FXML
    private Label nameLab;
    @FXML
    private Label surna;
    @FXML
    private Label telef;
    @FXML
    private Button removeBut;
       public void setBaseDades(ClinicDBAccess baseDades) {
        this.baseDades = baseDades;
    }

    public void setLlistaPacients(ArrayList<Patient> llistaPacients) {
        this.llistaPacients = llistaPacients;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buscadorPacients.textProperty().addListener((observable, oldVal, newVal) -> { 
            String text = buscadorPacients.getText().toLowerCase();
            ArrayList<Patient> elVisible = new ArrayList<>();
            for(int i = 0; i < llistaPacients.size(); i++) {
                String pacientActual = llistaPacients.get(i).getName()
                                                + " " + llistaPacients.get(i).getSurname();
                pacientActual = pacientActual.toLowerCase();
                if(pacientActual.contains(text)) {
                    elVisible.add(llistaPacients.get(i));
                }
            }
            observablePacients = FXCollections.observableArrayList(elVisible);
            //tablaPacients.setCellFactory(c -> new PersonaListCell());
            tablaPacients.setItems(observablePacients);
        });
        //tablaPacients.hoverProperty()
        tablaPacients.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> { 
                if(newVal!=null) {removeBut.setDisable(false);} else {removeBut.setDisable(true);}
                File f = new File("../images/defaultSelected.png");
                Image imageDefault = null;
                try{
                    imageDefault = new Image(f.toURI().toURL().toString());
                    imagenPaciente.setImage(imageDefault);
                } catch(MalformedURLException e){}
                Patient p = newVal;//tablaPacients.getFocusModel().getFocusedItem();
                String[] datos = new String[8];
                ident.setText(p.getIdentifier());
                nameLab.setText(p.getName());
                surna.setText(p.getSurname());
                telef.setText(p.getTelephon());
                
//                for(int i = 1; i < 8; i+=2) {
//                        //System.out.println(datos[i]);
//                        ((Label)datosPaciente.getChildren().get(i)).setText(datos[i]);
//                }
                imagenPaciente.setImage(p.getPhoto());
            });
    }    

    @FXML
    private void afegirPacient(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        //System.out.println(llistaPacients);
        //Parent root  = FXMLLoader.load((getClass().getResource("/vista/FXMLPersona.fxml")));
        FXMLLoader miloader = new FXMLLoader((getClass().getResource("afegirPacient_1.fxml")));
        Parent root =  miloader.load();        //Parent root  = FXMLLoader.load((getClass().getResource("/vista/FXMLPersona.fxml")));

        //((VistaTablaControlador)miloader.getController()).setLista(datos);
        //System.out.println(miloader.getController().toString());
        Scene scene = new Scene(root);
        Patient p = new Patient(null,null,null,null,null);
        ((AfegirPacientController)miloader.getController()).setPacient(p);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(((AfegirPacientController)miloader.getController()).getAfegir()){
                addOrdenat(p);
//            observablePacients.add(p);
//            llistaPacients.add(p);
            //System.out.println(llistaPacients);
        }
    }
    
    public void posarLlistaObservable(){
        observablePacients = FXCollections.observableArrayList(llistaPacients);
        tablaPacients.setCellFactory(c -> new PersonaListCell());
        tablaPacients.setItems(observablePacients);
    
    }

    @FXML
    private void eliminarPacient(ActionEvent event) {
        int i = tablaPacients.getSelectionModel().getSelectedIndex();
        
          if (i>=0){
             //System.out.println(baseDades);
             Patient p = observablePacients.get(i);
             //System.out.println(p);
             if(!baseDades.hasAppointments(p)){
                 observablePacients.remove(p);
                 llistaPacients.remove(p);
             }
             else{
                 mostrarDialogError(p);
             }
            
        }
          

        
    }
    
               public void mostrarDialogError(Patient p){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al eliminar paciente");
                alert.setHeaderText(p.getName() +" " +p.getSurname()+" tiene una cita registrada actualmente.");
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
           public void addOrdenat(Patient p) {
            String nom = p.getName() + p.getSurname();
            int i = 0;
            while(i < llistaPacients.size()  && 
                                                nom.compareTo(llistaPacients.get(i).getName()
                                                + " " + llistaPacients.get(i).getSurname()
                                                )>0
                    ) 
            { i++;}
            if(i >= llistaPacients.size()) { llistaPacients.add(p); 
                                        observablePacients.add(p);}//major es impossible que siga
            else {
            llistaPacients.add(i, p);
            observablePacients.add(i,p);
            }
        }

    
}

     class PersonaListCell extends ListCell<Patient> {
         private ImageView view = new ImageView();

        @Override
        protected void updateItem(Patient item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(""); setGraphic(null);
            } else {
                view = scale(view, 60, 60, true);
                view.setImage(item.getPhoto());
                
                VBox foto = new VBox(view);
                foto.setMaxWidth(70);
                foto.setMinWidth(70);
                Label pat = new Label(item.getName() + " " + item.getSurname());
                pat.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;");
                VBox patient = new VBox(pat);
                
                patient.setMargin(pat, new javafx.geometry.Insets(5,5, 5,5));
                patient.setMinWidth(510);
                patient.setMaxWidth(510);
                patient.setStyle( "-fx-background-color: #1976D2");
                HBox patientFoto = new HBox(foto, patient);
                patientFoto.setStyle("-fx-border-width:3; -fx-border-color:#455A64; -fx-border-radius: 5;");
                
                setGraphic(patientFoto);
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
