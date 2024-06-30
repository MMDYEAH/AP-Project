package model;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;


public abstract class Card extends Pane {
    protected String name,path;
    protected Unit unit;

    public abstract void apply();
    public Card(String name,String path) {
        this.setPrefHeight(400);
        this.setPrefWidth(200);
        this.setBackground(new Background(new BackgroundImage(new Image(path), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT
        ,BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        this.name = name;
        this.path = path;
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
    public String toJson(){
        if (unit == null)
            return "{card(name<"+name+">)(path<"+path+">)(unit<>)";
        else
            return "{card(name<"+name+">)(path<"+path+">)(unit<"+unit.toJson()+">)";
    }

}
