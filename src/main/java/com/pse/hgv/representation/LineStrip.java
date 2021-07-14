package com.pse.hgv.representation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

public class LineStrip extends Drawable {

    private CircleNode start;
    private CircleNode end;
    private List<Coordinate> coordinates;
    private Line representation;

    public LineStrip(CircleNode start, CircleNode end, int id, Color color) {
        super(id, color);
        this.start = start;
        this.end = end;
        representation = new Line();
        representation.startXProperty().bind(start.getRepresentation().centerXProperty());
        representation.startYProperty().bind(start.getRepresentation().centerYProperty());

        representation.endXProperty().bind(end.getRepresentation().centerXProperty());
        representation.endYProperty().bind(end.getRepresentation().centerYProperty());
    }

    public LineStrip(List<Coordinate> coordinates, int id, Color color) {
        super(id, color);
        this.coordinates = coordinates;
    }

    @Override
    public void draw(Pane pane) {
        pane.getChildren().add(representation);
        representation.setVisible(true);
    }
}
