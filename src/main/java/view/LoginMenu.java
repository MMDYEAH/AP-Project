package view;

import controller.LoginMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class LoginMenu extends Application {
    LoginMenuController controller = new LoginMenuController(this);
    @Override
    public void start(Stage stage) throws Exception {
        controller.initialize();
        // note: save question that user choose on Question.setNowQuestion()
    }

    public String acceptRandomPassword(String password){
        /**
         * this function show a scene with password made
         * and get accept or user back to menu or request another random password
         * @param string: a random password
         * @return if accept : "accept" if request another password : "again" if back to menu : "back"
         */
        return null; // delete this and write code
    }
}
