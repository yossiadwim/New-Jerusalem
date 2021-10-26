module theartificier {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;
    

    opens id.ac.ukdw.fti.rpl.theartificier to javafx.fxml;
    exports id.ac.ukdw.fti.rpl.theartificier;
    exports id.ac.ukdw.fti.rpl.theartificier.database;
    exports id.ac.ukdw.fti.rpl.theartificier.modal;
}
