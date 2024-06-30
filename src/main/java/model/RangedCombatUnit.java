package model;

public class RangedCombatUnit extends WarUnit {
    @Override
    public String toJson() {
        return "{unit(name<ranged>)}";
    }
    @Override
    public String arrayToJson() {
        StringBuilder stringBuilder = new StringBuilder("{unit(name<ranged>)(cardsArray<");
        for (Card card : cards){
            stringBuilder.append(card.toJson());
        }
        stringBuilder.append(">)}");
        return stringBuilder.toString();
    }
}
