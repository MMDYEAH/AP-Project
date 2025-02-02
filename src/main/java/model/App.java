package model;

import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import network.GameClient;
import view.LoginMenu;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;


public class App {
    private static Stage stage = new Stage();
    private static boolean CardApplyDoing = false;
    private static Boolean singupSuccessful =false;
    private static LoginMenu loginMenu;
    private static GameClient gameClient;
    private static SkelligeFaction skelligeFaction;
    private static RealmsNorthenFaction realmsNorthenFaction;
    private static MonstersFaction monstersFaction;
    private static EmpireNilfgaardianFaction empireNilfgaardianFaction;
    private static ScoiataelFaction scoiataelFaction;

    private static ArrayList<User> rankedUsers = new ArrayList<>();

    public static SkelligeFaction getSkelligeFaction() {
        return skelligeFaction;
    }

    public static void setSkelligeFaction(SkelligeFaction skelligeFaction) {
        App.skelligeFaction = skelligeFaction;
    }

    public static RealmsNorthenFaction getRealmsNorthenFaction() {
        return realmsNorthenFaction;
    }

    public static void setRealmsNorthenFaction(RealmsNorthenFaction realmsNorthenFaction) {
        App.realmsNorthenFaction = realmsNorthenFaction;
    }

    public static MonstersFaction getMonstersFaction() {
        return monstersFaction;
    }

    public static void setMonstersFaction(MonstersFaction monstersFaction) {
        App.monstersFaction = monstersFaction;
    }

    public static EmpireNilfgaardianFaction getEmpireNilfgaardianFaction() {
        return empireNilfgaardianFaction;
    }

    public static void setEmpireNilfgaardianFaction(EmpireNilfgaardianFaction empireNilfgaardianFaction) {
        App.empireNilfgaardianFaction = empireNilfgaardianFaction;
    }

    public static ScoiataelFaction getScoiataelFaction() {
        return scoiataelFaction;
    }

    public static void setScoiataelFaction(ScoiataelFaction scoiataelFaction) {
        App.scoiataelFaction = scoiataelFaction;
    }

    private static MediaPlayer mediaplayer;

    private static String randomPassword;

    private static final Random random = new Random();

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        App.stage = stage;
    }

    public static MediaPlayer getMediaplayer() {
        return mediaplayer;
    }

    public static void setMediaplayer(MediaPlayer mediaplayer) {
        App.mediaplayer = mediaplayer;
    }

    public static String getRandomPassword() {
        return randomPassword;
    }

    public static void setRandomPassword(String randomPassword) {
        App.randomPassword = randomPassword;
    }

    public static Random getRandom() {
        return random;
    }

    public static boolean isCardApplyDoing() {
        return CardApplyDoing;
    }

    public static void setCardApplyDoing(boolean cardApplyDoing) {
        CardApplyDoing = cardApplyDoing;
    }

    public static GameClient getGameClient() {
        return gameClient;
    }

    public static void
    setGameClient(GameClient gameClient) {
        App.gameClient = gameClient;
    }

    public static LoginMenu getLoginMenu() {
        return loginMenu;
    }

    public static void setLoginMenu(LoginMenu loginMenu) {
        App.loginMenu = loginMenu;
    }

    public static Boolean isSingupSuccessful() {
        return singupSuccessful;
    }

    public static void setSingupSuccessful(boolean singupSuccessful) {
        App.singupSuccessful = singupSuccessful;
    }

    public static ArrayList<User> getRankedUsers() {
        return rankedUsers;
    }
}
