package crossfitauxiliar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Grupo;
import modelo.Gym;
import modelo.Sesion;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class GrupsController implements Initializable {
    private Gym gymActual;
    private ObservableList<Grupo> observableGrupos;
    private ArrayList<Grupo> gruposDataBase;
    private Scene anteriorScene;
    private boolean haSigutModificat;
    private boolean saltarEso;
    @FXML
    private ListView<Grupo> listaGrupos;
    @FXML
    private TextField grupoName;
    @FXML
    private TextArea grupoDescr;
    @FXML
    private Button buttonGraphics;
    private Label sesionesGrupoLabel;
    @FXML
    private VBox vboxDeDatos;
    private TextField textFieldSesio;
    @FXML
    private TextField textFieldGrup;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        haSigutModificat = false;
        saltarEso = false;
        //vboxDeDatos.setVisible(false);
        listaGrupos.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> { 
                if(haSigutModificat){
                    
                    oldVal.setCodigo(grupoName.getText());
                    oldVal.setDescripcion(grupoDescr.getText());
                    //System.out.println("a-> "+grupoName.getText());
                    observableGrupos.clear();
                    observableGrupos = FXCollections.observableArrayList(gruposDataBase);
                    listaGrupos.setCellFactory(c -> new GrupListCell());
                    listaGrupos.setItems(observableGrupos);
                    
                }
                if(!saltarEso && newVal != null){
                vboxDeDatos.setVisible(true);
                grupoName.setText(newVal.getCodigo());
                grupoName.setEditable(true);
                grupoDescr.setText(newVal.getDescripcion());
                grupoDescr.setEditable(true);
                buttonGraphics.setDisable(false);
                haSigutModificat = false;
//                //Actualizar valores no va
//                oldVal.setCodigo(grupoName.getText());
//                oldVal.setDescripcion(grupoDescr.getText());
                }
                listaGrupos.refresh();
            });
        grupoName.textProperty().addListener((observable, oldVal, newVal) -> {
           haSigutModificat = true;

        });
        grupoDescr.textProperty().addListener((observable, oldVal, newVal)-> {
            haSigutModificat = true;

        });
        textFieldGrup.textProperty().addListener((observable, oldVal, newVal) -> { 
            String text = textFieldGrup.getText().toLowerCase();
            ArrayList<Grupo> elVisible = new ArrayList<>();
            for(int i = 0; i < gruposDataBase.size(); i++) {
                String sesioActual = gruposDataBase.get(i).getCodigo()+ "";                         
                sesioActual = sesioActual.toLowerCase();
                if(sesioActual.contains(text)) {
                    elVisible.add(gruposDataBase.get(i));
                }
                //if(elVisible.size() == 0){ vboxDeDatos.setVisible(false);}
            }
            observableGrupos = FXCollections.observableArrayList(elVisible);
            listaGrupos.setItems(observableGrupos);
        });
        
    }    
//    protected void actualizarGrupo(){
//                    int indexPrevio = listaGrupos.getSelectionModel().getSelectedIndex();
//                    listaGrupos.getSelectionModel().getSelectedItem().setCodigo(grupoName.getText());
//                    listaGrupos.getSelectionModel().getSelectedItem().setDescripcion(grupoDescr.getText());
//                    System.out.println("a-> "+grupoName.getText());
//                    observableGrupos.clear();
//                    observableGrupos = FXCollections.observableArrayList(gruposDataBase);
//                    listaGrupos.setCellFactory(c -> new GrupListCell());
//                    listaGrupos.setItems(observableGrupos);
//                    listaGrupos.getSelectionModel().select(indexPrevio);
//    
//    
//    }
    public void setGym(Gym g) {
        gymActual = g;
        gruposDataBase = g.getGrupos();
        observableGrupos = FXCollections.observableArrayList(gruposDataBase);
        listaGrupos.setCellFactory(c -> new GrupListCell());
        listaGrupos.setItems(observableGrupos);
    }

