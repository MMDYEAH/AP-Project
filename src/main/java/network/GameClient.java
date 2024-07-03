package network;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.App;
import view.LoginMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameClient extends Application {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private LoginMenu loginMenu;

    @Override
    public void start(Stage primaryStage) {
        try {
            App.setGameClient(this);
            loginMenu = new LoginMenu(this);
            loginMenu.start(primaryStage);

            socket = new Socket(SERVER_IP, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(this::listenForServerMessages).start();
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listenForServerMessages() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.startsWith("Game request from")) {
                    handleGameRequest(message);
                } else if (message.equals("empty field") || message.equals("there is exist an user with this username")
                        || message.equals("wrong username format")
                        || message.equals("wrong email format")
                        || message.equals("wrong password format")
                        || message.equals("weak password")
                        || message.equals("user created successfully")
                        || message.equals("wrong password")
                        || message.equals("confirm password failed")) {
                    signUp(message);
                } else if (message.equals("no such user exist") || message.equals("wrong password in login")
                        || message.equals("login successfully")) {
                    login(message);
                } else {
                    // Handle game state update
                    updateGameState(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(String message) {
        Platform.runLater(()->{
            if (message.equals("login successfully")) {
                loginMenu.loggedInSuccessfullyVideoPlay();
            } else if (message.equals("wrong password in login")) {
                loginMenu.wrongPasswordVideoPlay();
            } else if (message.equals("no such user exist")) {
                loginMenu.noSuchUserExistVideoPlay();
            }
        });
    }

    private void handleGameRequest(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    sendMessage("accept " + message.split(" ")[3]);
                }
            });
        });
    }

    private void updateGameState(String gameState) {
        // Update game UI with the new game state
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signUp(String message) {
        Platform.runLater(() -> {
            if (message.equals("empty field")) {
                loginMenu.emptyFieldVideoPlay();
            } else if (message.equals("there is exist an user with this username")) {
                loginMenu.thereIsExistUserWithUsernameVideoPlay();
            } else if (message.equals("wrong username format")) {
                loginMenu.wrongUsernameFormatVideoPlay();
            } else if (message.equals("wrong email format")) {
                loginMenu.wrongEmailFormatVideoPlay();
            } else if (message.equals("wrong password format")) {
                loginMenu.wrongPasswordFormatVideoPlay();
            } else if (message.equals("weak password")) {
                loginMenu.weakPasswordVideoPlay();
            } else if (message.equals("user created successfully")) {
                loginMenu.userCreatedSuccessfullyVideoPlay();
            } else if (message.equals("wrong password")) {
                loginMenu.wrongPasswordVideoPlay();
            } else if (message.equals("confirm password failed")) {
                loginMenu.confirmPasswordFailedVideoPlay();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
