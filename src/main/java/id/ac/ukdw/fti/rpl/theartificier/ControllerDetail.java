package id.ac.ukdw.fti.rpl.theartificier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import id.ac.ukdw.fti.rpl.theartificier.Controller;
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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ControllerDetail implements Initializable {
    @FXML
    private Button pindahHalaman;

    @FXML
    private Text judulDetail;

    @FXML
    private ListView listViewDetail;

    
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String searchInitialize = "";
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        Controller.searchInitialize = searchInitialize;

        String jdl = Controller.judul;
        
        try{

            ArrayList<String> ayatNum = Controller.ayat;
            ObservableList<String> hasilAyat = FXCollections.observableArrayList();
            for(String ayat : ayatNum){
                
                if(ayat.equals("No data available")){
                    hasilAyat.add("No data available");
                }
                else{
                    hasilAyat.add(ayat + "\n" + Controller.wrapText(Controller.cekAyat(ayat), 80, " ") + "\n ");
                }
                
            }
            
            judulDetail.setText(jdl);
            listViewDetail.setItems(hasilAyat);
            ayatNum.clear();
        }
        catch(Exception e){
            e.getMessage();
        }

        // if(Controller.ayat != null){
        //     String[] ayatNum = Controller.ayat;
        //     System.out.println("IF");

        //     ObservableList<String> hasilAyat = FXCollections.observableArrayList();
        //     for(String ayat : ayatNum){
        //         hasilAyat.add(ayat + "\n" + Controller.wrapText(Controller.cekAyat(ayat), 80, " ") + "\n ");
        //     }
        //     judulDetail.setText(jdl);
        //     listViewDetail.setItems(hasilAyat);
        // }
        // else{
        //    System.out.println("ELSE");
        
        //     ObservableList<String> hasilAyat = FXCollections.observableArrayList();
        //     hasilAyat.add(Controller.ayat + "\n" + "No data Available");
        //     judulDetail.setText(jdl);
        //     listViewDetail.setItems(hasilAyat);
        // }

        // String[] ayatNum = Controller.ayat;
        // ObservableList<String> hasilAyat = FXCollections.observableArrayList();
        // for(String ayat : ayatNum){
        //     hasilAyat.add(ayat + "\n" + Controller.wrapText(Controller.cekAyat(ayat), 80, " ") + "\n ");
        // }
        // judulDetail.setText(jdl);
        // listViewDetail.setItems(hasilAyat);
        
        
    }

    @FXML
    public void switchToPrevious(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New Jerusalem");
        scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
        stage.setScene(scene);
        stage.show();
    }
    
    
}