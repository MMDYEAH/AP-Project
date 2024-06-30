package model;

public class SiegeUnit extends WarUnit {
    @Override
    public String toJson() {
        return "{unit(name<siege>)}";
    }
    @Override
    public String arrayToJson() {
        StringBuilder stringBuilder = new StringBuilder("{unit(name<siege>)(cardsArray<");
        for (Card card : cards){
            stringBuilder.append(card.toJson());
        }
        stringBuilder.append(">)}");
        return stringBuilder.toString();
    }
}
