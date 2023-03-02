/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import DBAccess.ClinicDBAccess;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Doctor;
import model.Patient;
import model.Appointment;
import model.Days;
import utils.SlotAppointmentsWeek;
import utils.SlotWeek;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class LlistaCitesController implements Initializable {

    @FXML
    private ChoiceBox<Patient> listaPacientes;
    @FXML
    private ChoiceBox<Doctor> listaDoctores;
    @FXML
    private ListView<Appointment> listaCitas;
    @FXML
    private TextField buscarPacient;

    @FXML
    private TextField buscarDoctor;
    @FXML
    private TextField buscarCita;
    private ClinicDBAccess baseDades;
    @FXML
    private Button removeC;

    public void setBaseDades(ClinicDBAccess baseDades) {
        this.baseDades = baseDades;
    }
    private ArrayList<Patient> llistaPacients;
    private ArrayList<Doctor> llistaDoctors;
    private ArrayList<Appointment> llistaCites;
    private ArrayList<Appointment> llistaCitesActuals = new ArrayList<>();
    
    private ObservableList<Patient> observablePacients;
    private ObservableList<Doctor> observableDoctors;
    private ObservableList<Appointment> observableCites;
    
    private boolean dSelected = false;
    private boolean pSelected = false;
    
    
    private LocalDateTime diaCita;
    
    public void setLlistaPacients(ArrayList<Patient> llistaPacients) {
        this.llistaPacients = llistaPacients;
    }
    
    public void setLlistaDoctors(ArrayList<Doctor> llistaDoctors) {
        this.llistaDoctors = llistaDoctors;
    }
    
    public void setLlistaCitas(ArrayList<Appointment> llistaCitas) {
        //llistaCitesActuals = new ArrayList<>();
        //System.out.println("cites: "+llistaCitas);
//        llistaCites = llistaCitas;
        this.llistaCites = llistaCitas;
        for (int i = 0; i < llistaCites.size(); i++)
             llistaCitesActuals.add(llistaCites.get(i));
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker1);
        //Node popupContent = datePickerSkin.getPopupContent();
        listaCitas.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal == null) {
                //System.out.println(newVal);
                removeC.setDisable(true);
//                this.patient.setText("");if(newVal!=null) {removeBut.setDisable(false);} else {removeBut.setDisable(false);}
//            this.doctor.setText("");
//            this.sala.setText("");
//            this.hour.setText("");
            }
            else {
                //System.out.println(newVal);
            diaCita = newVal.getAppointmentDateTime();
            removeC.setDisable(false);
//            datePicker1.setValue(LocalDate.from(diaCita));
//            datePicker1.setDayCellFactory(picker -> new DateCell() {
//                
////                @Override
////                public void startEdit() {
////                    //if (!isEmpty()) {
////                        super.startEdit();
//////                createTextField();
////                        setText(null);
////                        setGraphic(listaDoctores);
//////                textField.selectAll();
////                   // }
////                }
////                
////                @Override
////                public void cancelEdit() {
////                    super.cancelEdit();
////
////                    //setText((String) getItem());
////                    setGraphic(null);
////                }
//                @Override
//                public void updateItem(LocalDate date, boolean empty) {
//                    super.updateItem(date, empty);
//                    if (MonthDay.from(date).equals(MonthDay.from(diaCita))) {
//                    //if(date.equals(diaCita)) {
//                            //&& !(getStyleClass().contains("next-month") || getStyleClass().contains("previous-month"))) {
//                        setTooltip(new Tooltip("Hola Raupepi!"));
//                        setStyle("-fx-background-color: #ff4444;");
//                        ChoiceBox<String> prova = new ChoiceBox<>();
//                        prova.setStyle( "-fx-background-color: black; ");
//                        //prova.getStylesheets()
//                        prova.getItems().add("1");
//                        prova.getItems().add("2");
//                        //System.out.println(prova.getChildrenUnmodifiable());
//                        prova.setMaxWidth(40);
//                        prova.setMaxHeight(100);
//                        ObservableList<String> p = prova.getItems();
//                        prova.setItems(p);
//                        setGraphic(prova);
//                    } else {
//                        //setTooltip(null);
//                        setStyle(null);
//                    }
//                }
//                
//                
//            });
            //datePicker1.show();
            //datePicker1.setFocusTraversable(false);
//            Patient p = newVal.getPatient();
//            Doctor d = newVal.getDoctor();
//            LocalDateTime hora = newVal.getAppointmentDateTime();
//            String patient = p.getName() + " " + p.getSurname();
//            String doctor = d.getName() + " " + d.getSurname();
//            String sala = "Sala " + d.getExaminationRoom().getIdentNumber();
//            String hour = hora.getHour() + ":" + hora.getMinute();
//            this.patient.setText(patient);
//            this.doctor.setText(doctor);
//            this.sala.setText(sala);
//            this.hour.setText(hour);
            } 
