package model;

import enums.Menu;

public class App {
    private static Menu currentMenu = Menu.LOGIN_MENU;

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }
}
