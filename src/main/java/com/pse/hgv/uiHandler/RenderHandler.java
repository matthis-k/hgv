package com.pse.hgv.uiHandler;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

        //renderCircle.radiusProperty().bind(Bindings.min(renderPane.widthProperty().divide(MIDDLE_FACTOR),
        //      renderPane.heightProperty().subtract(DOUBLE_HEIGHT_MENU).divide(MIDDLE_FACTOR)));

        //renderCircle.centerXProperty().bind(renderPane.prefWidthProperty().divide(MIDDLE_FACTOR));
        //renderCircle.centerYProperty().bind(renderPane.prefHeightProperty().divide(MIDDLE_FACTOR));

        renderCircle.setCenterX(640);
        renderCircle.setCenterY(340);

        renderCircle.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            double zoomFactor = scrollEvent.getDeltaY();
            renderCircle.setRadius(renderCircle.getRadius() + zoomFactor);
        });

        enableDrag(renderCircle);

        generateGraph();

    }

    private void refresh() {
        renderPane.getChildren().clear();
        renderPane.getChildren().add(renderCircle);
    }

    @FXML
    public void generateGraph() {
        refresh();
        Random random = new Random();
        double radius = 5;

        for(int i = 0; i < 50; i ++){

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

            start.getRepresentation().radiusProperty().bind(renderCircle.radiusProperty().multiply(0.025));
            end.getRepresentation().radiusProperty().bind(renderCircle.radiusProperty().multiply(0.025));

            end.getRepresentation().centerXProperty().bind(renderCircle.centerXProperty()
                    .add(renderCircle.radiusProperty().divide(renderCircle.getRadius())
                            .multiply(end.getCenter().getX() - renderCircle.getCenterX())));

            end.getRepresentation().centerYProperty().bind(renderCircle.centerYProperty()
                    .add(renderCircle.radiusProperty().divide(renderCircle.getRadius())
                            .multiply(end.getCenter().getY() - renderCircle.getCenterY())));
            //NICHT VERWERFEN!!!!!!!


            boolean firstNode = false;
            boolean secondNode = false;

            List<Drawable> graph = new ArrayList<Drawable>();

            if(Math.sqrt(Math.pow(start.getCenter().getX() - renderCircle.getCenterX(), 2) + Math.pow(start.getCenter().getY() - renderCircle.getCenterY(), 2)) <= renderCircle.getRadius()) {
                graph.add(start);
                firstNode = true;
            }

            if(Math.sqrt(Math.pow(end.getCenter().getX() - renderCircle.getCenterX(), 2) + Math.pow(end.getCenter().getY() - renderCircle.getCenterY(), 2)) <= renderCircle.getRadius()) {
                graph.add(end);
                secondNode = true;
            }

            if(firstNode && secondNode)
                graph.add(line);

            drawGraph(graph);
        }
    }


    public void drawGraph(List<Drawable> graph) {
        for (Drawable item : graph) {
            item.draw(renderPane);
        }
    }

    // make a node movable by dragging it around with the mouse.
    private void enableDrag(final Circle circle) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = circle.getCenterX() - mouseEvent.getX();
                dragDelta.y = circle.getCenterY() - mouseEvent.getY();
                circle.getScene().setCursor(Cursor.MOVE);
            }
        });
        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                circle.setCenterX(mouseEvent.getX() + dragDelta.x);
                circle.setCenterY(mouseEvent.getY() + dragDelta.y);
            }
        });
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    circle.getScene().setCursor(Cursor.HAND);
                }
            }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    circle.getScene().setCursor(Cursor.DEFAULT);
                }
            }
        });
    }
}

class Delta { double x, y; }

