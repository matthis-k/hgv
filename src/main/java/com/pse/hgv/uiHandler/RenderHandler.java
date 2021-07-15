package com.pse.hgv.uiHandler;

import com.pse.hgv.representation.CartesianCoordinate;
import com.pse.hgv.representation.CircleNode;
import com.pse.hgv.representation.Drawable;
import com.pse.hgv.representation.LineStrip;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //??????????????
        renderCircle.centerXProperty().bind(renderPane.widthProperty().divide(2));
        renderCircle.centerYProperty().bind(renderPane.prefHeightProperty().divide(2));


        renderCircle.radiusProperty().bind(Bindings.min(renderPane.widthProperty().divide(2), renderPane.heightProperty().subtract(50).divide(2)));
    }

    @FXML
    public void generateGraph() {
        renderPane.getChildren().clear(); //temporär
        renderPane.getChildren().add(renderCircle); //Temporär
        Random random = new Random();
        double radius = 3;

        for(int i = 0; i < 350; i ++){
            CircleNode start = new CircleNode(new CartesianCoordinate(renderCircle.getCenterX() + (random.nextInt((int)renderCircle.getRadius() * 2) - renderCircle.getRadius()), renderCircle.getCenterY() + random.nextInt((int)renderCircle.getRadius() * 2) - renderCircle.getRadius()), radius, i, Color.CYAN);
            CircleNode end = new CircleNode(new CartesianCoordinate(renderCircle.getCenterX() + (random.nextInt((int)renderCircle.getRadius() * 2) - renderCircle.getRadius()), renderCircle.getCenterY() + random.nextInt((int)renderCircle.getRadius() * 2) - renderCircle.getRadius()), radius, i, Color.GOLD);
            LineStrip line = new LineStrip(start, end, i, Color.BLACK);

            //NICHT VERWERFEN!!!!!!!!!!!
            start.getRepresentation().centerXProperty().bind(renderCircle.centerXProperty().add(renderCircle.radiusProperty().divide(renderCircle.getRadius()).multiply(start.getCenter().getX() - renderCircle.getCenterX())));
            start.getRepresentation().centerYProperty().bind(renderCircle.centerYProperty().add(renderCircle.radiusProperty().divide(renderCircle.getRadius()).multiply(start.getCenter().getY() - renderCircle.getCenterY())));
            end.getRepresentation().centerXProperty().bind(renderCircle.centerXProperty().add(renderCircle.radiusProperty().divide(renderCircle.getRadius()).multiply(end.getCenter().getX() - renderCircle.getCenterX())));
            end.getRepresentation().centerYProperty().bind(renderCircle.centerYProperty().add(renderCircle.radiusProperty().divide(renderCircle.getRadius()).multiply(end.getCenter().getY() - renderCircle.getCenterY())));

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
