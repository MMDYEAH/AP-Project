package controller;

import model.*;
import view.PreGameMenu;

import java.util.ArrayList;

public class PreGameMenuController {
    PreGameMenu preGameMenu;

    public PreGameMenuController(PreGameMenu preGameMenu) {
        this.preGameMenu = preGameMenu;
    }

    public void initialize() {
        ArrayList<FactionLeaderCard> realmsNorthernFactionCards = new ArrayList<>();
        ArrayList<Card> realmsNorthernUnitCards = new ArrayList<>();
        addNorthernRealmsFactionCards(realmsNorthernFactionCards);
        addNorthernRealmsCard(realmsNorthernUnitCards);
        addNaturalCards(realmsNorthernUnitCards);
        RealmsNorthenFaction realmsNorthenFaction= new RealmsNorthenFaction(realmsNorthernFactionCards,realmsNorthernUnitCards);
        ArrayList<FactionLeaderCard> monstersFactionCards = new ArrayList<>();
        ArrayList<Card> monstersUnitCards = new ArrayList<>();
        addMonstersFactionCards(monstersFactionCards);
        addMonstersCard(monstersUnitCards);
        addNaturalCards(monstersUnitCards);
        MonstersFaction monstersFaction= new MonstersFaction(monstersFactionCards,monstersUnitCards);
        ArrayList<FactionLeaderCard> nilfgaardFactionCards = new ArrayList<>();
        ArrayList<Card> nilfgaardUnitCards = new ArrayList<>();
        addNilfgaardFactionCards(nilfgaardFactionCards);
        addNilfgaardCard(nilfgaardUnitCards);
        addNaturalCards(nilfgaardUnitCards);
        EmpireNilfgaardianFaction empireNilfgaardianFaction= new EmpireNilfgaardianFaction(nilfgaardFactionCards,nilfgaardUnitCards);
        ArrayList<FactionLeaderCard> skelligeFactionCards = new ArrayList<>();
        ArrayList<Card> skelligeUnitCards = new ArrayList<>();
        addSkelligeFactionCards(skelligeFactionCards);
        addSkelligeCard(skelligeUnitCards);
        addNaturalCards(skelligeUnitCards);
        SkelligeFaction skelligeFaction= new SkelligeFaction(skelligeFactionCards,skelligeUnitCards);
        ArrayList<FactionLeaderCard> scoiataelFactionCards = new ArrayList<>();
        ArrayList<Card> scoiataelUnitCards = new ArrayList<>();
        addScoiataelFactionCards(scoiataelFactionCards);
        addScoiataelCard(scoiataelUnitCards);
        addNaturalCards(scoiataelUnitCards);
        ScoiataelFaction scoiataelFaction= new ScoiataelFaction(scoiataelFactionCards,scoiataelUnitCards);
        //TODO: set unit of all cards and add factions to app
    }

    private void addScoiataelCard(ArrayList<Card> scoiataelUnitCards) {
    }

    private void addScoiataelFactionCards(ArrayList<FactionLeaderCard> scoiataelFactionCards) {
    }

    private void addSkelligeCard(ArrayList<Card> skelligeUnitCards) {
    }

    private void addSkelligeFactionCards(ArrayList<FactionLeaderCard> skelligeFactionCards) {
    }

    private void addNilfgaardCard(ArrayList<Card> nilfgaardUnitCards) {
    }

    private void addNilfgaardFactionCards(ArrayList<FactionLeaderCard> nilfgaardFactionCards) {
    }

    private void addMonstersCard(ArrayList<Card> monstersUnitCards) {
    }

    private void addMonstersFactionCards(ArrayList<FactionLeaderCard> monstersFactionCards) {
    }

