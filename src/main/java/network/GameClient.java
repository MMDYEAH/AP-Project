package network;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import model.App;
import view.LoginMenu;

import java.io.*;
import java.net.Socket;

public class GameClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader userInput;

    public void start() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to server. Game starting...");

            //socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getOut() {
        return out;
    }

    public DataInputStream getIn() {
        return in;
    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.start();
    }
}