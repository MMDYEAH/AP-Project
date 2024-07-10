package controller;

import model.Result;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ProfileMenu;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileMenuControllerTest {

    private ProfileMenuController profileMenuController;
    private User user;

    @BeforeEach
    public void setUp4() {
        user = new User("currentUsername", "currentPassword", "currentNickname", "currentEmail@example.com");
        User.setLoggedInUser(user);
        profileMenuController = new ProfileMenuController(new ProfileMenu());
    }

    @Test
    public void testChangeUsername_SameUsername() {
        Result result = profileMenuController.changeUsername("currentUsername");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current username", result.toString());
    }

    @Test
    public void testChangeUsername_EmptyUsername() {
        Result result = profileMenuController.changeUsername("");

        assertFalse(result.isSuccessful());
        assertEquals("empty username", result.toString());
    }

    @Test
    public void testChangeUsername_UsernameExists() {
        User anotherUser = new User("existingUsername", "password", "nickname", "email@example.com");
        User.addToUsers(anotherUser);

        Result result = profileMenuController.changeUsername("existingUsername");

        assertFalse(result.isSuccessful());
        assertEquals("there is exist an user with this username", result.toString());
    }

    @Test
    public void testChangeUsername_WrongFormat() {
        Result result = profileMenuController.changeUsername("wrong_format");

        assertFalse(result.isSuccessful());
        assertEquals("wrong username format", result.toString());
    }

    @Test
    public void testChangeUsername_Success() {
        Result result = profileMenuController.changeUsername("newUsername");

        assertTrue(result.isSuccessful());
        assertEquals("username changed successfully", result.toString());
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testChangeNickname_SameNickname() {
        Result result = profileMenuController.changeNickname("currentNickname");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current nickname", result.toString());
    }

    @Test
    public void testChangeNickname_EmptyNickname() {
        Result result = profileMenuController.changeNickname("");

        assertFalse(result.isSuccessful());
        assertEquals("empty nickname", result.toString());
    }

    @Test
    public void testChangeNickname_Success() {
        Result result = profileMenuController.changeNickname("newNickname");

        assertTrue(result.isSuccessful());
        assertEquals("nickname changed successfully", result.toString());
        assertEquals("newNickname", user.getNickname());
    }

    @Test
    public void testChangeEmail_SameEmail() {
        Result result = profileMenuController.changeEmail("currentEmail@example.com");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current email", result.toString());
    }

    @Test
    public void testChangeEmail_EmptyEmail() {
        Result result = profileMenuController.changeEmail("");

        assertFalse(result.isSuccessful());
        assertEquals("empty email", result.toString());
    }

    @Test
    public void testChangeEmail_WrongFormat() {
        Result result = profileMenuController.changeEmail("wrongFormat");

        assertFalse(result.isSuccessful());
        assertEquals("wrong email format", result.toString());
    }

    @Test
    public void testChangeEmail_Success() {
        Result result = profileMenuController.changeEmail("newEmail@example.com");

        assertTrue(result.isSuccessful());
        assertEquals("email changed successfully", result.toString());
        assertEquals("newEmail@example.com", user.getEmail());
    }

    @Test
    public void testChangePassword_WrongOldPassword() {
        Result result = profileMenuController.changePassword("wrongOldPassword", "newPassword");

        assertFalse(result.isSuccessful());
        assertEquals("old password in wrong", result.toString());
    }

    @Test
    public void testChangePassword_SamePassword() {
        Result result = profileMenuController.changePassword("currentPassword", "currentPassword");

        assertFalse(result.isSuccessful());
        assertEquals("it's the same as your current password", result.toString());
    }

    @Test
    public void testChangePassword_EmptyNewPassword() {
        Result result = profileMenuController.changePassword("currentPassword", "");

        assertFalse(result.isSuccessful());
        assertEquals("empty password", result.toString());
    }

//    @Test
//    public void testChangePassword_WrongFormat() {
//        Result result = controller.changePassword("currentPassword", "wrongFormat");
//
//        assertFalse(result.isSuccessful());
//        assertEquals("wrong password format", result.toString());
//    }

    @Test
    public void testChangePassword_WeakPassword() {
        Result result = profileMenuController.changePassword("currentPassword", "weakpass");

        assertFalse(result.isSuccessful());
        assertEquals("weak password", result.toString());
    }

    @Test
    public void testChangePassword_Success() {
        Result result = profileMenuController.changePassword("currentPassword", "NewPass1!");

        assertTrue(result.isSuccessful());
        assertEquals("password changed successfully", result.toString());
        assertEquals("NewPass1!", user.getPassword());
    }
}
