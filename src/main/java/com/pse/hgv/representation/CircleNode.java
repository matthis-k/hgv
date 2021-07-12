package com.pse.hgv.representation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleNode extends Drawable{
    private CartesianCoordinate center;
    private double radius;
    private Circle representation;

    public CircleNode(CartesianCoordinate center, double radius, int id, Color color) {
        super(id, color);
        this.center = center;
        this.radius = radius;
        representation = new Circle(center.getX(), center.getY(), radius, color);
    }

    @Override
    public void draw(Pane pane) {
        pane.getChildren().add(representation);
        representation.setVisible(true);
    }

    public Circle getRepresentation() {
        return this.representation;
    }

    public CartesianCoordinate getCenter() { return this.center;}
}
