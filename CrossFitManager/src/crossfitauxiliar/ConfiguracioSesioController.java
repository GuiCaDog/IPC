/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitauxiliar;

import accesoBD.AccesoBD;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Grupo;
import modelo.Gym;
import modelo.Sesion;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author guicado
 */
public class ConfiguracioSesioController implements Initializable {

    @FXML
    private Button startSesionButton;
    private Gym gymActual;
    private ArrayList<Grupo> gruposDataBase;
    private ObservableList<Grupo> observableGrupos;
    private ArrayList<SesionTipo> sesionsTipus;
    private ObservableList<SesionTipo> observableSesionsTipus;
    private SesionTipo sesioDefaultGrupo;
    @FXML
    private TextField textFieldGrup;
    @FXML
    private ListView<Grupo> listViewGrup;
    @FXML
    private TextField textFieldSesio;
    @FXML
    private ListView<SesionTipo> listViewSesio;
    private Scene anteriorScene;
    private boolean grupSelected = false;
    private boolean sesioSelected = false;
    @FXML
    private ComboBox<Grupo> choiceBoxGrup;
    @FXML
    private Label codiSesio;
    @FXML
    private Label dato;
    @FXML
    private TextField textFieldDato;
    @FXML
    private Label mostrarDato;
    @FXML
    private Label dato1;
    @FXML
    private TextField textFieldDato1;
    @FXML
    private Label mostrarDato1;
    @FXML
    private Label dato11;
    @FXML
    private TextField textFieldDato11;
    @FXML
    private Label mostrarDato11;
    @FXML
    private Label dato111;
    @FXML
    private TextField textFieldDato111;
    @FXML
    private Label mostrarDato111;
    @FXML
    private Label dato1111;
    @FXML
    private TextField textFieldDato1111;
    @FXML
    private Label mostrarDato1111;
    @FXML
    private Label dato11111;
    @FXML
    private TextField textFieldDato11111;
    @FXML
    private Label mostrarDato11111;
    @FXML
    private Label labelGrup;
    private boolean esAlElegirGrup=false;
    private boolean typing;
    @FXML
    private VBox vBoxDatosSesion;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        //vBoxDatosSesion.setVisible(false);
        //---------------------------Listeners del grup---------------------------------------------------------------------------------
        textFieldGrup.textProperty().addListener((observable, oldVal, newVal) -> { 
            if(!esAlElegirGrup) {
                typing=true;
            labelGrup.setText("");
            choiceBoxGrup.show();
            String text = textFieldGrup.getText().toLowerCase();
            ArrayList<Grupo> elVisible = new ArrayList<>();
            for(int i = 0; i < gruposDataBase.size(); i++) {
                String grupoActual = gruposDataBase.get(i).getCodigo()+ "";                         
                grupoActual = grupoActual.toLowerCase();
                if(grupoActual.contains(text)) {
                    elVisible.add(gruposDataBase.get(i));
                }
            }
            observableGrupos = FXCollections.observableArrayList(elVisible);
            choiceBoxGrup.setItems(observableGrupos);
            
//            for(int i=0; i < choiceBoxGrup.getChildrenUnmodifiable().size(); i++) {
//                Node n = choiceBoxGrup.getChildrenUnmodifiable().get(i);
//                
//                if(n instanceof ContextMenu)
//            }
            //choiceBoxGrup.getChildrenUnmodifiable().get(i);
            }
            typing=false;
        });
        //listViewGrup.hoverProperty()
        choiceBoxGrup.valueProperty().addListener((observable, oldVal, newVal) -> { 
            if(!typing) {
                Grupo p = newVal;//tablaPacients.getFocusModel().getFocusedItem();
                //System.out.println(p.getCodigo());
                if(p!= null && p.getDefaultTipoSesion()!=null) {
                    sesioDefaultGrupo = p.getDefaultTipoSesion();
                }
                if(p!=null) {
                //labelGrup
                esAlElegirGrup=true;
                    textFieldGrup.setText(newVal.getCodigo());//+" -" +newVal.getDescripcion()+"-" );
                esAlElegirGrup=false;
                grupSelected=true;
                }
                else {
                    grupSelected = false;
                }
                codiSesio.requestFocus();
            }
            });
        //-----------------------------------------------------------------------------------------------------------------------------------
        //---------------------------Listeners de les sesions---------------------------------------------------------------------------------
        textFieldSesio.textProperty().addListener((observable, oldVal, newVal) -> { 
            String text = textFieldSesio.getText().toLowerCase();
            ArrayList<SesionTipo> elVisible = new ArrayList<>();
            for(int i = 0; i < sesionsTipus.size(); i++) {
                String sesioActual = sesionsTipus.get(i).getCodigo()+ "";                         
                sesioActual = sesioActual.toLowerCase();
                if(sesioActual.contains(text)) {
                    elVisible.add(sesionsTipus.get(i));
                }
            }
            //if(elVisible.size() == 0){ vBoxDatosSesion.setVisible(false);}
            observableSesionsTipus = FXCollections.observableArrayList(elVisible);
            listViewSesio.setItems(observableSesionsTipus);
        });
        //listViewGrup.hoverProperty()
        
