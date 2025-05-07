module com.fhm.oop_ems {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires java.sql;
    requires java.desktop;

    opens com.fhm.oop_ems to javafx.fxml;
    exports com.fhm.oop_ems;
    exports com.fhm.oop_ems.Admin;
    opens com.fhm.oop_ems.Admin to javafx.fxml;
    exports com.fhm.oop_ems.Attendee;
    opens com.fhm.oop_ems.Attendee to javafx.fxml;
    exports com.fhm.oop_ems.Organizer;
    opens com.fhm.oop_ems.Organizer to javafx.fxml;
}