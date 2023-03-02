/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import DBAccess.ClinicDBAccess;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Appointment;
import model.Doctor;
import model.ExaminationRoom;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author guicado
 */
public class FXMLDocumentController implements Initializable {
    private final String CSS_COLOR_FONDO = "-fx-background-color:  #03A9F4";
    private final String CSS_COLOR_SELECCION = "-fx-background-color: #81d4fa";
    private ClinicDBAccess  clinicDBAccess;
    @FXML
    private BorderPane borderPane;
    private BorderPane solapament;
    @FXML
    private Tab tabPacientes;
    @FXML
    private Tab tabDoctors;
    @FXML
    private Tab tabCitas;
    @FXML
    private Tab tabCal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            // TODO
            //System.out.println("a");
            clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
            //System.out.println(clinicDBAccess);
            //---------------------Per a que no done NullPExc si no cliques el tab al iniciar
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendari.fxml"));
            
            Parent root = loader.load();
            ((CalendariController) loader.getController()).setClinicDBAccess(clinicDBAccess);
            tabCal.setContent(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //--------------------------------------------------------------------------------------------------------
    }   

    public ClinicDBAccess getClinic() {return clinicDBAccess;}
    private void ratoliNoSobreText(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setStyle(CSS_COLOR_FONDO);
    }

    private void ratoliSobreText(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setStyle(CSS_COLOR_SELECCION);
    }

    @FXML
    private void abrirListaPacientes(Event event) throws IOException {
        
        //System.out.println("a");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("llistaPacients.fxml"));

        Parent root =  loader.load();  

        //System.out.println(loader.getController().toString());

        ArrayList<Patient> p = clinicDBAccess.getPatients();
        ((LlistaPacientsController)loader.getController()).setLlistaPacients(p);
        ((LlistaPacientsController) loader.getController()).setBaseDades(clinicDBAccess);
        ((LlistaPacientsController)loader.getController()).posarLlistaObservable();
        //coloresDeTabADefecto();
        //tabPacientes.setStyle("-fx-background-color: #81d4fa");
        tabPacientes.setContent(root);
    }

    @FXML
    private void abrirListaDoctores(Event event) throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("llistaDoctors.fxml"));
        
        Parent root =  loader.load();  

        //System.out.println(loader.getController().toString());

        ArrayList<Doctor> d = clinicDBAccess.getDoctors();
        ArrayList<ExaminationRoom> e = clinicDBAccess.getExaminationRooms();
        //System.out.println("AAAAAAAAAAAAAAAAAAAAA"+e);
        ((LlistaDoctorsController)loader.getController()).setLlistaDoctors(d);
        ((LlistaDoctorsController)loader.getController()).setLlistaHabitacions(e);
        ((LlistaDoctorsController) loader.getController()).setBaseDades(clinicDBAccess);
        ((LlistaDoctorsController)loader.getController()).posarLlistaObservable();
        //coloresDeTabADefecto();
        //tabDoctors.setStyle("-fx-background-color: #81d4fa");
        tabDoctors.setContent(root);
    }

    @FXML
    private void abrirListaCitas(Event event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("llistaCites.fxml"));

        Parent root = loader.load();
        ArrayList<Patient> p = clinicDBAccess.getPatients();
        ArrayList<Doctor> d = clinicDBAccess.getDoctors();
        ArrayList<Appointment> c = clinicDBAccess.getAppointments();
        ((LlistaCitesController) loader.getController()).setLlistaPacients(p);
        ((LlistaCitesController) loader.getController()).posarLlistaObservablePacients();
        ((LlistaCitesController) loader.getController()).setLlistaDoctors(d);
        ((LlistaCitesController) loader.getController()).posarLlistaObservableDoctors();
        ((LlistaCitesController) loader.getController()).setLlistaCitas(c);
        ((LlistaCitesController) loader.getController()).setBaseDades(clinicDBAccess);
        ((LlistaCitesController) loader.getController()).posarLlistaObservableCites();
        //coloresDeTabADefecto();
        //tabCitas.setStyle("-fx-background-color: #81d4fa");
        tabCitas.setContent(root);
    }

    @FXML
    private void abrirCalendar(Event event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendari.fxml"));

            Parent root = loader.load();
            ((CalendariController) loader.getController()).setClinicDBAccess(clinicDBAccess);
            //coloresDeTabADefecto();
            //tabCal.setStyle("-fx-background-color: #81d4fa");
        
            tabCal.setContent(root);
        } catch(NullPointerException e){System.out.println("Error al cargar inicialmente");}
    }
    
    private void coloresDeTabADefecto(){
        tabCitas.setStyle("-fx-background-color: #0288D1");
        tabCal.setStyle("-fx-background-color: #0288D1");
        tabDoctors.setStyle("-fx-background-color: #0288D1");
        tabPacientes.setStyle("-fx-background-color: #0288D1");
    }
}
