package model;

public class Mardroeme extends Card {
    public Mardroeme(String name, String path) {
        super(name, path);
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
        return super.toJson() + "(type<Mardroeme>)}";
    }
}
