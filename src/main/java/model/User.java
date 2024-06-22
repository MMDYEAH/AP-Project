package model;

import java.util.ArrayList;

public class User {
    public static User registeringUser;
    private static User loggedInUser;
    private static ArrayList<User> users = new ArrayList<>();
    private String username, password, nickname, email;
    private Question question;
    private boolean isUserRequestForgotPassword, isUserRegisterSuccesfully;
    private int score, numOfWins, numOfLosts, numOfDraws;
    private Faction faction;
    private FactionLeaderCard factionLeaderCard;
    private PlayBoard playBoard;
    private ArrayList<Game> gamesPlayed = new ArrayList<>();

    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        users.add(this);
        //TODO: make play board and faction and etc and saving users
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static void addToUsers(User user) {
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public static void removeFromUsers(User user) {
    }
}
