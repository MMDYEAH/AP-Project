package model;

public class Medic extends UnitCard {
    public Medic(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    private Card chosenCard;

    @Override
    public void apply() {
        if (chosenCard != null && Game.currentGame.currentUser.getPlayBoard().getDiscardPileUnit().cards.contains(chosenCard)) {
            Game.currentGame.currentUser.getPlayBoard().getDiscardPileUnit().removeCardFromUnit(chosenCard);

            if (chosenCard instanceof UnitCard) {
                UnitCard unitCard = (UnitCard) chosenCard;
                String unitType = getUnitType(unitCard);

                if (unitType.equals("close")) {
                    Game.currentGame.currentUser.getPlayBoard().getCloseCombatUnit().addCardToUnit(unitCard);
                    unitCard.setUnit(Game.currentGame.currentUser.getPlayBoard().getCloseCombatUnit());
                } else if (unitType.equals("ranged")) {
                    Game.currentGame.currentUser.getPlayBoard().getRangedCombatUnit().addCardToUnit(unitCard);
                    unitCard.setUnit(Game.currentGame.currentUser.getPlayBoard().getRangedCombatUnit());
                } else if (unitType.equals("siege")) {
                    Game.currentGame.currentUser.getPlayBoard().getSiegeUnit().addCardToUnit(unitCard);
                    unitCard.setUnit(Game.currentGame.currentUser.getPlayBoard().getSiegeUnit());
                }
            }
        }
    }

    public void setChosenCard(Card chosenCard) {
        this.chosenCard = chosenCard;
    }

    @Override
    public String toJson() {
        return super.toJson() + "(type<Medic>)}";
    }

    private String getUnitType(UnitCard unitCard) {
        if (unitCard.getName().contains("close")) {
            return "close";
        } else if (unitCard.getName().contains("ranged")) {
            return "ranged";
        } else if (unitCard.getName().contains("siege")) {
            return "siege";
        } else {
            return "unknown";
        }
    }
}
