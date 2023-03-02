/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import DBAccess.ClinicDBAccess;
import static java.lang.Boolean.TRUE;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.Days;
import model.Doctor;
import model.Patient;
import utils.SlotAppointmentsWeek;
import utils.SlotWeek;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class AfegirCitaController implements Initializable {
    @FXML
    private TextField buscarPacient;
    @FXML
    private TextField buscarDoctor;
    @FXML
    private ChoiceBox<Patient> listaPacientes; ///-------PACIENT DE LA CITA----------///
    @FXML
    private ChoiceBox<Doctor> listaDoctores;///--------DOCTOR DE LA CITA---------///
    
    private LocalDate diaCita;///-------DIA DE LA CITA-------///
    private LocalTime horaCita; ///-----HORA DE LA CITA-------//
    private ArrayList<LocalDate> diesSemana = new ArrayList<>();

    private Appointment cita;

    private ArrayList<Patient> llistaPacients;
    private ArrayList<Doctor> llistaDoctors;
    private ArrayList<Appointment> llistaCites;
    
    private ObservableList<Patient> observablePacients;
    private ObservableList<Doctor> observableDoctors;
    private ObservableList<Appointment> observableCites;
    private ObservableList<Appointment> observableCitesDoctor;
    private ClinicDBAccess baseDades;
    @FXML
    private GridPane semana;
    @FXML
    private ListView<String> dilluns;
    @FXML
    private ListView<String> dimarts;
    @FXML
    private ListView<String> dimecres;
    @FXML
    private ListView<String> dijous;
    @FXML
    private ListView<String> divendres;
    @FXML
    private ListView<String> dissabte;
    @FXML
    private ListView<String> diumenge;
    @FXML
    private ListView<String> hora;
    @FXML
    private GridPane semana1;
    
    private LocalDate dataCita = LocalDate.now();
    @FXML
    private Button anteriorButon;
    @FXML
    private Button aceptarButon;
    @FXML
    private Button hoy;
    @FXML
    private Button siguienteB;
    

    public void setBaseDades(ClinicDBAccess baseDades) {
        this.baseDades = baseDades;
    }
    @FXML
    private ChoiceBox<Integer> horaIni;
    @FXML
    private ChoiceBox<Integer> minIni;
    
    public void setCita(Appointment cita) {
        this.cita = cita;
    }
    
    public void setLlistaPacients(ArrayList<Patient> llistaPacients) {
        this.llistaPacients = llistaPacients;
    }

    public void setLlistaDoctors(ArrayList<Doctor> llistaDoctors) {
        this.llistaDoctors = llistaDoctors;
    }

    public void setLlistaCitas(ArrayList<Appointment> llistaCitas) {
        this.llistaCites = llistaCitas;
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

    @FXML
    private void anterior(ActionEvent event) {
        Doctor doc = listaDoctores.getValue();
        if (doc != null) {
        dataCita = dataCita.minusWeeks(1);
            LocalDate actual = LocalDate.now();
            //actual.
            Locale locale = Locale.getDefault();
                    //-----------
            int weekOfYear = dataCita.get(WeekFields.of(locale).weekOfWeekBasedYear());
            int weekNow = actual.get(WeekFields.of(locale).weekOfWeekBasedYear());
            //System.out.println(weekOfYear+ " "+ weekNow);
            if(weekOfYear == weekNow) { anteriorButon.setDisable(true);}
            
        posarDiesSemana();
        for(int i = 0; i < semana.getChildren().size(); i++) {
                    if (semana.getChildren().get(i) instanceof ListView) {
                        ((ListView) semana.getChildren().get(i)).getItems().clear();
                        ((ListView) semana.getChildren().get(i)).refresh();
                    }
        }        
            //System.out.println("anem a posar el nom");
           // nombreDoctor.setText(newVal.getName() + " " + newVal.getSurname());
            /* EXCEPCIO */
//            System.out.println(baseDades);
//            System.out.println(newVal.getIdentifier());
//            System.out.println(baseDades.getAppointments());
//            System.out.println("citas del doctor: " + baseDades.getDoctorAppointments(newVal.getIdentifier()));
            
            /* EXCEPCIO */
            //Doctor doc = listaDoctores.getValue();
            //------------------------------------------------------------------------
            ArrayList<Appointment> citesDoctor = baseDades.getDoctorAppointments(doc.getIdentifier());
                    //-----------
                    // LocalDate dataCita = //citesDoctor.get(0).getAppointmentDateTime(); dia.getValue();
                    //LocalDate dataCita = LocalDate.now(); //LocalDate.of(2019, 3, 30); //LocalDate.now();
                    //Locale locale = Locale.getDefault();
                    //-----------
            //weekOfYear = dataCita.get(WeekFields.of(locale).weekOfWeekBasedYear());
               // System.out.println(weekOfYear + " SEMANA");
                    
            ArrayList<Days> visitDays = doc.getVisitDays();
               // System.out.println("DIES"+visitDays.toString());

            LocalTime doctorStart = doc.getVisitStartTime();
            
            LocalTime doctorEnd = doc.getVisitEndTime();
            
            ArrayList<SlotWeek> slots = SlotAppointmentsWeek.getAppointmentsWeek(weekOfYear,
                    visitDays, doctorStart, doctorEnd, citesDoctor);
            for(int i = 0; i < slots.size(); i++) {
                SlotWeek hora = slots.get(i);
                this.hora.getItems().add(hora.getSlot().toString());
                //for(int j = 0; j < 7; j++) {
                    dilluns.getItems().add(hora.getMondayAvailability());
                    dimarts.getItems().add(hora.getTuesdayAvailability());
                    dimecres.getItems().add(hora.getWednesdayAvailability());
                    dijous.getItems().add(hora.getThursdayAvailability());
                    divendres.getItems().add(hora.getFridayAvailability());
                    dissabte.getItems().add(hora.getSaturdayAvailability());
                    diumenge.getItems().add(hora.getSundayAvailability());
                //}
            
            }
            for (int i = 0; i < semana.getChildren().size(); i++) {
                if (semana.getChildren().get(i) instanceof ListView) {
                    ((ListView) semana.getChildren().get(i)).setCellFactory(e -> new HorariListCell());
                    ((ListView) semana.getChildren().get(i)).refresh();
                }
            }
        }
    }

    @FXML
    private void siguiente(ActionEvent event) {
        Doctor doc = listaDoctores.getValue();
        if (doc != null) {
            
            dataCita = dataCita.plusWeeks(1);
            anteriorButon.setDisable(false);
            posarDiesSemana();
            for (int i = 0; i < semana.getChildren().size(); i++) {
                if (semana.getChildren().get(i) instanceof ListView) {
                    ((ListView) semana.getChildren().get(i)).getItems().clear();
                    ((ListView) semana.getChildren().get(i)).refresh();
                }
            }

            //Doctor doc = listaDoctores.getValue();
            //------------------------------------------------------------------------
            ArrayList<Appointment> citesDoctor = baseDades.getDoctorAppointments(doc.getIdentifier());
            //-----------
            Locale locale = Locale.getDefault();
            //-----------
            int weekOfYear = dataCita.get(WeekFields.of(locale).weekOfWeekBasedYear());
            //System.out.println(weekOfYear + " SEMANA");

            ArrayList<Days> visitDays = doc.getVisitDays();
            //System.out.println("DIES" + visitDays.toString());

            LocalTime doctorStart = doc.getVisitStartTime();

            LocalTime doctorEnd = doc.getVisitEndTime();

            ArrayList<SlotWeek> slots = SlotAppointmentsWeek.getAppointmentsWeek(weekOfYear,
                    visitDays, doctorStart, doctorEnd, citesDoctor);

            for (int i = 0; i < slots.size(); i++) {
                SlotWeek hora = slots.get(i);
                this.hora.getItems().add(hora.getSlot().toString());
                //for(int j = 0; j < 7; j++) {
                dilluns.getItems().add(hora.getMondayAvailability());
                dimarts.getItems().add(hora.getTuesdayAvailability());
                dimecres.getItems().add(hora.getWednesdayAvailability());
                dijous.getItems().add(hora.getThursdayAvailability());
                divendres.getItems().add(hora.getFridayAvailability());
                dissabte.getItems().add(hora.getSaturdayAvailability());
                diumenge.getItems().add(hora.getSundayAvailability());
                //semana.getRowIndex(diumenge);
                //}

            }
            for (int i = 0; i < semana.getChildren().size(); i++) {
                if (semana.getChildren().get(i) instanceof ListView) {
                    ((ListView) semana.getChildren().get(i)).setCellFactory(e -> new HorariListCell());
                    ((ListView) semana.getChildren().get(i)).refresh();
                }
            }
            //System.out.println(diesSemana);
        }
    }

    @FXML
    private void hoy(ActionEvent event) {
        Doctor doc = listaDoctores.getValue();
        if (doc != null) {
            dataCita = LocalDate.now();
            anteriorButon.setDisable(true);
            posarDiesSemana();
            for (int i = 0; i < semana.getChildren().size(); i++) {
                if (semana.getChildren().get(i) instanceof ListView) {
                    ((ListView) semana.getChildren().get(i)).getItems().clear();
                    ((ListView) semana.getChildren().get(i)).refresh();
                    
                }
            }

            //Doctor doc = listaDoctores.getValue();
            //------------------------------------------------------------------------
            ArrayList<Appointment> citesDoctor = baseDades.getDoctorAppointments(doc.getIdentifier());
            //-----------
            Locale locale = Locale.getDefault();
            //-----------
            int weekOfYear = dataCita.get(WeekFields.of(locale).weekOfWeekBasedYear());
            //System.out.println(weekOfYear + " SEMANA");

            ArrayList<Days> visitDays = doc.getVisitDays();
           // System.out.println("DIES" + visitDays.toString());

            LocalTime doctorStart = doc.getVisitStartTime();

            LocalTime doctorEnd = doc.getVisitEndTime();

            ArrayList<SlotWeek> slots = SlotAppointmentsWeek.getAppointmentsWeek(weekOfYear,
                    visitDays, doctorStart, doctorEnd, citesDoctor);

            for (int i = 0; i < slots.size(); i++) {
                SlotWeek hora = slots.get(i);
                this.hora.getItems().add(hora.getSlot().toString());
                //for(int j = 0; j < 7; j++) {
                dilluns.getItems().add(hora.getMondayAvailability());
                dimarts.getItems().add(hora.getTuesdayAvailability());
                dimecres.getItems().add(hora.getWednesdayAvailability());
                dijous.getItems().add(hora.getThursdayAvailability());
                divendres.getItems().add(hora.getFridayAvailability());
                dissabte.getItems().add(hora.getSaturdayAvailability());
                diumenge.getItems().add(hora.getSundayAvailability());
                //}

            }
            for (int i = 0; i < semana.getChildren().size(); i++) {
                if (semana.getChildren().get(i) instanceof ListView) {
                    ((ListView) semana.getChildren().get(i)).setCellFactory(e -> new HorariListCell());
                    ((ListView) semana.getChildren().get(i)).refresh();
                }
            }
        }
    }

    @FXML
    private void pacienteRequest(MouseEvent event) {
        listaPacientes.show();
    }

    @FXML
    private void doctorRequest(MouseEvent event) {
        listaDoctores.show();
    }
                
    class DoctorsConverter extends StringConverter<Doctor> {

        public Doctor fromString(String string) {
            // convert from a string to a myClass instance
            return null;
        }

        public String toString(Doctor item) {
            // convert a myClass instance to the text displayed in the choice box
            if (item == null) {
                return "";
            }
            return item.getName() + " " + item.getSurname();
        }
    }

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
     
         class CitaListCell extends ListCell<Appointment> {

        //private ImageView view = new ImageView();

        @Override
        protected void updateItem(Appointment item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null) {
                setText("null");
                if(empty) {setText("");}
            } else {
//                view = scale(view, 20, 20, true);
//                view.setImage(item.getPhoto());
//                setGraphic(view);
                Patient  p = item.getPatient();
                Doctor d = item.getDoctor();
                if(p != null && d != null) {
                setText(p.getName() + " " + p.getSurname() + " "+
                        d.getName()+" " +d.getSurname() +" " + item.getAppointmentDateTime());
                }
                else {setText("Paciente o Doctor no encontrado");}
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        llistaCitesDoctor.setCellFactory(c -> new CitaListCell());
        horaIni.getItems().removeAll(horaIni.getItems());
        horaIni.getItems().addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        
        minIni.getItems().removeAll(minIni.getItems());
        minIni.getItems().addAll(00, 15, 30, 45);
        
        //BUSCADOR DE PACIENTS ------------------------------------------------------------------------------------------------
          ChangeListener<? super String> buscPac = ((observable, oldVal, newVal) -> {
            
            //if(!newVal.equals("")) {
                listaPacientes.show();
            String text = buscarPacient.getText().toLowerCase();
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
            //}
        });
        buscarPacient.textProperty().addListener(buscPac);
        
        //BUSCADOR DE DOCTORS----------------------------------------------------------------------------------------------------------------
        ChangeListener<? super String> buscDoc =((observablee, oldVaal, newVaal) -> {
            
          // if(!newVal.equals("")) {
               listaDoctores.show();
            String text = buscarDoctor.getText().toLowerCase();
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
           // }
        });
        buscarDoctor.textProperty().addListener(buscDoc);

        ///////---------------------------------METODE PER A OBTENIR TOTS ELS DATOS NECESSARIS DEL DOCTOR EN QUESTIO--------------
        listaDoctores.valueProperty().addListener((observable, oldVal, newVal) -> {
            buscarDoctor.textProperty().removeListener(buscDoc);
            buscarDoctor.setText("");
            buscarDoctor.textProperty().addListener(buscDoc);
            
            //dia.requestFocus();
            if (newVal == null) {
                anteriorButon.setDisable(true);
                siguienteB.setDisable(true);
                hoy.setDisable(true);
                for (int i = 0; i < semana.getChildren().size(); i++) {
                if (semana.getChildren().get(i) instanceof ListView) {
                    ((ListView) semana.getChildren().get(i)).getItems().clear();
                    ((ListView) semana.getChildren().get(i)).refresh();
                    
                }
            }
                 aceptarButon.setDisable(true);
                buscarDoctor.setPromptText("") ;
                //hora.getItems().clear();
            } else {
                siguienteB.setDisable(false);
                hoy.setDisable(false);
                if(listaPacientes.getValue() != null && diaCita != null && horaCita != null)
                        aceptarButon.setDisable(false);
                        
                //System.out.println("asdjflkasjdgkljaslkdgjaksljknjfdsjjdslkjfdslkjdsfjñlfdsjlkfdsakjkjjk");
                for (int i = 0; i < semana.getChildren().size(); i++) {
                    if (semana.getChildren().get(i) instanceof ListView) {
                        ((ListView) semana.getChildren().get(i)).getItems().clear();
                        ((ListView) semana.getChildren().get(i)).refresh();
                    }
                }
                buscarDoctor.setPromptText(newVal.getName()
                        + " " + newVal.getSurname());
                //System.out.println(buscarDoctor.getText());
                //if (newVal != null) {
                //dataCita = LocalDate.now();
                posarDiesSemana();
                //------------------------------------------------------------------------
                ArrayList<Appointment> citesDoctor = baseDades.getDoctorAppointments(newVal.getIdentifier());
                //-----------
                // LocalDate dataCita = //citesDoctor.get(0).getAppointmentDateTime(); dia.getValue();
                //LocalDate dataCita = LocalDate.now(); //LocalDate.of(2019, 3, 30); //LocalDate.now();
                Locale locale = Locale.getDefault();
                //-----------
                int weekOfYear = dataCita.get(WeekFields.of(locale).weekOfWeekBasedYear());
                //System.out.println(weekOfYear + " SEMANA");

                ArrayList<Days> visitDays = newVal.getVisitDays();
                //System.out.println("DIES" + visitDays.toString());

                LocalTime doctorStart = newVal.getVisitStartTime();

                LocalTime doctorEnd = newVal.getVisitEndTime();

                ArrayList<SlotWeek> slots = SlotAppointmentsWeek.getAppointmentsWeek(weekOfYear,
                        visitDays, doctorStart, doctorEnd, citesDoctor);
                for (int i = 0; i < slots.size(); i++) {
                    SlotWeek hora = slots.get(i);
                    
                    this.hora.getItems().add(hora.getSlot().toString());
                    //for(int j = 0; j < 7; j++) {
                    dilluns.getItems().add(hora.getMondayAvailability());
                    dimarts.getItems().add(hora.getTuesdayAvailability());
                    dimecres.getItems().add(hora.getWednesdayAvailability());
                    dijous.getItems().add(hora.getThursdayAvailability());
                    divendres.getItems().add(hora.getFridayAvailability());
                    dissabte.getItems().add(hora.getSaturdayAvailability());
                    diumenge.getItems().add(hora.getSundayAvailability());
                    //}

                }
                //------------------------------------------------------------------------
                //           observableCitesDoctor = FXCollections.observableArrayList(citesDoctor);
//            llistaCitesDoctor.setItems(observableCitesDoctor);
                for (int i = 0; i < semana.getChildren().size(); i++) {
                    if (semana.getChildren().get(i) instanceof ListView) {
                        ((ListView) semana.getChildren().get(i)).setCellFactory(e -> new HorariListCell());
                        ((ListView) semana.getChildren().get(i)).refresh();
                    }
                }
            }
            //}
        });
        
        //////////////////////--------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------SELECCIO DE PACIENT ------------------------------------------------\\\\\\\
        listaPacientes.valueProperty().addListener((observable, oldVal, newVal) -> {
            buscarPacient.textProperty().removeListener(buscPac);
            buscarPacient.setText("");
            buscarPacient.textProperty().addListener(buscPac);
            //buscarDoctor.requestFocus();
            if (newVal == null) {
                buscarPacient.setPromptText("");
                aceptarButon.setDisable(true);
//                anteriorButon.setDisable(true);
//                hoy.setDisable(true);
            } else {
                if(listaDoctores.getValue() != null && diaCita != null && horaCita != null)
                        aceptarButon.setDisable(false);
//                        anteriorButon.setDisable(false);
//                hoy.setDisable(false);
                buscarPacient.setPromptText(newVal.getName()
                        + " " + newVal.getSurname());
            }
            //System.out.println(buscarPacient.getText());
        
        });
        
        
        
        
                    //ES PER A OBTINDRE ELS DIES DE LA SETMANA DEL DIA QUE S'HA CLICAT
                    
                    
//        dia.valueProperty().addListener((observable, oldVal, newVal) -> { 
////                System.out.println(datePicker1.getValue().getDayOfWeek());
////                System.out.println(datePicker1.getValue().getDayOfMonth());
////                System.out.println(datePicker1.getValue().getChronology());
//                LocalDateTime firstDayWeek = dia.getValue().atStartOfDay().minusDays(1);
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

//CITES DEL PACIENT, NO FAN FALTA        
//                listaPacientes.valueProperty().addListener((observable, oldVal, newVal) -> {
//            System.out.println("anem a posar el nom");
//            //nombreDoctor.setText(newVal.getName() + " " + newVal.getSurname());
//            /* EXCEPCIO */
////            System.out.println(baseDades);
////            System.out.println(newVal.getIdentifier());
//            System.out.println(baseDades.getAppointments());
//            System.out.println("citas del paciente: " + baseDades.getPatientAppointments(newVal.getIdentifier()));
//            ArrayList<Appointment> citesDoctor = baseDades.getDoctorAppointments(newVal.getIdentifier());
//            /* EXCEPCIO */
////            observableCitesDoctor = FXCollections.observableArrayList(citesDoctor);
////            llistaCitesDoctor.setItems(observableCitesDoctor);
//        });

///////////////////////-----------------------------SELECCIO DE LA DATA-----------------------------------------------------

        dilluns.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.equals(-1) && dilluns.getItems().get((int)newVal).equals("Free")) {
                 if(listaDoctores.getValue() != null && listaPacientes.getValue() != null)
                        aceptarButon.setDisable(false);
               // System.out.println(newVal);
                String horaClic = hora.getItems().get((int) newVal);
                String h = horaClic.substring(0, horaClic.indexOf(":"));
                String m = horaClic.substring(horaClic.indexOf(":") + 1);
                //System.out.println(h + m);
                horaCita = LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
                //System.out.println(horaCita);
                //System.out.println(observable.getValue());
                diaCita = diesSemana.get(0);
               // System.out.println(diaCita);
            }
        });

        dimarts.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.equals(-1)  && dimarts.getItems().get((int)newVal).equals("Free")) {
                if(listaDoctores.getValue() != null && listaPacientes.getValue() != null)
                        aceptarButon.setDisable(false);
                //System.out.println(newVal);
                String horaClic = hora.getItems().get((int) newVal);
                String h = horaClic.substring(0, horaClic.indexOf(":"));
                String m = horaClic.substring(horaClic.indexOf(":") + 1);
                //System.out.println(h + m);
                horaCita = LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
                //System.out.println(horaCita);
                //System.out.println(observable.getValue());
                diaCita = diesSemana.get(1);
                //System.out.println(diaCita);
            }
        });

        dimecres.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.equals(-1) && dimecres.getItems().get((int)newVal).equals("Free")) {
                if(listaDoctores.getValue() != null && listaPacientes.getValue() != null)
                        aceptarButon.setDisable(false);
                //System.out.println(newVal);
                String horaClic = hora.getItems().get((int) newVal);
                String h = horaClic.substring(0, horaClic.indexOf(":"));
                String m = horaClic.substring(horaClic.indexOf(":") + 1);
                //System.out.println(h + m);
                horaCita = LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
                //System.out.println(horaCita);
                //System.out.println(observable.getValue());
                diaCita = diesSemana.get(2);
                //System.out.println(diaCita);
            }
        });

        dijous.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.equals(-1) && dijous.getItems().get((int)newVal).equals("Free")) {
                if(listaDoctores.getValue() != null && listaPacientes.getValue() != null)
                        aceptarButon.setDisable(false);
               // System.out.println(newVal);
                String horaClic = hora.getItems().get((int) newVal);
                String h = horaClic.substring(0, horaClic.indexOf(":"));
                String m = horaClic.substring(horaClic.indexOf(":") + 1);
                //System.out.println(h + m);
                horaCita = LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
               // System.out.println(horaCita);
                //System.out.println(observable.getValue());
                diaCita = diesSemana.get(3);
                //System.out.println(diaCita);
            }
        });

        divendres.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.equals(-1) && divendres.getItems().get((int)newVal).equals("Free")) {
                if(listaDoctores.getValue() != null && listaPacientes.getValue() != null)
                        aceptarButon.setDisable(false);
               // System.out.println(newVal);
                String horaClic = hora.getItems().get((int) newVal);
                String h = horaClic.substring(0, horaClic.indexOf(":"));
                String m = horaClic.substring(horaClic.indexOf(":") + 1);
                //System.out.println(h + m);
                horaCita = LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
               // System.out.println(horaCita);
                //System.out.println(observable.getValue());
                diaCita = diesSemana.get(4);
               // System.out.println(diaCita);
            }
        });

        dissabte.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.equals(-1) && dissabte.getItems().get((int)newVal).equals("Free")) {
                if(listaDoctores.getValue() != null && listaPacientes.getValue() != null)
                        aceptarButon.setDisable(false);
                //System.out.println(newVal);
                String horaClic = hora.getItems().get((int) newVal);
                String h = horaClic.substring(0, horaClic.indexOf(":"));
                String m = horaClic.substring(horaClic.indexOf(":") + 1);
                //System.out.println(h + m);
                horaCita = LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
               // System.out.println(horaCita);
                //System.out.println(observable.getValue());
                diaCita = diesSemana.get(5);
                //System.out.println(diaCita);
            }

        });

        diumenge.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
