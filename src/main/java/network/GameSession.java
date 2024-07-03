package network;

import model.Game;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class GameSession implements Runnable {
    private final ClientHandler player1;
    private final ClientHandler player2;
    private Game game;

    public GameSession(ClientHandler player1, ClientHandler player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        try {
            DataInputStream player1in = new DataInputStream(player1.getSocket().getInputStream());
            DataInputStream player2in = new DataInputStream(player2.getSocket().getInputStream());
            DataOutputStream player1out = new DataOutputStream(player1.getSocket().getOutputStream());
            DataOutputStream player2out = new DataOutputStream(player2.getSocket().getOutputStream());

            boolean p1isStart = false;
            boolean p2isStart = false;
            User p1 = player1.getUser(), p2 = player2.getUser();

            while (true) {
                if (player1in.available() > 0) {
                    String p1request = player1in.readUTF();
                    if (p1request.matches("start game")) {
                        p1isStart = true;
                    } else if (p1request.matches("put card:.*")) {
                        // Handle put card logic for player 1
//                        game.handleCardAction(p1, p1request);
                        sendGameStateToPlayers();
                    }
                }
                if (player2in.available() > 0) {
                    String p2request = player2in.readUTF();
                    if (p2request.matches("start game")) {
                        p2isStart = true;
                    } else if (p2request.matches("put card:.*")) {
                        // Handle put card logic for player 2
//                        game.handleCardAction(p2, p2request);
                        sendGameStateToPlayers();
                    }
                }
                if (p1isStart && p2isStart) {
                    game = new Game(p1, p2, new Date());
                    sendGameStateToPlayers();
                    p1isStart = false;
                    p2isStart = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGameStateToPlayers() {
        String gameState = serializeGameState(game);
        player1.sendMessage(gameState);
        player2.sendMessage(gameState);
    }

    private String serializeGameState(Game game) {
        // Convert game state to a string (e.g., JSON)
        return "GameState";
    }
}
