package model;

public class ImpenetrableFog extends WeatherCard{
    public ImpenetrableFog(String name,String path) {
        super(name,path);
    }

    @Override
    public void apply() {
        for (Card card : Game.getCurrentGame().currentUser.getPlayBoard().rangedCombatUnit.cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(1);
            }
        }
        for (Card card : Game.getCurrentGame().nextUser.getPlayBoard().rangedCombatUnit.cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(1);
            }
        }
        //TODO: apply somethings like commanders horn
    }
    public void unApply(){
        for (Card card : Game.getCurrentGame().currentUser.getPlayBoard().rangedCombatUnit.cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(((UnitCard)card).firstPower);
            }
        }
        for (Card card : Game.getCurrentGame().nextUser.getPlayBoard().rangedCombatUnit.cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(((UnitCard)card).firstPower);
            }
        }
        //TODO: apply somethings like commanders horn
    }
}
