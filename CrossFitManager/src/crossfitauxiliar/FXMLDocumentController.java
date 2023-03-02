/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitauxiliar;

import accesoBD.AccesoBD;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Grupo;
import modelo.Gym;
import modelo.Sesion;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private Text cronometroNumber;
    Task<Void> cronometro;
    boolean esPauseButton = false;
    Thread threadCronometro;
    Thread listener;
    @FXML
    private ImageView pauseAndPlayImage;
    private Sesion sesion;
    private Grupo grupo;
    private boolean pause = false;
    private float segundos;
    private double cs;
    private double antiCs;
    private int seg;
    private int min;
    private int secondStop;
    private Scene anteriorScene;
    private boolean donaPlayPerPrimeraVegada = true;
    private boolean primeraSesio = true; //Per a guardar la data on comensa la primera
    private boolean haAvanzado = false;
    private boolean sesioEnCurs = false;
    long duracionSesionIni;
    long duracionSesionFin;
    @FXML
    private HBox hBoxCircuit;
    @FXML
    private HBox hBoxExercicis;
    @FXML
    private Canvas timeCanvas;
    private GraphicsContext c;
    private double inicioPunto;
    private double radi;
    private double antiSegundos;
    @FXML
    private Text decimas;
    @FXML
    private ImageView arrereImageView;
    @FXML
    private ImageView reestartView;
    @FXML
    private Label circuito;
    @FXML
    private Label ejercicio;
    @FXML
    private ImageView canviarInterval;
    @FXML
    private StackPane stackForward;
    @FXML
    private VBox vBForward;
    @FXML
    private VBox vBoxRset;
    @FXML
    private VBox fOutVB;
    @FXML
    private VBox rOutVB;
    @FXML
    private StackPane stackPanePlay;
    @FXML
    private VBox pOutVB;
    @FXML
    private VBox mainContainerVB;
    @FXML
    private Circle cercleIn;
    @FXML
    private Circle circleOut;
    private boolean iterator = true;
    @FXML
    private Label textoInfo;
    @FXML
    private HBox mainContainerHB;
    @FXML
    private VBox vBoxButtons;
    private boolean nearBy = true;
    private int timeCount = 0;
    @FXML
    private BorderPane borderMain;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println(cronometroNumber);
