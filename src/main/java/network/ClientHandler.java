package network;

import controller.LoginMenuController;
import enums.LoginMenuCommands;
import enums.MainMenuCommands;
import model.ClearWeather;
import model.Question;
import model.Result;
import model.User;

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
                } else if (message.startsWith("accept")) {
                    handleGameAcceptance(message);
                } else if (message.startsWith("put card")) {
                    handleGameAction(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Result result = controller.register(username, password, confirm, nickname, email, new Question(question,answer));
        this.user = User.getUserByUsername(username);
        System.out.println(result);
        sendMessage(result.toString());
    }

    private void handleLogin(Matcher matcher) {
        System.out.println("Username: " + matcher.group("username"));
        System.out.println("Password: " + matcher.group("password"));
        String username = matcher.group("username");
        String password = matcher.group("password");
        Result result = controller.login(username,password);
        sendMessage(result.toString());
    }

    private void handleGameRequest(Matcher matcher) {
        String username = matcher.group("username");
        for (ClientHandler clientHandler : server.getClients()){
            if (clientHandler.getUser().getUsername().equals(username)){
                clientHandler.sendMessage("");
            }
        }
    }

    private void handleGameAcceptance(String message) {
        String[] parts = message.split(" ");
        String requesterUsername = parts[1];

        synchronized (server.getClients()) {
            Optional<ClientHandler> requesterHandler = server.getClients().stream()
                    .filter(ch -> ch.getUser() != null && ch.getUser().getUsername().equals(requesterUsername))
                    .findFirst();
            if (requesterHandler.isPresent()) {
                GameSession gameSession = new GameSession(requesterHandler.get(), this);
                server.getExecutorService().submit(gameSession);
            } else {
                sendMessage("Game acceptance failed: requester not found or not online");
            }
        }
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
