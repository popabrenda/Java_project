module com.example.lab4_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.xerial.sqlitejdbc;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
      requires org.slf4j;
    opens com.example.lab4_fx to javafx.fxml;
    exports com.example.lab4_fx;
}