//            DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker1);
//            Node popupContent = datePickerSkin.getPopupContent();
//            ((VBox)datePicker1.getParent()).getChildren().remove(datePicker1);
//            ((VBox)datePicker1.getParent()).getChildren().
//            ((VBox)(this.patient).getParent()).getChildren().add(popupContent);
            
        });
        // TODO

        //ES PER A OBTINDRE ELS DIES DE LA SETMANA DEL DIA QUE S'HA CLICAT
//        datePicker1.valueProperty().addListener((observable, oldVal, newVal) -> { 
////                System.out.println(datePicker1.getValue().getDayOfWeek());
////                System.out.println(datePicker1.getValue().getDayOfMonth());
////                System.out.println(datePicker1.getValue().getChronology());
//                LocalDateTime firstDayWeek = datePicker1.getValue().atStartOfDay().minusDays(1);
//                //LocalDate d = datePicker1.getValue(); d.get
//                while(!firstDayWeek.getDayOfWeek().toString().equals("MONDAY")) {
//                    firstDayWeek = firstDayWeek.minusDays(1);
//                }
//                for(int i = 0; i < 7; i++) {
//                    System.out.println(firstDayWeek.getDayOfMonth()+" "+ firstDayWeek.getDayOfWeek());
//                    firstDayWeek = firstDayWeek.plusDays(1);
//                }
//               
//                //System.out.println(datePicker1.getValue().atStartOfDay().minusDays(1).getDayOfMonth());
//        });
        
        //BUSCADOR DE PACIENTS
        buscarPacient.textProperty().addListener((observable, oldVal, newVal) -> {
            listaPacientes.show();
            String text = buscarPacient.getText().toLowerCase();
            if (!text.equals("")) {
                ArrayList<Patient> elVisible = new ArrayList<>();
                // elVisible.add(null);
                for (int i = 0; i < llistaPacients.size(); i++) {
                    String pacientActual = llistaPacients.get(i).getName()
                            + " " + llistaPacients.get(i).getSurname();
                    pacientActual = pacientActual.toLowerCase();
                    if (pacientActual.contains(text)) {
                        elVisible.add(llistaPacients.get(i));
                    }
                }
                elVisible.add(null);
                observablePacients = FXCollections.observableArrayList(elVisible);
                //tablaPacients.setCellFactory(c -> new PersonaListCell());
                listaPacientes.setItems(observablePacients);
            }
            else {
                observablePacients = FXCollections.observableArrayList(llistaPacients);
                //tablaPacients.setCellFactory(c -> new PersonaListCell());
                observablePacients.add(null);
                listaPacientes.setItems(observablePacients);
            }
        });

        //BUSCADOR DE DOCTORS
        buscarDoctor.textProperty().addListener((observable, oldVal, newVal) -> {

            listaDoctores.show();
            String text = buscarDoctor.getText().toLowerCase();
            if (!text.equals("")) {
                ArrayList<Doctor> elVisible = new ArrayList<>();
                for (int i = 0; i < llistaDoctors.size(); i++) {
                    String doctorActual = llistaDoctors.get(i).getName()
                            + " " + llistaDoctors.get(i).getSurname();
                    doctorActual = doctorActual.toLowerCase();
                    if (doctorActual.contains(text)) {
                        elVisible.add(llistaDoctors.get(i));
                    }
                }
                elVisible.add(null);
                observableDoctors = FXCollections.observableArrayList(elVisible);
                //tablaPacients.setCellFactory(c -> new PersonaListCell());
                listaDoctores.setItems(observableDoctors);
            }
            else {
                observableDoctors = FXCollections.observableArrayList(llistaDoctors);
                 observableDoctors.add(null);
                listaDoctores.setItems(observableDoctors);
            }

        });
        
        //FILTRE TÍPIC
        buscarCita.textProperty().addListener((observable, oldVal, newVal) -> {
            
            buscador();

        });
        
        //FILTRE DE PACIENTS
        listaPacientes.valueProperty().addListener((observable, oldVal, newVal) -> {
            buscarPacient.setText("");
            buscarPacient.getParent().requestFocus();
            if (newVal == null) {
                buscarPacient.setPromptText("");
            } else {
                buscarPacient.setPromptText(newVal.getName()
                        + " " + newVal.getSurname());
            }

            buscador();


        });
        
        //FILTRE DE DOCTORS
        listaDoctores.valueProperty().addListener((observable, oldVal, newVal) -> {
            
            buscarDoctor.setText("");
            buscarDoctor.getParent().requestFocus();
            if (newVal == null) {
                buscarDoctor.setPromptText("");
            } else {
                buscarDoctor.setPromptText(newVal.getName()
                        + " " + newVal.getSurname());
            }
            buscador();

            
        });
        //ta
        
