package network;

import com.google.gson.Gson;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import model.Card;
import model.Game;
import model.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameSession implements Runnable {
    private Socket player1Socket;
    private Socket player2Socket;
    Game game;

    public GameSession(Socket player1Socket, Socket player2Socket) {
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;
    }

    @Override
    public void run() {
        try {
            // Initialize game state
//            Game game = new Game(new User("s", "s", "s", "s"),
//                    new User("s", "s", "s", "s"), new Date());
//            Game.setCurrentGame(game);
            // Set up network handlers for each player
//            NetworkHandler player1Handler = new NetworkHandler(player1Socket, game, true);
//            NetworkHandler player2Handler = new NetworkHandler(player2Socket, game, false);
            DataInputStream player1in = new DataInputStream(player1Socket.getInputStream());
            DataInputStream player2in = new DataInputStream(player2Socket.getInputStream());
            while (true) {
                String p1request = player1in.readUTF();
                String p2request = player2in.readUTF();
                if (p1request.matches("start game : .*")) {
                    String userJson = p1request.substring(13);
                    System.out.println(userJson);
                    String name = extractField(userJson, "name");
                    String password = extractField(userJson, "password");
                    String nickname = extractField(userJson, "nickname");
                    String email = extractField(userJson, "email");
                    String score = extractField(userJson, "score");
                    String wins = extractField(userJson, "wins");
                    String losts = extractField(userJson, "losts");
                    String draws = extractField(userJson, "draws");
                    String question = extractField(userJson, "question");
                    String answer = extractField(userJson, "answer");
                    ArrayList<Card> deckUnitCards = extractCards(userJson, "deckUnit");
                    ArrayList<Card> handUnitCards = extractCards(userJson, "handUnit");
                    ArrayList<Card> closeUnitCards = extractCards(userJson, "closeUnit");
                    ArrayList<Card> rangedUnitCards = extractCards(userJson, "rangedUnit");
                    ArrayList<Card> siegeUnitCards = extractCards(userJson, "siegeUnit");
                    ArrayList<Card> discardUnitCards = extractCards(userJson, "discardUnit");
                }
            }


//            // Start the game loop
//            while (!game.isGameOver()) {
//                // Handle player actions
//                if (game.getCurrentUser() == game.getMe()) {
//                    player1Handler.handlePlayerTurn();
//                } else {
//                    player2Handler.handlePlayerTurn();
//                }
//
//                // Update game state
//                // Send updated state to both players
//                String gameState = serializeGameState(game);
//                player1Handler.sendGameState(gameState);
//                player2Handler.sendGameState(gameState);
//            }

            // Game over, close connections
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractField(String input, String fieldName) {
        String patternString = fieldName + "<(.*?)>";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static ArrayList<Card> extractCards(String input, String unitType) {
        ArrayList<Card> cards = new ArrayList<>();
        String unitPatternString = unitType + "\\<\\{unit\\(name<" + unitType.replace("Unit", "") + ">\\)\\(cardsArray\\<(.*?)\\>\\)\\}\\>";
        Pattern unitPattern = Pattern.compile(unitPatternString);
        Matcher unitMatcher = unitPattern.matcher(input);
        if (unitMatcher.find()) {
            String cardsArray = unitMatcher.group(1);
            String cardPatternString = "\\{card\\(name<(.*?)>\\)\\(path<(.*?)>\\)\\(unit<(.*?)>\\)\\}";
            Pattern cardPattern = Pattern.compile(cardPatternString);
            Matcher cardMatcher = cardPattern.matcher(cardsArray);
            while (cardMatcher.find()) {
                String name = cardMatcher.group(1);
                String path = cardMatcher.group(2);
                String unit = cardMatcher.group(3);
                String type = card.getName().substring(card.getName().indexOf("<"));
                type = type.substring(1, type.length() - 1);
                if (type.equals("close")) {

                } else if (type.equals("ranged")) {
                } else if (type.equals("siege")) {
                } else if (type.equals("agile")) {
                } else if (type.equals("weather")) {

                }
                cards.add();
            }
        }

        return cards;
    }

    private static void printCards(String title, ArrayList<Card> cards) {
        System.out.println(title + ": ");
        for (Card card : cards) {
            System.out.println(card);
        }
    }

}


    private String serializeGameState(Game game) {
        // Convert game state to a string (e.g., JSON)
        // For now, just return a placeholder
        return "GameState";
    }
}