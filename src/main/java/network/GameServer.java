package network;

import model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private static final int PORT = 8080;
    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public GameServer() {
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Game server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clients.add(clientHandler);
                executorService.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ClientHandler> getClients() {
        return clients;
    }


    public ExecutorService getExecutorService() {
        return executorService;
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }
}
