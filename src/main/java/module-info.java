module com.example.project2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project2 to javafx.fxml;
    exports com.example.project2;
    exports com.example.project2.game;
    opens com.example.project2.game to javafx.fxml;
    exports com.example.project2.menu;
    opens com.example.project2.menu to javafx.fxml;
}