//        System.out.println(datePicker1.getValue().getDayOfWeek());
//    try{
//            listaPacientes.getEditor().textProperty().addListener((observable, oldVal, newVal) -> { 
//            String text = listaPacientes.getEditor().getText().toLowerCase();
//            ArrayList<Patient> elVisible = new ArrayList<>();
//            for(int i = 0; i < llistaPacients.size(); i++) {
//                String pacientActual = llistaPacients.get(i).getName()
//                                                + " " + llistaPacients.get(i).getSurname();
//                pacientActual = pacientActual.toLowerCase();
//                if(pacientActual.contains(text)) {
//                    elVisible.add(llistaPacients.get(i));
//                }
//            }
//                System.out.println(llistaPacients.size());
//            observablePacients = FXCollections.observableArrayList(elVisible);
//            //tablaPacients.setCellFactory(c -> new PersonaListCell());
//            listaPacientes.setItems(observablePacients);
//            });
//    } catch (ArrayIndexOutOfBoundsException e) {
//        System.out.println("aaa");
//    }

///////////////////-----------------METODES PER A CRIDAR DESDE ALTRES CONTROLADORS------------------------------------------
    }    
        public void posarLlistaObservablePacients() {
        observablePacients = FXCollections.observableArrayList(llistaPacients);
        observablePacients.add(null);
//        listaPacientes.setCellFactory(c -> new PersonaListCell());
        listaPacientes.setConverter(new PacientsConverter());
        listaPacientes.setItems(observablePacients);

    }
        
                public void posarLlistaObservableDoctors() {
        observableDoctors = FXCollections.observableArrayList(llistaDoctors);
        observableDoctors.add(null);
//        listaPacientes.setCellFactory(c -> new PersonaListCell());
        listaDoctores.setConverter(new DoctorsConverter());
        listaDoctores.setItems(observableDoctors);

    }
                
                                public void posarLlistaObservableCites() {
        observableCites = FXCollections.observableArrayList(llistaCites);
//        listaPacientes.setCellFactory(c -> new PersonaListCell());
        listaCitas.setCellFactory(c -> new CitaListCell());
        listaCitas.setItems(observableCites);

    }

    @FXML
    private void afegirCita(ActionEvent event) throws IOException{ ////////--------------------------AFEGIR CITA---------------//
        Stage stage = new Stage();
        FXMLLoader miloader = new FXMLLoader((getClass().getResource("afegirCita.fxml")));
        Parent root =  miloader.load();        //Parent root  = FXMLLoader.load((getClass().getResource("/vista/FXMLPersona.fxml")));

        //((VistaTablaControlador)miloader.getController()).setLista(datos);
        //System.out.println(miloader.getController().toString());
        Scene scene = new Scene(root);
        Appointment a = new Appointment(null, null, null);
        ((AfegirCitaController)miloader.getController()).setCita(a);
        //------------------------------------------------
        ArrayList<Patient> p = llistaPacients;
        ArrayList<Doctor> d = llistaDoctors;
        ArrayList<Appointment> c = llistaCites;
        ((AfegirCitaController) miloader.getController()).setLlistaPacients(p);
        ((AfegirCitaController) miloader.getController()).posarLlistaObservablePacients();
        ((AfegirCitaController) miloader.getController()).setLlistaDoctors(d);
        ((AfegirCitaController) miloader.getController()).posarLlistaObservableDoctors();
        ((AfegirCitaController) miloader.getController()).setLlistaCitas(c);
        ((AfegirCitaController) miloader.getController()).setBaseDades(baseDades);
        //((AfegirCitaController) miloader.getController()).posarLlistaObservableCites();
        //-------------------------------------------------
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(a.getDoctor()!= null && a.getPatient() != null && a.getAppointmentDateTime() != null) { ///////-----GUARDAR LA CITA EN SI-------
            //Si s'afig una cita en algo buit, despres hi hauran excepcions
            llistaCites.add(a); 
            observableCites = FXCollections.observableArrayList(llistaCites);
            listaCitas.setItems(observableCites);
        }
    }
