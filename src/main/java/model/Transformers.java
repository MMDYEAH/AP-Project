package model;

public class Transformers extends UnitCard {
    public Transformers(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        // you should this function after one round!
        if (this.name.equals("Kambi") || this.name.equals("Cow"))
            this.power = 8;
    }
}
