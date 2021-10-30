package id.ac.ukdw.fti.rpl.theartificier;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        // stage.getIcons().add(new Image(new File("appicon.png").toURI().toString()));
        stage.setTitle("New Jerusalem");
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
