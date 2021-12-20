package id.ac.ukdw.fti.rpl.theartificier;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.DataMaps;
import id.ac.ukdw.fti.rpl.theartificier.modal.OlahDataMaps;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VisualisasiUtamaController implements Initializable{

    @FXML
    private Button pindahHalaman;

    @FXML
    private Button btnHomePage;

    @FXML
    private AnchorPane AnchorMaps;

    private HashMap<String, DataMaps> placesMap= new HashMap<String, DataMaps>();
    private Database db = new Database();
    // private final Double MAX_WIDTH = 900.0;
    // private final Double MAX_HEIGHT = 465.0;

    // private final Double MIN_X =  -17.458756;
    // private final Double MAX_X = 85.242152;

    // private final Double MAX_Y = 3.399022;
    // private final Double MIN_Y = 50.733410;

    private final Double MAX_WIDTH = 1004.0;
    private final Double MAX_HEIGHT = 671.0;

    private final Double MIN_X =  4.33299;
    private final Double MAX_X = 52.23635;

    private final Double MAX_Y = 16.1033;
    private final Double MIN_Y = 44.75533;

    private final String BUTTON_STYLE = "-fx-background-radius: 5em; -fx-min-width: 7px; -fx-min-height: 7px; -fx-max-width:7px; -fx-max-height:7px; -fx-background-color:blue;";

    @FXML
    void switchHalaman(ActionEvent event) {

    }

    @FXML
    void switchToHomePage(ActionEvent event) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        ArrayList<DataMaps> dataMaps = db.dataMaps();
        
        OlahDataMaps olah = new OlahDataMaps();
        olah.olahData(dataMaps);
        // for ( String key : olah.getListEvent().keySet() ) {
        //     // System.out.println( olah.getListEvent().get(key).getNamaLocation());
        //     System.out.println(olah.getListEvent().get(key).getLatitude());
        // }
        
        // for(String key: olah.getListEvent().keySet()){
        //     Button btn = new Button();
        //     btn.setLayoutX(hitungXY(olah.getListEvent().get(key).getLongitude(), MAX_X, MIN_X));
        //     btn.setLayoutY(hitungXY(olah.getListEvent().get(key).getLatitude(), MAX_Y, MIN_Y));
        //     btn.setStyle(BUTTON_STYLE);
        //     for(String key2: olah.getListEvent().get(key).getTitleDuration().keySet()){
        //         System.out.println(key2);
        //     }
        // }

        AnchorMaps.getChildren().addAll(createPoint(olah));

        // Button btn = new Button();
        // btn.setLayoutX(0);
        // btn.setLayoutY(0);
        // btn.setStyle(BUTTON_STYLE);
        // ArrayList<Button> btnList = new ArrayList<Button>();
        // btnList.add(btn);
        // AnchorMaps.getChildren().addAll(btnList);
        System.out.println("finish");
    }

    private Double hitungXY(Double input, Double max, Double min){
        return ((input-min)/(max-min));
    }

    
    private ArrayList<Button> createPoint(OlahDataMaps olahData){
        ArrayList<Button> btnList = new ArrayList<Button>();
        // key nya namaLocation
        for(String key: olahData.getListEvent().keySet()){
            Button btn = new Button();
            // Double x = (hitungXY(olahData.getListEvent().get(key).getLongitude(), MAX_X, MIN_X) * MAX_WIDTH) - 10;
            // Double y = (hitungXY(olahData.getListEvent().get(key).getLatitude(), MAX_Y, MIN_Y) * MAX_HEIGHT) +31;
            Double x = (hitungXY(olahData.getListEvent().get(key).getLongitude(), MAX_X, MIN_X) * MAX_WIDTH) - 30;
            Double y = (hitungXY(olahData.getListEvent().get(key).getLatitude(), MAX_Y, MIN_Y) * MAX_HEIGHT) + 21;
            // System.out.println(x);
            // btn.setLayoutX(hitungXY(olahData.getListEvent().get(key).getLongitude(), MAX_X, MIN_X));
            // btn.setLayoutY(hitungXY(olahData.getListEvent().get(key).getLatitude(), MAX_Y, MIN_Y));
            btn.setLayoutX(x);
            btn.setLayoutY(y);
            btn.setStyle(BUTTON_STYLE);
            String tooltip = "";
            for(String key2: olahData.getListEvent().get(key).getTitleDuration().keySet()){
                // key2(title) : valuenya key2(duration)
                tooltip += key2 + " : " + olahData.getListEvent().get(key).getTitleDuration().get(key2) + "\n";
            }
            // System.out.println(tooltip + " " + x + " " + y);
                System.out.println(key + " " + x + " " + y);
            btn.setTooltip(new Tooltip(tooltip));
            btnList.add(btn);
            // if(count == 2){
            //     break;
            // }
            // count++;
        }
        return btnList;
        
    }

}
