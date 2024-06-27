package model;

public class Decoy extends Card{
    public Decoy(String name, String path) {
        super(name,path);
    }
    private Card cardShouldBeReplaced;
    public void setCardShouldBeReplaced(Card card){
        cardShouldBeReplaced = card;
    }
    @Override
    public void apply() {
       cardShouldBeReplaced.unit.removeCardFromUnit(cardShouldBeReplaced);
       Game.currentGame.currentUser.getPlayBoard().getHandUnit().addCardToUnit(cardShouldBeReplaced);
    }
}
