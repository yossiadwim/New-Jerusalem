package id.ac.ukdw.fti.rpl.theartificier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import id.ac.ukdw.fti.rpl.theartificier.database.database;
import id.ac.ukdw.fti.rpl.theartificier.modal.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField places;

    @FXML
    private ImageView image;

    @FXML
    private TabPane tp;

    @FXML
    private Button button;

    @FXML
    private Tab place;

    @FXML
    private ScrollPane scrollpanePlace;

    @FXML
    private Tab event;

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

    database db = new database();

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
        
        handleButtonAction(event);
    }

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

        String olahPlace = places.getText().toLowerCase();
        String olahEvent = places.getText().toLowerCase();

        if(alertEmpty()){
            
            olahPlace = olahPlace.replace(" ", "-");
            olahPlace = String.valueOf(olahPlace.charAt(0)).toUpperCase() + olahPlace.substring(1).toLowerCase();

            ArrayList<versesAndCount> hasilPlace = db.viewDataPlace(olahPlace);

            olahEvent = String.valueOf(olahEvent.charAt(0)).toUpperCase() + olahEvent.substring(1).toLowerCase();
            ArrayList<eventHandle> hasilEvent = db.viewDataEvents(olahEvent);

            int count = 1;
            try{

                if(hasilPlace.isEmpty() && hasilEvent.isEmpty()){

                    alertNotFound(event);
                }

                else{

                    for (versesAndCount verse : hasilPlace) {
                        
                        if(verse.getVerseCount() > 1){

                            String[] split = verse.getVerses().split(",");
                            for(String i: split){
                                
                                ButtonLabel btn = createButtonLabel(i);
                                addPeoplePlacesMap(i);
                                buttonPlace.add(btn.getButton());
                                labelPlace.add(btn.getLabel());
                                count += 1;
                            }
                        }   
                        else{
                            
                            ButtonLabel btn = createButtonLabel(verse.getVerses());
                            addPeoplePlacesMap(verse.getVerses());
                            buttonPlace.add(btn.getButton());
                            labelPlace.add(btn.getLabel());
                            count += 1;
                        }
                    }
                    layoutY = 14;

                    for (eventHandle events: hasilEvent) {

                        if(events.getVerses() != null){
                            String[] splitEvent = events.getVerses().split(",");
                            if(splitEvent.length > 1){
                                for (String a : splitEvent) {
                                    ButtonLabel button = createButtonLabel(events.getTitle()+", " + a);
                                    addPeoplePlacesMap(a);
                                    buttonEvent.add(button.getButton()); 
                                    labelEvent.add(button.getLabel());
                                    count +=1;
                                }
                                
                            }
                            
                            else{
                                ButtonLabel button = createButtonLabel(events.getTitle() + ", " + events.getVerses());
                                addPeoplePlacesMap(events.getVerses());
                                buttonEvent.add(button.getButton());
                                labelEvent.add(button.getLabel());
                                count +=1;
                            } 
                        }   
                    }

                    labelJumlah.setText(count - 1+ " hasil telah ditemukan untuk '" + places.getText() + "'");
                }
            }
            catch(Exception e){
                e.getMessage();
                
            }

            tampilAyatTempat.getChildren().clear();
            tampilAyatTempat.getChildren().addAll(buttonPlace);
            tampilAyatTempat.getChildren().addAll(labelPlace);
        
            tampilAyatEvent.getChildren().clear();
            tampilAyatEvent.getChildren().addAll(buttonEvent);
            tampilAyatEvent.getChildren().addAll(labelEvent);

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
    }

    private void addPeoplePlacesMap(String ayat){

        versesAndCount vac = db.viewVisualisasiUtama(ayat);
        if(vac.getPeopleCount() > 1){
            String[] splitPeople = vac.getPeople().split(",");
            for(String j: splitPeople){
                try{
                    peopleMap.put(j, peopleMap.get(j)+1);
                }
                catch(Exception e){
                    peopleMap.put(j, 1);
                }
            }
        }
        else{
            try{
                peopleMap.put(vac.getPeople(), peopleMap.get(vac.getPeople())+1);
            }
            catch(Exception e){
                peopleMap.put(vac.getPeople(), 1);
            }
        }

        if(vac.getPlacesCount() > 1){
            String[] splitPlaces = vac.getPlaces().split(",");
            for(String j: splitPlaces){
                try{
                    placesMap.put(j, placesMap.get(j)+1);
                }
                catch(Exception e){
                    placesMap.put(j, 1);
                }
            }
        }
        else{
            try{
                placesMap.put(vac.getPlaces(), placesMap.get(vac.getPlaces())+1);
            }
            catch(Exception e){
                placesMap.put(vac.getPlaces(), 1);
            }
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
    
    private BarVisualisasi createBarVisualisasi(String nama, double count, double jumlah, String keterangan){
        double width = (count/jumlah) * maxRect;
        Label lbl = new Label(nama + " = " + (int) count);
        Rectangle rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(heightRect);
        rect.setX(XBar);
        rect.setFill(Color.GREY);
        if(keterangan.equals("people")){
            rect.setY(YBarPeople);
            lbl.setLayoutY(layoutYBarPeople);
            YBarPeople += 32;
            layoutYBarPeople += 32;
        }
        else if(keterangan.equals("places")){
            rect.setY(YBarPlaces);
            lbl.setLayoutY(layoutYBarPlaces);
            YBarPlaces += 32;
            layoutYBarPlaces += 32;
        }
        lbl.setLayoutX(layoutXBar);
        BarVisualisasi bar = new BarVisualisasi(rect, lbl);
        return bar;
    }
    private String cekAyat(String ayat){
        // database db = new database();
        String hasil = db.viewAyat(ayat);
        return hasil;
    }
    
    private ButtonLabel createButtonLabel(String ayat){
        
        // EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            
        //     public void handle(ActionEvent e)
        //     {   
                
        //         // String verse = ayat + "\n" + cekAyat(ayat);
        //         // area.setText(verse);
                
                
        //     }
        // };

        try{

            String[] ayatSplit = ayat.split(", ");
            Button btn = new Button(ayat+":");
            Label lbl = new Label();
            String isiAyat = cekAyat(ayatSplit[1]);
            int lenIsiAyat = isiAyat.length();
            
            btn.setLayoutX(7);
            lbl.setLayoutX(0);
            btn.setLayoutY(layoutY);
            layoutY += 20;
            lbl.setLayoutY(layoutY);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-underline: true");
            
            double lebar = lenIsiAyat/39;
            lbl.setPrefHeight(lebar * 20);
            layoutY += (lebar * 20) + 13;

            lbl.setPadding(new javafx.geometry.Insets(0, 20, 0, 10));
            lbl.setText(isiAyat);
            lbl.setWrapText(true);
            lbl.setPrefWidth(349);
            lbl.setEllipsisString(null);
            
            ButtonLabel btnLbl = new ButtonLabel(btn, lbl);
            return btnLbl;
            
        }
        catch(Exception e){

            Button btn = new Button(ayat);
            Label lbl = new Label();
            String isiAyat = cekAyat(ayat);
            int lenIsiAyat = isiAyat.length();
            btn.setLayoutX(7);
            lbl.setLayoutX(0);
            btn.setLayoutY(layoutY);
            layoutY += 20;
            lbl.setLayoutY(layoutY);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-underline: true");
            
            double lebar = lenIsiAyat/39;
            lbl.setPrefHeight(lebar * 20);
            layoutY += (lebar * 20) + 13;
            lbl.setPadding(new javafx.geometry.Insets(0, 20, 0, 10));
            lbl.setText(isiAyat);
            lbl.setWrapText(true);
            lbl.setPrefWidth(349);
            lbl.setEllipsisString(null);
            
            ButtonLabel btnLbl = new ButtonLabel(btn, lbl);
            return btnLbl;
            
        }
        
        
    }

    // @Override
    // public void initialize(URL url, ResourceBundle rb) {

    // }  

    private  void alertNotFound (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // stage.getIcons().add(new Image("/img/appicon.png"));
       
        String olah = places.getText();
        alert.setTitle("New Jerusalem");
        alert.setHeaderText(null);
        alert.setContentText( olah + " tidak ditemukan");
        alert.showAndWait();
        
    }
    
    private boolean alertEmpty(){
        String olah = places.getText().toLowerCase();
        if(olah.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // stage.getIcons().add(new Image("/img/appicon.png"));
            alert.setHeaderText(null);
            alert.setContentText("Pencarian masih kosong");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