///////-----------------------------ESTE METODE NO TE RES A VORE EN BORRAR DE MOMENT, ESTAVA PROVANT COSES.---------------
    @FXML
    private void eliminarCita(ActionEvent event) {
//        ArrayList<Days> diasVisita = new ArrayList<Days>();
//        diasVisita.add(Days.Monday); diasVisita.add(Days.Friday);
//        Doctor d = new Doctor();
//        Appointment a = new Appointment();
//        
//        //d.
//        System.out.println(llistaCites);
//        System.out.println(LocalTime.MIDNIGHT);
//        System.out.println(LocalTime.NOON);
//        System.out.println(diasVisita);
//            ArrayList<SlotWeek> consultasLibres = SlotAppointmentsWeek.getAppointmentsWeek(1, diasVisita, LocalTime.MIDNIGHT, LocalTime.NOON, llistaCites);
//            System.out.println(consultasLibres.get(0).getFridayAvailability());
//            System.out.println(consultasLibres.get(0).getSaturdayAvailability());
//            System.out.println(consultasLibres.size());
        int i = listaCitas.getSelectionModel().getSelectedIndex();

        if (i >= 0) {
            //System.out.println(baseDades);
            Appointment a = observableCites.get(i);
            //System.out.println(a);
            //haPassatCita(a);
             if(!haPassatCita(a)){
                 observableCites.remove(a);
            llistaCites.remove(a);
//            llistaCites.add(a); 
            observableCites = FXCollections.observableArrayList(llistaCites);
            listaCitas.setItems(observableCites);
            listaCitas.setCellFactory(e -> new CitaListCell());
            listaCitas.refresh();
             }
             else { mostrarDialogError();}
        }
    }
    
        public void mostrarDialogError(){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al eliminar cita");
                alert.setHeaderText("La cita seleccionada ya ha passado.");
                alert.setContentText("No se puede eliminar una cita que ya ha ocurrido.");
                alert.showAndWait();
           }
    public boolean haPassatCita(Appointment a) {
        LocalDateTime data = a.getAppointmentDateTime();
        LocalDateTime dataActual = LocalDateTime.now();
        return dataActual.compareTo(data) >= 0 ? true : false;
        
    }

    @FXML
    private void textFieldRequest(MouseEvent event) {
        listaPacientes.show();
    }

    @FXML
    private void textFieldRequestDocs(MouseEvent event) {
        listaDoctores.show();
    }
        
    ////////////////////// ---------------------------------------- CELL FACTORY PER A LES CITES -------------------------------------------------   
    class CitaListCell extends ListCell<Appointment> {

        private ImageView doc = new ImageView();
        private ImageView pat = new ImageView();
        private Label doct = new Label();
        private Label pati = new Label();
        private Label sala = new Label();
        private Label dia = new Label();
        private Label hora = new Label();
        private ArrayList<String> colors = new ArrayList<>();
        @Override
        protected void updateItem(Appointment item, boolean empty) {
            super.updateItem(item, empty);
          colors.add("#00bcd4");  colors.add("#673ab7");colors.add("#f44336");colors.add("#ff5722");colors.add("#4caf50");
                colors.add("#009688"); colors.add("#607d8b");colors.add("#795548");colors.add("#ffa726");colors.add("#3f51b5"); colors.add("#e91e63");
            if (item == null || empty) {
                setText("");
                //setStyle( "-fx-background-color: default;");
            } else {
                Patient  p = item.getPatient();
                Doctor d = item.getDoctor();
                

                
//                if(item.getDoctor().getName().equals("Pepe")) {
//                     setStyle( "-fx-background-color: #00BCD4;"); }
//                else { setStyle("-fx-background-color:#CDDC39 ");}
                if(p != null && d != null) {
//                setText(p.getName() + " " + p.getSurname() + " "+
//                        d.getName()+" " +d.getSurname() +" " + item.getAppointmentDateTime());
                
                doc = scale(doc,90, 90, true);
                doc.setImage(d.getPhoto());
                VBox fotoDoc = new VBox(doc);
                fotoDoc.setMinWidth(90);
                fotoDoc.setMaxWidth(90);
                //fotoDoc.setStyle("-fx-border-width:5; -fx-border-color: #03A9F4; -fx-border-radius: 10;");
                
                doct.setText("Dr./Dra. "+"\n"+d.getName()+"\n" +d.getSurname());
                doct.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;");
                HBox doctor = new HBox(doct);
                HBox.setHgrow(doctor, Priority.ALWAYS);
                doctor.setMargin(doct, new javafx.geometry.Insets(5,5, 5,5));
                doctor.setMinWidth(200);
                //doctor.setMaxWidth(200);
                HBox doctorMesImage = new HBox(fotoDoc, doctor);
                HBox.setHgrow(doctorMesImage, Priority.ALWAYS);
                doctorMesImage.setStyle("-fx-border-width:3; -fx-border-color: #455a64; -fx-border-radius: 5;");
                ///////////---------------------------------COLOR A CADA DOCTOR---------------------------------------------------
                               int nDoc = ClinicDBAccess.getSingletonClinicDBAccess().getDoctors().size();
               //int rand = (int) (Math.random()*colors.size());
                //System.out.println(rand);
                //if(!jaRandom)
                //{for(int i = 0; i < rand; i++) {String r = colors.remove(0); colors.add(r); } jaRandom=true;}
               for(int i = 0; i < nDoc; i++){
                   Doctor doo = ClinicDBAccess.getSingletonClinicDBAccess().getDoctors().get(i);
                   String docAct = d.getName()+" " +d.getSurname();
                   if(docAct.contains(doo.getName() + " " + doo.getSurname())) {
                      doctor.setStyle("-fx-background-color:"+ colors.get(i)+";");
                   }
               }
               ///////////////------------------------------------------------------------------------------------------------------------------------------------
                
                
                
                
                //doctor.setStyle( "-fx-background-color: #03A9F4");
                
                pat = scale(pat, 90, 90, true);
                pat.setImage(p.getPhoto());
                VBox fotoPat = new VBox(pat);
                fotoPat.setMinWidth(90);
                fotoPat.setMaxWidth(90);
                //fotoPat.setStyle("-fx-border-width:5; -fx-border-color: #03A9F4; -fx-border-radius: 10;");
                
                pati.setText(p.getName()+"\n" +p.getSurname());
                pati.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;");
                HBox patient = new HBox(pati);
                HBox.setHgrow(patient, Priority.ALWAYS);
                patient.setMargin(pati, new javafx.geometry.Insets(5,5, 5,5));
                patient.setMinWidth(200);
                //patient.setMaxWidth(200);
                patient.setStyle( "-fx-background-color: #1976D2");
                HBox patientMesImage = new HBox(fotoPat, patient);
                HBox.setHgrow(patientMesImage, Priority.ALWAYS);
                patientMesImage.setStyle("-fx-border-width:3; -fx-border-color:#455A64; -fx-border-radius: 5;");
                
                sala.setText("Sala "+item.getDoctor().getExaminationRoom().getIdentNumber());
                sala.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;");
                VBox salaB = new VBox(sala);
                salaB.setMargin(sala, new javafx.geometry.Insets(5,5, 5,5));
                salaB.setAlignment(Pos.CENTER);
                salaB.setStyle( "-fx-background-color: #607D8B");
                
                DayOfWeek day = item.getAppointmentDateTime().getDayOfWeek();
                Month mes = item.getAppointmentDateTime().getMonth();
                String diaSemana = translateDia(day);
                String delMes = translateMonth(mes);
                dia.setText(diaSemana+" "+item.getAppointmentDateTime().getDayOfMonth()+ " " + delMes);
                dia.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;");
                VBox diaVB = new VBox(dia);
                diaVB.setMinWidth(200);
                //diaVB.setMaxWidth(200);
                
                diaVB.setMargin(dia, new javafx.geometry.Insets(5,5, 5,5));
                diaVB.setAlignment(Pos.CENTER);
                diaVB.setStyle( "-fx-background-color: #455A64");
                
                int horam = item.getAppointmentDateTime().getHour();
                int min = item.getAppointmentDateTime().getMinute();
                hora.setText((horam < 10 ? ("0"+horam):(""+horam)) + ":" +  (min < 10 ? ("0"+min): (""+min)));
                hora.setStyle("-fx-font-size: 18; -fx-text-fill: #ffffff;");
                VBox horaVB = new VBox(hora);
                horaVB.setMinWidth(100);
                //horaVB.setMaxWidth(100);
                horaVB.setMargin(hora, new javafx.geometry.Insets(5,5, 5,5));
                horaVB.setAlignment(Pos.CENTER);
                horaVB.setStyle( "-fx-background-color: #455A64");
                //pat.setStyle("-fx-background-color: blue;");
                HBox apoint = new HBox(10);
                HBox.setHgrow(apoint, Priority.ALWAYS);
                apoint.getChildren().addAll(doctorMesImage,patientMesImage,salaB, diaVB, horaVB);
                
//                view = scale(view, 20, 20, true);
//                view.setImage(item.getPhoto());
               setGraphic(apoint);
                }
            }
        }
        
    }
    /////////////////-----------------------------------------------------------------------------------------------------------------------------------------

    public ImageView scale(ImageView imageView, int targetWidth, int targetHeight, boolean preserveRatio) {
        imageView.setSmooth(true);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView;//.snapshot(null, null);
    }
    
    public String translateDia(DayOfWeek day) {
         String s = "";
                    switch (day) {
                        case MONDAY:
                            s = "Lunes";
                            break;
                        case TUESDAY:
                            s = "Martes";
                            break;
                        case WEDNESDAY:
                            s = "Miercoles";
                            break;
                        case THURSDAY:
                            s = "Jueves";
                            break;
                        case FRIDAY:
                            s = "Viernes";
                            break;
                        case SATURDAY:
                            s = "Sábado";
                            break;
                        case SUNDAY:
                            s = "Domingo";
                            break;
                    }
                    return s;
    
    }
    public String translateMonth(Month m) {
         String s = "";
        switch (m) {
            case JANUARY:
                s = "Enero";
                break;
            case FEBRUARY:
                s = "Febrero";
                break;
            case MARCH:
                s = "Marzo";
                break;
            case APRIL:
                s = "Abril";
                break;
            case MAY:
                s = "Mayo";
                break;
            case JUNE:
                s = "Junio";
                break;
            case JULY:
                s = "Julio";
                break;
            case AUGUST:
                s = "Agosto";
                break;
            case SEPTEMBER:
                s = "Septiembre";
                break;
            case NOVEMBER:
                s = "Noviembre";
                break;
            case OCTOBER:
                s = "Octubre";
                break;
            case DECEMBER:
                s = "Diciembre";
                break;
        }
        return s;

    }

