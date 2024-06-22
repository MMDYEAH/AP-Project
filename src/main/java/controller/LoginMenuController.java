package controller;

import javafx.stage.Stage;
import model.App;
import model.Question;
import model.Result;
import model.User;
import view.LoginMenu;
import view.MainMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoginMenuController {
    LoginMenu loginMenu;

    public LoginMenuController(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }

    public void initialize() {
//        LoginMenu.questionCombo.setValue("...");
        Question.getQuestions().add(new Question("What's the name of your favorite high school teacher?", ""));
        Question.getQuestions().add(new Question("In what city did you have your first ever flight?", ""));
        Question.getQuestions().add(new Question("What was your favorite subject in high school??", ""));
    }

    public void goToMainMenu(LoginMenu loginMenu) throws Exception {
        loginMenu.stop();
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(App.getStage());
    }

    public void exit(Stage stage) {
        stage.close();
    }

    public void showCurrentMenu() {
        System.out.println("register/login menu");
    }

    public String makeRandomPassword() {
        int bigChar = App.getRandom().nextInt(0, 26);
        int smallChar = App.getRandom().nextInt(0, 26);
        ArrayList<String> specialCharacters = new ArrayList<>();
        specialCharacters.add("!");
        specialCharacters.add("&");
        specialCharacters.add("$");
        specialCharacters.add("#");
        specialCharacters.add("@");
        String randomSpecialCharacter = specialCharacters.get(App.getRandom().nextInt(0,4));
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            integers.add(App.getRandom().nextInt(0,9));
        }
        StringBuilder password = new StringBuilder();
        password.append(randomSpecialCharacter);
        password.append((char)('A' + bigChar));
        password.append((char)('a' + smallChar));
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
        } else if (User.getUserByUsername(username) != null) {
            return new Result(false, "there is exist an user with this username");
        } else if (!ErrorController.isNameFormatTrue(username)) {
            return new Result(false, "wrong username format");
        } else if (!ErrorController.isEmailFormatTrue(email)) {
            return new Result(false, "wrong email format");
        } else if (!ErrorController.isPasswordFormatTrue(password)) {
            return new Result(false, "wrong password format");
        } else if (ErrorController.isPasswordWeak(password)) {
            return new Result(false, "weak password");
        } else if (!ErrorController.AreStringsEqual(password, passwordConfirm)) {
            return new Result(false, "confirm password failed");
        } else {
            if (password.equals("random")) {
                while (true) {
                    String randomPassword = makeRandomPassword();
                    App.setRandomPassword(randomPassword);
                    String response = loginMenu.acceptRandomPassword(password);
                    if (response.equals("back")) {
                        return new Result(false, "back successfully");
                    } else if (response.equals("accept")) {
                        User user = new User(username, randomPassword, nickname, email);
                        user.setQuestion(question);
                        return new Result(true, "user created successfully");
                    } else if (response.equals("again")) {
                    }
                }
            } else {
                User user = new User(username, password, nickname, email);
                user.setQuestion(question);
                User.registeringUser = user;
                return new Result(true, "user created successfully");
            }
        }
    }

    public Result login(String username, String password) throws Exception {
        User user = User.getUserByUsername(username);
        if (user == null) return new Result(false, "no such user exist");
        else {
            if (!ErrorController.AreStringsEqual(password, user.getPassword())) {
                return new Result(false, "wrong password");
            } else {
                loginMenu.stop();
                User.setLoggedInUser(user);
                MainMenu mainMenu = new MainMenu();
                mainMenu.start(App.getStage());
                return new Result(true, "login successfully");
            }
        }
    }
}
