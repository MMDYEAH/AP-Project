package model;

import java.util.ArrayList;

public class SkelligeStorm extends WeatherCard{
    public SkelligeStorm(String name,String path) {
        super(name,path);
    }

    @Override
    public void apply() {
        ArrayList<Card> allCard = new ArrayList<>();
        allCard.addAll(Game.getCurrentGame().currentUser.getPlayBoard().siegeUnit.cards);
        allCard.addAll(Game.getCurrentGame().currentUser.getPlayBoard().rangedCombatUnit.cards);
        allCard.addAll(Game.getCurrentGame().enemy.getPlayBoard().rangedCombatUnit.cards);
        allCard.addAll(Game.getCurrentGame().enemy.getPlayBoard().siegeUnit.cards);
        for(Card card : allCard){
            UnitCard unitCard = (UnitCard) card;
            unitCard.setPower(1);
        }
    }
    public void unApply(){

    }
}
