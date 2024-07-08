package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.App;
import model.User;

public class FriendRequestMenu extends Application {
    public ScrollPane scrollPane;
    public VBox vBox;

    private Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/friendRequest.fxml"));
        pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        scrollPane = (ScrollPane) scene.lookup("#scrollPane");
        vBox = (VBox) scrollPane.getContent().lookup("#vBox");
        App.getGameClient().sendMessage("get users");
        stage.setScene(scene);
        stage.show();
    }

    public void addUser(User user) {
        System.out.println("hi");
        Text text = new Text("username : " +  user.getUsername());
        text.setTextAlignment(TextAlignment.RIGHT);
        text.setOnMouseClicked(mouseEvent -> {
            Text info = new Text("nick name: "+user.getNickname()+" score: "+user.getScore());
            Button send = new Button("send request");
            Button back = new Button("back");
            VBox userInfo = new VBox(info,send,back);
            send.setOnMouseClicked(mouseEvent1 -> {
                App.getGameClient().sendMessage("friend request:"+user.getUsername());
                pane.getChildren().remove(userInfo);
            });
            userInfo.setMaxWidth(500);
            back.setOnMouseClicked(mouseEvent1 -> {
                pane.getChildren().remove(userInfo);
            });
            userInfo.setAlignment(Pos.CENTER);
            pane.getChildren().add(userInfo);
        });
        vBox.getChildren().add(text);
    }
}
