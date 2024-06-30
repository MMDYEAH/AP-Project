package model;

public class PlayBoard {
    protected int score, numberOfVetoes;
    protected DeckUnit deckUnit;
    protected HandUnit handUnit;
    protected RangedCombatUnit rangedCombatUnit;
    protected CloseCombatUnit closeCombatUnit;
    protected SiegeUnit siegeUnit;
    protected DiscardPileUnit discardPileUnit;
    public int getLegendaryCardsNumber(){
        return 0;//TODO: delete this code and write
    }
    public int getSpecialCardsNumber(){
        return 0;//TODO: delete this code and write
    }
    public int getSoliderCardsNumber(){
        return 0;//TODO: delete this code and write
    }
    public int getPowerOfAllCards(){
        return 0;//TODO: delete this code and write
    }

    public int getNumberOfVetoes() {
        return numberOfVetoes;
    }

    public void setNumberOfVetoes(int numberOfVetoes) {
        this.numberOfVetoes = numberOfVetoes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DeckUnit getDeckUnit() {
        return deckUnit;
    }

    public void setDeckUnit(DeckUnit deckUnit) {
        this.deckUnit = deckUnit;
    }

    public HandUnit getHandUnit() {
        return handUnit;
    }

    public void setHandUnit(HandUnit handUnit) {
        this.handUnit = handUnit;
    }

    public RangedCombatUnit getRangedCombatUnit() {
        return rangedCombatUnit;
    }

    public void setRangedCombatUnit(RangedCombatUnit rangedCombatUnit) {
        this.rangedCombatUnit = rangedCombatUnit;
    }

    public CloseCombatUnit getCloseCombatUnit() {
        return closeCombatUnit;
    }

    public void setCloseCombatUnit(CloseCombatUnit closeCombatUnit) {
        this.closeCombatUnit = closeCombatUnit;
    }

    public SiegeUnit getSiegeUnit() {
        return siegeUnit;
    }

    public void setSiegeUnit(SiegeUnit siegeUnit) {
        this.siegeUnit = siegeUnit;
    }

    public DiscardPileUnit getDiscardPileUnit() {
        return discardPileUnit;
    }

    public void setDiscardPileUnit(DiscardPileUnit discardPileUnit) {
        this.discardPileUnit = discardPileUnit;
    }

    public String toJson(){
        return "{playBoard(deckUnit<"+deckUnit.arrayToJson()+">)(handUnit<"+handUnit.arrayToJson()+">)(closeUnit<"+closeCombatUnit.arrayToJson()+
                rangedCombatUnit.arrayToJson()+">)(siegeUnit<"+siegeUnit.arrayToJson()+">)(discardUnit<"+discardPileUnit.arrayToJson()+">)}";
    }
}
