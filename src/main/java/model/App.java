package model;

import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;

import java.text.Normalizer;
import java.util.Random;


public class App {
    private static Stage stage = new Stage();

    private static SkelligeFaction skelligeFaction;
    private static RealmsNorthenFaction realmsNorthenFaction;
    private static MonstersFaction monstersFaction;
    private static EmpireNilfgaardianFaction empireNilfgaardianFaction;
    private static ScoiataelFaction scoiataelFaction;

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

}