        listViewSesio.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> { 
                //SesionTipo p =
                        //newVal;//tablaPacients.getFocusModel().getFocusedItem();
                //System.out.println(newVal);
                if(newVal!= null) {
                    //A COMPLETAR
                     vBoxDatosSesion.setVisible(true);
                    codiSesio.setText(newVal.getCodigo());
                    mostrarDato.setText("\t"+"\t"+"\t"+"\t"+newVal.getNum_ejercicios()+"");
                    mostrarDato1.setText("\t"+"\t"+"\t"+"\t"+newVal.getNum_circuitos()+"");
                    mostrarDato11.setText("\t"+"\t"+"\t"+"\t"+newVal.getT_calentamiento()+"");
                    mostrarDato111.setText("\t"+"\t"+"\t"+"\t"+newVal.getT_ejercicio()+"");
                    mostrarDato1111.setText("\t"+"\t"+"\t"+"\t"+newVal.getD_ejercicio()+"");
                    mostrarDato11111.setText("\t"+"\t"+"\t"+"\t"+newVal.getD_circuito()+"");
                }
                
            });
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //--Listeners para activar/desactivar botón de empezar
        listViewSesio.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal == null) {
                //System.out.println(newVal);
                sesioSelected = false;
                startSesionButton.setDisable(true);
            }
            else {
                
                
                listViewSesio.refresh();
                sesioSelected = true;
                if(grupSelected) startSesionButton.setDisable(false);
            }
        });
        choiceBoxGrup.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal == null) {
                //System.out.println(newVal);
                grupSelected = false;
                startSesionButton.setDisable(true);
            }
            else {
                //System.out.println(newVal);
                grupSelected = true;
                if(sesioSelected) startSesionButton.setDisable(false);
            }
        });
    }    

    @FXML
    private void startSesion(ActionEvent event) throws IOException {
        
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/FXMLDocument.fxml"));

        Parent root =  loader.load();  
        Scene sesio = new Scene(root);
        Scene actual = textFieldGrup.getScene();
        Sesion s = new Sesion();
        s.setTipo(listViewSesio.getSelectionModel().getSelectedItem());
        Grupo g = choiceBoxGrup.getSelectionModel().getSelectedItem();
        ((FXMLDocumentController)loader.getController()).setSesionAndGrupo(s, g);
        ((FXMLDocumentController)loader.getController()).setAnteriorScene(actual);
        ((Stage)startSesionButton.getScene().getWindow()).setScene(sesio);
    }
    
    public void setGym(Gym g) {
        gymActual = g;
        gruposDataBase = g.getGrupos();
        sesionsTipus = gymActual.getTiposSesion();
        //------------------------------------------------------------------------------------------
        observableGrupos = FXCollections.observableArrayList(gruposDataBase);
        choiceBoxGrup.setItems(observableGrupos);
        choiceBoxGrup.setCellFactory(e -> new GrupListCell());
        //------------------------------------------------------------------------------------------
        observableSesionsTipus = FXCollections.observableArrayList(sesionsTipus);
        listViewSesio.setItems(observableSesionsTipus);
        listViewSesio.setCellFactory(e->new SesioListCell());
        
    }
    public void setAnteriorScene(Scene s){
        anteriorScene = s;
    }
    @FXML
    private void arrere(ActionEvent event) {
        ((Stage)textFieldGrup.getScene().getWindow()).setScene(anteriorScene);
    }

    @FXML
    private void crearSesion(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader miloader = new FXMLLoader((getClass().getResource("/models/afegirSesio.fxml")));
        Parent root =  miloader.load();

        Scene scene = new Scene(root);
        SesionTipo s = new SesionTipo();
        ((AfegirSesioController)miloader.getController()).setSesionTipo(s);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir sesión tipo");
        stage.getIcons().add(new Image("resources/appIcon.png"));
        stage.showAndWait();
        if(s.getCodigo() != null){
            addOrdenat(s);
        }
        
    }
    private void addOrdenat(SesionTipo g){
            String nom = g.getCodigo();
            int i = 0;
            while(i < sesionsTipus.size()  && nom.compareTo(sesionsTipus.get(i).getCodigo())>0){ i++;}
            if(i >= sesionsTipus.size()) { 
                sesionsTipus.add(g); 
                observableSesionsTipus.add(g);
            }//major es impossible que siga
            else {
                sesionsTipus.add(i, g);
                observableSesionsTipus.add(i,g);
            }
    }

    @FXML
    private void mouseEntraEnGrup(MouseEvent event) {
        labelGrup.setText("");
        choiceBoxGrup.show();
    }

    @FXML
    private void actualitzarItems(ScrollEvent event) {
        listViewSesio.refresh();
    }
    
    
    class GrupListCell extends ListCell<Grupo> {

        @Override
        protected void updateItem(Grupo item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText("");
                setGraphic(null);
            } else {
                //setStyle("-fx-background-color: pink;");
                setText(item.getCodigo());
            }
        }

    }
    class SesioListCell extends ListCell<SesionTipo> {
        @Override
        protected void updateItem(SesionTipo item, boolean empty) {
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
    
}

//class HorariListCell extends ListCell<String> {
//
//        //private ArrayLi view = new ImageView();
//
//        @Override
//        protected void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//////            int size = ((ListView) getParent().getParent().getParent().getParent()).getItems().size();
//////            int size2 = getParent().getChildrenUnmodifiable().size();
//////            if (size > size2) {
//////                GridPane grid = ((GridPane) getParent().getParent().getParent().getParent().getParent());
//////                for (int i = 0; i < grid.getChildren().size(); i++) {
//////                    Node n = grid.getChildren().get(i);
//////                    if (n instanceof ListView) {
//////                        //((ListView)n).setC
//////                        //((ListView) n).getFocusModel().focus(-1);
//////                    }
//////                }
////
////            }
//            if (item == null || empty) {
//                setText("");
//                setStyle( "-fx-background-color; #9E9E9E; -fx-font-size: 12; -fx-text-fill: #ffffff;");
//            } else {
//                
//                //((ListView) getParent().getParent().getParent().getParent()).getFocusModel().focus(-1);
////                view = scale(view, 20, 20, true);
////                view.setImage(item.getPhoto());
////                setGraphic(view);
////                Patient  p = item.getPatient();
////                Doctor d = item.getDoctor();
//                if(item.equals("Free")) {
//                    //boolean  clicked = false;
//                    //if(!clicked) {
//                     setStyle( "-fx-background-color: #8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;"); 
//                    //}
//                     setOnMouseEntered(e -> {
//                        setStyle("-fx-background-color:#DCEDC8;-fx-font-size: 12; -fx-text-fill: #ffffff;");
//                    } );
//                    setOnMouseExited(e -> {
//                       // if(!clicked) {
//                        setStyle("-fx-background-color:#8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;");
//                      //  }
//                    });
//                    ////////-----------PER A QUE SOLS HI HAJA 1 SELECCIONAT DDDD: ---------------------/////////
//                    setOnMouseClicked(e -> {
//                        //HEM DE DESELECCIONAR TOTS ELS DE LES ALTRES LLISTES I POSARLOS EL FORMAT ADECUAT AMB REFRES
//                        GridPane grid = ((GridPane) getParent().getParent().getParent().getParent().getParent());
//                        for (int i = 0; i < grid.getChildren().size(); i++) {
//                            Node n = grid.getChildren().get(i);
//                            if (n instanceof ListView) {
//                               
//                                if (!n.getId().equals(((ListView) getParent().getParent().getParent().getParent()).getId())) {
//                                    System.out.println("n " + (ListView) n);
//                                    System.out.println("clic " +((ListView) getParent().getParent().getParent().getParent()));
//                                     ((ListView) n).refresh();
//                                    //((ListView) n).getFocusModel().focus(-1);
//                                    
//                                }
//                            }
//                        }
//                        ((ListView) getParent().getParent().getParent().getParent()).requestFocus();
//                        //removeEventHandler(EventType.ROOT, eventHandler);
//                        setStyle("-fx-border-color:#388E3C; -fx-background-color:#8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;" );
//                        System.out.println(((ListView) getParent().getParent().getParent().getParent()).getItems());
//                        //QUAN CLICK, LI LLEVEM ELS HANDLERS DE HOVER
//                        setOnMouseEntered(f -> {
//                        });
//                        setOnMouseExited(g -> {
//                        });
//                        //PROBLEMA: A LA LLISTA DE CHILDREnUNMODIFIABLE SOLS ESTAN ELS QUE ES MOSTREN, NO TOTES LES CEL·LES
//                        //PER TANT, QUAN AGAFES UN INDEX GRAN, TOTES LES QUE ES MOSTREN, SON MENORS A IXE INDEX, I PER TANT,
//                        //A TOTS SE'LS ACTUALITZEN ELS HANDLERS
//                        int in = ((ListView) getParent().getParent().getParent().getParent()).getFocusModel().getFocusedIndex();
//                        System.out.println(in);
//                        //A TOTS ELS DEMES Free's SELS HA DE POSAR ELS HANDLERS ADEQUATS
//                        int size = Integer.min(((ListView) getParent().getParent().getParent().getParent()).getItems().size(), getParent().getChildrenUnmodifiable().size());
//                        for (int i = 0; i < size; i++) {
//                            System.out.println(getParent().getParent().getParent().getChildrenUnmodifiable().size());
//                            System.out.println(getParent().getChildrenUnmodifiable().size());
//                            System.out.println(((ListView) getParent().getParent().getParent().getParent()).getItems().size());
//                            if (i != in  &&((ListView) getParent().getParent().getParent().getParent()).getItems().get(i).equals("Free")) { //&&((ListView) getParent().getParent().getParent().getParent()).getItems().get(i).equals("Free")
//                                System.out.println("index " + in + " i " + i);
//                                getParent().getChildrenUnmodifiable().get(i).setStyle("-fx-background-color:#8BC34A;-fx-font-size: 12; -fx-text-fill: #ffffff;");
//                                getParent().getChildrenUnmodifiable().get(i).setOnMouseEntered(h -> {
//                                    ((Node) h.getSource()).setStyle("-fx-background-color:#DCEDC8;-fx-font-size: 12; -fx-text-fill: #ffffff;");
//                                    
//                                });
//                                getParent().getChildrenUnmodifiable().get(i).setOnMouseExited(j -> {
//                                    ((Node) j.getSource()).setStyle("-fx-background-color:#8BC34A; -fx-font-size: 12; -fx-text-fill: #ffffff;");
//                                });
//                                
//                            }
//                            
//                        }
//                        //clicked = true;
//                    });
//                    setText("Libre");
//                }
//                else if(item.equals("Not Available")) {
//                    setStyle( "-fx-background-color: #FF5252; -fx-font-size: 12; -fx-text-fill: #ffffff;");// -fx-border-color: #ffffff; -fx-border-width: 1; -fx-border-radius: 10;");
//                    setOnMouseEntered(e -> {
//                        setStyle("-fx-background-color:#FFCDD2; -fx-font-size: 12; -fx-text-fill: #ffffff;");
//                        //setFocused(true);
//                        
////                       System.out.println(((ListView)getParent().getParent().getParent().getParent()).getFocusModel().getFocusedIndex());
//// -------------------------((ListView)getParent().getParent().getParent().getParent()).requestFocus();
//                        //System.out.println(((ListView)getParent().getParent().getParent().getParent()).isFocused());
//                    } );
//                    setOnMouseExited(e -> {
//                        setStyle("-fx-background-color:#FF5252; -fx-font-size: 12; -fx-text-fill: #ffffff;");
//                        //setFocused(true);
//                        
//                    });
//                    setOnMouseClicked(e -> {});
//                    setText("No disponible");
//                }
//                else { setStyle("-fx-background-color:#03A9F4; -fx-font-size: 12; -fx-text-fill: #ffffff;");
//                        
//                setOnMouseEntered(e -> {
//                        setStyle("-fx-background-color:#B3E5FC; -fx-font-size: 12; -fx-text-fill: #ffffff;");
//                        System.out.println(((ListView)getParent().getParent().getParent().getParent()).isManaged());
//                    } );
//                    setOnMouseExited(e -> {
//                        setStyle("-fx-background-color:#03A9F4; -fx-font-size: 12;-fx-text-fill: #ffffff;");
//                    });
//                    setOnMouseClicked(e -> {});
//                    setText(item);
//                }
////                if(p != null && d != null) {
////                setText(p.getName() + " " + p.getSurname() + " "+
////                        d.getName()+" " +d.getSurname() +" " + item.getAppointmentDateTime());
////                }
//
////getParent().getChildrenUnmodifiable().get(0).setStyle("-fx-background-color: #8BC34A;");
//            }
//        }
//        
//    }