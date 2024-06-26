package view;

import controller.PreGameMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.App;

import java.io.IOException;

public class PreGameMenu extends Application {

    @FXML
    public ScrollPane scrollPane;
    PreGameMenuController controller = new PreGameMenuController(this);

    @FXML
    public TilePane tilePane;

    @Override
    public void start(Stage stage) throws IOException {
        controller.initialize();
        App.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/PreGame.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);


//        Image image = new Image(getClass().getResource("/pics/neutral/Decoy.jpg").toExternalForm());
//        ImageView imageView = new ImageView(image);
//        scrollPane = (ScrollPane) scene.lookup("#scrollPane");
//        tilePane = (TilePane) scrollPane.getContent().lookup("#tilePane");
//        tilePane.getChildren().add(imageView);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void goToGame(MouseEvent mouseEvent) throws Exception {
        GameMenu gameMenu = new GameMenu();
        this.stop();
        gameMenu.start(App.getStage());
    }
}

