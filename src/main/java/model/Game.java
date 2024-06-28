package model;

import controller.GameMenuController;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Game {
    protected User me, enemy,Winner,currentUser,nextUser;
    protected Date date;
    protected int myFinalScore,enemyFinalScore,roundNumber;
    protected HashMap<Integer, ArrayList<Integer>> roundsScore = new HashMap<>();
    protected SpellUnit spellUnit;
    private GameMenuController controller;
    protected boolean mePassRound, enemyPassRound;

    public Game(User me, User enemy, Date date) {
        this.me = me;
        this.enemy = enemy;
        this.date = date;
    }

    protected static Game currentGame;

    public User getNextUser() {
        return nextUser;
    }

    public void setNextUser(User nextUser) {
        this.nextUser = nextUser;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Game.currentGame = currentGame;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public User getEnemy() {
        return enemy;
    }

    public void setEnemy(User enemy) {
        this.enemy = enemy;
    }

    public void setRoundsScore(HashMap<Integer, ArrayList<Integer>> roundsScore) {
        this.roundsScore = roundsScore;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public boolean isMePassRound() {
        return mePassRound;
    }

    public void setMePassRound(boolean mePassRound) {
        this.mePassRound = mePassRound;
    }

    public boolean isEnemyPassRound() {
        return enemyPassRound;
    }

    public void setEnemyPassRound(boolean enemyPassRound) {
        this.enemyPassRound = enemyPassRound;
    }

    public User getWinner() {
        return Winner;
    }

    public void setWinner(User winner) {
        Winner = winner;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMyFinalScore() {
        return myFinalScore;
    }

    public void setMyFinalScore(int myFinalScore) {
        this.myFinalScore = myFinalScore;
    }

    public int getEnemyFinalScore() {
        return enemyFinalScore;
    }

    public void setEnemyFinalScore(int enemyFinalScore) {
        this.enemyFinalScore = enemyFinalScore;
    }

    public HashMap<Integer, ArrayList<Integer>> getRoundsScore() {
        return roundsScore;
    }

    public void addToRoundsScore(Integer round, ArrayList<Integer> scores) {
        this.roundsScore.put(round,scores);
    }

    public SpellUnit getSpellUnit() {
        return spellUnit;
    }

    public void setSpellUnit(SpellUnit spellUnit) {
        this.spellUnit = spellUnit;
    }

    public GameMenuController getController() {
        return controller;
    }

    public void setController(GameMenuController controller) {
        this.controller = controller;
    }
}
