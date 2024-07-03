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
    private final ArrayList<Game> gamesPlayed = new ArrayList<>();

    public String toJson() {
        return "{user(name<" + username + ">)(password<" + password + ">)(nickname<" + nickname + ">)(email<" + email +
                ">)(factionChosen<" + faction.toJson() + ">)(factionLeader<" + factionLeaderCard.toJson() + ">)(board<" + playBoard.toJson() +
                ">)(score<" + score + ">)(wins<" + numOfWins + ">)(losts<" + numOfLosts + ">)(draws<" + numOfDraws + ">)(questionChosen<" + question.toJson() +
                ">)}";
    }

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

    public static void removeFromUsers(User user) {
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

    public PlayBoard getPlayBoard() {
        return playBoard;
    }

    public static User getRegisteringUser() {
        return registeringUser;
    }

    public static void setRegisteringUser(User registeringUser) {
        User.registeringUser = registeringUser;
    }

    public boolean isUserRequestForgotPassword() {
        return isUserRequestForgotPassword;
    }

    public void setUserRequestForgotPassword(boolean userRequestForgotPassword) {
        isUserRequestForgotPassword = userRequestForgotPassword;
    }

    public boolean isUserRegisterSuccesfully() {
        return isUserRegisterSuccesfully;
    }

    public void setUserRegisterSuccesfully(boolean userRegisterSuccesfully) {
        isUserRegisterSuccesfully = userRegisterSuccesfully;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    public int getNumOfLosts() {
        return numOfLosts;
    }

    public void setNumOfLosts(int numOfLosts) {
        this.numOfLosts = numOfLosts;
    }

    public int getNumOfDraws() {
        return numOfDraws;
    }

    public void setNumOfDraws(int numOfDraws) {
        this.numOfDraws = numOfDraws;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public FactionLeaderCard getFactionLeaderCard() {
        return factionLeaderCard;
    }

    public void setFactionLeaderCard(FactionLeaderCard factionLeaderCard) {
        this.factionLeaderCard = factionLeaderCard;
    }

    public void setPlayBoard(PlayBoard playBoard) {
        this.playBoard = playBoard;
    }

    public ArrayList<Game> getGamesPlayed() {
        return gamesPlayed;
    }
}
