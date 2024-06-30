package model;

public class SpellScorch extends Card{
    public SpellScorch(String name, String path) {
        super(name, path);
    }

    @Override
    public void apply() {

    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<SpellScorch>)}";
    }
}