    private void addNorthernRealmsFactionCards(ArrayList<FactionLeaderCard> factionLeaderCards) {
        factionLeaderCards.add(new FactionLeaderCard("The Siegemaster",
                this.getClass().getResource("/pics/northenRealms/faction/TheSiegemaster.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("The Steel-Forged",
                this.getClass().getResource("/pics/northenRealms/faction/TheSteelForged.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("King of Temeria",
                this.getClass().getResource("/pics/northenRealms/faction/KingOfTemeria.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Lord Commander of the North",
                this.getClass().getResource("/pics/northenRealms/faction/LordCommanderOfTheNorth.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Son of Medell",
                this.getClass().getResource("/pics/northenRealms/faction/SonOfMedell.jpg").toExternalForm()));
    }

    private void addNaturalCards(ArrayList<Card> UnitCards) {
        for (int i = 0; i < 3; i++) {
            UnitCards.add(new BitingFrost("BitingFrost"
                    , this.getClass().getResource("/pics/neutral/BitingFrost.jpg").toExternalForm()));
            UnitCards.add(new ImpenetrableFog("ImpenetrableFog"
                    , this.getClass().getResource("/pics/neutral/ImpenetrableFog.jpg").toExternalForm()));
            UnitCards.add(new TorrentialRain("TorrentialRain"
                    , this.getClass().getResource("/pics/neutral/TorrentialRain.jpg").toExternalForm()));
            UnitCards.add(new Decoy("Decoy"
                    , this.getClass().getResource("/pics/neutral/Decoy.jpg").toExternalForm()));
            UnitCards.add(new Muster("Gaunter O’DImm Darkness", 4, false
                    , this.getClass().getResource("/pics/neutral/GaunterO’DImmDarkness.jpg").toExternalForm()));
        }
        UnitCards.add(new UnitCommandersHorn("Dandelion", 2, false
                , this.getClass().getResource("/pics/neutral/Dandelion.jpg").toExternalForm()));
        UnitCards.add(new Solider("Emiel Regis", 2, false
                , this.getClass().getResource("/pics/neutral/EmielRegis.jpg").toExternalForm()));
        UnitCards.add(new Muster("Gaunter O’Dimm", 2, false
                , this.getClass().getResource("/pics/neutral/GaunterO’Dimm.jpg").toExternalForm()));
        UnitCards.add(new Spy("Mysterious Elf", 0, true
                , this.getClass().getResource("/pics/neutral/MysteriousElf.jpg").toExternalForm()));
        UnitCards.add(new MoralBooster("Olgierd Von Everc", 6, false
                , this.getClass().getResource("/pics/neutral/OlgierdVonEverc.jpg").toExternalForm()));
        UnitCards.add(new Solider("Triss Merigold", 7, true
                , this.getClass().getResource("/pics/neutral/TrissMerigold.jpg").toExternalForm()));
        UnitCards.add(new Scorch("Villentretenmerth", 7, false
                , this.getClass().getResource("/pics/neutral/Villentretenmerth.jpg").toExternalForm()));
        UnitCards.add(new Medic("Yennefer of Vengerberg", 7, true
                , this.getClass().getResource("/pics/neutral/YenneferOfVengerberg.jpg").toExternalForm()));
        UnitCards.add(new Solider("Zoltan Chivay", 5, false
                , this.getClass().getResource("/pics/neutral/ZoltanChivay.jpg").toExternalForm()));
    }

    private void addNorthernRealmsCard(ArrayList<Card> realmsNorthernUnitCards) {
        for (int i = 0; i < 2; i++) {
            realmsNorthernUnitCards.add(new Solider("Ballista", 6, false
                    , this.getClass().getResource("/pics/northenRealms/Ballista.jpg").toExternalForm()));
            realmsNorthernUnitCards.add(new Solider("Redanian Foot Soldier", 1, false
                    , this.getClass().getResource("/pics/northenRealms/RedanianFootSoldier"+(i+1)+".jpg").toExternalForm()));
            realmsNorthernUnitCards.add(new TightBound("Catapult", 8, false
                    , this.getClass().getResource("/pics/northenRealms/Catapult.jpg").toExternalForm()));
        }
        for (int i = 0; i < 3; i++) {
            realmsNorthernUnitCards.add(new MoralBooster("Kaedweni Siege Expert", 1, false
                    , this.getClass().getResource("/pics/northenRealms/KaedweniSiegeExpert"+(i+1)+".jpg").toExternalForm()));
            realmsNorthernUnitCards.add(new TightBound("Dragon Hunter", 5, false
                    , this.getClass().getResource("/pics/northenRealms/DragonHunter.jpg").toExternalForm()));
        }
        for (int i = 0; i < 4; i++) {
            realmsNorthernUnitCards.add(new TightBound("Poor Fucking Infantry", 1, false
                    , this.getClass().getResource("/pics/northenRealms/PoorFuckingInfantry.jpg").toExternalForm()));
        }
        realmsNorthernUnitCards.add(new Medic("Dun Banner Medic", 5, false
                , this.getClass().getResource("/pics/northenRealms/DunBannerMedic.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Esterad Thyssen", 10, true
                , this.getClass().getResource("/pics/northenRealms/EsteradThyssen.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("John Natalis", 10, true
                , this.getClass().getResource("/pics/northenRealms/JohnNatalis.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Philippa Eilhart", 10, true
                , this.getClass().getResource("/pics/northenRealms/PhilippaEilhart.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Spy("Prince Stennis", 5, false
                , this.getClass().getResource("/pics/northenRealms/PrinceStennis.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Sabrina Glevissing", 4, false
                , this.getClass().getResource("/pics/northenRealms/SabrinaGlevissing.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Siege Tower", 6, false
                , this.getClass().getResource("/pics/northenRealms/SiegeTower.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Siegfried of Denesle", 5, false
                , this.getClass().getResource("/pics/northenRealms/SiegfriedOfDenesle.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Spy("Sigismund Dijkstra", 4, false
                , this.getClass().getResource("/pics/northenRealms/SigismundDijkstra.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Sile de Tansarville", 5, false
                , this.getClass().getResource("/pics/northenRealms/SileDeTansarville.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Spy("Thaler", 1, false
                , this.getClass().getResource("/pics/northenRealms/Thaler.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Vernon Roche", 10, true
                , this.getClass().getResource("/pics/northenRealms/VernonRoche.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Ves", 5, false
                , this.getClass().getResource("/pics/northenRealms/Ves.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Yarpen Zirgrin", 2, false
                , this.getClass().getResource("/pics/northenRealms/YarpenZirgrin.jpg").toExternalForm()));
    }

    public void showCards() {
    }

    public void showFactions() {
    }

    public void showLeaders() {
    }

    public void showDeck() {
    }

    public void changeTurn() {
    }

    public void showCurrentUserInfo() {
    }

    public Result startGame() {
        return null;//TODO: delete this code and write
    }

    public Result selectFaction(String factionName) {
        return null;//TODO: delete this code and write
    }

    public Result createGame(String enemyUsername) {
        return null;//TODO: delete this code and write
    }

    public Result selectLeader(String leaderName) {
        return null;//TODO: delete this code and write
    }

    public Result saveDeckWithAddress(String address) {
        return null;//TODO: delete this code and write
    }

    public Result saveDeckWithName(String name) {
        return null;//TODO: delete this code and write
    }

    public Result loadDeckWithAddress(String address) {
        return null;//TODO: delete this code and write
    }

    public Result loadDeckWithName(String name) {
        return null;//TODO: delete this code and write
    }

    public Result addCardToDeck(String cardName, int count) {
        return null;//TODO: delete this code and write
    }

    public Result removeFromDeck(String cardName, int count) {
        return null;//TODO: delete this code and write
    }

    private void makeRandomFaction() {
    }
}
