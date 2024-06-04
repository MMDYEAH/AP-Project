package controller;

public class ErrorController {
    public static boolean isNameFormatTrue(String name) {
        return name.matches("[A-Za-z0-9-]+");
    }

    public static boolean isEmailFormatTrue(String email) {
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
    }

    public static boolean isPasswordFormatTrue(String password) {
        return password.matches("[A-Za-z0-9!&$#@^]+");
    }

    public static boolean isPasswordWeak(String password) {
        return password.matches("(?=.*[!&$#@^])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}");
    }

    public static boolean AreStringsEqual(String firstString, String secondString) {
        return firstString.equals(secondString);
    }

    public static boolean isNumberValid(int number) {
        return false;//TODO: delete this code and write
    }

    public static boolean isFileFormatValid() { //TODO: inputsOfThisFunction
        return false;//TODO: delete this code and write
    }

    public static boolean isCountNumberTrue(int count) {
        return false;//TODO: delete this code and write
    }
}
