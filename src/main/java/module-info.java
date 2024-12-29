module com.example.scenebuildercode {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires transitive com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.naming;

    opens com.example.scenebuildercode to javafx.fxml;
    exports com.example.scenebuildercode;

}