/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import DBAccess.ClinicDBAccess;
import com.sun.javafx.scene.control.skin.ChoiceBoxSkin;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
//import com.sun.xml.internal.ws.wsdl.writer.document.ParamType;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.Doctor;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author guicado
 */
public class CalendariController implements Initializable {
    @FXML
    private DatePicker date_picker;
    ObservableList<String> images=FXCollections.observableArrayList("sunny.png","kweather.png","cloud.png","sun_rain.png","showers.png");
    private ClinicDBAccess  clinicDBAccess;
    private ArrayList<String> colors = new ArrayList<>();
    private Label bugSolver;
    private DatePicker a;
   // private Node a;
   private ComboBox<String> citesMostrar = new ComboBox<>();
    @FXML
    private VBox pane;
    public void setClinicDBAccess(ClinicDBAccess clinicDBAccess) {
        this.clinicDBAccess = clinicDBAccess;
    }
    //int cont = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        colors.add("#00bcd4"); colors.add("#03a9f4"); colors.add("#673ab7");colors.add("#f44336");colors.add("#ff5722");colors.add("#e91e63");
        colors.add("#1b5e20");colors.add("#607d8b");colors.add("#795548");colors.add("#ffc107");colors.add("#ffffff");colors.add("#3f51b5");
        DatePicker calendari = new DatePicker(LocalDate.now());
        
//        calendari.addListener((obs, old, newV) ->{
//            
//        });
        calendari.setShowWeekNumbers(false);
        a = calendari;
        //calendari.setOnAction(value);
        //a.dayCellFactoryProperty()
        
       calendari.setDayCellFactory(new Callback<DatePicker,DateCell>(){
           
           @Override
           public DateCell call(DatePicker param) {
             
             return new DateCell(){
             @Override
             public void updateItem(LocalDate item, boolean empty){
             super.updateItem(item, empty);
             
             if (empty || item == null) {
                 setText(null);
                 setGraphic(null);
                
                 } else {
                //DateCell n = (DateCell) getParent().getChildrenUnmodifiable().get(0);
                StackPane cell_pane = new StackPane();
               
                Random r=new Random();
               
                ImageView image_view=new ImageView("file:"+images.get(r.nextInt(images.size())));
               

                 //param.getValue().getMonth().
                
//                 setDisable(true);
//                 System.out.println(item.getMonth().getValue());
//                 if (item.getMonth().getValue() == param.getValue().getMonth().getValue()) {
//                     setDisable(false);
//                 }
                
                Label label=new Label();
                label.setText(getText());
                label.setTextFill(Color.BLACK);
                label.setStyle("-fx-font: 24 arial;");
                
                StackPane.setAlignment(image_view, Pos.CENTER_RIGHT);
                StackPane.setMargin(image_view,new javafx.geometry.Insets(0,40, 120,90));

                cell_pane.getChildren().addAll(label,image_view);

                //item.get(ChronoField.DAY_OF_WEEK) returns an int from 1 to 7(Monday-Sunday)
                //DayOfWeek.of(int) return the name of the day of week. type ENUM.

                DayOfWeek day=DayOfWeek.of(item.get(ChronoField.DAY_OF_WEEK));
                 //System.out.println("Day: "+day.getValue());
                //if(day.getValue() > cont){ setStyle("-fx-background-color:#90a4ae;"); }
                 if (item.atStartOfDay().equals(LocalDate.now().atStartOfDay())) {
                     setStyle("-fx-background-color:#ffa726;");//saturday cells blue background
                     setOnMouseEntered(e -> {
                         setStyle("-fx-background-color:#ffcc80;");
                         date_picker.getScene().setCursor(Cursor.OPEN_HAND);
                         //obrirLlistaCites(e);
                     });
                     setOnMouseExited(e -> {
                         //setGraphic(cell_pane);
                         setStyle("-fx-background-color:#ffa726;");
                         date_picker.getScene().setCursor(Cursor.DEFAULT);
                     });
                     
                 } else if (day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)) {
                     setStyle("-fx-background-color:#4fc3f7;");//saturday cells blue background
                     setOnMouseEntered(e -> {
                         setStyle("-fx-background-color:#bbdefb");
                         date_picker.getScene().setCursor(Cursor.HAND);
                         //obrirLlistaCites(e);
                     });
                     setOnMouseExited(e -> {
                         //setGraphic(cell_pane);
                         setStyle("-fx-background-color:#4fc3f7");
                         date_picker.getScene().setCursor(Cursor.DEFAULT);
                     });
                     
                 } else {
                     setStyle("-fx-background-color:#b3e5fc;"); //weekdays grey
                     setOnMouseEntered(e -> {
                         setStyle("-fx-background-color:#e1f5fe");
                         date_picker.getScene().setCursor(Cursor.HAND);
                         //obrirLlistaCites(e);
                     });
                     setOnMouseExited(e -> {
                         date_picker.getScene().setCursor(Cursor.DEFAULT);
                         //setGraphic(cell_pane);
                         setStyle("-fx-background-color:#b3e5fc");
                     });

                 }
                
                 setOnMousePressed(e -> {

                     bugSolver = new Label();
                     bugSolver.setText(""+item.getDayOfMonth());
                     //bugSolver.setTextFill(Color.valueOf("#ffffff"));
                     bugSolver.setStyle("-fx-font: 24 arial;");
                     date_picker.getScene().setCursor(Cursor.DEFAULT);
                     obrirLlistaCites(e);
                     
                     setStyle("-fx-background-color: #81d4fa;");//+bugSolver+";"); //#FF9800 -fx-background-color: #ffb74d;
                 });
                
                setGraphic(cell_pane);
                setText("");//dont show any text in the cells
                
                 }
               
            }
             };
             
       };
       });
       DatePickerSkin skin = new DatePickerSkin(calendari);
       
       Node node = skin.getPopupContent();
       pane.getChildren().add(node);
       
