package network;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private static final int PORT = 8080;
    private ServerSocket serverSocket;

    public GameServer() {
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Game server started on port " + PORT);
            while (true) {
                Socket player1 = serverSocket.accept();
                System.out.println("Player 1 connected: " + player1.getInetAddress());
                Socket player2 = serverSocket.accept();
                System.out.println("Player 2 connected: " + player2.getInetAddress());
                GameSession gameSession = new GameSession(player1,player2);
                gameSession.run();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }
}
