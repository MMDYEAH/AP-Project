package controller;

import model.Result;
import model.User;
import view.ProfileMenu;

public class ProfileMenuController {
    ProfileMenu profileMenu;

    public ProfileMenuController(ProfileMenu profileMenu) {
        this.profileMenu = profileMenu;
    }

    public Result changeUsername(String username) {
        if (User.getLoggedInUser().getUsername().equals(username))
            return new Result(false, "it's the same as your current username");
        else if (username.equals(""))
            return new Result(false, "empty username");
        else if (User.getUserByUsername(username) != null)
            return new Result(false, "there is exist an user with this username");
        else if (!ErrorController.isNameFormatTrue(username)) {
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
        else if (!ErrorController.isEmailFormatTrue(email))
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
        else if (!ErrorController.isPasswordFormatTrue(newPassword))
            return new Result(false, "wrong password format");
        else if (ErrorController.isPasswordWeak(newPassword))
            return new Result(false, "weak password");
        else {
            User.getLoggedInUser().setPassword(newPassword);
            return new Result(true, "password changed successfully");
        }
    }

    public Result showGamesHistory(int numberOfGames) {
        return null;
    }
}
