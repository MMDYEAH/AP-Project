package network;

import model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkHandler {
    private Socket socket;
    private Game game;
    private boolean isPlayer1;
    private BufferedReader in;
    private PrintWriter out;

    public NetworkHandler(Socket socket, Game game, boolean isPlayer1) throws IOException {
        this.socket = socket;
        this.game = game;
        this.isPlayer1 = isPlayer1;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void handlePlayerTurn() throws IOException {
        String action = in.readLine();
        // Process the action and update the game state
        // For now, just print the action
        System.out.println("Received action: " + action);
    }

    public void sendGameState(String gameState) {
        out.println(gameState);
    }
}