//            for (int i = 0; i < semana.getChildren().size(); i++) {
//                Node n = semana.getChildren().get(i);
//                if (n instanceof ListView) {
//                    ((ListView) n).getSelectionModel().clearSelection();
//                }
//            }
            if (!newVal.equals(-1) && diumenge.getItems().get((int)newVal).equals("Free")) {
                if(listaDoctores.getValue() != null && listaPacientes.getValue() != null)
                        aceptarButon.setDisable(false);
                //System.out.println(newVal);
                String horaClic = hora.getItems().get((int) newVal);
                String h = horaClic.substring(0, horaClic.indexOf(":"));
                String m = horaClic.substring(horaClic.indexOf(":") + 1);
                //System.out.println(h + m);
                horaCita = LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
                //System.out.println(horaCita);
                //System.out.println(observable.getValue());
                diaCita = diesSemana.get(6);
                //System.out.println(diaCita);
            }
        });
        
        
        ////////////////////--------------------------------FORMAT DE LA DATA------------------------------------------------------
        for(int i = 0; i < semana.getChildren().size(); i++) {
                Node n = semana.getChildren().get(i);
                if(n instanceof ListView)
                    ((ListView) n).setCellFactory(c -> new HorariListCell());
            }
    
    }

    @FXML
    private void aceptar(ActionEvent event) {
        //...
        Doctor dSelect = listaDoctores.getValue();
        Patient pSelect = listaPacientes.getValue();
//        LocalDate d = dia.getValue();
//        Integer h = horaIni.getValue();
//        Integer m = minIni.getValue();
//        System.out.println(h + " " + m);
       // LocalTime hora = LocalTime.of(h, m);
        LocalDateTime what = LocalDateTime.of(diaCita, horaCita);
        //LocalDateTime day = LocalDateTime.of(d, hora);
        cita.setAppointmentDateTime(what);
        cita.setDoctor(dSelect);
        cita.setPatient(pSelect);
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATION
            alert.setTitle("Operación realizada correctamente");
            alert.setHeaderText("Se ha añadido la cita con éxito");
            // ó null si no queremos cabecera
            alert.showAndWait();

            ((Stage) buscarDoctor.getScene().getWindow()).close();
         //cita = new Appointment(day, dSelect, pSelect);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) buscarDoctor.getScene().getWindow()).close();
    }
    ///////////////////////////------------- PER A QUE APAREGUEN ELS DIES DE LA SETMANA
    public void posarDiesSemana() {
        diesSemana.removeAll(diesSemana);
        LocalDateTime firstDayWeek = dataCita.atStartOfDay();//.minusDays(1);
        //LocalDate d = datePicker1.getValue(); d.get
        while (!firstDayWeek.getDayOfWeek().toString().equals("MONDAY")) {
            firstDayWeek = firstDayWeek.minusDays(1);
        }
//        for (int i = 0; i < 7; i++) {
//            System.out.println(firstDayWeek.getDayOfMonth() + " " + firstDayWeek.getDayOfWeek());
//            firstDayWeek = firstDayWeek.plusDays(1);
//        }
        for (int i = 0; i < semana.getChildren().size(); i++) {
            Node dia = semana.getChildren().get(i);
            if (dia instanceof Text) {
                Text diaT = (Text) dia;
                if (!diaT.getText().equals("Hora")) {
                    String s = "";
                    switch (firstDayWeek.getDayOfWeek()) {
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
                    diesSemana.add(LocalDate.from(firstDayWeek));
                    ((Text) semana.getChildren().get(i)).setText(s + " " + firstDayWeek.getDayOfMonth());
                    //System.out.println(firstDayWeek.getDayOfMonth() + " " + firstDayWeek.getDayOfWeek());
                    firstDayWeek = firstDayWeek.plusDays(1);
                }
            }
        }
    }
  //////////////////////////////--------------------------------------------------------------------------------
}
 class HorariListCell extends ListCell<String> {

        //private ArrayLi view = new ImageView();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
////            int size = ((ListView) getParent().getParent().getParent().getParent()).getItems().size();
////            int size2 = getParent().getChildrenUnmodifiable().size();
////            if (size > size2) {
////                GridPane grid = ((GridPane) getParent().getParent().getParent().getParent().getParent());
////                for (int i = 0; i < grid.getChildren().size(); i++) {
////                    Node n = grid.getChildren().get(i);
////                    if (n instanceof ListView) {
////                        //((ListView)n).setC
////                        //((ListView) n).getFocusModel().focus(-1);
////                    }
////                }
//
//            }
            if (item == null || empty) {
                setText("");
                setStyle( "-fx-background-color; #9E9E9E; -fx-font-size: 12; -fx-text-fill: #ffffff;");
                 setOnMouseEntered(e -> {
                       // setStyle("-fx-background-color:#DCEDC8;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                    } );
                    setOnMouseExited(e -> {
                       // if(!clicked) {
                        //setStyle("-fx-background-color:#8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                      //  }
                    });
            } else {
                
                //((ListView) getParent().getParent().getParent().getParent()).getFocusModel().focus(-1);
//                view = scale(view, 20, 20, true);
//                view.setImage(item.getPhoto());
//                setGraphic(view);
//                Patient  p = item.getPatient();
//                Doctor d = item.getDoctor();
                if(item.equals("Free")) {
                    //boolean  clicked = false;
                    //if(!clicked) {
                     setStyle( "-fx-background-color: #8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;"); 
                    //}
                     setOnMouseEntered(e -> {
                        setStyle("-fx-background-color:#DCEDC8;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                    } );
                    setOnMouseExited(e -> {
                       // if(!clicked) {
                        setStyle("-fx-background-color:#8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                      //  }
                    });
                    ////////-----------PER A QUE SOLS HI HAJA 1 SELECCIONAT DDDD: ---------------------/////////
                    setOnMouseClicked(e -> {
                        //HEM DE DESELECCIONAR TOTS ELS DE LES ALTRES LLISTES I POSARLOS EL FORMAT ADECUAT AMB REFRES
                        GridPane grid = ((GridPane) getParent().getParent().getParent().getParent().getParent());
                        for (int i = 0; i < grid.getChildren().size(); i++) {
                            Node n = grid.getChildren().get(i);
                            if (n instanceof ListView) {
                               
                                if (!n.getId().equals(((ListView) getParent().getParent().getParent().getParent()).getId())) {
                                    //System.out.println("n " + (ListView) n);
                                    //System.out.println("clic " +((ListView) getParent().getParent().getParent().getParent()));
                                     ((ListView) n).refresh();
                                    //((ListView) n).getFocusModel().focus(-1);
                                    
                                }
                            }
                        }
                        ((ListView) getParent().getParent().getParent().getParent()).requestFocus();
                        //removeEventHandler(EventType.ROOT, eventHandler);
                        setStyle("-fx-border-color:#388E3C; -fx-background-color:#8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;" );
                        //System.out.println(((ListView) getParent().getParent().getParent().getParent()).getItems());
                        //QUAN CLICK, LI LLEVEM ELS HANDLERS DE HOVER
                        setOnMouseEntered(f -> {
                        });
                        setOnMouseExited(g -> {
                        });
                        //PROBLEMA: A LA LLISTA DE CHILDREnUNMODIFIABLE SOLS ESTAN ELS QUE ES MOSTREN, NO TOTES LES CEL·LES
                        //PER TANT, QUAN AGAFES UN INDEX GRAN, TOTES LES QUE ES MOSTREN, SON MENORS A IXE INDEX, I PER TANT,
                        //A TOTS SE'LS ACTUALITZEN ELS HANDLERS
                        int in = ((ListView) getParent().getParent().getParent().getParent()).getFocusModel().getFocusedIndex();
                       // System.out.println(in);
                        //A TOTS ELS DEMES Free's SELS HA DE POSAR ELS HANDLERS ADEQUATS
                        int size = Integer.min(((ListView) getParent().getParent().getParent().getParent()).getItems().size(), getParent().getChildrenUnmodifiable().size());
                        for (int i = 0; i < size; i++) {
                            //System.out.println(getParent().getParent().getParent().getChildrenUnmodifiable().size());
                            //System.out.println(getParent().getChildrenUnmodifiable().size());
                            //System.out.println(((ListView) getParent().getParent().getParent().getParent()).getItems().size());
                            if (i != in  &&((ListView) getParent().getParent().getParent().getParent()).getItems().get(i).equals("Free")) { //&&((ListView) getParent().getParent().getParent().getParent()).getItems().get(i).equals("Free")
                                //System.out.println("index " + in + " i " + i);
                                getParent().getChildrenUnmodifiable().get(i).setStyle("-fx-background-color:#8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                                getParent().getChildrenUnmodifiable().get(i).setOnMouseEntered(h -> {
                                    ((Node) h.getSource()).setStyle("-fx-background-color:#DCEDC8;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                                    
                                });
                                getParent().getChildrenUnmodifiable().get(i).setOnMouseExited(j -> {
                                    ((Node) j.getSource()).setStyle("-fx-background-color:#8BC34A; -fx-font-size: 12; -fx-text-fill: #ffffff;");
                                });
                                
                            }
                            
                        }
                        //clicked = true;
                    });
                    setText("Libre");
                }
                else if(item.equals("Not Available")) {
                    setStyle( "-fx-background-color: #FF5252; -fx-font-size: 12; -fx-text-fill: #ffffff;");// -fx-border-color: #ffffff; -fx-border-width: 1; -fx-border-radius: 10;");
                    setOnMouseEntered(e -> {
                        setStyle("-fx-background-color:#FFCDD2; -fx-font-size: 12; -fx-text-fill: #ffffff;");
                        //setFocused(true);
                        
//                       System.out.println(((ListView)getParent().getParent().getParent().getParent()).getFocusModel().getFocusedIndex());
// -------------------------((ListView)getParent().getParent().getParent().getParent()).requestFocus();
                        //System.out.println(((ListView)getParent().getParent().getParent().getParent()).isFocused());
                    } );
                    setOnMouseExited(e -> {
                        setStyle("-fx-background-color:#FF5252; -fx-font-size: 12; -fx-text-fill: #ffffff;");
                        //setFocused(true);
                        
                    });
                    setOnMouseClicked(e -> {});
                    setText("No disponible");
                }
                else { setStyle("-fx-background-color:#03A9F4; -fx-font-size: 12; -fx-text-fill: #ffffff;");
                        
                setOnMouseEntered(e -> {
                        setStyle("-fx-background-color:#B3E5FC; -fx-font-size: 12; -fx-text-fill: #ffffff;");
                        //System.out.println(((ListView)getParent().getParent().getParent().getParent()).isManaged());
                    } );
                    setOnMouseExited(e -> {
                        setStyle("-fx-background-color:#03A9F4; -fx-font-size: 12;-fx-text-fill: #ffffff;");
                    });
                    setOnMouseClicked(e -> {});
                    setText(item);
                }
//                if(p != null && d != null) {
//                setText(p.getName() + " " + p.getSurname() + " "+
//                        d.getName()+" " +d.getSurname() +" " + item.getAppointmentDateTime());
//                }

//getParent().getChildrenUnmodifiable().get(0).setStyle("-fx-background-color: #8BC34A;");
            }
        }
        
    }

