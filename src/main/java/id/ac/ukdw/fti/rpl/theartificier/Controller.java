package id.ac.ukdw.fti.rpl.theartificier;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller{

    @FXML
    private Label labelJumlah;

    @FXML
    private TextField places;

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

    private List<Button> buttonPlace = new ArrayList<>();
    private List<Label> labelPlace = new ArrayList<>();

    private List<Button> buttonEvent = new ArrayList<>();
    private List<Label> labelEvent = new ArrayList<>();
    private int layoutY = 14;

    @FXML
    public void onEnter(ActionEvent event){
        
        handleButtonAction(event);
    }

    @FXML
    private void handleButtonAction(ActionEvent event){

        buttonPlace.clear();
        labelPlace.clear();
        buttonEvent.clear();
        labelEvent.clear();

        layoutY = 14;
        database db = new database();

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
                                buttonPlace.add(btn.getButton());
                                labelPlace.add(btn.getLabel());
                                count += 1;
                                
                            }
                        }   
                        else{
                            
                            ButtonLabel btn = createButtonLabel(verse.getVerses());
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
                                    buttonEvent.add(button.getButton()); 
                                    labelEvent.add(button.getLabel());
                                    count +=1;
                                }
                                
                            }
                            
                            else{
                                ButtonLabel button = createButtonLabel(events.getTitle() + ", " + events.getVerses());
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

        }
    }

    
    private String cekAyat(String ayat){
        database db = new database();
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
            System.out.println(ayat + " " + lebar);
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
            System.out.println(ayat + " " + lebar);
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
