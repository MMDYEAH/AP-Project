package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainMenuControllerTest {

    private MainMenuController controller;

    @BeforeEach
    void setUp() {
        controller = new MainMenuController();
    }

    @Test
    void testGoToProfileMenu() {
        // This method doesn't return anything, so we can only test that it doesn't throw an exception
        assertDoesNotThrow(() -> controller.goToProfileMenu());
    }

    @Test
    void testGoToGameMenu() {
        // This method doesn't return anything, so we can only test that it doesn't throw an exception
        assertDoesNotThrow(() -> controller.goToGameMenu());
    }

    @Test
    void testLogout() {
        // This method doesn't return anything, so we can only test that it doesn't throw an exception
        assertDoesNotThrow(() -> controller.logout());
    }
}