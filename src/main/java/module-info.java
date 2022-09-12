module com.example.proyecto1luisygabriel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.proyecto1luisygabriel to javafx.fxml;
    exports com.example.proyecto1luisygabriel;
}