//        System.out.println("1");
//        mostrarCalendari();

       
    }    
    
    public void mostrarCalendari(){
  /************ VERSION NO THREAD BACKGROUND *****************/        
//        System.out.println("2");
////        date_picker.show();
//        new Thread(){
//            @Override
//            public void run(){ 
//                //while(true){
//                    try{
//                    System.out.println("3");
//                    Thread.sleep(1000);
//                    date_picker.show();
//                    System.out.println("4");
//                    }catch(InterruptedException e){}
//                //}
//            }
//        }.start();
 /************ VERSION NO THREAD *****************/  
/************ VERSION THREAD *****************/  
        // Create a Runnable
//        Runnable task = new Runnable()
//        {
//            public void run()
//            {
//                runTask();
//            }
//        };
// 
//        // Run the task in a background thread
//        Thread backgroundThread = new Thread(task);
//        // Terminate the running thread if the application exits
//        backgroundThread.setDaemon(true);
//        // Start the thread
//        backgroundThread.start();
//    }
//    
//    
//     public void runTask() { 
//             
//            while (true) {
//                
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Insert code
//                        if(date_picker!=null){
//                            date_picker.show();
//                        }
//                        }
//                    }
//                );
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
     /************ VERSION THREAD *****************/     
    }


   
    @FXML
    private void obrirLlistaCites(MouseEvent event) {
        String fechaDia = ((Cell)event.getSource()).getItem().toString(); // yyyy-mm-dd
//        int year = Integer.parseInt(fechaDia.substring(0, 4));
//        int month = Integer.parseInt(fechaDia.substring(5,7));
//        int day = Integer.parseInt(fechaDia.substring(8,10));
        //LocalDate ld = LocalDate.of(year, month, day);
        //System.out.println(clinicDBAccess);
        //LocalDate dia = ((DatePicker)event.getSource()).getValue();
        ArrayList <Appointment> citesDelDia = new ArrayList<>();
        ArrayList<Appointment> llista = clinicDBAccess.getAppointments();
        
        for(int i = 0 ; i< llista.size(); i++){
//            if(llista.get(i).getAppointmentDateTime().equals(ld)){
//                System.out.println("bien");
//            }
            if(llista.get(i) != null && llista.get(i).getAppointmentDateTime() != null){
                LocalDateTime dataCita = llista.get(i).getAppointmentDateTime();
                String fechaDiaCita = dataCita.toString();
                //System.out.println(fechaDia);
                fechaDiaCita = fechaDiaCita.substring(0,10);
                //System.out.println(fechaDia);
                if(fechaDia.equals(fechaDiaCita)){
                    citesDelDia.add(llista.get(i));
                    
                }
                
                //System.out.println("HORA " + llista.get(i).getAppointmentDateTime());
            }
        }
        ArrayList<String> cites = new ArrayList<>();
        boolean hiCita = false; boolean hemPosat = false; boolean borrar = false;
        for(int i = 8*60; i < 20*60; i+=15) {
            int hora = i/60; int min = i % 60;
            String horaS = (hora < 10 ? "0":"") + hora + ":" +( min < 10 ? "0":"") + min;
            //System.out.println(horaS);
            String afegir = horaS;
            if(!hemPosat) { hemPosat = true; }
            cites.add(afegir);
            for(int j = 0; j < citesDelDia.size(); j++) {
                String horaCita = citesDelDia.get(j).getAppointmentDateTime().getHour() + ":"
                        +citesDelDia.get(j).getAppointmentDateTime().getMinute();
                //System.out.print("HoraCita" +horaCita);
                
                if(horaCita.equals(horaS)) {
                    //if(!hiCita && cites.size() > 0) { cites.remove(cites.size()-1);}
                    Doctor d = citesDelDia.get(j).getDoctor();
                    String dYS =" " + d.getName() + " " + d.getSurname() + " - "+ "Sala " + d.getExaminationRoom().getIdentNumber();
                    cites.add(afegir + dYS);
                    //hiCita = true;
                    hemPosat = false;
                    borrar = false;
                }

            }
            if (borrar) {
                //System.out.println(afegir);
                cites.remove(afegir);
            }
            if (hemPosat) {
                borrar = true;
            }
        }
        cites.add("20:00");
        //System.out.println(cites);
       
        //ComboBox <String> 
        citesMostrar = new ComboBox<>();
        citesMostrar.setItems(FXCollections.observableArrayList(cites));
        citesMostrar.setMaxWidth(120);
        citesMostrar.setMinHeight(120);
        citesMostrar.setOpacity(0);
        citesMostrar.setCellFactory(f ->(new CitaListCell()));

        StackPane s = new StackPane();
        s.setAlignment(Pos.CENTER);
        
        s.getChildren().add(bugSolver);
        s.getChildren().add(citesMostrar);

        ((Cell)event.getSource()).setGraphic(s);
        citesMostrar.show();
        
        
        //System.out.println(citesDelDia);
    }

