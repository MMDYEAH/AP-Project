package model;

public class ClearWeather extends WeatherCard{
    public ClearWeather(String name,String path) {
        super(name,path);
    }

    @Override
    public void apply() {

    }
    public void unApply(){

    }
    @Override
    public String toJson() {
        return super.toJson()+"(type<ClearWeather>)}";
    }
}
