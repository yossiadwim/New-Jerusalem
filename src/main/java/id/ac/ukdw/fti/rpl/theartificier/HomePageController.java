package id.ac.ukdw.fti.rpl.theartificier;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.ResourceBundle;



import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.BookChapterNum;
import id.ac.ukdw.fti.rpl.theartificier.modal.VersesAndCount;


public class HomePageController implements Initializable
{

    
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
   
    @FXML
    private ComboBox<String> kitab;
    

    @FXML
    private Button pindahHalaman;

    @FXML
    private ComboBox<String> pasal;

    @FXML
    private ComboBox<Integer> ayat;

    @FXML
    private AnchorPane visualisasiPlaces;

    @FXML
    private AnchorPane visualisasiEvents;
    
    @FXML
    private ListView tampilAyat;

    @FXML
    private Label perikop;

    @FXML
    private Button buttonKembali;

    Database db = new Database();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        ArrayList<BookChapterNum> verse = db.versesFromStart();
        ObservableList<String> hasilVerse = FXCollections.observableArrayList();

        for(BookChapterNum isiVerse : verse){
            // hasilVerse.add(isiVerse.getOsisRef()+"\n"+isiVerse.getVerse()+"\n"+" ");
            hasilVerse.add(isiVerse.getOsisRef()+"\n"+wrapText(isiVerse.getVerse())+"\n"+" ");
        }
        tampilAyat.setItems(hasilVerse);


        //kitab
        ArrayList<BookChapterNum> data = db.ambilKitab();
        ObservableList<String> hasil = FXCollections.observableArrayList();

        for(BookChapterNum isi : data){
            hasil.add(isi.getBook());
            
        }
        kitab.setItems(hasil); 

    }

    private String wrapText(String text){
        String[] split = text.split(" ");
        int count = 0;
        int check = 117;
        String output = "";
        for(String i: split){
            if(count+i.length() + 1 > check){
                output += "\n";
                output += i;
                check += 117;
            }
            else{
                output += i + " ";
            }
            count+=i.length();
        }
        return output;
    }

    public void switchToScene(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("New Jerusalem");
        scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }




    public void onClickSelectedKitab(ActionEvent event) {
        String kt = kitab.getSelectionModel().getSelectedItem();

        ArrayList<BookChapterNum> dataPasal = db.ambilPasal(kt);
        ObservableList<String> hasil = FXCollections.observableArrayList();

        for(BookChapterNum isi : dataPasal){
            hasil.add(isi.getChapter());
        }
        pasal.setItems(hasil);
    
    }
    
    public void onClickSelectedPasal(ActionEvent event) {
        String ps = pasal.getSelectionModel().getSelectedItem();
        ArrayList<BookChapterNum> dataAyat = db.ambilAyat(ps);
        ObservableList<Integer> hasil2 = FXCollections.observableArrayList();

        for(BookChapterNum isi2 : dataAyat){
            hasil2.add(isi2.getNum());
        }
        ayat.setItems(hasil2);
    
    }

    public void onClickSelectedAyat(ActionEvent event) {
        try{
            String kt = kitab.getSelectionModel().getSelectedItem();
            String ps = pasal.getSelectionModel().getSelectedItem();
            int ayt = ayat.getSelectionModel().getSelectedItem();
            
            ArrayList<BookChapterNum> isiAyat = db.ayatHomePage(kt,ps,ayt);
            ObservableList<String> hasil3 = FXCollections.observableArrayList();
            
            
            for(BookChapterNum isi3 : isiAyat){
                
                hasil3.add(isi3.getOsisRef()+ "\n"+ wrapText(isi3.getVerse()) + "\n ");
            }

            tampilAyat.setItems(hasil3);
        }
        catch(Exception e){
            e.getMessage();
        }

    }
    
    

}