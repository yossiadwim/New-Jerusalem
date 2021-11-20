
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

    private final int maxWidth = 1000;
    private final int maxHeight = 25;
    private final int layoutX = 66;
    private int layoutY = 0;
    private int layoutXChapter = 66;
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
                rect.setFill(Color.GREY);
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
                btn.setStyle("-fx-cursor: hand;");
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

                // // create rect
                // Rectangle rect = new Rectangle();
                // rect.setHeight(maxHeight);
                // double rectWidth =(double) (1-((double) maxWidth/(double)book.getBookDiv().getVerseTotal())) * (double)maxWidth;
                // rect.setWidth(rectWidth);
                // rect.setFill(Color.GREY);
                // rect.setLayoutX(layoutX);
                // rect.setLayoutY(layoutY);
                // rectList.add(rect);
                // // selesai rect

                // // create label
                // Label lbl = new Label(book.getBookDiv().getBookDiv());
                // lbl.setPrefHeight(maxHeight);
                // lbl.setPrefWidth(rectWidth);
                // lbl.setLayoutX(layoutX);
                // lbl.setLayoutY(layoutY);
                // lbl.setAlignment(Pos.CENTER);
                // lbl.setTooltip(new Tooltip(book.getBookDiv().getBookDiv() + "\n" + book.getBookDiv().getVerseTotal() + " verses"));
                // lblList.add(lbl);
                // // selesai label

                // layoutY += 27;

                // // create button
                // Button btn = new Button();
                // double btnWidth = (double)book.getVerseCount()/(double)book.getBookDiv().getVerseTotal() * (double)rectWidth;
                // double tambah;
                // if(btnWidth <= 29){
                //     btn.setPrefWidth(0);
                //     tambah = 16;
                // }
                // else{
                //     btn.setPrefWidth(btnWidth);
                //     btn.setText(book.getBook());
                //     tambah = btnWidth;
                // }
                // btn.setPrefHeight(maxHeight);
                // btn.setLayoutX(layoutXChapter);
                // btn.setLayoutY(layoutY);
                // btn.setTooltip(new Tooltip(book.getBook() + "\n" + book.getChapterCount() + " chapters\n" + book.getVerseCount() + " verses"));
                // layoutXChapter += tambah + 2;
                // // selesai button

                bookDiv = book.getBookDiv().getBookDiv();
                btnList.add(btn);
            }
            else{
                // // create button
                // Button btn = new Button();
                // double rectWidth = (double)(1-((double)maxWidth/(double)book.getBookDiv().getVerseTotal())) * (double)maxWidth;
                // double btnWidth = (double)book.getVerseCount()/(double)book.getBookDiv().getVerseTotal() * (double)rectWidth;
                // double tambah;
                // btn.setPrefHeight(maxHeight);
                // if(btnWidth <= 29){
                //     btn.setPrefWidth(0);
                //     tambah = 16;
                // }
                // else{
                //     btn.setPrefWidth(btnWidth);
                //     btn.setText(book.getBook());
                //     tambah = btnWidth;
                // }
                // btn.setLayoutX(layoutXChapter);
                // btn.setLayoutY(layoutY);
                // btn.setTooltip(new Tooltip(book.getBook() + "\n" + book.getChapterCount() + " chapters\n" + book.getVerseCount() + " verses"));

                // layoutXChapter += tambah + 2;
                // // selesai button

                // create button
                Button btn = new Button(book.getBook());
                double btnWidth = (double)maxWidth/(double) book.getCountBookDiv();
                btn.setPrefWidth(btnWidth);
                btn.setPrefHeight(maxHeight);
                btn.setLayoutX(layoutXChapter);
                btn.setLayoutY(layoutY);
                btn.setStyle("-fx-cursor: hand;");
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