//    private void afegirGrup(ActionEvent event) throws IOException{
//        if(haSigutModificat){
//                    listaGrupos.getSelectionModel().getSelectedItem().setCodigo(grupoName.getText());
//                    listaGrupos.getSelectionModel().getSelectedItem().setDescripcion(grupoDescr.getText());
//                    listaGrupos.getSelectionModel().selectLast();
//                    listaGrupos.getSelectionModel().selectFirst();
//                    vboxDeDatos.setVisible(false);
//                    ArrayList<Grupo> limpiador = new ArrayList<>();
//                    observableGrupos = FXCollections.observableArrayList(limpiador);
//                    listaGrupos.setItems(observableGrupos);
//                    observableGrupos = FXCollections.observableArrayList(gruposDataBase);
//                    listaGrupos.setItems(observableGrupos);
//        }
//        Stage stage = new Stage();
//        FXMLLoader miloader = new FXMLLoader((getClass().getResource("/models/afegirGrup.fxml")));
//        Parent root =  miloader.load();
//
//        Scene scene = new Scene(root);
//        Grupo g = new Grupo();
//        ((AfegirGrupController)miloader.getController()).setGrup(g);
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("Añadir grupo");
//        stage.showAndWait();
//        if(g.getCodigo() != null){
//            addOrdenat(g);
//        }
//    }
    
    private void addOrdenat(Grupo g){
            String nom = g.getCodigo();
            int i = 0;
            while(i < gruposDataBase.size()  && nom.compareTo(gruposDataBase.get(i).getCodigo())>0){ i++;}
            if(i >= gruposDataBase.size()) { 
                gruposDataBase.add(g); 
                observableGrupos.add(g);
            }//major es impossible que siga
            else {
                gruposDataBase.add(i, g);
                observableGrupos.add(i,g);
            }
    }
    
    public void setAnteriorScene(Scene s){
        anteriorScene = s;
    }

    @FXML
    private void volverAnterior(ActionEvent event) {
        if(haSigutModificat){
                    listaGrupos.getSelectionModel().getSelectedItem().setCodigo(grupoName.getText());
                    listaGrupos.getSelectionModel().getSelectedItem().setDescripcion(grupoDescr.getText());
                    
        }
        ((Stage)listaGrupos.getScene().getWindow()).setScene(anteriorScene);
    }

    @FXML
    private void verGrafica(ActionEvent event) throws IOException{
        Grupo g;
        if(haSigutModificat){
                    g = listaGrupos.getSelectionModel().getSelectedItem();
                    listaGrupos.getSelectionModel().getSelectedItem().setCodigo(grupoName.getText());
                    listaGrupos.getSelectionModel().getSelectedItem().setDescripcion(grupoDescr.getText());
                    listaGrupos.getSelectionModel().selectLast();
                    listaGrupos.getSelectionModel().selectFirst();
                    vboxDeDatos.setVisible(false);
                    ArrayList<Grupo> limpiador = new ArrayList<>();
                    observableGrupos = FXCollections.observableArrayList(limpiador);
                    listaGrupos.setItems(observableGrupos);
                    observableGrupos = FXCollections.observableArrayList(gruposDataBase);
                    listaGrupos.setItems(observableGrupos);
        }
        else{
            g = listaGrupos.getSelectionModel().getSelectedItem();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/Grafica.fxml"));

        Parent root =  loader.load();  
        Scene sesio = new Scene(root);
        Scene actual = grupoName.getScene();
        
        ((GraficaController)loader.getController()).setGrupo(g);
        ((GraficaController)loader.getController()).setAnteriorScene(actual);
        ((Stage)listaGrupos.getScene().getWindow()).setScene(sesio);
    }



    @FXML
    private void afegirGrup(ActionEvent event) throws IOException{
        if(haSigutModificat){
                    listaGrupos.getSelectionModel().getSelectedItem().setCodigo(grupoName.getText());
                    listaGrupos.getSelectionModel().getSelectedItem().setDescripcion(grupoDescr.getText());
                    listaGrupos.getSelectionModel().selectLast();
                    listaGrupos.getSelectionModel().selectFirst();
                    vboxDeDatos.setVisible(false);
                    ArrayList<Grupo> limpiador = new ArrayList<>();
                    observableGrupos = FXCollections.observableArrayList(limpiador);
                    listaGrupos.setItems(observableGrupos);
                    observableGrupos = FXCollections.observableArrayList(gruposDataBase);
                    listaGrupos.setItems(observableGrupos);
        }
        Stage stage = new Stage();
        FXMLLoader miloader = new FXMLLoader((getClass().getResource("/models/afegirGrup.fxml")));
        Parent root =  miloader.load();

        Scene scene = new Scene(root);
        Grupo g = new Grupo();
        ((AfegirGrupController)miloader.getController()).setGrup(g);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir grupo");
        stage.getIcons().add(new Image("resources/appIcon.png"));
        stage.showAndWait();
        if(g.getCodigo() != null){
            addOrdenat(g);
        }
    }
    
}




  class GrupListCell extends ListCell<Grupo> {
        @Override
        protected void updateItem(Grupo item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText("");
                setGraphic(null);
                 setStyle("-fx-background-color: #0288D1;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                //setStyle("-fx-background-color: #0288D1;-fx-font-size: 12; -fx-text-fill: #ffffff;");
            } else {
                setText(item.getCodigo());
                boolean selected = focusedProperty().get();
                
                if(selected) {
                    //System.out.println("aSDAFASD"+ " "+ item.getCodigo());
                    setStyle("-fx-background-color: #4fc3f7;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                    
                }
                else {
                     //System.out.println("aSDAFASD"+ " "+ item.getCodigo());
                setStyle("-fx-background-color: #0288D1;-fx-font-size: 12; -fx-text-fill: #ffffff;");
                }
                //}
//                setOnMouseEntered(e -> {
//                    setStyle("-fx-background-color:#B3E5FC;-fx-font-size: 12; -fx-text-fill: #ffffff;");
//                });
//                setOnMouseExited(e -> {
//                    // if(!clicked) {
//                    setStyle("-fx-background-color:#0288D1;-fx-font-size: 12; -fx-text-fill: #ffffff;");
//                    //  }
//                });
            }
        }

    }
 