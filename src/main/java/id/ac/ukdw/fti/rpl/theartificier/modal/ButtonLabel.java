package id.ac.ukdw.fti.rpl.theartificier.modal;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * 
 */
public class ButtonLabel {
    private Button btn;
    private Label lbl;
    public ButtonLabel(Button btn, Label lbl){
        this.btn = btn;
        this.lbl = lbl;
    }
    public Button getButton(){
        return this.btn;
    }
    public Label getLabel(){
        return this.lbl;
    }
    public void setVisible(Boolean bool){
        btn.setVisible(bool);
        lbl.setVisible(bool);
    }

}
