package view;

import controller.PreGameMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

public class PreGameMenu extends Application {
    @FXML
    private TilePane leaderCard;
    @FXML
    private Text numberOfHeroCards;
    @FXML
    private Text strengthOfCards;
    @FXML
    private Text slashAnd22;
    @FXML
    private Text numberOfUnitCards;
    @FXML
    private Text totalCardsInDeck;
    @FXML
    private ScrollPane scrollPaneOfCardsInDeck;
    @FXML
    private TilePane tilePaneOfCardsInDeck;
    @FXML
    private ScrollPane scrollPaneOfCardCollection;
    @FXML
    private TilePane tilePaneOfCardCollection;

    PreGameMenuController controller = new PreGameMenuController(this);

    @Override
    public void start(Stage stage) throws IOException {
        App.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/PreGame.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        controller.initialize();
        scrollPaneOfCardsInDeck = (ScrollPane) scene.lookup("#scrollPaneOfCardsInDeck");
        tilePaneOfCardsInDeck = (TilePane) scrollPaneOfCardsInDeck.getContent().lookup("#tilePaneOfCardsInDeck");
        scrollPaneOfCardCollection = (ScrollPane) scene.lookup("#scrollPaneOfCardCollection");
        tilePaneOfCardCollection = (TilePane) scrollPaneOfCardCollection.getContent().lookup("#tilePaneOfCardCollection");
        totalCardsInDeck = (Text) scene.lookup("#totalCardsInDeck");
        numberOfUnitCards = (Text) scene.lookup("#numberOfUnitCards");
        slashAnd22 = (Text) scene.lookup("#slashAnd22");
        strengthOfCards = (Text) scene.lookup("#strengthOfCards");
        numberOfHeroCards = (Text) scene.lookup("#numberOfHeroCards");
        leaderCard = (TilePane) scene.lookup("#leaderCard");
        addCardsOfNorthernRealmsFactionToScrollPane(pane);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void addCardsOfNorthernRealmsFactionToScrollPane(Pane pane) {
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().add(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
        for (Card card : App.getRealmsNorthenFaction().getUnitCards()) {
            card.setWidth(card.getWidth() * 3 / 4);
            card.setHeight(card.getHeight() * 3 / 4);
            card.setOnMouseClicked(e -> {
                //TODO : you should run the code i commented when other menus completed
                //User.getLoggedInUser().getPlayBoard().getDeckUnit().addCardToUnit(card);
                tilePaneOfCardsInDeck.getChildren().add(card);
                int numberOfTotalCards = Integer.parseInt(totalCardsInDeck.getText());
                numberOfTotalCards+=1;
                totalCardsInDeck.setText(String.valueOf(numberOfTotalCards));
                if((card instanceof UnitCard)){
                    int parsedIntNumberOfUnitCards = Integer.parseInt(numberOfUnitCards.getText());
                    parsedIntNumberOfUnitCards+=1;
                    numberOfUnitCards.setText(String.valueOf(parsedIntNumberOfUnitCards));
                    if(parsedIntNumberOfUnitCards>22) pane.getChildren().remove(slashAnd22);
                    int parsedIntStrengthOfCards = Integer.parseInt(strengthOfCards.getText());
                    parsedIntStrengthOfCards += ((UnitCard) card).getPower();
                    strengthOfCards.setText(String.valueOf(parsedIntStrengthOfCards));
                    if(((UnitCard) card).isLegendary()){
                        int parsedIntnumberOfHeroCards = Integer.parseInt(numberOfHeroCards.getText());
                        parsedIntnumberOfHeroCards += 1;
                        numberOfHeroCards.setText(String.valueOf(parsedIntnumberOfHeroCards));
                    }
                }
            });
            tilePaneOfCardCollection.getChildren().add(card);
        }

    }



    public static void main(String[] args) {
        launch(args);
    }

    public void goToGame(MouseEvent mouseEvent) throws Exception {
        GameMenu gameMenu = new GameMenu();
        this.stop();
        gameMenu.start(App.getStage());
    }
}

