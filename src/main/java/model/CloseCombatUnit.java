package model;

public class CloseCombatUnit extends WarUnit {
    @Override
    public String toJson() {
        return "{unit(name<close>)}";
    }
    @Override
    public String arrayToJson() {
        StringBuilder stringBuilder = new StringBuilder("{unit(name<close>)(cardsArray<");
        for (Card card : cards){
            stringBuilder.append(card.toJson());
        }
        stringBuilder.append(">)}");
        return stringBuilder.toString();
    }
}
