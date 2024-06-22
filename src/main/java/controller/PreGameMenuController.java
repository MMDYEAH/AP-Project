package controller;

import model.*;
import view.PreGameMenu;

import java.util.ArrayList;

public class PreGameMenuController {
    PreGameMenu preGameMenu;

    public PreGameMenuController(PreGameMenu preGameMenu) {
        this.preGameMenu = preGameMenu;
    }
    public void initialize(){
        ArrayList<FactionLeaderCard> realmsNorthernFactionCards = new ArrayList<>();
        ArrayList<Card> realmsNorthernUnitCards = new ArrayList<>();

        realmsNorthernFactionCards.add(new FactionLeaderCard("The Siegemaster"));
        realmsNorthernFactionCards.add(new FactionLeaderCard("The Steel-Forged"));
        realmsNorthernFactionCards.add(new FactionLeaderCard("King of Temeria"));
        realmsNorthernFactionCards.add(new FactionLeaderCard("Lord Commander of the North"));
        realmsNorthernFactionCards.add(new FactionLeaderCard("Son of Medell"));
        addNorthernRealmsCard(realmsNorthernUnitCards);
        for (int i = 0; i < 3 ; i++){
            realmsNorthernUnitCards.add(new BitingFrost("BitingFrost"));
            realmsNorthernUnitCards.add(new ImpenetrableFog("ImpenetrableFog"));
            realmsNorthernUnitCards.add(new TorrentialRain("TorrentialRain"));
            realmsNorthernUnitCards.add(new Decoy("Decoy"));
            realmsNorthernUnitCards.add(new Muster("Gaunter O’DImm Darkness",4,false));
        }
        realmsNorthernUnitCards.add(new UnitCommandersHorn("Dandelion",2,false));
        realmsNorthernUnitCards.add(new Solider("Emiel Regis",2,false));
        realmsNorthernUnitCards.add(new Muster("Gaunter O’Dimm",2,false));
        realmsNorthernUnitCards.add(new Spy("Mysterious Elf",0,true));
        realmsNorthernUnitCards.add(new MoralBooster("Olgierd Von Everc",6,false));
        realmsNorthernUnitCards.add(new Solider("Triss Merigold",7,true));
        realmsNorthernUnitCards.add(new Scorch("Villentretenmerth",7,false));
        realmsNorthernUnitCards.add(new Medic("Yennefer of Vengerberg",7,true));
        realmsNorthernUnitCards.add(new Solider("Zoltan Chivay",5,false));
        //TODO: set unit of all cards
    }
    private void addNorthernRealmsCard(ArrayList<Card> realmsNorthernUnitCards){
        for (int i = 0 ; i < 2 ; i++){
            realmsNorthernUnitCards.add(new Solider("Ballista",6,false));
            realmsNorthernUnitCards.add(new Solider("Redanian Foot Soldier",1,false));
            realmsNorthernUnitCards.add(new TightBound("Catapult",8,false));
        }
        for (int i = 0 ; i < 3 ; i++){
            realmsNorthernUnitCards.add(new MoralBooster("Kaedweni Siege Expert",1,false));
            realmsNorthernUnitCards.add(new TightBound("Dragon Hunter",5,false));
        }
        for (int i = 0; i < 4; i++) {
            realmsNorthernUnitCards.add(new TightBound("Poor Fucking Infantry",1,false));
        }
    }
    public void showCards(){}
    public void showFactions(){}
    public void showLeaders(){}
    public void showDeck(){}
    public void changeTurn(){}
    public void showCurrentUserInfo(){}
    public Result startGame(){
        return null;//TODO: delete this code and write
    }
    public Result selectFaction(String factionName){
        return null;//TODO: delete this code and write
    }
    public Result createGame(String enemyUsername){
        return null;//TODO: delete this code and write
    }
    public Result selectLeader(String leaderName){
        return null;//TODO: delete this code and write
    }
    public Result saveDeckWithAddress(String address){
        return null;//TODO: delete this code and write
    }
    public Result saveDeckWithName(String name){
        return null;//TODO: delete this code and write
    }
    public Result loadDeckWithAddress(String address){
        return null;//TODO: delete this code and write
    }
    public Result loadDeckWithName(String name){
        return null;//TODO: delete this code and write
    }
    public Result addCardToDeck(String cardName, int count){
        return null;//TODO: delete this code and write
    }
    public Result removeFromDeck(String cardName, int count){
        return null;//TODO: delete this code and write
    }
    private void makeRandomFaction(){}
}
