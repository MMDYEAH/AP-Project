package model;


import controller.GameMenuController;

public class Muster extends UnitCard{
    public Muster(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        String nameForCheck = name.split(" ")[0];
        for (Card card : Game.getCurrentGame().getCurrentUser().getPlayBoard().getHandUnit().cards){
            String cardNameForCheck = card.getName().split(" ")[0];
            System.out.println(cardNameForCheck+" == "+nameForCheck);
            if (cardNameForCheck.equals(nameForCheck)){
                System.out.println(card.getName());
                String type = card.getName().substring(card.getName().indexOf("<"));
                type = type.substring(1, type.length() - 1);
                GameMenuController controller = Game.getCurrentGame().getController();
                PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
                if (type.equals("close")){
                    controller.putCard(card, currentPlayBoard.getCloseCombatUnit(), true);
                } else if (type.equals("ranged")) {
                    controller.putCard(card, currentPlayBoard.getRangedCombatUnit(), true);
                } else if (type.equals("siege")) {
                    controller.putCard(card, currentPlayBoard.getSiegeUnit(), true);
                }

            }
        }
    }
}
