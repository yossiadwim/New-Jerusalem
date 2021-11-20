
package id.ac.ukdw.fti.rpl.theartificier.modal;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;

public class BarVisualisasi {
    private Rectangle rect;
    private Label lbl;
    private Tooltip tooltip;

    public BarVisualisasi(Rectangle rect, Label lbl){
        this.rect = rect;
        this.lbl = lbl;
    }

    public BarVisualisasi(Rectangle rect, Label lbl, Tooltip tooltip){
        this.rect = rect;
        this.lbl = lbl;
        this.tooltip = tooltip;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Label getLbl() {
        return lbl;
    }
}
