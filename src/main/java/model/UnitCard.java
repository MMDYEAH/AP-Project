package model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public abstract class UnitCard extends Card{
    protected boolean isLegendary;
    protected int power;
    Circle circle;
    Text powerText;

    protected int firstPower;
    public UnitCard(String name, int power, boolean isLegendary,String path) {
        super(name,path);
        this.power = power;
        this.firstPower = power;
        this.isLegendary = isLegendary;
        powerText = new Text(Integer.toString(power));
        circle = new Circle(this.getLayoutX()+20,this.getLayoutY()+25,this.getPrefHeight()/400*30);
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            circle.setRadius(newValue.doubleValue()/200*25);
            double centerX = newValue.doubleValue() / 2;
            circle.setCenterX(centerX-(newValue.doubleValue()/200)*75);
            powerText.setX(circle.getCenterX()-circle.getRadius()/2.5);
        });
        this.heightProperty().addListener((observable, oldValue, newValue)  -> {
            circle.setRadius(newValue.doubleValue()/400*25);
            double centerY = newValue.doubleValue() / 2;
            circle.setCenterY(centerY-(newValue.doubleValue()/400)*170);
            powerText.setY(circle.getCenterY()+circle.getRadius()/2);
            powerText.setStyle("-fx-font: +"+newValue.doubleValue()/400*35+" arial;");
        });
        circle.setFill(Color.WHITE);
        powerText.setX(circle.getCenterX());
        powerText.setY(circle.getCenterY());
        this.getChildren().addAll(circle,powerText);
    }
    public abstract void apply();

    public int getFirstPower() {
        return firstPower;
    }

    public void setFirstPower(int firstPower) {
        this.firstPower = firstPower;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
        powerText.setText(Integer.toString(power));
    }
}
