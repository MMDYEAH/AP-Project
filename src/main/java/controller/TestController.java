package controller;

import model.App;
import model.Question;
import model.Result;
import model.User;

import java.util.ArrayList;
import java.util.Random;

public class TestController {
    public static boolean isNameFormatTrue(String name) {
        return name.matches("[A-Za-z0-9-]+");
    }

    public static boolean isEmailFormatTrue(String email) {
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
    }

    public static boolean isPasswordFormatTrue(String password) {
        return password.matches("[A-Za-z0-9!&$#@]+");
    }

    public static boolean isPasswordWeak(String password) {
        return !password.matches("(?=.*[!&$#@])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}");
    }

    public static boolean AreStringsEqual(String firstString, String secondString) {
        return firstString.equals(secondString);
    }

    public void initialize() {
//        LoginMenu.questionCombo.setValue("...");
        Question.getQuestions().add(new Question("What's the name of your favorite high school teacher?", ""));
        Question.getQuestions().add(new Question("In what city did you have your first ever flight?", ""));
        Question.getQuestions().add(new Question("What was your favorite subject in high school??", ""));
    }

    public String makeRandomPassword() {
        int bigChar = new Random().nextInt(0, 26);
        int smallChar = new Random().nextInt(0, 26);
        ArrayList<String> specialCharacters = new ArrayList<>();
        specialCharacters.add("!");
        specialCharacters.add("&");
        specialCharacters.add("$");
        specialCharacters.add("#");
        specialCharacters.add("@");
        String randomSpecialCharacter = specialCharacters.get(new Random().nextInt(0, 4));
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            integers.add(new Random().nextInt(0, 9));
        }
        StringBuilder password = new StringBuilder();
        password.append(randomSpecialCharacter);
        password.append((char) ('A' + bigChar));
        password.append((char) ('a' + smallChar));
        for (int i = 0; i < 5; i++) {
            password.append(integers.get(i));
        }
        return password.toString();
    }

    public void setQuestions(User user, Question question) {
        user.setQuestion(question);
    }

    public Result register(String username, String password, String passwordConfirm, String nickname, String email, Question question) {
        if (username.equals("")
                || password.equals("")
                || passwordConfirm.equals("")
                || nickname.equals("")
                || email.equals("")) {
            return new Result(false, "empty field");
        } else if (!isNameFormatTrue(username)) {
            return new Result(false, "wrong username format");
        } else if (!isEmailFormatTrue(email)) {
            return new Result(false, "wrong email format");
        } else if (!isPasswordFormatTrue(password)) {
            return new Result(false, "wrong password format");
        } else if (isPasswordWeak(password)) {
            return new Result(false, "weak password");
        } else if (!AreStringsEqual(password, passwordConfirm)) {
            return new Result(false, "confirm password failed");
        } else {
            for (User user : User.getUsers()) {
                if (user.getUsername().equals(username))
                    return new Result(false, "there is exist an user with this username");
            }
            User user = new User(username, password, nickname, email);
            user.setQuestion(question);
            User.registeringUser = user;
            return new Result(true, "user created successfully");
        }
    }

    public Result login(String username, String password) {
        User user = User.getUserByUsername(username);
        if (user == null) return new Result(false, "no such user exist");
        else {
            if (!AreStringsEqual(password, user.getPassword())) {
                return new Result(false, "wrong password in login");
            } else {
//                loginMenu.stop();
                User.setLoggedInUser(user);
//                MainMenu mainMenu = new MainMenu();
//                mainMenu.start(App.getStage());
                return new Result(true, "login successfully");
            }
        }
    }
    public Result changeUsername(String username) {
        if (User.getLoggedInUser().getUsername().equals(username))
            return new Result(false, "it's the same as your current username");
        else if (username.equals(""))
            return new Result(false, "empty username");
        else if (User.getUserByUsername(username) != null)
            return new Result(false, "there is exist an user with this username");
        else if (!isNameFormatTrue(username)) {
            return new Result(false, "wrong username format");
        } else {
            User.getLoggedInUser().setUsername(username);
            return new Result(true, "username changed successfully");
        }
    }

    public Result changeNickname(String nickname) {
        if (User.getLoggedInUser().getNickname().equals(nickname))
            return new Result(false, "it's the same as your current nickname");
        else if (nickname.equals(""))
            return new Result(false, "empty nickname");
        else {
            User.getLoggedInUser().setNickname(nickname);
            return new Result(true, "nickname changed successfully");
        }
    }

    public Result changeEmail(String email) {
        if (User.getLoggedInUser().getEmail().equals(email))
            return new Result(false, "it's the same as your current email");
        else if (email.equals(""))
            return new Result(false, "empty email");
        else if (!isEmailFormatTrue(email))
            return new Result(false, "wrong email format");
        else {
            User.getLoggedInUser().setEmail(email);
            return new Result(true, "email changed successfully");
        }
    }

    public Result changePassword(String oldPassword, String newPassword) {
        if (!User.getLoggedInUser().getPassword().equals(oldPassword))
            return new Result(false, "old password in wrong");
        if (oldPassword.equals(newPassword))
            return new Result(false, "it's the same as your current password");
        else if (newPassword.equals(""))
            return new Result(false, "empty password");
        else if (!isPasswordFormatTrue(newPassword))
            return new Result(false, "wrong password format");
        else if (isPasswordWeak(newPassword))
            return new Result(false, "weak password");
        else {
            User.getLoggedInUser().setPassword(newPassword);
            return new Result(true, "password changed successfully");
        }
    }
}