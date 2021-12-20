
package id.ac.ukdw.fti.rpl.theartificier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import id.ac.ukdw.fti.rpl.theartificier.database.Database;
import id.ac.ukdw.fti.rpl.theartificier.modal.BookChapterNum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class VisualisasiPageController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane VisualisasiUtama;

    @FXML
    private Button pindahHalaman;

    @FXML
    private Button btnHomePage;

    private final int maxWidth = 1000;
    private final int maxHeight = 25;
    private final int layoutX = 66;
    private int layoutY = 0;
    private int layoutXChapter = 66;

    private final String BUTTON_STYLE = "-fx-background-color: #6B7B8E;-fx-text-fill: #ffffff;-fx-font-weight: bold;";
    private final String BUTTON_HOVER = "-fx-background-color: #8FBBAF; -fx-text-fill: #000000;-fx-font-weight: bold;";
    private final String RECT_STYLE = "#ACDEAA";
    private final String TESTAMENT_STYLE = "#D6F8B8";
    Database db = new Database();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        System.out.println("start");
        ArrayList<BookChapterNum> books = db.getAllBooks();
        ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
        ArrayList<Label> lblList = new ArrayList<Label>();
        ArrayList<Button> btnList = new ArrayList<Button>();
        String bookDiv = "";
        for(BookChapterNum book: books){
            if(!bookDiv.equals(book.getBookDiv().getBookDiv())){
                layoutXChapter = 66;
                layoutY += 35;

                // create rect
                Rectangle rect = new Rectangle();
                rect.setHeight(maxHeight);
                rect.setWidth(maxWidth);
                // rect.setFill(Color.GREY);
                rect.setFill(Color.valueOf(RECT_STYLE));
                rect.setLayoutX(layoutX);
                rect.setLayoutY(layoutY);
                rectList.add(rect);
                // selesai rect

                // create label
                Label lbl = new Label(book.getBookDiv().getBookDiv());
                lbl.setPrefHeight(maxHeight);
                lbl.setPrefWidth(maxWidth);
                lbl.setLayoutX(layoutX);
                lbl.setLayoutY(layoutY);
                lbl.setAlignment(Pos.CENTER);
                lbl.setTooltip(new Tooltip(book.getBookDiv().getBookDiv() + "\n" + book.getBookDiv().getVerseTotal() + " verses"));
                lblList.add(lbl);
                // selesai label

                layoutY += 27;


                // create button
                Button btn = new Button(book.getBook());
                double btnWidth = (double)maxWidth/(double) book.getCountBookDiv();
                btn.setPrefWidth(btnWidth);
                btn.setPrefHeight(maxHeight);
                btn.setLayoutX(layoutXChapter);
                btn.setLayoutY(layoutY);
                btn.setStyle(BUTTON_STYLE);
                btn.setCursor(Cursor.HAND);
                btn.setOnMouseEntered(e -> btn.setStyle(BUTTON_HOVER));
                btn.setOnMouseExited(e -> btn.setStyle(BUTTON_STYLE));
                btn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        // TODO Auto-generated method stub
                        HomePageController.kitabAwal = book.getOsisName();
                        HomePageController.isFromVisual = true;
                        try {
                            switchToHomePage(event);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    
                });
                btn.setTooltip(new Tooltip(book.getBook() + "\n" + book.getChapterCount() + " chapters\n" + book.getVerseCount() + " verses\nklik untuk melihat detail"));
                layoutXChapter += btnWidth + 2;
                bookDiv = book.getBookDiv().getBookDiv();
                btnList.add(btn);
            }
            else{

                // create button
                Button btn = new Button(book.getBook());
                double btnWidth = (double)maxWidth/(double) book.getCountBookDiv();
                btn.setPrefWidth(btnWidth);
                btn.setPrefHeight(maxHeight);
                btn.setLayoutX(layoutXChapter);
                btn.setLayoutY(layoutY);
                btn.setStyle(BUTTON_STYLE);
                btn.setCursor(Cursor.HAND);
                btn.setOnMouseEntered(e -> btn.setStyle(BUTTON_HOVER));
                btn.setOnMouseExited(e -> btn.setStyle(BUTTON_STYLE));
                
                btn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        // TODO Auto-generated method stub
                        HomePageController.kitabAwal = book.getOsisName();
                        HomePageController.isFromVisual = true;
                        try {
                            switchToHomePage(event);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    
                });
                btn.setTooltip(new Tooltip(book.getBook() + "\n" + book.getChapterCount() + " chapters\n" + book.getVerseCount() + " verses\nklik untuk melihat detail"));
                layoutXChapter += btnWidth + 2;
                // selesai button
                btnList.add(btn);
                bookDiv = book.getBookDiv().getBookDiv();
            }

        }

        // Label lbl = new Label("perjanjian lama");
        // lbl.setPrefHeight(36);
        // lbl.setPrefWidth(300);
        // lbl.setLayoutX(-111);
        // lbl.setLayoutY(156);
        // lbl.setRotate(270);


        // rect dan label old testament
        Rectangle rectOldTestament = new Rectangle();
        // rectOldTestament.setFill(Color.LIGHTGRAY);
        rectOldTestament.setFill(Color.valueOf(TESTAMENT_STYLE));
        rectOldTestament.setHeight(36);
        rectOldTestament.setWidth(300);
        rectOldTestament.setLayoutX(-111);
        rectOldTestament.setLayoutY(167);
        rectOldTestament.setRotate(270);

        Label lblOldTestament = new Label("Old Testament");
        lblOldTestament.setAlignment(Pos.CENTER);
        lblOldTestament.setPrefHeight(36);
        lblOldTestament.setPrefWidth(300);
        lblOldTestament.setLayoutX(-111);
        lblOldTestament.setLayoutY(167);
        lblOldTestament.setRotate(270);
        // selesai

        // rect dan label old testament
        Rectangle rectNewTestament = new Rectangle();
        // rectNewTestament.setFill(Color.LIGHTGRAY);
        rectNewTestament.setFill(Color.valueOf(TESTAMENT_STYLE));
        rectNewTestament.setHeight(36);
        rectNewTestament.setWidth(300);
        rectNewTestament.setLayoutX(-111);
        rectNewTestament.setLayoutY(477);
        rectNewTestament.setRotate(270);

        Label lblNewTestament = new Label("New Testament");
        lblNewTestament.setAlignment(Pos.CENTER);
        lblNewTestament.setPrefHeight(36);
        lblNewTestament.setPrefWidth(300);
        lblNewTestament.setLayoutX(-111);
        lblNewTestament.setLayoutY(477);
        lblNewTestament.setRotate(270);
        // selesai

        rectList.add(rectOldTestament);
        rectList.add(rectNewTestament);
        lblList.add(lblOldTestament);
        lblList.add(lblNewTestament);
        
        VisualisasiUtama.getChildren().clear();
        VisualisasiUtama.getChildren().addAll(rectList);
        VisualisasiUtama.getChildren().addAll(lblList);
        VisualisasiUtama.getChildren().addAll(btnList);
        System.out.println("finish");
    }

    public void switchToHomePage(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("New Jerusalem");
        scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchHalaman(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("New Jerusalem");
        scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
}