//        reestartView.setVisible(false);
//        canviarInterval.setVisible(false);
    }    


    public void setAnteriorScene(Scene s){
        anteriorScene = s;
    }

    public static double fDeX(double x, double r) {
        return Math.sqrt(r*r-x*x);
    }
    public void cronometrarTemps(int n, boolean descanso) {
        cs = 0.0;
        antiCs=0.0;
        //inicioPunto = 0.0;
        antiSegundos = 0.0;
        radi = 300;
        segundos = n;
        min = (int) segundos / 60;
        seg = (int) segundos % 60;
        c=timeCanvas.getGraphicsContext2D();
        
//        c.setStroke(Paint.valueOf("#ffffff"));
//        for (inicioPunto = -radi; inicioPunto <= radi; inicioPunto += 0.1) {
//            c.strokeLine(inicioPunto + 150, fDeX(inicioPunto, radi) + 150, inicioPunto + 0.1 + 150, fDeX(inicioPunto, radi) + 0.1 + 150);
//        }
//        for (inicioPunto = -radi; inicioPunto <= radi; inicioPunto += 0.1) {
//            c.strokeLine(inicioPunto + 150, -fDeX(inicioPunto, radi) + 150, inicioPunto + 0.1 + 150, -fDeX(inicioPunto, radi) + 0.1 + 150);
//        }
        while (segundos >= 0  && !haAvanzado) {
            
            if(segundos > 5) {
            c.setStroke(Paint.valueOf("#ffffff"));
            }
            if(segundos <= 5&& cs == 9 && !pause){
                
                //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                new Sonido(getClass().getResource("sonidoPitido.mp3").toString()).start();
            }
            
            if (segundos <= 5 && cs % 2 == 0 && !pause) {
                if (iterator) {
                    cronometroNumber.setFill(Paint.valueOf("#fafafa"));
                    decimas.setFill(Paint.valueOf("#fafafa"));
                    cercleIn.setFill(Paint.valueOf("#212121"));

                    if (descanso) {
                        c.setStroke(Paint.valueOf("#00e5ff"));
                    } else {
                        c.setStroke(Paint.valueOf("#f44336"));
                    }
                } else {
                    cronometroNumber.setFill(Paint.valueOf("#212121"));
                    decimas.setFill(Paint.valueOf("#212121"));
                    cercleIn.setFill(Paint.valueOf("#fafafa"));

                    if (descanso) {
                        c.setStroke(Paint.valueOf("#00e5ff"));
                    } else {
                        c.setStroke(Paint.valueOf("#f44336"));
                    }
                }
                iterator = !iterator;
            }

            if (!pause) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        cronometroNumber.setText(min + ":" + (seg < 10 ? "0" + (seg) : (seg)));//+":"+ (cs < 10 ? ("0"+cs) : cs));
                        decimas.setText("0"+(int)cs);
                        if (cs == 0) {
                            cs = 9;
                            seg--;
                            //min--;
                        if(seg <= 0) {
                            if(min > 0) {
                                seg = 59;
                                min--;
                            }
                        }    
                        } else {
                            cs--;
                        }
                        segundos-= 0.1;
                        //c.strokeLine(0, 0, 1, 1);
//                        for(inicioPunto=-radi; inicioPunto <= 0; inicioPunto+=0.5) {
//                            c.strokeLine(inicioPunto + 150, fDeX(inicioPunto, radi)+150, inicioPunto+0.5+150, fDeX(inicioPunto, radi)+0.5+150);
//                        }
//                        for(inicioPunto=-radi; inicioPunto <= radi; inicioPunto+=0.1) {
//                            c.strokeLine(inicioPunto + 150, -fDeX(inicioPunto, radi)+150, inicioPunto+0.1+150, -fDeX(inicioPunto, radi)+0.1+150);
//                        }

                       // System.out.println(segundos);
                        
                    }
                    
                });
                //float stop = (float) antiSegundos + 1;
                //while (antiSegundos <= stop) {
                    Platform.runLater(new Runnable() {
                        final double seg = n*10;
                        
                        @Override
                        public void run() {

                            if (antiCs<= seg) {// / 4) { //antiSegundos < n/4
                                for (double alpha = Math.PI / 2+0.05; alpha <= (antiCs * 2 * Math.PI) / seg + (Math.PI / 2+0.1); alpha += (2 * Math.PI+0.05) / seg) {
                                    for (double a = Math.PI / 2+0.05; a <= alpha; a += 0.005) {
                                        c.strokeLine(radi * Math.cos(a) + 300, radi * Math.sin(a) + 300, (radi -15) * Math.cos(a) + 300, (radi -15) * Math.sin(a) + 300);
                                    }
                                }

                            }
                            antiCs += 1;

                        }

                    });
                   
                //}
            }
         
            try {
                for(int i = 0; i < 100; i++) {
                    if(!pause) {secondStop=i;i=secondStop;}
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
               // System.out.println("pausado");
            }

        }
        c.clearRect(0, 0, 600, 600);
        haAvanzado = false;
    }

    public void setSesionAndGrupo(Sesion s, Grupo g) {
        sesion = s;
        grupo = g;
    }
    
    private void cronometroPrincipal(){
        String colorExercicis = "#424242";
        String colorExActual = "#ffffff";
        String cExerciciFet = "#bdbdbd";
        String cSeparador = "#cfd8dc";
        String cCircuit = "";
        String cCActual = "";
        String cCalentament = "";
        Runnable task = new Runnable(){

            public void run(){
                
                if(primeraSesio) {
                    sesion.setFecha(LocalDateTime.now());
                    primeraSesio = false;
                    duracionSesionIni = System.currentTimeMillis();
                };
                SesionTipo sT = sesion.getTipo();
                int numRepeticionesCircuito = sT.getNum_circuitos();
                int numEjercicios = sT.getNum_ejercicios();
                int tEjercicios = sT.getT_ejercicio();
                int  tCalentamiento = sT.getT_calentamiento();
                int  tDescansoEjercicio = sT.getD_ejercicio();
                int  tDescansoCircuito = sT.getD_circuito();
               // System.out.println(numRepeticionesCircuito + " " + numEjercicios);
                
                for(int i = 0; i < numRepeticionesCircuito; i++) {
                    Platform.runLater(new Runnable() {
                        final int anchoC =  200 / numRepeticionesCircuito;
                        @Override
                        public void run() {
                            Rectangle r = new Rectangle(anchoC, 6, Paint.valueOf("#0288d1"));//#ff9800"));
                            //hBoxCircuit.getChildren().add(r);
                        }

                    });
                }
                Platform.runLater(new Runnable() {//-----------AFEGIR CALENTAMENT
                        @Override
                        public void run() {
                            Rectangle r = new Rectangle(40, 6, Paint.valueOf(colorExActual));
                            hBoxExercicis.getChildren().add(r);
                            r= new Rectangle(1,10,Paint.valueOf(cSeparador));
                            hBoxExercicis.getChildren().add(r);
                            //r = new Rectangle(20, 10, Paint.valueOf("#283593"));
                            //hBoxExercicis.getChildren().add(r);
                            
                        }

                    });
                for(int i = 0; i < numEjercicios; i++) {//-----------AFEGIR LA RESTA
                    final int nE = numEjercicios;
                    final int anchoE =  200 / numEjercicios;
                    final int anchoD =  200 / numEjercicios;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Rectangle r = new Rectangle(anchoE, 6, Paint.valueOf(colorExercicis));
                            hBoxExercicis.getChildren().add(r);
                            r= new Rectangle(1,10,Paint.valueOf(cSeparador));
                            hBoxExercicis.getChildren().add(r);
                            r = new Rectangle(anchoD, 6, Paint.valueOf(colorExercicis));
                            hBoxExercicis.getChildren().add(r);
                            r= new Rectangle(1,10,Paint.valueOf(cSeparador));
                            hBoxExercicis.getChildren().add(r);
                            
                        }

                    });
                    
                }
                if(hBoxCircuit.getChildren().size()>0) {
                ((Rectangle)hBoxCircuit.getChildren().get(0)).setFill(Paint.valueOf("#0288d1"));//#ffeb3b"));
                }
                //Calentamiento
                mostrarTextoInfo("Calentamiento","","");
                cronometrarTemps(tCalentamiento, true);
                //Descanso calentamiento? Se pone o no?
                ((Rectangle)hBoxExercicis.getChildren().get(0)).setFill(Paint.valueOf(cExerciciFet));
               
                for(int i = 0; i < numRepeticionesCircuito; i++){
                    int rect = 2;
                    for(int k = 2; k < hBoxExercicis.getChildren().size(); k+=2) {
                        ((Rectangle)hBoxExercicis.getChildren().get(k)).setFill(Paint.valueOf(colorExercicis));
                    }
                    for(int j = 0; j < numEjercicios; j++){
                        //Ejercicio j
                        if(j!=0){
                            ((Rectangle)hBoxExercicis.getChildren().get(rect-2)).setFill(Paint.valueOf(cExerciciFet));
                        }
                        ((Rectangle)hBoxExercicis.getChildren().get(rect)).setFill(Paint.valueOf(colorExActual)); rect+=2;
                        mostrarTextoInfo("Circuito " +(i+1)+"/"+numRepeticionesCircuito+ " - Ejercicio " +(j+1)+"/"+numEjercicios,"Circuito  "+(i+1),"Descanso "+(j+1));
                        //circuito.setText("Circuito   " +(i+1));
                        //ejercicio.setText("Ejercicio " +(j+1));
                        if(pause){
                                mostrarSiguienteValor(tEjercicios);
                        }
                        //--------------CERCLES----------------
                        circleOut.setFill(Paint.valueOf("#ffab91"));
                        cercleIn.setFill(Paint.valueOf("#FF9800"));
                        
                        //---------------FONS-----------------
                        borderMain.setStyle("-fx-background-color:#ff3d00; -fx-border-color:#ff1744; -fx-border-width:25;");
                        //mainContainerVB.setStyle("-fx-background-color:#ff3d00;");
                        
                        //--------------LLETRES----------------
                        cronometroNumber.setFill(Paint.valueOf("#000000"));
                        decimas.setFill(Paint.valueOf("#000000"));

                        cronometrarTemps(tEjercicios, false);
                        
                        
                        //Descanso j
                        if(j < numEjercicios - 1){
                            ((Rectangle)hBoxExercicis.getChildren().get(rect-2)).setFill(Paint.valueOf(cExerciciFet));//posar gris
                            ((Rectangle)hBoxExercicis.getChildren().get(rect)).setFill(Paint.valueOf(colorExActual)); rect+=2;
                            mostrarTextoInfo("Circuito " +(i+1)+"/"+numRepeticionesCircuito+ " - Descanso " +(j+1)+"/"+numEjercicios,"Circuito  "+(i+1),"Descanso "+(j+1));
                            if(pause){
                                mostrarSiguienteValor(tDescansoEjercicio);
                            }//#FF9800 #ffab91
                            //#03a9f4 
                            circleOut.setFill(Paint.valueOf("#81d4fa"));
                            cercleIn.setFill(Paint.valueOf("#03a9f4"));
                            
                            borderMain.setStyle("-fx-background-color:#0288D1");
                            //mainContainerVB.setStyle("-fx-background-color:#0288D1");

                            cronometroNumber.setFill(Paint.valueOf("#000000"));
                            decimas.setFill(Paint.valueOf("#000000"));
                            
                            cronometrarTemps(tDescansoEjercicio, true);
                            
                        }
                    }
                    if(i < numRepeticionesCircuito -1){
                        ((Rectangle) hBoxExercicis.getChildren().get(rect - 2)).setFill(Paint.valueOf(cExerciciFet));//posar gris
                        ((Rectangle) hBoxExercicis.getChildren().get(rect)).setFill(Paint.valueOf(colorExActual));
                        rect+=2;
                        //Descanso para el circuito i
                        mostrarTextoInfo("Descanso para el circuito "+(i+1+1), "Circuito "+(i+1+1), "Descanso previo");
                        if(pause){
                                mostrarSiguienteValor(tDescansoCircuito);
                            }
                        circleOut.setFill(Paint.valueOf("#81d4fa"));
                        cercleIn.setFill(Paint.valueOf("#03a9f4"));
                        
                        borderMain.setStyle("-fx-background-color:#0288D1");
                        //mainContainerVB.setStyle("-fx-background-color:#0288D1");

                        cronometroNumber.setFill(Paint.valueOf("#000000"));
                        decimas.setFill(Paint.valueOf("#000000"));

                        cronometrarTemps(tDescansoCircuito, true);
                        
                        ((Rectangle) hBoxExercicis.getChildren().get(rect - 2)).setFill(Paint.valueOf(cExerciciFet));//posar gris
                        
                            //((Rectangle)hBoxCircuit.getChildren().get(i)).setFill(Paint.valueOf("#0288d1"));
                        
                            //((Rectangle)hBoxCircuit.getChildren().get(i+1)).setFill(Paint.valueOf("#0288d1"));
                    }
                }
                for(int k = 0; k < hBoxExercicis.getChildren().size(); k+=2) {
                        ((Rectangle)hBoxExercicis.getChildren().get(k)).setFill(Paint.valueOf(colorExercicis));
                    }
                c.clearRect(0, 0, 600, 600);
                mostrarTextoInfo("Sesión finalizada", "","");
                decimas.setText("00");
                canviarInterval.setDisable(true);
                reestartView.setDisable(true);
                pauseAndPlayImage.setDisable(true);
                rOutVB.getStylesheets().clear();
                fOutVB.getStylesheets().clear();
                pOutVB.getStylesheets().clear();
                rOutVB.getStylesheets().add("/models/crono/buttonsDissable.css");
                fOutVB.getStylesheets().add("models/crono/buttonsDissable.css");
                pOutVB.getStylesheets().add("models/crono/dissablePlay.css");
                duracionSesionFin = System.currentTimeMillis();
                sesion.setDuracion(Duration.ofMillis(duracionSesionFin - duracionSesionIni));
                grupo.getSesiones().add(sesion);
                sesioEnCurs = false;
            }
        };
        
        threadCronometro = new Thread(task);
        threadCronometro.setDaemon(true);
        threadCronometro.start();
    }
    
    private void mostrarTextoInfo(String texto, String circu, String ejer){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                textoInfo.setText(texto);
                circuito.setText(circu);
                ejercicio.setText(ejer);
                
            }
    
        });
    }
    
    private void mostrarSiguienteValor (int sec){
        cronometroNumber.setText(sec/60 + ":" + (sec%60 < 10 ? "0" + (sec%60) : (sec%60)));//+":"+ (cs < 10 ? ("0"+cs) : cs));
        decimas.setText("00");
    }
    
    @FXML
    private void mouseExitedButton(MouseEvent event) {
       // System.out.println("HE IXIT 0000000OOOOO");
    }

    @FXML
    private void mouseEnteredButton(MouseEvent event) {
       // System.out.println("HE ENTRAT wiiiiIIIIIIIII");
    }

    @FXML
    private void arrere(MouseEvent event) {
        if (threadCronometro != null) {
            pause = true;
        }
        if(sesioEnCurs){
            if(mostrarDialog() == 1){
                ((Stage)arrereImageView.getScene().getWindow()).setScene(anteriorScene);
            }
            else{
                if(esPauseButton){ pause = false;}
            }
        }
        else{
            ((Stage)arrereImageView.getScene().getWindow()).setScene(anteriorScene);
        }
    }

    @FXML
    private void pauseOPlay(MouseEvent event) {
        if(esPauseButton){
            //threadCronometro.suspend();
            pause = true;
            pauseAndPlayImage.setImage(new Image("resources/play48dp.png"));
        }
        else{
            //threadCronometro.resume();
            pause = false;
            pauseAndPlayImage.setImage(new Image("resources/pause48dp.png"));
        }
        esPauseButton = !esPauseButton;
        if(donaPlayPerPrimeraVegada){
            donaPlayPerPrimeraVegada = false;
                rOutVB.getStylesheets().clear();
                fOutVB.getStylesheets().clear();
                rOutVB.getStylesheets().add("models/crono/buttonsEnable.css");
                fOutVB.getStylesheets().add("models/crono/buttonsEnable.css");
            canviarInterval.setDisable(false);
            reestartView.setDisable(false);
            sesioEnCurs = true;
            cronometroPrincipal();
            inactiveButtonsListener();
        }
    }

    @FXML
    private void stop(MouseEvent event) {
        //Guarrada
        threadCronometro.stop();
        //System.out.println("parau");
        c.clearRect(0, 0, 600, 600);
        cronometroNumber.setText("0:00");
        textoInfo.setText("");
        decimas.setText("00");
        pauseAndPlayImage.setImage(new Image("resources/play48dp.png"));
        esPauseButton = false;
        donaPlayPerPrimeraVegada = true;
        canviarInterval.setDisable(true);
        reestartView.setDisable(true);
        rOutVB.getStylesheets().clear();
        fOutVB.getStylesheets().clear();
        rOutVB.getStylesheets().add("models/crono/buttonsDissable.css");
        fOutVB.getStylesheets().add("models/crono/buttonsDissable.css");
        hBoxCircuit.getChildren().clear();
        hBoxExercicis.getChildren().clear();
        sesioEnCurs = false;
    }
    
    private int mostrarDialog(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setHeaderText("Si abandonas en medio de la sesión perderás su progreso.");
        alert.setContentText("¿Seguro que quieres abandonar la sesión?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            return 1;
        } else {
            return 0;
        }
    }
    
    @FXML
    private void canviarInterval(MouseEvent event) {
        haAvanzado = true;
        
    }
    private void inactiveButtonsListener() {
        timeCount = 0;
        Runnable tosk = new Runnable() {

            public void run() {
                while(true){
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        //System.out.println("hahahahahhahahaahahaha");
                        timeCount++;
                        //hBoxCircuit.getChildren().add(r);
                        if(timeCount > 3) {
                            nearBy = false;
                        }
                    }

                });
                try {
                    for (int i = 0; i < 100; i++) {
                        if (!nearBy) {
                            final double op  = vBoxButtons.getOpacity() - 0.002;
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    if(op>0) {
                                    vBoxButtons.setOpacity(op);
                                    }
                                }

                            });

                        }
                        Thread.sleep(10);
                    }
                    
                } catch (InterruptedException e) {
                   // System.out.println("pausado");
                }
                }
            }
        };
       listener= new Thread(tosk);
       listener.setDaemon(true);
       listener.start();

    }

    @FXML
    private void mouseNearBy(MouseEvent event) {
        nearBy = true;
        timeCount = 0;
        vBoxButtons.setOpacity(1);
    }
}

