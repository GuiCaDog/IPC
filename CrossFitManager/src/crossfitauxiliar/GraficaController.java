/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitauxiliar;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Grupo;
import modelo.Sesion;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class GraficaController implements Initializable {
    @FXML
    private Label grupoName;
    private Scene anteriorScene;
    private Grupo grup;
    private ArrayList<Sesion> sesiones;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private ChoiceBox<Integer> numberSesions;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberSesions.valueProperty().addListener(e -> {
            if(sesiones != null){
               // System.out.println("error : "+sesiones);
                crearGrafica(sesiones.size() - numberSesions.getValue());
            }
        });
    }    

    @FXML
    private void volverAnterior(ActionEvent event) {
        ((Stage)grupoName.getScene().getWindow()).setScene(anteriorScene);
    }
    
    public void setAnteriorScene(Scene s){
        anteriorScene = s;
    }
    
    public void setGrupo(Grupo g){
       grup = g;
       grupoName.setText(g.getCodigo());
       rellenarChoiceBox(g.getSesiones().size());
       crearGrafica(0);
    }
    private void crearGrafica(int sesionesSaltadas){
        lineChart.getData().remove(0, lineChart.getData().size());
        xAxis.setLabel("Sesiones");
        yAxis.setLabel("Tiempo");
        sesiones = grup.getSesiones();
        //System.out.println(sesiones);
        //TDescanso
        XYChart.Series<String,Number> series = new XYChart.Series();
        for (int i = 0 + sesionesSaltadas; i < sesiones.size(); i++){
            SesionTipo sT = sesiones.get(i).getTipo();
            int tiempoDescansado = (sT.getNum_circuitos() - 1) * sT.getD_circuito() + sT.getNum_circuitos() * (sT.getNum_ejercicios() - 1) * sT.getD_ejercicio();
            String fechaSimple = sesiones.get(i).getFecha().toString().substring(0, sesiones.get(i).getFecha().toString().indexOf('T'));
            series.getData().add(new XYChart.Data<>(sesiones.get(i).getTipo().getCodigo() +"("+i+")\n" + fechaSimple, tiempoDescansado));
        }
        series.setName("Tiempo de descanso");
        lineChart.getData().add(series);
        //Tejercicios + TCalentamiento
        series = new XYChart.Series();
        for (int i = 0 + sesionesSaltadas; i < sesiones.size(); i++){
            SesionTipo sT = sesiones.get(i).getTipo();
            int tiempoTrabajando = sT.getNum_circuitos()* sT.getT_ejercicio() * sT.getNum_ejercicios() + sT.getT_calentamiento();
            String fechaSimple = sesiones.get(i).getFecha().toString().substring(0, sesiones.get(i).getFecha().toString().indexOf('T'));
            series.getData().add(new XYChart.Data<>(sesiones.get(i).getTipo().getCodigo() +"("+i+")\n" + fechaSimple, tiempoTrabajando));
        }
        series.setName("Tiempo de trabajo");
        lineChart.getData().add(series);
        //Duración Real
        series = new XYChart.Series();
        for (int i = 0 + sesionesSaltadas; i < sesiones.size(); i++){
            SesionTipo sT = sesiones.get(i).getTipo();
            String fechaSimple = sesiones.get(i).getFecha().toString().substring(0, sesiones.get(i).getFecha().toString().indexOf('T'));
            series.getData().add(new XYChart.Data<>(sesiones.get(i).getTipo().getCodigo() +"("+i+")\n" + fechaSimple, sesiones.get(i).getDuracion().getSeconds()));
        }
        series.setName("Tiempo real");
        lineChart.getData().add(series);
    }
    
    private void rellenarChoiceBox(int numMax){
        numberSesions.getItems().removeAll(numberSesions.getItems());
        for(int i = 1; i <= numMax; i++){ numberSesions.getItems().add(i);}
        numberSesions.setValue(numMax);
    }
}
