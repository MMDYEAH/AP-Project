package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.*;
import view.GameMenu;

class GameMenuControllerTest {

    private GameMenuController controller;
    private GameMenu gameMenu;

    @BeforeEach
    void setUp() {
        gameMenu = new GameMenu(); // You might need to mock this
        controller = new GameMenuController(gameMenu);
    }

    @Test
    void testPutCard() {
        // This test is complex and would require mocking many objects
        // Here's a skeleton of how it might look:

        // Card card = new Card(); // Mock or create a test card
        // Unit unit = new CloseCombatUnit(); // Mock or create a test unit
        // controller.putCard(card, unit, true);

        // Assert that the card was added to the correct unit
        // Assert that the card's apply method was called
        // Assert that the power text was updated
    }

    @Test
    void testShowHandCard() {
        Result result = controller.showHandCard(1);
        assertNotNull(result);
        // Add more specific assertions based on expected behavior
    }

    @Test
    void testShowRemainingCardsToPlay() {
        Result result = controller.showRemainingCardsToPlay();
        assertNotNull(result);
        // Add more specific assertions based on expected behavior
    }

    // Add more tests for other methods...

    @Test
    void testPlayCommanderPower() {
        Result result = controller.PlayCommanderPower();
        assertNotNull(result);
        // Add more specific assertions based on expected behavior
    }
}