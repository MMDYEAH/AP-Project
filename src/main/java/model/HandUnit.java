package model;

import java.util.ArrayList;

public class HandUnit extends Unit{
    @Override
    public String toJson() {
        return "{unit(name<hand>)}";
    }
    @Override
    public String arrayToJson() {
        StringBuilder stringBuilder = new StringBuilder("{unit(name<hand>)(cardsArray<");
        for (Card card : cards){
            stringBuilder.append(card.toJson());
        }
        stringBuilder.append(">)}");
        return stringBuilder.toString();
    }
}
