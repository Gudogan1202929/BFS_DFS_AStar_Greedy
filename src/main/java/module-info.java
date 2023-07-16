module com.example.proj1_ai {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proj1_ai to javafx.fxml;
    exports com.example.proj1_ai;
}