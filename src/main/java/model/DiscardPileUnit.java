package model;

public class DiscardPileUnit extends Unit{
    @Override
    public String toJson() {
        return "{unit(name<discard>)}";
    }

    @Override
    public String arrayToJson() {
        StringBuilder stringBuilder = new StringBuilder("{unit(name<discard>)(cardsArray<");
        for (Card card : cards){
            stringBuilder.append(card.toJson());
        }
        stringBuilder.append(">)}");
        return stringBuilder.toString();
    }
}
