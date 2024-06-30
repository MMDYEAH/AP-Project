package model;

public class SpellUnit extends Unit{
    @Override
    public String toJson() {
        return "{unit(name<spell>)}";

    }
    @Override
    public String arrayToJson() {
        StringBuilder stringBuilder = new StringBuilder("{unit(name<spell>)(cardsArray<");
        for (Card card : cards){
            stringBuilder.append(card.toJson());
        }
        stringBuilder.append(">)}");
        return stringBuilder.toString();
    }
}
