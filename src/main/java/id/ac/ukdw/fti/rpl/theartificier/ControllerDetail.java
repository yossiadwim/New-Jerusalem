package id.ac.ukdw.fti.rpl.theartificier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import id.ac.ukdw.fti.rpl.theartificier.Controller;
import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.BarVisualisasi;
import id.ac.ukdw.fti.rpl.theartificier.modal.VersesAndCount;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ControllerDetail implements Initializable {
    @FXML
    private Button pindahHalaman;

    @FXML
    private Text judulDetail;

    @FXML
    private ListView listViewDetail;

    @FXML
    private ScrollPane scrollpanePeople;

    @FXML
    private AnchorPane tampilJumlahPeople;

    @FXML
    private ScrollPane scrollpanePlaces;

    @FXML
    private AnchorPane tampilJumlahPlaces;

    private List<Rectangle> rectPeople = new ArrayList<>();
    private List<Label> labelRectPeople = new ArrayList<>();

    private List<Rectangle> rectPlaces = new ArrayList<>();
    private List<Label> labelRectPlaces = new ArrayList<>();


    private HashMap<String, Integer> peopleMap= new HashMap<String, Integer>();
    private HashMap<String, Integer> placesMap= new HashMap<String, Integer>();
    private int layoutY = 14;
    private int XBar = 6;
    private int YBarPeople = 5;
    private int YBarPlaces = 5;
    private int layoutXBar = 14;
    private int layoutYBarPeople = 11;
    private int layoutYBarPlaces = 11;
    private int peopleCountMap, placesCountMap;
    private final double maxRect = 215;
    private final int heightRect = 29;
    
    private Database db = new Database();
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String searchInitialize = "";
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        Controller.searchInitialize = searchInitialize;

        String jdl = Controller.judul;

        peopleCountMap = 0;
        placesCountMap = 0;

        peopleMap.clear();
        placesMap.clear();
        rectPeople.clear();
        rectPlaces.clear();
        labelRectPeople.clear();
        labelRectPlaces.clear();

        layoutY = 14;
        YBarPeople = 5;
        YBarPlaces = 5;
        layoutXBar = 14;
        layoutYBarPeople = 11;
        layoutYBarPlaces = 11;
        
        try{

            ArrayList<String> ayatNum = Controller.ayat;
            ObservableList<String> hasilAyat = FXCollections.observableArrayList();
            for(String ayat : ayatNum){
                
                if(ayat.equals("No data available")){
                    hasilAyat.add("No data available");
                }
                else{
                    addPeoplePlacesMap(ayat);
                    hasilAyat.add(ayat + "\n" + Controller.wrapText(Controller.cekAyat(ayat), 80, " ") + "\n ");
                }
                
            }
            
            judulDetail.setText(jdl);
            listViewDetail.setItems(hasilAyat);
            ayatNum.clear();

            Map<String, Integer> sortedPeopleMap = Controller.sortByValue(peopleMap);
            Map<String, Integer> sortedPlacesMap = Controller.sortByValue(placesMap);

            sortedPeopleMap.remove(null);
            sortedPlacesMap.remove(null);
            
            // mulai hitung jumlah
            List<Map.Entry<String, Integer> > listPeople = new LinkedList<Map.Entry<String, Integer> >(sortedPeopleMap.entrySet());
            for (Map.Entry<String, Integer> peopleCount : listPeople) {
                peopleCountMap += peopleCount.getValue();
            }

            List<Map.Entry<String, Integer> > listPlaces = new LinkedList<Map.Entry<String, Integer> >(sortedPlacesMap.entrySet());
            for (Map.Entry<String, Integer> placesCount : listPlaces) {
                placesCountMap += placesCount.getValue();
            }
            // berhenti hitung jumlah

            // mulai buat bar visualisasi
            List<Map.Entry<String, Integer> > listPeople2 = new LinkedList<Map.Entry<String, Integer> >(sortedPeopleMap.entrySet());

            for (Map.Entry<String, Integer> peopleCount : listPeople) {
                BarVisualisasi barPeople = createBarVisualisasi(peopleCount.getKey(), peopleCount.getValue(), peopleCountMap, "people");
                rectPeople.add(barPeople.getRect());
                labelRectPeople.add(barPeople.getLbl());
                
            }

            List<Map.Entry<String, Integer> > listPlaces2 = new LinkedList<Map.Entry<String, Integer> >(sortedPlacesMap.entrySet());
            for (Map.Entry<String, Integer> placesCount : listPlaces) {
                BarVisualisasi barPlaces = createBarVisualisasi(placesCount.getKey(), placesCount.getValue(), placesCountMap, "places");
                rectPlaces.add(barPlaces.getRect());
                labelRectPlaces.add(barPlaces.getLbl());
            }
            // berhenti buat bar visualisasi

            tampilJumlahPeople.getChildren().clear();
            tampilJumlahPeople.getChildren().addAll(rectPeople);
            tampilJumlahPeople.getChildren().addAll(labelRectPeople);

            tampilJumlahPlaces.getChildren().clear();
            tampilJumlahPlaces.getChildren().addAll(rectPlaces);
            tampilJumlahPlaces.getChildren().addAll(labelRectPlaces);
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
    
    protected void addPeoplePlacesMap(String ayat){

        VersesAndCount vac = db.viewVisualisasiUtama(ayat);
        try{
            if(vac.getPeopleCount() > 1){
                String[] splitPeople = vac.getPeople().split(",");
                for(String j: splitPeople){
                    try{
                        peopleMap.put(j, peopleMap.get(j)+1);
                        peopleMap.remove(null);
                    }
                    catch(Exception e){
                        peopleMap.put(j, 1);
                        peopleMap.remove(null);
                    }
                }
            }
            else{
                try{
                    peopleMap.put(vac.getPeople(), peopleMap.get(vac.getPeople())+1);
                    peopleMap.remove(null);
                }
                catch(Exception e){
                    peopleMap.put(vac.getPeople(), 1);
                    peopleMap.remove(null);
                }
            }
        }
        catch(Exception e){
            e.getMessage();
        }

        try{
            if(vac.getPlacesCount() > 1){
                String[] splitPlaces = vac.getPlaces().split(",");
                for(String j: splitPlaces){
                    try{
                        placesMap.put(j, placesMap.get(j)+1);
                        placesMap.remove(null);
                    }
                    catch(Exception e){
                        placesMap.put(j, 1);
                        placesMap.remove(null);
                    }
                }
            }
            else{
                try{
                    placesMap.put(vac.getPlaces(), placesMap.get(vac.getPlaces())+1);
                    placesMap.remove(null);
                }
                catch(Exception e){
                    placesMap.put(vac.getPlaces(), 1);
                    placesMap.remove(null);
                }
            }
        }
        catch(Exception e){
            e.getMessage();
        }

    }

    protected BarVisualisasi createBarVisualisasi(String id, double count, double jumlah, String keterangan){
        double width = (count/jumlah) * maxRect;
        Rectangle rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(heightRect);
        rect.setX(XBar);
        rect.setFill(Color.GREY);

        if(keterangan.equals("people")){
            String namaPeople = db.ambilNamaPeople(id);
            Label lbl = new Label(namaPeople +  " = " + (int) count);
            rect.setY(YBarPeople);
            lbl.setLayoutY(layoutYBarPeople);
            YBarPeople += 32;
            layoutYBarPeople += 32;

            lbl.setLayoutX(layoutXBar);
            BarVisualisasi bar = new BarVisualisasi(rect, lbl);
            return bar;
        }
        else if(keterangan.equals("places")){
            String namaPlaces = db.ambilNamaPlaces(id);
            Label lbl = new Label(namaPlaces +  " = " + (int) count);
            rect.setY(YBarPlaces);
            lbl.setLayoutY(layoutYBarPlaces);
            YBarPlaces += 32;
            layoutYBarPlaces += 32;
            
            lbl.setLayoutX(layoutXBar);
            BarVisualisasi bar = new BarVisualisasi(rect, lbl);
            return bar;
        }
        return null;
        
    }
    
}