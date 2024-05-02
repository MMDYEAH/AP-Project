package model;

import java.util.ArrayList;

public class User {
    private static User loggenInUser;
    private static ArrayList<User> users;
    private String username,password,nickname,email;
    private Question question;
    private boolean isUserRequestForgotPassword, isUserRegisterSuccesfully;
    private int score,numOfWins,numOfLosts,numOfDraws;
    private Faction faction;
    private FactionLeaderCard factionLeaderCard;
    private PlayBoard playBoard;
    private ArrayList<Game> gamesPlayed = new ArrayList<>();
    public static User getUserByUsername(String username){
        return null;//TODO: delete this code and write
    }
    public static void addToUsers(User user){}

    public static User getLoggenInUser() {
        return loggenInUser;
    }

    public static void setLoggenInUser(User loggenInUser) {
        User.loggenInUser = loggenInUser;
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

    public static void removeFromUsers(User user){}
}
