package network;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import view.ChatBoxMenu;
import view.FinishGameMenu;
import view.GameMenu;
import view.LoginMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
//            EmailVerificationServer.setLoginMenu(loginMenu);
//            EmailVerificationServer.startServer();
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
                    System.out.println("sign up check");
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
                } else if (message.startsWith("enemy update:")) {
                    System.out.println("game update");
                    updateEnemyState(message);
                } else if (message.startsWith("spell update:")) {
                    System.out.println("game update");
                    updateSpell(message);
                } else if (message.startsWith("send user:")) {
                    handleGettingUser(message);
                } else if (message.startsWith("forgotQuestion:")) {
                    handleForgot(message);
                } else if (message.startsWith("rank user:")) {
                    System.out.println("rank user");
                    handleRankUser(message);
                } else if (message.startsWith("chat:")) {
                    handleChat(message);
                } else if (message.equals("bad img")) {
                    Platform.runLater(() -> loginMenu.getMainMenu().getPreGameMenu().getGameMenu().makeEmojiOfBadImg());
                } else if (message.equals("nice img")) {
                    Platform.runLater(() -> loginMenu.getMainMenu().getPreGameMenu().getGameMenu().makeEmojiOfNiceImg());
                } else if (message.equals("nice play")) {
                    Platform.runLater(() -> loginMenu.getMainMenu().getPreGameMenu().getGameMenu().popUpText(new Text("nice play!")));
                } else if (message.equals("not good")) {
                    Platform.runLater(() -> loginMenu.getMainMenu().getPreGameMenu().getGameMenu().popUpText(new Text("not good")));
                } else if (message.startsWith("friend request:")) {
                    handleFriendRequest(message);
                } else if (message.startsWith("accept friend:")) {
                    handleAcceptFriendRequest(message);
                } else if (message.equals("pass")) {
                    handlePassing();
                }  else if (message.equals("send rank finish")) {
                    handleShowRanks();
                } else if (message.startsWith("password:")) {
                    getPassword(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateSpell(String message) {
        String json = message.replaceAll("spell update:","");
        ObservableList<Card> spells = extractCards(json,"spellUnit");
        Game game = loginMenu.getMainMenu().getOnlineGame();
        for (Card card : spells){
            if (!game.getSpellUnit().getCards().contains(card)){
                game.getSpellUnit().getCards().add(card);
                card.apply();
            }
        }
    }

    private void updateEnemyState(String message) {

        Platform.runLater(() -> {
            Game game = loginMenu.getMainMenu().getOnlineGame();
            User my = userExtract(message.replaceAll("enemy update:",""));
            User localMy = game.getCurrentUser();
            updateLocal(game, my, localMy,false);
        });
    }

    private void updateLocal(Game game, User my, User localMy, boolean nextTurn) {
        localMy.setFactionLeaderCard(my.getFactionLeaderCard());
        updateLocalCards(my.getPlayBoard().getDeckUnit(), localMy.getPlayBoard().getDeckUnit());
        updateLocalCards(my.getPlayBoard().getHandUnit(), localMy.getPlayBoard().getHandUnit());
        updateLocalCards(my.getPlayBoard().getSiegeUnit(), localMy.getPlayBoard().getSiegeUnit());
        updateLocalCards(my.getPlayBoard().getRangedCombatUnit(), localMy.getPlayBoard().getRangedCombatUnit());
        updateLocalCards(my.getPlayBoard().getCloseCombatUnit(), localMy.getPlayBoard().getCloseCombatUnit());
        updateOnlineCard(my.getPlayBoard().getDeckUnit(), localMy.getPlayBoard().getDeckUnit());
        updateOnlineCard(my.getPlayBoard().getHandUnit(), localMy.getPlayBoard().getHandUnit());
        updateOnlineCard(my.getPlayBoard().getSiegeUnit(), localMy.getPlayBoard().getSiegeUnit());
        updateOnlineCard(my.getPlayBoard().getRangedCombatUnit(), localMy.getPlayBoard().getRangedCombatUnit());
        updateOnlineCard(my.getPlayBoard().getCloseCombatUnit(), localMy.getPlayBoard().getCloseCombatUnit());
        updateFaction(my,localMy);
        game.setNextUser(my);
        game.setEnemy(my);
        if (loginMenu.getMainMenu().getPreGameMenu().getGameMenu() != null) {
            loginMenu.getMainMenu().getPreGameMenu().getGameMenu().addListeners();
            loginMenu.getMainMenu().getPreGameMenu().getGameMenu().fillingUnits();
            loginMenu.getMainMenu().getPreGameMenu().getGameMenu().updatePowerText();
        }
        if (nextTurn) game.setTurnNumber(game.getTurnNumber() + 1);
    }

    private void handleShowRanks() {
        Platform.runLater(()-> {
            try {
                loginMenu.getMainMenu().pointChart(loginMenu.getMainMenu().getRoot());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleRankUser(String message) {
        String json = message.replaceAll("rank user:", "");
        String username = extractField(json, "name");
        String nickName = extractField(json, "nickname");
        String score = extractField(json, "score");
        User user = new User(username, "", nickName, "");
        user.setScore(Integer.parseInt(score));
        System.out.println("user : " + user);
        App.getRankedUsers().add(user);
    }

    private void getPassword(String message) {
        String password = message.replaceAll("password:", "");
        Platform.runLater(() -> loginMenu.showPassword(password));
    }

    private void handleForgot(String message) {
        String question = message.replaceAll("forgotQuestion:", "");
        String text = extractField(question, "text");
        String answer = extractField(question, "answer");
        Platform.runLater(() -> loginMenu.forgotQuestion(text, answer));
    }

    private void handleChat(String message) {
        ChatBoxMenu chatBoxMenu = loginMenu.getMainMenu().getPreGameMenu().getGameMenu().getChatBoxMenu();
        chatBoxMenu.setGameMenu(loginMenu.getMainMenu().getPreGameMenu().getGameMenu());
        String clear = message.substring(5);
        String[] variables = clear.split("&");
        Platform.runLater(() -> chatBoxMenu.addMessage(variables[0] + " : " + variables[1] + " ( " + variables[2] + " )"));
    }

    private void handleFriendRequest(String message) {
        String username = message.replaceAll("friend request:", "");
        User.getLoggedInUser().getFriendsRequest().add(username);
    }

    private void handleGettingUser(String message) {
        System.out.println("friend: " + message.substring(10));
        String username = extractField(message, "name");
        String nickname = extractField(message, "nickname");
        String score = extractField(message, "score");
        User userForFriend = new User(username, "", nickname, "");
        userForFriend.setScore(Integer.parseInt(score));
        if (!User.getLoggedInUser().getFriends().contains(username)) {
            Platform.runLater(() -> loginMenu.getMainMenu().getFriendRequestMenu().addUser(userForFriend));
        }
    }

    private void handleAcceptFriendRequest(String message) {
        String username = message.replaceAll("accept friend:", "");
        User.getLoggedInUser().getFriends().add(username);
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
                    int size = game.getRoundsScore().size();
                    ArrayList<Integer> scores = new ArrayList<>();
                    scores.add(myPower);
                    scores.add(enemyPower);
                    game.getRoundsScore().put(size, scores);
                    System.out.println("size:" + size + ",arr:" + scores);
                    if (myPower > enemyPower) {
                        checkLives(gameMenu.enemyLives);
                        Game.getCurrentGame().setEnemyLive(game.getEnemyLive() - 1);
                    } else if (enemyPower > myPower) {
                        checkLives(gameMenu.myLives);
                        Game.getCurrentGame().setMylive(game.getMylive() - 1);
                    }
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
                        game.getSpellUnit().getCards().clear();
                        game.setMePassRound(false);
                        game.setEnemyPassRound(false);
                    }
                }
            }
        });
    }


    private void checkLives(Text lives) {
        int live = Integer.parseInt(lives.getText().substring(8));
        System.out.println("live: " + live);
        if (live == 2) lives.setText("lives : 1");
        else if (live == 1) {
            try {
                if (loginMenu.getMainMenu().getOnlineGame().getRoundsScore().size() == 2) {
                    loginMenu.getMainMenu().getOnlineGame().getRoundsScore().put(2, new ArrayList<>(List.of(new Integer[]{0, 0})));
                }
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
            updateLocal(game, enemy, localEnemy,true);
        });
    }

    private void updateFaction(User enemy,User local) {
        local.setFactionLeaderCard(enemy.getFactionLeaderCard());
        if (enemy.getFaction() instanceof RealmsNorthenFaction){
            local.setFaction(new RealmsNorthenFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (enemy.getFaction() instanceof EmpireNilfgaardianFaction) {
            local.setFaction(new EmpireNilfgaardianFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (enemy.getFaction() instanceof ScoiataelFaction) {
            local.setFaction(new SociataelFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (enemy.getFaction() instanceof SkelligeFaction) {
            local.setFaction(new SkelligeFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (enemy.getFaction() instanceof MonstersFaction) {
            local.setFaction(new MonstersFaction(new ArrayList<>(),new ArrayList<>()));
        }
        System.out.println("local faction : "+local.getFaction().toJson());
        Platform.runLater(()->loginMenu.getMainMenu().getPreGameMenu().getGameMenu().updateEnemyFaction());
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
        String faction = extractField(userJson,"factionChosen");
        String factionName = extractField(faction,"name");
        Matcher leaderCard = extractFactionLeader(userJson);
        System.out.println("leader:"+leaderCard);
        String leaderName = leaderCard.group("name");
        System.out.println("name:"+leaderName);
        String leaderPath = leaderCard.group("path");
        System.out.println("path:"+leaderPath);
        FactionLeaderCard factionLeaderCard = new FactionLeaderCard(leaderName,leaderPath);
        ObservableList<Card> deckUnitCards = extractCards(userJson, "deckUnit");
        ObservableList<Card> handUnitCards = extractCards(userJson, "handUnit");
        ObservableList<Card> closeUnitCards = extractCards(userJson, "closeUnit");
        ObservableList<Card> rangedUnitCards = extractCards(userJson, "rangedUnit");
        ObservableList<Card> siegeUnitCards = extractCards(userJson, "siegeUnit");
        ObservableList<Card> discardUnitCards = extractCards(userJson, "discardUnit");
        System.out.println("close:" + closeUnitCards + "ranged:" + rangedUnitCards + "siege" + siegeUnitCards);
        User user = new User(username, password, nickname, email);
        if (factionName.equals("RealmsNorthen")){
            user.setFaction(new RealmsNorthenFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (factionName.equals("EmpireNilfgaardian")) {
            user.setFaction(new EmpireNilfgaardianFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (factionName.equals("Sociatael")) {
            user.setFaction(new SociataelFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (factionName.equals("Skellige")) {
            user.setFaction(new SkelligeFaction(new ArrayList<>(),new ArrayList<>()));
        } else if (factionName.equals("Monsters")) {
            user.setFaction(new MonstersFaction(new ArrayList<>(),new ArrayList<>()));
        }
        user.setFactionLeaderCard(factionLeaderCard);
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

    private Matcher extractFactionLeader(String userJson) {
        Pattern pattern = Pattern.compile("\\(factionLeader<\\{leader\\(name<(?<name>[a-zA-Z0-9 ]+)>\\)\\(path<(?<path>[a-zA-Z0-9_:./]+)>\\)}>\\)");
        Matcher matcher = pattern.matcher(userJson);
        if (matcher.find()) return matcher;
        else return null;
    }

    private String extractField(String input, String fieldName) {
        String patternString = fieldName + "<(.*?)>";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1) : "";
    }

    private ObservableList<Card> extractCards(String input, String unitType) {
        Pattern unitPattern = Pattern.compile("\\(" + unitType + "<\\{unit\\(name<" + unitType.replace("Unit", "") + ">\\)\\(cardsArray<(?<cards>(\\{card\\(name<[a-zA-Z0-9<> ]+>\\)\\(path<[a-zA-Z0-9_:./]+>\\)\\(unit<(?<unit>(\\{unit\\(name<\\w+>\\)})*)>\\)\\(power<[0-9-]+>\\)\\(hero<\\w+>\\)\\(type<\\w+>\\)})*)>\\)}>\\)");
        Matcher unitMatcher = unitPattern.matcher(input);
        ObservableList<Card> cards = FXCollections.observableList(new ArrayList<>());
        if (unitMatcher.find()) {
            System.out.println("---cards" + unitMatcher.group("cards"));
            String cardsArray = unitMatcher.group("cards");
            String cardPatternString = "\\{card\\(name<(?<name>[a-zA-Z0-9<> ]+)>\\)\\(path<(?<path>[a-zA-Z0-9_:./]+)>\\)\\(unit<(?<unit>(\\{unit\\(name<\\w+>\\)})*)>\\)\\(power<(?<power>[0-9-]+)>\\)\\(hero<(?<hero>\\w+)>\\)\\(type<(?<type>\\w+)>\\)}";
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
        System.out.println("get error message");
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

    public void setLoginMenu(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }

    public LoginMenu getLoginMenu() {
        return loginMenu;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
