package controller;

import model.*;
import view.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JUniteControllerTest {
    private LoginMenu loginMenu;
    private PreGameMenu preGameMenu;
    private JUniteController jUniteController;
    private User user;

    @BeforeEach
    void setUp() {
        loginMenu = new LoginMenu();
        preGameMenu = new PreGameMenu();
        user = new User("currentUsername", "currentPassword", "currentNickname", "currentEmail@example.com");
        User.setLoggedInUser(user);
        jUniteController = new JUniteController(new ProfileMenu());
    }

    @Test
    public void testChangeUsername_SameUsername() {
        Result result = jUniteController.changeUsername("currentUsername");
        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current username", result.toString());
    }

    @Test
    public void testChangeUsername_EmptyUsername() {
        Result result = jUniteController.changeUsername("");
        assertFalse(result.isSuccessful());
        assertEquals("empty username", result.toString());
    }

    @Test
    public void testChangeUsername_UsernameExists() {
        User anotherUser = new User("existingUsername", "password", "nickname", "email@example.com");
        User.addToUsers(anotherUser);
        Result result = jUniteController.changeUsername("existingUsername");
        assertFalse(result.isSuccessful());
        assertEquals("there is exist an user with this username", result.toString());
    }

    @Test
    public void testChangeUsername_WrongFormat() {
        Result result = jUniteController.changeUsername("wrong_format");
        assertFalse(result.isSuccessful());
        assertEquals("wrong username format", result.toString());
    }

    @Test
    public void testChangeUsername_Success() {
        Result result = jUniteController.changeUsername("newUsername");
        assertTrue(result.isSuccessful());
        assertEquals("username changed successfully", result.toString());
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testChangeNickname_SameNickname() {
        Result result = jUniteController.changeNickname("currentNickname");
        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current nickname", result.toString());
    }

    @Test
    public void testChangeNickname_EmptyNickname() {
        Result result = jUniteController.changeNickname("");
        assertFalse(result.isSuccessful());
        assertEquals("empty nickname", result.toString());
    }

    @Test
    public void testChangeNickname_Success() {
        Result result = jUniteController.changeNickname("newNickname");
        assertTrue(result.isSuccessful());
        assertEquals("nickname changed successfully", result.toString());
        assertEquals("newNickname", user.getNickname());
    }

    @Test
    public void testChangeEmail_SameEmail() {
        Result result = jUniteController.changeEmail("currentEmail@example.com");
        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current email", result.toString());
    }

    @Test
    public void testChangeEmail_EmptyEmail() {
        Result result = jUniteController.changeEmail("");
        assertFalse(result.isSuccessful());
        assertEquals("empty email", result.toString());
    }

    @Test
    public void testChangeEmail_WrongFormat() {
        Result result = jUniteController.changeEmail("wrongFormat");
        assertFalse(result.isSuccessful());
        assertEquals("wrong email format", result.toString());
    }

    @Test
    public void testChangeEmail_Success() {
        Result result = jUniteController.changeEmail("newEmail@example.com");
        assertTrue(result.isSuccessful());
        assertEquals("email changed successfully", result.toString());
        assertEquals("newEmail@example.com", user.getEmail());
    }

    @Test
    public void testChangePassword_WrongOldPassword() {
        Result result = jUniteController.changePassword("wrongOldPassword", "newPassword");
        assertFalse(result.isSuccessful());
        assertEquals("old password in wrong", result.toString());
    }

    @Test
    public void testChangePassword_SamePassword() {
        Result result = jUniteController.changePassword("currentPassword", "currentPassword");
        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current password", result.toString());
    }

    @Test
    public void testChangePassword_EmptyNewPassword() {
        Result result = jUniteController.changePassword("currentPassword", "");
        assertFalse(result.isSuccessful());
        assertEquals("empty password", result.toString());
    }

    @Test
    public void testChangePassword_WrongFormat() {
        Result result = jUniteController.changePassword("currentPassword", "wrongFormat");
        assertFalse(result.isSuccessful());
        assertEquals("wrong password format", result.toString());
    }

    @Test
    public void testChangePassword_WeakPassword() {
        Result result = jUniteController.changePassword("currentPassword", "weakpass");
        assertFalse(result.isSuccessful());
        assertEquals("weak password", result.toString());
    }

    @Test
    public void testChangePassword_Success() {
        Result result = jUniteController.changePassword("currentPassword", "NewPass1!");
        assertTrue(result.isSuccessful());
        assertEquals("password changed successfully", result.toString());
        assertEquals("NewPass1!", user.getPassword());
    }
    @Test
    void testInitialize() {
        jUniteController.initialize();
        assertFalse(Question.getQuestions().isEmpty());
        assertEquals(3, Question.getQuestions().size());
    }

    @Test
    void testMakeRandomPassword() {
        String password = jUniteController.makeRandomPassword();
        assertNotNull(password);
        assertEquals(8, password.length());
        assertTrue(password.matches("^[!&$#@][A-Z][a-z]\\d{5}$"));
    }

    @Test
    void testSetQuestions() {
        User user = new User("testUser", "password", "nickname", "email@test.com");
        Question question = new Question("Test question?", "Test answer");
        jUniteController.setQuestions(user, question);
        assertEquals(question, user.getQuestion());
    }

    @Test
    void testRegisterSuccess() {
        Question question = new Question("Test question?", "Test answer");
        Result result = jUniteController.register("validUser", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertTrue(result.isSuccessful());
        assertEquals("user created successfully", result.toString());
    }

    @Test
    void testRegisterEmptyField() {
        Question question = new Question("Test question?", "Test answer");
        Result result = jUniteController.register("", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("empty field", result.toString());
    }

    @Test
    void testRegisterInvalidUsername() {
        Question question = new Question("Test question?", "Test answer");
        Result result = jUniteController.register("invalid user", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("wrong username format", result.toString());
    }

    @Test
    void testRegisterInvalidEmail() {
        Question question = new Question("Test question?", "Test answer");
        Result result = jUniteController.register("validUser", "ValidPass1!", "ValidPass1!", "nickname", "invalidemail", question);
        assertFalse(result.isSuccessful());
        assertEquals("wrong email format", result.toString());
    }

    @Test
    void testRegisterInvalidPassword() {
        Question question = new Question("Test question?", "Test answer");
        Result result = jUniteController.register("validUser", "weakpass", "weakpass", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("weak password", result.toString());
    }

    @Test
    void testRegisterPasswordMismatch() {
        Question question = new Question("Test question?", "Test answer");
        Result result = jUniteController.register("validUser", "ValidPass1!", "Mismatch1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("confirm password failed", result.toString());
    }

    @Test
    void testRegisterUsernameExists() {
        Question question = new Question("Test question?", "Test answer");
        User existingUser = new User("existingUser", "password", "nickname", "email@test.com");
        User.addToUsers(existingUser);
        Result result = jUniteController.register("existingUser", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("there is exist an user with this username", result.toString());
    }

    @Test
    void testLoginSuccessful() {
        User newUser = new User("testUser", "ValidPass1!", "nickname", "email@test.com");
        User.addToUsers(newUser);
        Result result = jUniteController.login("testUser", "ValidPass1!");
        assertTrue(result.isSuccessful());
        assertEquals("login successfully", result.toString());
    }

    @Test
    void testLoginEmptyField() {
        Result result = jUniteController.login("", "ValidPass1!");
        assertFalse(result.isSuccessful());
        assertEquals("no such user exist", result.toString());
    }

    @Test
    void testLoginUserNotFound() {
        Result result = jUniteController.login("nonexistentUser", "ValidPass1!");
        assertFalse(result.isSuccessful());
        assertEquals("no such user exist", result.toString());
    }

    @Test
    void testLoginWrongPassword() {
        User newUser = new User("testUser", "ValidPass1!", "nickname", "email@test.com");
        User.addToUsers(newUser);
        Result result = jUniteController.login("testUser", "WrongPass1!");
        assertFalse(result.isSuccessful());
        assertEquals("wrong password in login", result.toString());
    }

}
