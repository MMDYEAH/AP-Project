module GWENT {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires java.mail;
    requires jjwt;
    requires spark.core;
    requires java.xml.bind;
    requires javafx.swing;

    exports view;
    exports network;
    opens view to javafx.fxml, com.google.gson;
    opens model to com.google.gson;
    opens network to com.google.gson;
    opens controller to com.google.gson;
}
