package network;

import controller.LoginMenuController;
import enums.LoginMenuCommands;
import enums.MainMenuCommands;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.regex.Matcher;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final GameServer server;
    private DataInputStream in;
    private DataOutputStream out;
    private User user;

    ClientHandler requester;
    GameSession gameSession;


    private LoginMenuController controller = new LoginMenuController();

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            System.out.println("client join successfully");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String message = in.readUTF();
                System.out.println("m:" + message);
                Matcher matcher;
                if ((matcher = LoginMenuCommands.SIGN_UP.getMatcher(message)) != null) {
                    handleRegister(matcher);
                } else if ((matcher = LoginMenuCommands.LOGIN.getMatcher(message)) != null) {
                    handleLogin(matcher);
                } else if ((matcher = MainMenuCommands.PLAY_REQUEST.getMatcher(message)) != null) {
                    handleGameRequest(matcher);
                } else if (message.equals("accept")) {
                    handleGameAcceptance(message);
                } else if (message.equals("reject")) {
                    handleGameRejectance(message);
                } else if (message.startsWith("ready for game:")) {
                    sendGameReady(message);
                } else if (message.startsWith("friend request:")) {
                    handleFriendRequest(message);
                } else if (message.startsWith("accept friend:")) {
                    handleAcceptFriendRequest(message);
                } else if (message.startsWith("chat:")) {
                    handleChat(message);
                } else if (message.equals("pass")) {
                    requester.sendMessage("pass");
                } else if (message.equals("bad img")) {
                    requester.sendMessage(message);
                } else if (message.equals("nice img")) {
                    requester.sendMessage(message);
                }  else if (message.equals("nice play")) {
                    requester.sendMessage(message);
                }  else if (message.equals("not good")) {
                    requester.sendMessage(message);
                }  else if (message.equals("random game")) {
                    handleRandomGame();
                }  else if (message.equals("cancel random game")) {
                    handleCancelRandomGame();
                } else if (message.equals("get users")) {
                    handleSendingUsers();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleChat(String message) {
        requester.sendMessage(message);
    }

    private void handleCancelRandomGame() {
        server.getRandomGames().remove(this);
    }

    private void handleRandomGame() {
        if (!(server.getRandomGames().size() == 0)) {
            ClientHandler clientHandler = server.getRandomGames().get(0);
            this.requester = clientHandler;
            clientHandler.requester = this;
            gameSession = new GameSession(clientHandler, this);
            requester.gameSession = gameSession;
            gameSession.run();
            server.getRandomGames().remove(clientHandler);
            clientHandler.sendRequest(user.getUsername());
        } else {
            server.getRandomGames().add(this);
        }
    }

    private void handleFriendRequest(String message) {
        String username = message.replaceAll("friend request:", "");
        String sending = message.replaceAll(username, user.getUsername());
        for (ClientHandler clientHandler : server.getClients()) {
            if (clientHandler == this) continue;
            if (clientHandler.getUser().getUsername().equals(username)) {
                clientHandler.sendMessage(sending);
                break;
            }
        }
        user.getFriendsRequest().add(username);
    }

    private void handleAcceptFriendRequest(String message) {
        String username = message.replaceAll("accept friend:", "");
        for (ClientHandler clientHandler : server.getClients()) {
            if (clientHandler == this) continue;
            if (clientHandler.getUser().getUsername().equals(username)) {
                clientHandler.sendMessage("accept friend:" + user.getUsername());
                break;
            }
        }
    }

    private void handleSendingUsers() {
        for (ClientHandler clientHandler : server.getClients()) {
            if (clientHandler == this) continue;
            sendMessage("send user:" + clientHandler.getUser().simpleToJson());
        }
    }

    private void sendGameReady(String message) {
        requester.sendMessage(message);
    }

    private void handleGameRejectance(String message) {
        requester.sendMessage(message);
    }

    private void handleRegister(Matcher matcher) {
        System.out.println("Username: " + matcher.group("username"));
        System.out.println("Password: " + matcher.group("password"));
        System.out.println("Confirm: " + matcher.group("confirm"));
        System.out.println("Nickname: " + matcher.group("nickname"));
        System.out.println("Email: " + matcher.group("email"));
        System.out.println("Question: " + matcher.group("text"));
        System.out.println("Answer: " + matcher.group("answer"));
        System.out.println("handle register");
        String username = matcher.group("username");
        String password = matcher.group("password");
        String confirm = matcher.group("confirm");
        String nickname = matcher.group("nickname");
        String email = matcher.group("email");
        String question = matcher.group("text");
        String answer = matcher.group("answer");
        Result result = controller.register(username, password, confirm, nickname, email, new Question(question, answer));
        this.user = User.getUserByUsername(username);
        user.setClientHandler(this);
        System.out.println(result);
        sendMessage(result.toString());
    }

    private void handleLogin(Matcher matcher) {
        System.out.println("Username: " + matcher.group("username"));
        System.out.println("Password: " + matcher.group("password"));
        String username = matcher.group("username");
        String password = matcher.group("password");
        Result result = controller.login(username, password);
        sendMessage(result.toString());
    }

    private void handleGameRequest(Matcher matcher) {
        String username = matcher.group("username");
        ClientHandler enemy = null;
        for (ClientHandler clientHandler : server.getClients()) {
            System.out.println("username-->" + clientHandler.getUser().getUsername());
            if (clientHandler.getUser().getUsername().equals(username) && clientHandler.getUser() != this.user) {
                enemy = clientHandler;
            }
        }
        if (enemy == null) sendMessage("user not online or not exist");
        else {
            enemy.requester = this;
            this.requester = enemy;
            enemy.sendRequest(user.getUsername());
        }
    }

    private void sendRequest(String user) {
        sendMessage(user + " request game to you");
    }

    private void handleGameAcceptance(String message) {
        requester.sendMessage(message);
        gameSession = new GameSession(requester, this);
        requester.gameSession = gameSession;
        gameSession.run();
    }

    private void handleGameAction(String message) {
        // این قسمت به متدهای داخل کلاس GameSession مربوط می‌شود
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
