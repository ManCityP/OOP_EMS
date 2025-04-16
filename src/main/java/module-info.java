module com.fhm.oop_ems {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.fhm.oop_ems to javafx.fxml;
    exports com.fhm.oop_ems;
}