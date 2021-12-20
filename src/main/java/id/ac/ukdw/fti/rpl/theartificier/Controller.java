package id.ac.ukdw.fti.rpl.theartificier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.BarVisualisasi;
import id.ac.ukdw.fti.rpl.theartificier.modal.ButtonLabel;
import id.ac.ukdw.fti.rpl.theartificier.modal.EventHandle;
import id.ac.ukdw.fti.rpl.theartificier.modal.VersesAndCount;
import javafx.collections.FXCollections;
import javafx.scene.image.Image ;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable{

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

    @FXML
    private Button buttonVisual;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static String click;
    public static String judul;
    public static ArrayList<String> ayat = new ArrayList<>();
    
    static Database db = new Database();

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
    public static String searchInitialize = "";
    public static boolean isFromVisualUtama = false;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(!searchInitialize.equals("")){
            ControllerDetail.searchInitialize = searchInitialize;
            search.setText(searchInitialize);
            tampilHasilSearch();
        }
        if(isFromVisualUtama){
            search.setText(searchInitialize);
        }
    }

    @FXML
    public void onEnter(ActionEvent event){
        
        //ini kalo di enter jalanin handleButton
        handleButtonAction(event);
    }

    //method dibawah di ekseskusi abis enter
    @FXML
    private void handleButtonAction(ActionEvent event){
        tampilHasilSearch(event);
    }
    
    // method dipanggil saat kembali dari HomePageController
    private void tampilHasilSearch(){
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

        ControllerDetail.searchInitialize = search.getText().toLowerCase();

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
                            tampilPlace.add(ayatPlace+"\n"+wrapText(cekAyat(ayatPlace), 82, " ") + "\n ");
                            addPeoplePlacesMap(ayatPlace);
                            count++;
                        }
                    }
                    catch(Exception e){
                        tampilPlace.add(isi.getVerses()+"\n"+wrapText(cekAyat(isi.getVerses()), 82, " ") + "\n ");
                        addPeoplePlacesMap(isi.getVerses());
                        count++;
                    }
                }
                listViewPlace.setItems(tampilPlace);

                ObservableList<String> tampilEvent = FXCollections.observableArrayList();
                for(EventHandle isi2 : hasilEvent){
                    if(isi2.getVerses() != null){
                        // tampilEvent.add(isi2.getTitle()+"\n"+isi2.getVerses());
                        tampilEvent.add(isi2.getTitle()+"\n"+wrapText(isi2.getVerses(), 82, ","));
                    }
                    else{
                        tampilEvent.add(isi2.getTitle()+"\n" + "No data available");
                    }
                    
                    try{
                        String[] splitEvent = isi2.getVerses().split(",");
                        for(String ayatEvent : splitEvent){
                            addPeoplePlacesMap(ayatEvent);
                            count++;
                        }
                    }
                    catch(Exception e){
                        addPeoplePlacesMap(isi2.getVerses());
                        count++;
                    }
                }
                listViewEvent.setItems(tampilEvent);

                listViewEvent.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {  
                        try{
                            if(listViewEvent.getSelectionModel().getSelectedItem() != null){
                                click = listViewEvent.getSelectionModel().getSelectedItem().toString();

                                // String[] split = click.replace("\n","").split(",");
                                // judul = split[0];
                                // for(int i=1;i< split.length;i++){
                                //     ayat.add(split[i]);
                                // }
                                click = click.substring(0,click.indexOf("\n")) +"," + click.substring(click.indexOf("\n")+1, click.length()).replace("\n", "");
                                String[] split = click.split(",");
                                judul = split[0];

                                for(int i = 1 ; i<split.length ;i++){
                                    ayat.add(split[i]);
                                }
                                // String aaa = split[1];
                                
                                // for (String i : split) {
                                //     System.out.println(i);
                                // }


                                // String[] split = click.split("\n");
                                // judul = split[0];
                                // String[] ayt = split[1].replace("\n", "").split(",");
                                
                                // for(int i=1; i<split[1].length(); i++){
                                //     ayat.add(split[1][i]);
                                // }
                                // for(String i : ayt){
                                //     System.out.println("Line 279 " + i);
                                //     ayat.add(i);
                                // }
                                    
                                root = FXMLLoader.load(getClass().getResource("Detail.fxml"));
                                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                                stage.setTitle("New Jerusalem");
                                scene = new Scene(root);
                                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
                                stage.setScene(scene);
                                stage.show();
                            }
                            
                        
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });


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
        }
    }

    // method dipanggil saat melakukan search
    private void tampilHasilSearch(ActionEvent event){
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

        ControllerDetail.searchInitialize = search.getText().toLowerCase();

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
                            tampilPlace.add(ayatPlace+"\n"+wrapText(cekAyat(ayatPlace), 82, " ") + "\n ");
                            addPeoplePlacesMap(ayatPlace);
                            count++;
                        }
                    }
                    catch(Exception e){
                        tampilPlace.add(isi.getVerses()+"\n"+wrapText(cekAyat(isi.getVerses()), 82, " ") + "\n ");
                        addPeoplePlacesMap(isi.getVerses());
                        count++;
                    }
                }
                listViewPlace.setItems(tampilPlace);
                
                ObservableList<String> tampilEvent = FXCollections.observableArrayList();
                for(EventHandle isi2 : hasilEvent){
                    if(isi2.getVerses() != null){
                        // tampilEvent.add(isi2.getTitle()+"\n"+isi2.getVerses());
                        tampilEvent.add(isi2.getTitle()+"\n"+wrapText(isi2.getVerses(), 82, ","));
                    }
                    else{
                        tampilEvent.add(isi2.getTitle()+"\n" + "No data available");
                    }
                    
                    try{
                        String[] splitEvent = isi2.getVerses().split(",");
                        for(String ayatEvent : splitEvent){
                            addPeoplePlacesMap(ayatEvent);
                            count++;
                        }
                    }
                    catch(Exception e){
                        addPeoplePlacesMap(isi2.getVerses());
                        count++;
                    }
                }
                listViewEvent.setItems(tampilEvent);

                listViewEvent.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event){

                        try{

                            if(listViewEvent.getSelectionModel().getSelectedItem() != null){
                                click = listViewEvent.getSelectionModel().getSelectedItem().toString();
                                click = click.substring(0,click.indexOf("\n")) +"," + click.substring(click.indexOf("\n")+1, click.length()).replace("\n", "");
                                String[] split = click.split(",");
                                judul = split[0];

                                for(int i = 1 ; i<split.length ;i++){
                                    ayat.add(split[i]);
                                }

                                root = FXMLLoader.load(getClass().getResource("Detail.fxml"));
                                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                                stage.setTitle("New Jerusalem");
                                scene = new Scene(root);
                                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
                                stage.setScene(scene);
                                stage.show();
                            }
                        }
                        catch(IOException e){
                            e.getMessage();
                        }
                    } 
                });


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

    public static String wrapText(String text, int len, String delimiter){
        String[] split = text.split(delimiter);
        int count = 0;
        // int check = 82;
        int check = len;
        String output = "";
        for(String i: split){
            if(count+i.length() + 1 > check){
                output += "\n";
                output += i + delimiter;
                // check += 82;
                check += len;
            }
            else{
                output += i + delimiter;
            }
            count+=i.length();
        }
        output = output.substring(0,output.length()-1);
        return output;
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
    
    public static String cekAyat(String ayat){
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

    public void switchToVisualisasiPage(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("VisualisasiUtama.fxml"));
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