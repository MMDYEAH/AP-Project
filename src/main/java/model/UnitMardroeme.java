package model;

public class UnitMardroeme extends UnitCard{

    public UnitMardroeme(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {

    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<UnitMardroeme>)}";
    }
}
