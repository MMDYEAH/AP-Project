package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.App;
import model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ChatBoxMenu extends Application {
    public TextField message;
    public Button send;
    public ScrollPane scrollPane;
    public VBox vBox;

    private Scene scene;

    private Pane pane;

    private GameMenu gameMenu;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatBoxMenu.class.getResource("/chatBox.fxml"));
        pane = fxmlLoader.load();
        scene = new Scene(pane);
        initialize();
        sendOnMouseClick();
        stage.setScene(scene);
        stage.show();
    }

    private void sendOnMouseClick() {
        send.setOnMouseClicked(mouseEvent -> {
            if (message.getText().equals("") || message.getText().length() > 20 || message.getText().contains("&")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("short or long message (or invalid message:{you can't use \"&\" in chat})");
            } else {
                String time = LocalTime.now().toString();
                App.getGameClient().sendMessage("chat:" + User.getLoggedInUser().getUsername() + "&"
                        + message.getText() + "&" + time);
                addMessage(User.getLoggedInUser().getUsername() + " : " + message.getText() + " ( " + time + " )");
            }

        });
    }

    private void initialize() {
        message = (TextField) scene.lookup("#message");
        send = (Button) scene.lookup("#send");
        scrollPane = (ScrollPane) scene.lookup("#scrollPane");
        vBox = (VBox) scrollPane.getContent().lookup("#vBox");
        for (String message : gameMenu.getMessages()){
            Text text = new Text(message);
            vBox.getChildren().add(text);
        }
    }

    public void addMessage(String message) {
        gameMenu.getMessages().add(message);
        Text text = new Text(message);
        vBox.getChildren().add(text);
    }

    public void setGameMenu(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }
}
