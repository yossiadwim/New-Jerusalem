package id.ac.ukdw.fti.rpl.theartificier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TimelineController implements Initializable{

    @FXML
    private Button pindahHalaman;

    @FXML
    private Button btnHomePage;
    
    @FXML
    private AnchorPane anchorTimeline;

    private Database db = new Database();
    private final double MAX_WIDTH = 2000.0;
    private final double COUNT = 2110;
    private double layoutX = 62;
    private double layoutY = 26;
    private double layoutXLbl = 14;

    private Stage stage;
    private Scene scene;
    private Parent root;
    

    private ArrayList<Button> btnList = new ArrayList<Button>();
    private ArrayList<Label> lblList = new ArrayList<Label>();


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ArrayList<Timeline> timelines = db.dataTimeline();
        for(Timeline tl: timelines){
            createButton(tl);
        }

        anchorTimeline.getChildren().addAll(btnList);
        anchorTimeline.getChildren().addAll(lblList);

    }
    public void switchToHomePage(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setTitle("New Jerusalem");
        scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void switchHalaman(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New Jerusalem");
        scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void createButton(Timeline tl){
        String predecessor = "";
        if(tl.getPredecessor().equals("Creation of Adam and Eve")){
            predecessor = "Adam";
        }
        else{
            predecessor = tl.getPredecessor();
        }
        Label lbl = new Label(predecessor);
        lbl.setLayoutX(layoutXLbl);
        lbl.setLayoutY(layoutY);

        Button birth = new Button(String.valueOf(tl.getBirth()));
        birth.setLayoutX(layoutX);
        birth.setLayoutY(layoutY);
        birth.setStyle("-fx-background-color: red");
        String tooltip = "";
        tooltip += tl.getBirth() + " Tahun\n\n";
        for(String keyBirth: tl.getVersesBirthHashMap().keySet()){
            tooltip += keyBirth +": " +  tl.getVersesBirthHashMap().get(keyBirth) + "\n";
        }
        birth.setTooltip(new Tooltip(tooltip));
        double prefWidth = (tl.getBirth()/COUNT) * (MAX_WIDTH - layoutX);
        layoutX += prefWidth;
        layoutXLbl += prefWidth - 5;
        birth.setPrefWidth(prefWidth);

        Button death = new Button(String.valueOf(tl.getDeath()));
        death.setLayoutX(layoutX);
        death.setLayoutY(layoutY);
        death.setStyle("-fx-background-color: skyblue");
        String tooltip2 = "";
        tooltip2 += tl.getDeath() + " Tahun\n\n";
        for(String keyDeath: tl.getVersesDeathMap().keySet()){
            tooltip2 += keyDeath +": " +  tl.getVersesDeathMap().get(keyDeath) + "\n";
        }
        death.setTooltip(new Tooltip(tooltip2));
        double prefWidth2 = (tl.getDeath()/COUNT) * (MAX_WIDTH - layoutX - prefWidth);
        death.setPrefWidth(prefWidth2);

        layoutY += 28;

        // System.out.println(prefWidth + " " + prefWidth2);

        btnList.add(birth);
        btnList.add(death);
        
        lblList.add(lbl);
    }

}
