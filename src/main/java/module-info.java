module org.example.recup {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires com.zaxxer.hikari;
    requires java.sql;


    opens org.example.recup to javafx.fxml;
    exports org.example.recup;
}