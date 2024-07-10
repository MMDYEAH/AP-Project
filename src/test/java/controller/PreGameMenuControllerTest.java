package controller;

import javafx.embed.swing.JFXPanel;
import model.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.PreGameMenu;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PreGameMenuControllerTest {
    JFXPanel jfxPanel = new JFXPanel();
    private PreGameMenuController controller;
    private PreGameMenu preGameMenu;

    @BeforeEach
    void setUp() {
        preGameMenu = new PreGameMenu();
        controller = new PreGameMenuController(preGameMenu);
    }

    @Test
    void testInitialize() {
        controller.initialize();

        assertNotNull(App.getSkelligeFaction());
        assertNotNull(App.getRealmsNorthenFaction());
        assertNotNull(App.getMonstersFaction());
        assertNotNull(App.getEmpireNilfgaardianFaction());
        assertNotNull(App.getScoiataelFaction());

        // Check if factions have cards
        assertTrue(App.getSkelligeFaction().getUnitCards().size() > 0);
        assertTrue(App.getRealmsNorthenFaction().getUnitCards().size() > 0);
        assertTrue(App.getMonstersFaction().getUnitCards().size() > 0);
        assertTrue(App.getEmpireNilfgaardianFaction().getUnitCards().size() > 0);
        assertTrue(App.getScoiataelFaction().getUnitCards().size() > 0);
    }

    @Test
    void testAddCards() throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<FactionLeaderCard> leaderCards = new ArrayList<>();

        Method addScoiataelCard = PreGameMenuController.class.getDeclaredMethod("addScoiataelCard", ArrayList.class);
        Method addScoiataelFactionCards = PreGameMenuController.class.getDeclaredMethod("addScoiataelFactionCards", ArrayList.class);
        Method addSkelligeCard = PreGameMenuController.class.getDeclaredMethod("addSkelligeCard", ArrayList.class);
        Method addSkelligeFactionCards = PreGameMenuController.class.getDeclaredMethod("addSkelligeFactionCards", ArrayList.class);
        Method addNilfgaardCard = PreGameMenuController.class.getDeclaredMethod("addNilfgaardCard", ArrayList.class);
        Method addNilfgaardFactionCards = PreGameMenuController.class.getDeclaredMethod("addNilfgaardFactionCards", ArrayList.class);
        Method addMonstersCard = PreGameMenuController.class.getDeclaredMethod("addMonstersCard", ArrayList.class);
        Method addMonstersFactionCards = PreGameMenuController.class.getDeclaredMethod("addMonstersFactionCards", ArrayList.class);
        Method addNorthernRealmsCard = PreGameMenuController.class.getDeclaredMethod("addNorthernRealmsCard", ArrayList.class);
        Method addNorthernRealmsFactionCards = PreGameMenuController.class.getDeclaredMethod("addNorthernRealmsFactionCards", ArrayList.class);
        Method addNaturalCards = PreGameMenuController.class.getDeclaredMethod("addNaturalCards", ArrayList.class);

        addScoiataelCard.setAccessible(true);
        addScoiataelFactionCards.setAccessible(true);
        addSkelligeCard.setAccessible(true);
        addSkelligeFactionCards.setAccessible(true);
        addNilfgaardCard.setAccessible(true);
        addNilfgaardFactionCards.setAccessible(true);
        addMonstersCard.setAccessible(true);
        addMonstersFactionCards.setAccessible(true);
        addNorthernRealmsCard.setAccessible(true);
        addNorthernRealmsFactionCards.setAccessible(true);
        addNaturalCards.setAccessible(true);

        addScoiataelCard.invoke(controller, cards);
        addScoiataelFactionCards.invoke(controller, leaderCards);
        addSkelligeCard.invoke(controller, cards);
        addSkelligeFactionCards.invoke(controller, leaderCards);
        addNilfgaardCard.invoke(controller, cards);
        addNilfgaardFactionCards.invoke(controller, leaderCards);
        addMonstersCard.invoke(controller, cards);
        addMonstersFactionCards.invoke(controller, leaderCards);
        addNorthernRealmsCard.invoke(controller, cards);
        addNorthernRealmsFactionCards.invoke(controller, leaderCards);
        addNaturalCards.invoke(controller, cards);

        assertTrue(cards.size() > 0);
        assertTrue(leaderCards.size() > 0);
    }

    // Add the rest of your test methods here

    @Test
    void testShowCards() {
        assertDoesNotThrow(() -> controller.showCards());
    }

    @Test
    void testShowFactions() {
        assertDoesNotThrow(() -> controller.showFactions());
    }

    @Test
    void testShowLeaders() {
        assertDoesNotThrow(() -> controller.showLeaders());
    }

    @Test
    void testShowDeck() {
        assertDoesNotThrow(() -> controller.showDeck());
    }

    @Test
    void testChangeTurn() {
        assertDoesNotThrow(() -> controller.changeTurn());
    }

    @Test
    void testShowCurrentUserInfo() {
        assertDoesNotThrow(() -> controller.showCurrentUserInfo());
    }

    @Test
    void testStartGame() {
        Result result = controller.startGame();
        assertNull(result);
    }

    @Test
    void testSelectFaction() {
        Result result = controller.selectFaction("TestFaction");
        assertNull(result);
    }

    @Test
    void testCreateGame() {
        Result result = controller.createGame("TestEnemy");
        assertNull(result);
    }

    @Test
    void testSelectLeader() {
        Result result = controller.selectLeader("TestLeader");
        assertNull(result);
    }

    @Test
    void testSaveDeckWithAddress() {
        Result result = controller.saveDeckWithAddress("TestAddress");
        assertNull(result);
    }

    @Test
    void testSaveDeckWithName() {
        Result result = controller.saveDeckWithName("TestName");
        assertNull(result);
    }

    @Test
    void testLoadDeckWithAddress() {
        Result result = controller.loadDeckWithAddress("TestAddress");
        assertNull(result);
    }

    @Test
    void testLoadDeckWithName() {
        Result result = controller.loadDeckWithName("TestName");
        assertNull(result);
    }

    @Test
    void testAddCardToDeck() {
        Result result = controller.addCardToDeck("TestCard", 1);
        assertNull(result);
    }

    @Test
    void testRemoveFromDeck() {
        Result result = controller.removeFromDeck("TestCard", 1);
        assertNull(result);
    }
    @Before
    void initialize(){
        controller.initialize();
    }
//    @Test
//    void TestCardMaking(){
//    preGameMenu.start();
//    assertEquals(App.getRealmsNorthenFaction().getFactionLeaderCards().size(),5);
//    }
}