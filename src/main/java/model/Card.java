package model;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class Card extends Pane {
    protected String name;
    protected Unit unit;

    public abstract void apply();

    public Card(String name,String path) {
        this.setPrefHeight(400);
        this.setPrefWidth(200);
        this.setBackground(new Background(new BackgroundImage(new Image(path), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT
        ,BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false))));
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
