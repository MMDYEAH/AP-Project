module GWENT {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

    exports view;
    opens view to javafx.fxml;

}
