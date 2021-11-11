package id.ac.ukdw.fti.rpl.theartificier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.BarVisualisasi;
import id.ac.ukdw.fti.rpl.theartificier.modal.ButtonLabel;
import id.ac.ukdw.fti.rpl.theartificier.modal.EventHandle;
import id.ac.ukdw.fti.rpl.theartificier.modal.VersesAndCount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller{

    @FXML
    private Label labelJumlah;

    @FXML
    private TextField search;

    @FXML
    private ImageView image;

    @FXML
    private TabPane tp;

    @FXML
    private Button button;

    @FXML
    private Tab tabPlace;

    @FXML
    private ScrollPane scrollpanePlace;

    @FXML
    private Tab tabEvent;

    @FXML
    private ScrollPane scrollpaneEvent;

    @FXML
    private AnchorPane tampilAyatTempat;

    @FXML
    private AnchorPane tampilAyatEvent;

    @FXML
    private ScrollPane scrollpanePeople;

    @FXML
    private AnchorPane tampilJumlahPeople;

    @FXML
    private ScrollPane scrollpanePlaces;

    @FXML
    private AnchorPane tampilJumlahPlaces;

    @FXML
    private ListView<String> listViewPlace;

    @FXML
    private ListView listViewEvent;

        
    private Stage stage;
    private Scene scene;
    private Parent root;

    Database db = new Database();

    private List<Button> buttonPlace = new ArrayList<>();
    private List<Label> labelPlace = new ArrayList<>();

    private List<Button> buttonEvent = new ArrayList<>();
    private List<Label> labelEvent = new ArrayList<>();

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

    @FXML
    public void onEnter(ActionEvent event){
        
        //ini kalo di enter jalanin handleButton
        handleButtonAction(event);
    }

    //method dibawah di ekseskusi abis enter
    @FXML
    private void handleButtonAction(ActionEvent event){

        peopleCountMap = 0;
        placesCountMap = 0;

        buttonPlace.clear();
        labelPlace.clear();
        buttonEvent.clear();
        labelEvent.clear();
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
        // database db = new database(); 

        String olahPlace = search.getText().toLowerCase();
        String olahEvent = search.getText().toLowerCase();

        if(alertEmpty()){
            
            olahPlace = olahPlace.replace(" ", "-");
            olahPlace = String.valueOf(olahPlace.charAt(0)).toUpperCase() + olahPlace.substring(1).toLowerCase();
            ArrayList<VersesAndCount> hasilPlace = db.viewDataPlace(olahPlace);


            olahEvent = String.valueOf(olahEvent.charAt(0)).toUpperCase() + olahEvent.substring(1).toLowerCase();
            ArrayList<EventHandle> hasilEvent = db.viewDataEvents(olahEvent);

            ObservableList<String> tampilPlace = FXCollections.observableArrayList();

            
            if(!hasilPlace.isEmpty() || !hasilEvent.isEmpty()){

                int count = 0;
                for(VersesAndCount isi : hasilPlace){
                    try{
                        String[] splitPlace = isi.getVerses().split(",");
                        for (String ayatPlace :splitPlace){
                            tampilPlace.add(ayatPlace+"\n"+cekAyat(ayatPlace));
                            addPeoplePlacesMap(ayatPlace);
                            count++;
                        }
                    }
                    catch(Exception e){
                        tampilPlace.add(isi.getVerses()+"\n"+cekAyat(isi.getVerses()));
                        addPeoplePlacesMap(isi.getVerses());
                        count++;
                    }
       
                }
                listViewPlace.setItems(tampilPlace);

    
    
                ObservableList<String> tampilEvent = FXCollections.observableArrayList();
                for(EventHandle isi2 : hasilEvent){
                    if(isi2.getVerses() != null){
                        try{
                            String[] splitEvent = isi2.getVerses().split(",");
                            for(String ayatEvent : splitEvent){
                                tampilEvent.add(ayatEvent +"\n"+cekAyat(ayatEvent));
                                addPeoplePlacesMap(ayatEvent);
                                count++;
                            }
                        }
                        catch(Exception e){
                            tampilEvent.add(isi2.getVerses()+"\n"+isi2.getVerses());
                            addPeoplePlacesMap(isi2.getVerses());
                            count++;
                        }
                    }   

                }
                listViewEvent.setItems(tampilEvent);
                labelJumlah.setText(count + " hasil pencarian telah ditemukan untuk " + '"'+search.getText()+'"');
    
    
                if(hasilPlace.isEmpty()){
                    tp.getSelectionModel().select(tabEvent);
                }
                else if(hasilEvent.isEmpty()){
                    tp.getSelectionModel().select(tabPlace);
                }
                else if(hasilEvent != null && hasilPlace != null){
                    tp.getSelectionModel().select(tabPlace);
                }
    
    
                Map<String, Integer> sortedPeopleMap = sortByValue(peopleMap);
                Map<String, Integer> sortedPlacesMap = sortByValue(placesMap);
    
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
            else{
                alertNotFound(event);
            }
        }
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
    

    // sort hashmap by value. source geeksforgeeks
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
	{
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer> > list =
			new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
			public int compare(Map.Entry<String, Integer> o1,
							Map.Entry<String, Integer> o2)
			{
				return (o2.getValue()).compareTo(o1.getValue());
            }
		});
		
		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
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
    
    private String cekAyat(String ayat){
        // database db = new database();
        String hasil = db.viewAyat(ayat);
        return hasil;
    }
    
    
    public void switchToHomePage(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("New Jerusalem");
        scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
        stage.setScene(scene);
        stage.show();
    }


    private  void alertNotFound (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
       
        String olah = search.getText();
        alert.setTitle("New Jerusalem");
        alert.setHeaderText(null);
        alert.setContentText( olah + " tidak ditemukan");
        alert.showAndWait();
        
    }
    
    private boolean alertEmpty(){
        String olah = search.getText();
        if(olah.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
            alert.setHeaderText(null);
            alert.setContentText("Pencarian masih kosong");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    
}