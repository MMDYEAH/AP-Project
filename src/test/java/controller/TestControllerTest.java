package controller;

import model.Question;
import model.Result;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ProfileMenu;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestControllerTest {
    private TestController testController;
    private User user;
    @BeforeEach
    void setUp() {
        user = new User("currentUsername", "currentPassword", "currentNickname", "currentEmail@example.com");
        User.setLoggedInUser(user);
        testController = new TestController();
        Question.getQuestions().clear();
        testController.initialize();
    }
    @Test
    public void testChangeUsername_SameUsername() {
        Result result = testController.changeUsername("currentUsername");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current username", result.toString());
    }

    @Test
    public void testChangeUsername_EmptyUsername() {
        Result result = testController.changeUsername("");

        assertFalse(result.isSuccessful());
        assertEquals("empty username", result.toString());
    }

    @Test
    public void testChangeUsername_UsernameExists() {
        User anotherUser = new User("existingUsername", "password", "nickname", "email@example.com");
        User.addToUsers(anotherUser);

        Result result = testController.changeUsername("existingUsername");

        assertFalse(result.isSuccessful());
        assertEquals("there is exist an user with this username", result.toString());
    }

    @Test
    public void testChangeUsername_WrongFormat() {
        Result result = testController.changeUsername("wrong_format");

        assertFalse(result.isSuccessful());
        assertEquals("wrong username format", result.toString());
    }

    @Test
    public void testChangeUsername_Success() {
        Result result = testController.changeUsername("newUsername");

        assertTrue(result.isSuccessful());
        assertEquals("username changed successfully", result.toString());
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testChangeNickname_SameNickname() {
        Result result = testController.changeNickname("currentNickname");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current nickname", result.toString());
    }

    @Test
    public void testChangeNickname_EmptyNickname() {
        Result result = testController.changeNickname("");

        assertFalse(result.isSuccessful());
        assertEquals("empty nickname", result.toString());
    }

    @Test
    public void testChangeNickname_Success() {
        Result result = testController.changeNickname("newNickname");

        assertTrue(result.isSuccessful());
        assertEquals("nickname changed successfully", result.toString());
        assertEquals("newNickname", user.getNickname());
    }

    @Test
    public void testChangeEmail_SameEmail() {
        Result result = testController.changeEmail("currentEmail@example.com");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current email", result.toString());
    }

    @Test
    public void testChangeEmail_EmptyEmail() {
        Result result = testController.changeEmail("");

        assertFalse(result.isSuccessful());
        assertEquals("empty email", result.toString());
    }

    @Test
    public void testChangeEmail_WrongFormat() {
        Result result = testController.changeEmail("wrongFormat");

        assertFalse(result.isSuccessful());
        assertEquals("wrong email format", result.toString());
    }

    @Test
    public void testChangeEmail_Success() {
        Result result = testController.changeEmail("newEmail@example.com");

        assertTrue(result.isSuccessful());
        assertEquals("email changed successfully", result.toString());
        assertEquals("newEmail@example.com", user.getEmail());
    }

    @Test
    public void testChangePassword_WrongOldPassword() {
        Result result = testController.changePassword("wrongOldPassword", "newPassword");

        assertFalse(result.isSuccessful());
        assertEquals("old password in wrong", result.toString());
    }

    @Test
    public void testChangePassword_SamePassword() {
        Result result = testController.changePassword("currentPassword", "currentPassword");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current password", result.toString());
    }

    @Test
    public void testChangePassword_EmptyNewPassword() {
        Result result = testController.changePassword("currentPassword", "");

        assertFalse(result.isSuccessful());
        assertEquals("empty password", result.toString());
    }
    @Test
    public void testChangePassword_WeakPassword() {
        Result result = testController.changePassword("currentPassword", "weakpass");

        assertFalse(result.isSuccessful());
        assertEquals("weak password", result.toString());
    }

    @Test
    public void testChangePassword_Success() {
        Result result = testController.changePassword("currentPassword", "NewPass1!");

        assertTrue(result.isSuccessful());
        assertEquals("password changed successfully", result.toString());
        assertEquals("NewPass1!", user.getPassword());
    }
    @Test
    void testInitialize() {
        assertFalse(Question.getQuestions().isEmpty());
        assertEquals(3, Question.getQuestions().size());
    }

    @Test
    void testSetQuestions() {
        User user = new User("testUser", "password", "nickname", "email@test.com");
        Question question = new Question("Test question?", "Test answer");
        testController.setQuestions(user, question);
        assertEquals(question, user.getQuestion());
    }

    @Test
    void testRegisterSuccess() {
        Question question = new Question("Test question?", "Test answer");
        Result result = testController.register("validUser", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertTrue(result.isSuccessful());
        assertEquals("user created successfully", result.toString());
    }

    @Test
    void testRegisterEmptyField() {
        Question question = new Question("Test question?", "Test answer");
        Result result = testController.register("", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("empty field", result.toString());
    }

    @Test
    void testRegisterInvalidUsername() {
        Question question = new Question("Test question?", "Test answer");
        Result result = testController.register("invalid user", "ValidPass1!", "ValidPass1!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("wrong username format", result.toString());
    }

    @Test
    void testRegisterInvalidEmail() {
        Question question = new Question("Test question?", "Test answer");
        Result result = testController.register("validUser", "ValidPass1!", "ValidPass1!", "nickname", "invalidemail", question);
        assertFalse(result.isSuccessful());
        assertEquals("wrong email format", result.toString());
    }

    @Test
    void testRegisterWeakPassword() {
        Question question = new Question("Test question?", "Test answer");
        Result result = testController.register("validUser", "weak", "weak", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("weak password", result.toString());
    }

    @Test
    void testRegisterPasswordMismatch() {
        Question question = new Question("Test question?", "Test answer");
        Result result = testController.register("validUser", "ValidPass1!", "DifferentPass2!", "nickname", "email@test.com", question);
        assertFalse(result.isSuccessful());
        assertEquals("confirm password failed", result.toString());
    }

    @Test
    void testLoginSuccess() {
        User user = new User("testUser", "ValidPass1!", "nickname", "email@test.com");
        User.getUsers().add(user);
        Result result = testController.login("testUser", "ValidPass1!");
        assertTrue(result.isSuccessful());
        assertEquals("login successfully", result.toString());
    }

    @Test
    void testLoginUserNotFound() {
        Result result = testController.login("nonexistentUser", "ValidPass1!");
        assertFalse(result.isSuccessful());
        assertEquals("no such user exist", result.toString());
    }

    @Test
    void testLoginWrongPassword() {
        User user = new User("testUser", "ValidPass1!", "nickname", "email@test.com");
        User.getUsers().add(user);
        Result result = testController.login("testUser", "WrongPass2!");
        assertFalse(result.isSuccessful());
        assertEquals("wrong password in login", result.toString());
    }
    @Test
    public void testIsNameFormatTrue() {
        assertTrue(TestController.isNameFormatTrue("ValidName123"));
        assertTrue(TestController.isNameFormatTrue("AnotherName-456"));
        assertFalse(TestController.isNameFormatTrue("Invalid Name"));
        assertFalse(TestController.isNameFormatTrue("Invalid_Name@"));
    }

    @Test
    public void testIsEmailFormatTrue() {
        assertTrue(TestController.isEmailFormatTrue("valid.email@example.com"));
        assertTrue(TestController.isEmailFormatTrue("another_valid-email@example.co.uk"));
        assertFalse(TestController.isEmailFormatTrue("invalid.email@example"));
        assertFalse(TestController.isEmailFormatTrue("invalid-email@example..com"));
    }
    @Test
    public void testIsPasswordWeak() {
        assertTrue(TestController.isPasswordWeak("WeakPassword"));
        assertTrue(TestController.isPasswordWeak("Short!1"));
        assertFalse(TestController.isPasswordWeak("StrongPassword!1"));
    }

    @Test
    public void testAreStringsEqual() {
        assertTrue(TestController.AreStringsEqual("hello", "hello"));
        assertFalse(TestController.AreStringsEqual("hello", "world"));
    }

    @Test
    public void randomPasswordTest(){
        String randomPassword = testController.makeRandomPassword();
        assertTrue(TestController.isPasswordFormatTrue(randomPassword));
    }

    @Test
    public void wrongPass(){
        Result result = testController.register("mmd","aA12345 @#","aA12345 @#","ali","mmd@gmail.com",new Question("" , ""));
        assertEquals(result.toString(),"wrong password format");
    }
    @Test
    public void existUser(){
        testController.register("mmd","aA12345@#","aA12345@#","ali","mmd@gmail.com",new Question("" , ""));
        Result result = testController.register("mmd","aA12345@#","aA12345@#","ali","mmd@gmail.com",new Question("" , ""));
        assertEquals(result.toString(),"there is exist an user with this username");
    }
    @Test
    public void wrongPassFor(){
        testController.register("mmd","aA12345@#","aA12345@#","ali","mmd@gmail.com",new Question("" , ""));
        testController.login("mmd","aA12345@#");
        Result result = testController.changePassword("aA12345@#","aA12345 @#");
        assertEquals(result.toString(),"wrong password format");
    }
}
