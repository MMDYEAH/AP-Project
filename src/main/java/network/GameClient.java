package network;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import view.FinishGameMenu;
import view.GameMenu;
import view.LoginMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameClient extends Application {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private LoginMenu loginMenu;

    @Override
    public void start(Stage primaryStage) {
        try {
            App.setGameClient(this);
            loginMenu = new LoginMenu(this);
            loginMenu.start(primaryStage);

            socket = new Socket(SERVER_IP, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(this::listenForServerMessages).start();
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listenForServerMessages() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.equals("empty field") || message.equals("there is exist an user with this username")
                        || message.equals("wrong username format")
                        || message.equals("wrong email format")
                        || message.equals("wrong password format")
                        || message.equals("weak password")
                        || message.equals("user created successfully")
                        || message.equals("wrong password")
                        || message.equals("confirm password failed")) {
                    signUp(message);
                } else if (message.equals("no such user exist") || message.equals("wrong password in login")
                        || message.equals("login successfully")) {
                    login(message);
                } else if (message.equals("user not online or not exist")) {
                    notAccept();
                } else if (message.endsWith("request game to you")) {
                    handleGameRequest(message);
                } else if (message.equals("reject")) {
                    handleRejectGame();
                } else if (message.equals("accept")) {
                    handleAcceptGame();
                } else if (message.startsWith("ready for game:")) {
                    System.out.println("game update");
                    updateGameState(message);
                } else if (message.equals("pass")) {
                    handlePassing();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePassing() {
        Game game = loginMenu.getMainMenu().getOnlineGame();
        GameMenu gameMenu = loginMenu.getMainMenu().getPreGameMenu().getGameMenu();
        Platform.runLater(() -> {
            game.setEnemyPassRound(true);
            if (game.isMePassRound()) {
                if (Game.getCurrentGame().isEnemyPassRound()) {
                    int myPower = Integer.parseInt(gameMenu.myTotalPower.getText());
                    int enemyPower = Integer.parseInt(gameMenu.enemyTotalPower.getText());
                    if (myPower > enemyPower) {
                        checkLives(gameMenu.enemyLives);
                        Game.getCurrentGame().setEnemyLive(game.getEnemyLive() - 1);
                    } else if (enemyPower > myPower) {
                        checkLives(gameMenu.myLives);
                        Game.getCurrentGame().setMylive(game.getMylive() - 1);
                    }
                    int size = game.getRoundsScore().size();
                    ArrayList<Integer> scores = new ArrayList<>();
                    scores.add(myPower);
                    scores.add(enemyPower);
                    game.getRoundsScore().put(size, scores);
                    if (game.getRoundsScore().size() == 3) {
                        System.out.println("finish");
                        try {
                            gameMenu.stop();
                            FinishGameMenu finishGameMenu = new FinishGameMenu();
                            finishGameMenu.start(App.getStage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        gameMenu.finishRoundForMe(game.getCurrentUser().getPlayBoard().getCloseCombatUnit());
                        gameMenu.finishRoundForMe(game.getCurrentUser().getPlayBoard().getRangedCombatUnit());
                        gameMenu.finishRoundForMe(game.getCurrentUser().getPlayBoard().getSiegeUnit());
                        gameMenu.finishRoundForEnemy(game.getNextUser().getPlayBoard().getCloseCombatUnit());
                        gameMenu.finishRoundForEnemy(game.getNextUser().getPlayBoard().getRangedCombatUnit());
                        gameMenu.finishRoundForEnemy(game.getNextUser().getPlayBoard().getSiegeUnit());
                        gameMenu.settingPowersToZero(gameMenu.myTotalPower, gameMenu.mySiegePower, gameMenu.myRangedPower, gameMenu.myClosePower,
                                gameMenu.enemyTotalPower, gameMenu.enemySiegePower, gameMenu.enemyRangedPower, gameMenu.enemyClosePower);
                        game.setMePassRound(false);
                        game.setEnemyPassRound(false);
                    }
                }
            }
        });
    }


    private void checkLives(Text lives) {
        int live = Integer.parseInt(lives.getText().substring(8));
        System.out.println("live: "+live);
        if (live == 2) lives.setText("lives : 1");
        else if (live == 1) {
            try {
                loginMenu.getMainMenu().getPreGameMenu().getGameMenu().stop();
                FinishGameMenu finishGameMenu = new FinishGameMenu();
                finishGameMenu.start(App.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAcceptGame() {
        Platform.runLater(() -> loginMenu.getMainMenu().acceptGame());
    }

    private void handleRejectGame() {
        Platform.runLater(() -> loginMenu.getMainMenu().alertReject());
    }

    private void notAccept() {
        Platform.runLater(() -> loginMenu.getMainMenu().showNotAccept());
    }

    private void login(String message) {
        Platform.runLater(() -> {
            if (message.equals("login successfully")) {
                loginMenu.loggedInSuccessfullyVideoPlay();
            } else if (message.equals("wrong password in login")) {
                loginMenu.wrongPasswordVideoPlay();
            } else if (message.equals("no such user exist")) {
                loginMenu.noSuchUserExistVideoPlay();
            }
        });
    }

    private void handleGameRequest(String message) {
        Platform.runLater(() -> {
            loginMenu.getMainMenu().alertRequest(message);
        });
    }

    private void updateGameState(String gameState) {
        System.out.println("game state:" + gameState.substring(15));

        Platform.runLater(() -> {
            Game game = loginMenu.getMainMenu().getOnlineGame();
            User enemy = userExtract(gameState.substring(15));
            User localEnemy = game.getNextUser();
            localEnemy.setFactionLeaderCard(enemy.getFactionLeaderCard());
            updateLocalCards(enemy.getPlayBoard().getDeckUnit(), localEnemy.getPlayBoard().getDeckUnit());
            updateLocalCards(enemy.getPlayBoard().getHandUnit(), localEnemy.getPlayBoard().getHandUnit());
            updateLocalCards(enemy.getPlayBoard().getSiegeUnit(), localEnemy.getPlayBoard().getSiegeUnit());
            updateLocalCards(enemy.getPlayBoard().getRangedCombatUnit(), localEnemy.getPlayBoard().getRangedCombatUnit());
            updateLocalCards(enemy.getPlayBoard().getCloseCombatUnit(), localEnemy.getPlayBoard().getCloseCombatUnit());
            updateOnlineCard(enemy.getPlayBoard().getDeckUnit(), localEnemy.getPlayBoard().getDeckUnit());
            updateOnlineCard(enemy.getPlayBoard().getHandUnit(), localEnemy.getPlayBoard().getHandUnit());
            updateOnlineCard(enemy.getPlayBoard().getSiegeUnit(), localEnemy.getPlayBoard().getSiegeUnit());
            updateOnlineCard(enemy.getPlayBoard().getRangedCombatUnit(), localEnemy.getPlayBoard().getRangedCombatUnit());
            updateOnlineCard(enemy.getPlayBoard().getCloseCombatUnit(), localEnemy.getPlayBoard().getCloseCombatUnit());
            game.setNextUser(enemy);
            game.setEnemy(enemy);
            if (loginMenu.getMainMenu().getPreGameMenu().getGameMenu() != null) {
                loginMenu.getMainMenu().getPreGameMenu().getGameMenu().addListeners();
                loginMenu.getMainMenu().getPreGameMenu().getGameMenu().fillingUnits();
                loginMenu.getMainMenu().getPreGameMenu().getGameMenu().updatePowerText();
            }
            game.setTurnNumber(game.getTurnNumber() + 1);
        });
    }

    private void updateOnlineCard(Unit online, Unit local) {
        System.out.println("update online");
        for (Card onlineCard : online.getCards()) {
            if (onlineCard == null) continue;
            Card isExistCard = null;
            for (Card offlineCard : local.getCards()) {
                if (offlineCard.getName().equals(onlineCard.getName())) isExistCard = offlineCard;
            }
            if (isExistCard == null) {
                local.getCards().add(onlineCard);
                if (local instanceof WarUnit) {
                    onlineCard.setUnit(local);
                    onlineCard.apply();
                    if (onlineCard instanceof UnitCard) {
                        ((UnitCard) onlineCard).setPower(((UnitCard) onlineCard).getPower());
                    }
                }
            }
        }
    }

    private void updateLocalCards(Unit online, Unit local) {
        System.out.println("update local");
        if (local.getCards().size() == 0) return;
        for (int i = local.getCards().size() - 1; i >= 0; i--) {
            Card card = local.getCards().get(i);
            Card isExistCard = null;
            for (Card onlineCard : online.getCards()) {
                if (onlineCard.getName().equals(card.getName())) isExistCard = onlineCard;
            }
            local.getCards().remove(card);
            if (isExistCard != null) {
                local.getCards().add(isExistCard);
            }
        }
    }

    private User userExtract(String userJson) {
        String username = extractField(userJson, "name");
        String password = extractField(userJson, "password");
        String nickname = extractField(userJson, "nickname");
        String email = extractField(userJson, "email");
        String score = extractField(userJson, "score");
        String wins = extractField(userJson, "wins");
        String losts = extractField(userJson, "losts");
        String draws = extractField(userJson, "draws");
        String question = extractField(userJson, "text");
        String answer = extractField(userJson, "answer");
        ObservableList<Card> deckUnitCards = extractCards(userJson, "deckUnit");
        ObservableList<Card> handUnitCards = extractCards(userJson, "handUnit");
        ObservableList<Card> closeUnitCards = extractCards(userJson, "closeUnit");
        ObservableList<Card> rangedUnitCards = extractCards(userJson, "rangedUnit");
        ObservableList<Card> siegeUnitCards = extractCards(userJson, "siegeUnit");
        ObservableList<Card> discardUnitCards = extractCards(userJson, "discardUnit");
        System.out.println("close:" + closeUnitCards + "ranged:" + rangedUnitCards + "siege" + siegeUnitCards);
        User user = new User(username, password, nickname, email);
        user.setScore(Integer.parseInt(score));
        user.setNumOfWins(Integer.parseInt(wins));
        user.setNumOfLosts(Integer.parseInt(losts));
        user.setNumOfDraws(Integer.parseInt(draws));
        Question q = new Question(question, answer);
        user.setQuestion(q);
        PlayBoard playBoard = new PlayBoard();
        HandUnit handUnit = new HandUnit();
        handUnit.setCards(handUnitCards);
        DeckUnit deckUnit = new DeckUnit();
        deckUnit.setCards(deckUnitCards);
        CloseCombatUnit closeCombatUnit = new CloseCombatUnit();
        closeCombatUnit.setCards(closeUnitCards);
        RangedCombatUnit rangedCombatUnit = new RangedCombatUnit();
        rangedCombatUnit.setCards(rangedUnitCards);
        SiegeUnit siegeUnit = new SiegeUnit();
        siegeUnit.setCards(siegeUnitCards);
        DiscardPileUnit discardPileUnit = new DiscardPileUnit();
        discardPileUnit.setCards(discardUnitCards);
        playBoard.setDeckUnit(deckUnit);
        playBoard.setHandUnit(handUnit);
        playBoard.setCloseCombatUnit(closeCombatUnit);
        playBoard.setRangedCombatUnit(rangedCombatUnit);
        playBoard.setSiegeUnit(siegeUnit);
        playBoard.setDiscardPileUnit(discardPileUnit);
        user.setPlayBoard(playBoard);
        return user;
    }

    private String extractField(String input, String fieldName) {
        String patternString = fieldName + "<(.*?)>";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1) : "";
    }

    private ObservableList<Card> extractCards(String input, String unitType) {
        Pattern unitPattern = Pattern.compile("\\(" + unitType + "<\\{unit\\(name<" + unitType.replace("Unit", "") + ">\\)\\(cardsArray<(?<cards>(\\{card\\(name<[a-zA-z0-9<> ]+>\\)\\(path<[a-zA-Z0-9_:./]+>\\)\\(unit<(?<unit>(\\{unit\\(name<\\w+>\\)})*)>\\)\\(power<[0-9-]+>\\)\\(hero<\\w+>\\)\\(type<\\w+>\\)})*)>\\)}>\\)");
        Matcher unitMatcher = unitPattern.matcher(input);
        ObservableList<Card> cards = FXCollections.observableList(new ArrayList<>());
        if (unitMatcher.find()) {
            System.out.println("---cards" + unitMatcher.group("cards"));
            String cardsArray = unitMatcher.group("cards");
            String cardPatternString = "\\{card\\(name<(?<name>[a-zA-z0-9<> ]+)>\\)\\(path<(?<path>[a-zA-Z0-9_:./]+)>\\)\\(unit<(?<unit>(\\{unit\\(name<\\w+>\\)})*)>\\)\\(power<(?<power>[0-9-]+)>\\)\\(hero<(?<hero>\\w+)>\\)\\(type<(?<type>\\w+)>\\)}";
            Pattern cardPattern = Pattern.compile(cardPatternString);
            Matcher cardMatcher = cardPattern.matcher(cardsArray);
            while (cardMatcher.find()) {
                String name = cardMatcher.group("name");
                String path = cardMatcher.group("path");
                String unit = cardMatcher.group("unit");
                String power = cardMatcher.group("power");
                String hero = cardMatcher.group("hero");
                String type = cardMatcher.group("type");
                System.out.println("***" + name + " " + power + " " + hero + " " + type);
                Card card = null;
                if (power.equals("-1")) {
                    if (type.equals("BitingFrost")) {
                        card = new BitingFrost(name, path);
                    } else if (type.equals("ClearWeather")) {
                        card = new ClearWeather(name, path);
                    } else if (type.equals("CommandersHorn")) {
                        card = new CommandersHorn(name, path);
                    } else if (type.equals("Decoy")) {
                        card = new Decoy(name, path);
                    } else if (type.equals("ImpenetrableFog")) {
                        card = new ImpenetrableFog(name, path);
                    } else if (type.equals("Mardroeme")) {
                        card = new Mardroeme(name, path);
                    } else if (type.equals("SpellScorch")) {
                        card = new SpellScorch(name, path);
                    } else if (type.equals("TorrentialRain")) {
                        card = new TorrentialRain(name, path);
                    } else if (type.equals("SkelligeStorm")) {
                        card = new SkelligeStorm(name, path);
                    }
                } else {
                    if (type.equals("Berserker")) {
                        card = new Berserker(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("Medic")) {
                        card = new Medic(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("MoralBooster")) {
                        card = new MoralBooster(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("Muster")) {
                        card = new Muster(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("Scorch")) {
                        card = new Scorch(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("Solider")) {
                        card = new Solider(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("Spy")) {
                        card = new Spy(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("TightBound")) {
                        card = new TightBound(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("Transformers")) {
                        card = new Transformers(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("UnitCommandersHorn")) {
                        card = new UnitCommandersHorn(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    } else if (type.equals("UnitMardroeme")) {
                        card = new UnitMardroeme(name, Integer.parseInt(power), Boolean.getBoolean(hero), path);
                    }
                }
                card.setPrefHeight(90);
                card.setPrefWidth(50);
                cards.add(card);
                //TODO: setUnitOfCard;
            }
        }
        return cards;
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signUp(String message) {
        Platform.runLater(() -> {
            if (message.equals("empty field")) {
                loginMenu.emptyFieldVideoPlay();
            } else if (message.equals("there is exist an user with this username")) {
                loginMenu.thereIsExistUserWithUsernameVideoPlay();
            } else if (message.equals("wrong username format")) {
                loginMenu.wrongUsernameFormatVideoPlay();
            } else if (message.equals("wrong email format")) {
                loginMenu.wrongEmailFormatVideoPlay();
            } else if (message.equals("wrong password format")) {
                loginMenu.wrongPasswordFormatVideoPlay();
            } else if (message.equals("weak password")) {
                loginMenu.weakPasswordVideoPlay();
            } else if (message.equals("user created successfully")) {
                loginMenu.userCreatedSuccessfullyVideoPlay();
            } else if (message.equals("wrong password")) {
                loginMenu.wrongPasswordVideoPlay();
            } else if (message.equals("confirm password failed")) {
                loginMenu.confirmPasswordFailedVideoPlay();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