//////----------------------------------------FORMAT PER A MOSTRAR PACIENTS I DOCTORS -------------------------------------//////////
    class PacientsConverter extends StringConverter<Patient> { 

        public Patient fromString(String string) {
            // convert from a string to a myClass instance
            return null;
        }

        public String toString(Patient item) {
            // convert a myClass instance to the text displayed in the choice box
            if(item == null) {return "";}
            return item.getName() + " " + item.getSurname();
        }
    }

    class DoctorsConverter extends StringConverter<Doctor> {

        public Doctor fromString(String string) {
            // convert from a string to a myClass instance
            return null;
        }

        public String toString(Doctor item) {
            // convert a myClass instance to the text displayed in the choice box
            if(item == null) {return "";}
            return item.getName() + " " + item.getSurname();
        }
    }

    class CitesConverter extends StringConverter<Appointment> {

        public Appointment fromString(String string) {
            // convert from a string to a myClass instance
            return null;
        }

        public String toString(Appointment item) {
            // convert a myClass instance to the text displayed in the choice box
            return item.getPatient() + " " + item.getPatient() + " " + item.getAppointmentDateTime();
        }
    }
    
    ///////------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    ////////------------------------------METODE PER A BUSCAR, CONJUNT ALS 3 LISTENERS---------------------------------------------------------
    public void buscador() {
        String text1 = buscarCita.getText().toLowerCase();
        String text2 = "";
        if (listaPacientes.getValue() != null) {
            text2 = (listaPacientes.getValue().getName() + " " + listaPacientes.getValue().getSurname())
                    .toLowerCase();
        }
        String text3 = "";
        if (listaDoctores.getValue() != null) {
            text3 = (listaDoctores.getValue().getName() + " " + listaDoctores.getValue().getSurname())
                    .toLowerCase();
        }
        ArrayList<Appointment> elVisible = new ArrayList<>();
        for (int i = 0; i < llistaCites.size(); i++) {
            Appointment c = llistaCites.get(i); 
            Patient p = c.getPatient();
            Doctor d = c.getDoctor();
            
            DayOfWeek day = c.getAppointmentDateTime().getDayOfWeek();
                Month mes = c.getAppointmentDateTime().getMonth();
                String diaSemana = translateDia(day);
                String delMes = translateMonth(mes);
                
                int horam = c.getAppointmentDateTime().getHour();
                int min = c.getAppointmentDateTime().getMinute();
            String citaActual = p.getName() + " " + p.getSurname() + " "
                    + d.getName() + " " + d.getSurname() + " " + "Sala "+d.getExaminationRoom().getIdentNumber()+
                    diaSemana+" "+c.getAppointmentDateTime().getDayOfMonth()+ " " + delMes+
                    (horam < 10 ? ("0"+horam):(""+horam)) + ":" +  (min < 10 ? ("0"+min): (""+min));
            citaActual = citaActual.toLowerCase();
            if (citaActual.contains(text1) && citaActual.contains(text2) && citaActual.contains(text3)) {
                elVisible.add(llistaCites.get(i));
            }
        }
        observableCites = FXCollections.observableArrayList(elVisible);
        listaCitas.setCellFactory(c -> new CitaListCell());
        listaCitas.setItems(observableCites);
        listaCitas.refresh();
    }
    
}
    
    