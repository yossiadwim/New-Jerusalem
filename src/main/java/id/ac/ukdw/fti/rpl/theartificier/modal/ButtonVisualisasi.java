
package id.ac.ukdw.fti.rpl.theartificier.modal;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ButtonVisualisasi {

    private Button btn;
    private Tooltip tooltip;
    private String bookDiv;
    private String kitab;
    private int pasal;
    private int ayat;

    public ButtonVisualisasi(Button btn, Tooltip tooltip, String kitab){
        this.btn = btn;
        this.tooltip = tooltip;
        this.kitab = kitab;
    }
}
