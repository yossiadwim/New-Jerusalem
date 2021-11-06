package id.ac.ukdw.fti.rpl.theartificier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.BookChapterNum;
import id.ac.ukdw.fti.rpl.theartificier.modal.VersesAndCount;

public class HomePageController implements Initializable
{

    Database db = new Database();
    
   
    @FXML
    private ComboBox<String> kitab;
    

    @FXML
    private Button pindahHalaman;

    // @FXML
    // private AnchorPane tampilAyat;

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

    



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //kitab
        ArrayList<BookChapterNum> data = db.ambilKitab();
        ObservableList<String> hasil = FXCollections.observableArrayList();

        for(BookChapterNum isi : data){
            hasil.add(isi.getBook());
            
        }
        kitab.setItems(hasil);  

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
                
                hasil3.add(isi3.getOsisRef()+ "\n"+ isi3.getVerse());
            }
            
            // System.out.println(hasil3);
            tampilAyat.setItems(hasil3);
        }
        catch(Exception e){
            e.getMessage();
        }

    }

}
