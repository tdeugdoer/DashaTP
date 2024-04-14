module com.babich.laba6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.babich.laba6 to javafx.fxml;
    exports com.babich.laba6;
    exports com.babich.laba6.model;
    opens com.babich.laba6.model to javafx.fxml;
    exports com.babich.laba6.controller;
    opens com.babich.laba6.controller to javafx.fxml;
}