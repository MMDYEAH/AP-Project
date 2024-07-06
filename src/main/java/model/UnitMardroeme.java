package model;

public class UnitMardroeme extends UnitCard{

    public UnitMardroeme(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        for (Card card : unit.cards) {
            if (card instanceof Berserker) {
                transformToMoralBooster((Berserker) card);
            }
        }
    }

    private void transformToMoralBooster(Berserker berserker) {
        MoralBooster moralBooster = new MoralBooster("Vidkaarl", berserker.getPower(), berserker.isLegendary(), getClass().getResource("/pics/skellige/Vildkaarl.jpg").toExternalForm());

        berserker.unit.removeCardFromUnit(berserker);
        berserker.unit.addCardToUnit(moralBooster);
//        Game.currentGame.currentUser.getPlayBoard().getDiscardPileUnit().addCardToUnit(berserker);

        moralBooster.unit = berserker.unit;
        moralBooster.apply();
    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<UnitMardroeme>)}";
    }
}
