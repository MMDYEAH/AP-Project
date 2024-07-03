package model;

public class TorrentialRain extends WeatherCard{
    public TorrentialRain(String name,String path) {
        super(name,path);
    }

    @Override
    public void apply() {
//TODO: commanders horn
        for (Card card : Game.getCurrentGame().getCurrentUser().getPlayBoard().getSiegeUnit().cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(1);
            }
        }
        for (Card card : Game.getCurrentGame().getNextUser().getPlayBoard().getSiegeUnit().cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(1);
            }
        }
        //TODO: apply somethings like commanders horn
    }
    public void unApply(){
        for (Card card : Game.getCurrentGame().getCurrentUser().getPlayBoard().getSiegeUnit().cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(((UnitCard)card).firstPower);
            }
        }
        for (Card card : Game.getCurrentGame().getNextUser().getPlayBoard().getSiegeUnit().cards){
            if (!((UnitCard)card).isLegendary){
                ((UnitCard)card).setPower(((UnitCard)card).firstPower);
            }
        }
        //TODO: apply somethings like commanders horn
    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<TorrentialRain>)}";
    }
}
