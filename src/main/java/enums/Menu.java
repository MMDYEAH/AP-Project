package enums;

import view.*;

import java.util.Scanner;

public enum Menu {
    GAME_MENU(new GameMenu()),
    MAIN_MENU(new MainMenu()),
    LOGIN_MENU(new LoginMenu()),
    PROFILE_MENU(new ProfileMenu()),
    EXIT_MENU(new ExitMenu()),
    PREGAME_MENU(new PreGameMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void run(Scanner scanner) {
        this.menu.run(scanner);
    }
}