//    @FXML
//    private void debug(ActionEvent event) {
//        System.out.println(a.getValue());
//    }

}

class CitaListCell extends ListCell<String> {
private ArrayList<String> colors = new ArrayList<>();
//private boolean jaRandom = false;
    
    //private ImageView view = new ImageView();
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
         colors.add("#00bcd4");  colors.add("#673ab7");colors.add("#f44336");colors.add("#ff5722");colors.add("#4caf50");
                colors.add("#009688"); colors.add("#607d8b");colors.add("#795548");colors.add("#ffa726");colors.add("#3f51b5"); colors.add("#e91e63");
        if (item == null || empty) {
            setText("");
            //setStyle( "-fx-background-color: default;");
        } else {
            if(item.length()<6) {
                setStyle("-fx-background-color: #9ccc65; -fx-text-fill: #ffffff;-fx-font-size: 14; -fx-border-width: 1; -fx-border-color: #ffffff"); //#CDDC39
                setText(item + "---Sin citas---");
                
            }
            //else if (empty) {}
            else{
               int nDoc = ClinicDBAccess.getSingletonClinicDBAccess().getDoctors().size();
               //int rand = (int) (Math.random()*colors.size());
                //System.out.println(rand);
                //if(!jaRandom)
                //{for(int i = 0; i < rand; i++) {String r = colors.remove(0); colors.add(r); } jaRandom=true;}
               for(int i = 0; i < nDoc; i++){
                   Doctor d = ClinicDBAccess.getSingletonClinicDBAccess().getDoctors().get(i);
                   
                   if(item.contains(d.getName() + " " + d.getSurname())) {
                       setStyle("-fx-background-color:"+ colors.get(i)+"; -fx-text-fill: #ffffff;-fx-font-size: 18;");
                   }
               }
             // TableView j; j.getItems().clear();
                setText(item);
            }
        }

    }
}
