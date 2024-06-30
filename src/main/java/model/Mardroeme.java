package model;

public class Mardroeme extends Card{
    public Mardroeme(String name, String path) {
        super(name, path);
    }

    @Override
    public void apply() {

    }
    @Override
    public String toJson() {
        return super.toJson()+"(type<Mardroeme>)}";
    }
}
