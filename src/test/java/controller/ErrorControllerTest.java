package controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorControllerTest {

    @Test
    public void testIsNameFormatTrue() {
        assertTrue(ErrorController.isNameFormatTrue("ValidName123"));
        assertTrue(ErrorController.isNameFormatTrue("AnotherName-456"));
        assertFalse(ErrorController.isNameFormatTrue("Invalid Name"));
        assertFalse(ErrorController.isNameFormatTrue("Invalid_Name@"));
    }

    @Test
    public void testIsEmailFormatTrue() {
        assertTrue(ErrorController.isEmailFormatTrue("valid.email@example.com"));
        assertTrue(ErrorController.isEmailFormatTrue("another_valid-email@example.co.uk"));
        assertFalse(ErrorController.isEmailFormatTrue("invalid.email@example"));
        assertFalse(ErrorController.isEmailFormatTrue("invalid-email@example..com"));
    }

//    @Test
//    public void testIsPasswordFormatTrue() {
//        assertTrue(ErrorController.isPasswordFormatTrue("ValidPassword123!"));
//        assertTrue(ErrorController.isPasswordFormatTrue("Another$Password456"));
//        assertFalse(ErrorController.isPasswordFormatTrue("invalidpassword"));
//        assertFalse(ErrorController.isPasswordFormatTrue("WeakPassword!"));
//    }

    @Test
    public void testIsPasswordWeak() {
        assertTrue(ErrorController.isPasswordWeak("WeakPassword"));
        assertTrue(ErrorController.isPasswordWeak("Short!1"));
        assertFalse(ErrorController.isPasswordWeak("StrongPassword!1"));
    }

    @Test
    public void testAreStringsEqual() {
        assertTrue(ErrorController.AreStringsEqual("hello", "hello"));
        assertFalse(ErrorController.AreStringsEqual("hello", "world"));
    }


    @Test
    public void testIsNumberValid() {
        assertFalse(ErrorController.isNumberValid(-1));
        assertFalse(ErrorController.isNumberValid(10));
    }


    @Test
    public void testIsFileFormatValid() {
        assertFalse(ErrorController.isFileFormatValid());
    }

    @Test
    public void testIsCountNumberTrue() {
        assertFalse(ErrorController.isCountNumberTrue(0));
        assertFalse(ErrorController.isCountNumberTrue(5));
    }
}
