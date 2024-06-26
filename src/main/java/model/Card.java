package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Card extends Rectangle {
    protected String name;
    protected Unit unit;

    public abstract void apply();

    public Card(String name,String path) {
        this.setHeight(80);
        this.setWidth(40);
        this.setFill(new ImagePattern(new Image(path)));
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
