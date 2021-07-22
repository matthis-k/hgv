package com.pse.hgv.uiHandler;

import com.pse.hgv.representation.CartesianCoordinate;
import com.pse.hgv.representation.CircleNode;
import com.pse.hgv.representation.Drawable;
import com.pse.hgv.representation.LineStrip;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class RenderHandler implements UIHandler, Initializable {

    @FXML
    private Pane renderPane;
    @FXML
    private Circle renderCircle;

    private static final int MIDDLE_FACTOR = 2;
    private static final int DOUBLE_HEIGHT_MENU = 50;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderCircle.centerXProperty().bind(renderPane.widthProperty().divide(MIDDLE_FACTOR));
        renderCircle.centerYProperty().bind(renderPane.prefHeightProperty().divide(MIDDLE_FACTOR));

        renderCircle.radiusProperty().bind(Bindings.min(renderPane.widthProperty().divide(MIDDLE_FACTOR),
                renderPane.heightProperty().subtract(DOUBLE_HEIGHT_MENU).divide(MIDDLE_FACTOR)));
    }

    @FXML
    public void generateGraph() {
        renderPane.getChildren().clear(); //temporär
        renderPane.getChildren().add(renderCircle); //Temporär
        Random random = new Random();
        double radius = 5;

        for(int i = 0; i < 3000; i ++){

            double startX = renderCircle.getCenterX()
                    + (random.nextInt((int)renderCircle.getRadius() * MIDDLE_FACTOR) - renderCircle.getRadius());
            double startY = renderCircle.getCenterY()
                    + (random.nextInt((int)renderCircle.getRadius() * MIDDLE_FACTOR) - renderCircle.getRadius());

            double endX = renderCircle.getCenterX()
                    + (random.nextInt((int)renderCircle.getRadius() * MIDDLE_FACTOR) - renderCircle.getRadius());
            double endY = renderCircle.getCenterY()
                    + (random.nextInt((int)renderCircle.getRadius() * MIDDLE_FACTOR) - renderCircle.getRadius());

            CircleNode start = new CircleNode(new CartesianCoordinate(startX, startY), radius, i, Color.CYAN);
            CircleNode end = new CircleNode(new CartesianCoordinate(endX, endY), radius, i, Color.GOLD);
            LineStrip line = new LineStrip(start, end, i, Color.BLACK);

            //NICHT VERWERFEN!!!!!!!!!!!
            start.getRepresentation().centerXProperty().bind(renderCircle.centerXProperty()
                    .add(renderCircle.radiusProperty().divide(renderCircle.getRadius())
                            .multiply(start.getCenter().getX() - renderCircle.getCenterX())));

            start.getRepresentation().centerYProperty().bind(renderCircle.centerYProperty().
                    add(renderCircle.radiusProperty().divide(renderCircle.getRadius())
                            .multiply(start.getCenter().getY() - renderCircle.getCenterY())));

            end.getRepresentation().centerXProperty().bind(renderCircle.centerXProperty()
                    .add(renderCircle.radiusProperty().divide(renderCircle.getRadius())
                            .multiply(end.getCenter().getX() - renderCircle.getCenterX())));

            end.getRepresentation().centerYProperty().bind(renderCircle.centerYProperty()
                    .add(renderCircle.radiusProperty().divide(renderCircle.getRadius())
                            .multiply(end.getCenter().getY() - renderCircle.getCenterY())));
            //NICHT VERWERFEN!!!!!!!


            boolean firstNode = false;
            boolean secondNode = false;

            if(Math.sqrt(Math.pow(start.getCenter().getX() - renderCircle.getCenterX(), 2) + Math.pow(start.getCenter().getY() - renderCircle.getCenterY(), 2)) <= renderCircle.getRadius()) {
                addChild(start);
                firstNode = true;
            }

            if(Math.sqrt(Math.pow(end.getCenter().getX() - renderCircle.getCenterX(), 2) + Math.pow(end.getCenter().getY() - renderCircle.getCenterY(), 2)) <= renderCircle.getRadius()) {
                addChild(end);
                secondNode = true;
            }

            if(firstNode && secondNode)
                addChild(line);
        }
    }

    public void addChild(Drawable node) {
        node.draw(renderPane);
    }
}
