
package id.ac.ukdw.fti.rpl.theartificier;

import java.io.File;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       

        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
    
       
        
        stage.setTitle("New Jerusalem");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/id/ac/ukdw/fti/rpl/theartificier/style.css");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/appicon.png")));
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