
package id.ac.ukdw.fti.rpl.theartificier.modal;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class BarVisualisasi {
    private Rectangle rect;
    private Label lbl;

    public BarVisualisasi(Rectangle rect, Label lbl){
        this.rect = rect;
        this.lbl = lbl;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Label getLbl() {
        return lbl;
    }
}
