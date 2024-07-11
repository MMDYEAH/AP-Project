package controller;

import javafx.application.Platform;
import model.App;
import model.Question;
import model.Result;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.LoginMenu;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Application;
import javafx.stage.Stage;



class LoginMenuControllerTest {

    private LoginMenuController controller;
    private LoginMenu loginMenu;

    @BeforeEach
    void setUp() {
        loginMenu = new LoginMenu();
        controller = new LoginMenuController(loginMenu);
        Question.getQuestions().clear();
        controller.initialize();
    }

    @Test
    void testInitialize() {
        assertFalse(Question.getQuestions().isEmpty());
        assertEquals(3, Question.getQuestions().size());
    }

//    @Test
//    void testMakeRandomPassword() {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            try {
//                String password = controller.makeRandomPassword();
//                assertNotNull(password);
//                assertEquals(8, password.length());
//                assertTrue(password.matches("^[!&$#@][A-Z][a-z]\\d{5}$"),
//                        "Password does not match expected pattern. Generated password: " + password);
//            } finally {
//                latch.countDown();
//            }
//        });
//
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            fail("Test interrupted", e);
//        }
//    }

    @Test
    void testSetQuestions() {
        User user = new User("testUser", "password", "nickname", "email@test.com");
        Question question = new Question("Test question?", "Test answer");
        controller.setQuestions(user, question);
        assertEquals(question, user.getQuestion());
    }

    @Test
    void testRegisterSuccess() {
        Question question = new Question("Test question?", "Test answer");
        Result result = controller.register("validUser", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertTrue(result.isSuccessful());
        assertEquals("user created successfully", result.toString());
    }

    @Test
    void testRegisterEmptyField() {
        Question question = new Question("Test question?", "Test answer");
        Result result = controller.register("", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("empty field", result.toString());
    }

    @Test
    void testRegisterInvalidUsername() {
        Question question = new Question("Test question?", "Test answer");
        Result result = controller.register("invalid user", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("wrong username format", result.toString());
    }

    @Test
    void testRegisterInvalidEmail() {
        Question question = new Question("Test question?", "Test answer");
        Result result = controller.register("validUser", "ValidPass1!", "ValidPass1!", "nickname", "invalidemail", question);
        assertFalse(result.isSuccessful());
        assertEquals("wrong email format", result.toString());
    }

    @Test
    void testRegisterWeakPassword() {
        Question question = new Question("Test question?", "Test answer");
        Result result = controller.register("validUser", "weak", "weak", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("weak password", result.toString());
    }

    @Test
    void testRegisterPasswordMismatch() {
        Question question = new Question("Test question?", "Test answer");
        Result result = controller.register("validUser", "ValidPass1!", "DifferentPass2!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("confirm password failed", result.toString());
    }

    @Test
    void testLoginSuccess() {
        User user = new User("testUser", "ValidPass1!", "nickname", "email@test.com");
        User.getUsers().add(user);
        Result result = controller.login("testUser", "ValidPass1!");
        assertTrue(result.isSuccessful());
        assertEquals("login successfully", result.toString());
    }

    @Test
    void testLoginUserNotFound() {
        Result result = controller.login("nonexistentUser", "ValidPass1!");
        assertFalse(result.isSuccessful());
        assertEquals("no such user exist", result.toString());
    }

    @Test
    void testLoginWrongPassword() {
        User user = new User("testUser", "ValidPass1!", "nickname", "email@test.com");
        User.getUsers().add(user);
        Result result = controller.login("testUser", "WrongPass2!");
        assertFalse(result.isSuccessful());
        assertEquals("wrong password in login", result.toString());
    }
}
