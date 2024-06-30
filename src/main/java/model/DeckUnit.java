package model;

public class DeckUnit extends Unit{
    @Override
    public String toJson() {
        return "{unit(name<deck>)}";
    }
    @Override
    public String arrayToJson() {
        StringBuilder stringBuilder = new StringBuilder("{unit(name<deck>)(cardsArray<");
        for (Card card : cards){
            stringBuilder.append(card.toJson());
        }
        stringBuilder.append(">)}");
        return stringBuilder.toString();
    }
}
