package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Question;
import model.Result;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.LoginMenu;
import view.ProfileMenu;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ControllerTest {
    private Controller controller;
    private HashMap<String, Scene> mockScenes;
    private Stage mockStage;

    @BeforeEach
    void setUp() {
        controller = new Controller();
        mockScenes = mock(HashMap.class);
        mockStage = mock(Stage.class);
        controller.setScenes(mockScenes);
        controller.setStage(mockStage);
    }

    @Test
    void switchToScene_shouldSetSceneAndShowStage() {
        String sceneName = "sampleScene";
        Scene mockScene = mock(Scene.class);
        when(mockScenes.get(sceneName)).thenReturn(mockScene);

        // Execute UI-related code on the JavaFX Application thread
        Platform.runLater(() -> {
            controller.switchToScene(sceneName);
        });

        // Verify that the stage is set and shown
        verify(mockStage).setScene(mockScene);
        verify(mockStage).show();
    }
}
