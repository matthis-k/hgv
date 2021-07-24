package com.pse.hgv.uiHandler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;

import java.net.URL;
import java.util.*;

public class RenderHandler implements UIHandler{


    @FXML
    private Pane renderPane;
    @FXML
    private Circle renderCircle;
    @FXML
    private Group group;

    private double vectorX = 0;
    private double vectorY = 0;


    private List<CircleNode> graph_nodes;
    private List<LineStrip> graph_edges;

    private static final int MIDDLE_FACTOR = 2;
    private static final int START_CENTER_X = 640;
    private static final int START_CENTER_Y = 360;
    private static final int START_RADIUS = 300;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graph_nodes = new ArrayList<>();
        graph_edges = new ArrayList<>();
        renderCircle.setRadius(START_RADIUS);
        renderCircle.setCenterX(START_CENTER_X);
        renderCircle.setCenterY(START_CENTER_Y);

        renderPane.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            if(scrollEvent.isControlDown())
                zoom(scrollEvent.getDeltaY());
        });

        enableDragMainCircle(renderCircle);
        generateGraph(); //löschen
    }

    public void zoom(double zoom) {
        renderCircle.setRadius(renderCircle.getRadius() + zoom);
    }

    @FXML
    public void generateGraph() {
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

           /* bindNodeX(start, renderCircle);
            bindNodeY(start, renderCircle);

            bindNodeX(end,renderCircle);
            bindNodeY(end,renderCircle); */

            graph_nodes.add(start);
            group.getChildren().add(start.getRepresentation());
            graph_nodes.add(end);
            group.getChildren().add(end.getRepresentation());
            graph_edges.add(line);
            group.getChildren().add(line.getRepresentation());


            if(Math.sqrt(Math.pow(start.getCenter().getX() - renderCircle.getCenterX(), 2) + Math.pow(start.getCenter().getY() - renderCircle.getCenterY(), 2)) <= renderCircle.getRadius())
                start.getRepresentation().setVisible(true);
            else
                start.getRepresentation().setVisible(false);


            if(Math.sqrt(Math.pow(end.getCenter().getX() - renderCircle.getCenterX(), 2) + Math.pow(end.getCenter().getY() - renderCircle.getCenterY(), 2)) <= renderCircle.getRadius())
                end.getRepresentation().setVisible(true);
            else
                end.getRepresentation().setVisible(false);


            if(start.getRepresentation().isVisible() && end.getRepresentation().isVisible())
                line.getRepresentation().setVisible(true);
            else
                line.getRepresentation().setVisible(false);

        }
        drawGraphNode(graph_nodes);
        drawGraphEdge(graph_edges);
    }


    public void drawGraphNode(List<CircleNode> graph) {
        for (Drawable item : graph)
            item.draw(renderPane);
    }

    public void drawGraphEdge(List<LineStrip> graph) {
        for (Drawable item : graph)
            item.draw(renderPane);
    }


    private void enableDragMainCircle(final Circle circle) {
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
                if(!mouseEvent.isAltDown() && !mouseEvent.isControlDown()) {
                    circle.setCenterX(mouseEvent.getX() + dragDelta.x);
                    circle.setCenterY(mouseEvent.getY() + dragDelta.y);
                }
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

    /*private void enableDragNode(final Circle circle) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = circle.getCenterX() - mouseEvent.getX();
                dragDelta.y = circle.getCenterY() - mouseEvent.getY();
                vectorX = 0;
                vectorY = 0;
                vectorX = (-1) * mouseEvent.getX();
                vectorY = (-1) * mouseEvent.getY();

                System.out.println("x davor" + vectorX);
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
                if(mouseEvent.isControlDown() && mouseEvent.isAltDown()) {
                    vectorX += mouseEvent.getX();
                    vectorY += mouseEvent.getY();
                    circle.setCenterX(circle.getCenterX() + vectorX + mouseEvent.getX());
                    circle.setCenterY(circle.getCenterY() + vectorY + mouseEvent.getY());

                    System.out.println("circle " + circle.getCenterX());
                    System.out.println("x danach " + vectorX);
                }
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
    }*/

/*
    private void enableDragMainCircle(final Circle circle) {
        final Delta dragDelta = new Delta();

        circle.setOnMousePressed(mouseEvent -> {
            dragDelta.x = mouseEvent.getX();
            dragDelta.y = mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
        });

        circle.setOnMouseReleased(mouseEvent -> circle.getScene().setCursor(Cursor.HAND));

        final double[] vectorX = new double[1];
        final double[] vectorY = new double[1];

        circle.setOnMouseDragged(mouseEvent -> {
            if (!mouseEvent.isControlDown() && !mouseEvent.isAltDown()) {
                circle.setCenterX(mouseEvent.getX() + dragDelta.x);
                circle.setCenterY(mouseEvent.getY() + dragDelta.y);
            } /*else if(mouseEvent.isControlDown() && mouseEvent.isAltDown()) {
                vectorX[0] = mouseEvent.getX() - dragDelta.x;
                vectorY[0] = mouseEvent.getY() - dragDelta.y;
            }

            if(vectorX[0] != 0 && vectorY[0] != 0) {
                for (CircleNode node : graph_nodes) {
                    node.getRepresentation().setCenterY(node.getRepresentation().getCenterY() + vectorY[0]);
                    node.getRepresentation().setCenterX(node.getRepresentation().getCenterX() + vectorX[0]);
                }
            }
        });


        circle.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown())
                circle.getScene().setCursor(Cursor.HAND);
        });

        circle.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown())
                circle.getScene().setCursor(Cursor.DEFAULT);
        });
    }*/

        /*private void enableDragNodes(final Circle circle) {
        final Delta dragDelta = new Delta();
        final double[] startMouseX = new double[1];
        final double[] startMouseY = new double[1];

        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startMouseX[0] = mouseEvent.getX();
                startMouseY[0] = mouseEvent.getY();
                dragDelta.x = circle.getCenterX() - mouseEvent.getX();
                dragDelta.y = circle.getCenterY() - mouseEvent.getY();
                circle.getScene().setCursor(Cursor.MOVE);
            }});

        circle.setOnMouseReleased(mouseEvent -> circle.getScene().setCursor(Cursor.HAND));

        circle.setOnMouseDragged(mouseEvent -> {
            if(mouseEvent.isControlDown() && mouseEvent.isAltDown()) {
                for (CircleNode node : graph_nodes) {
                    node.getRepresentation().setCenterX(mouseEvent.getX() + node.getRepresentation().getCenterX() - startMouseX[0]);
                    node.getRepresentation().setCenterY(mouseEvent.getY() + node.getRepresentation().getCenterY() - startMouseY[0]);
                }
            }
        });

        circle.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown())
                circle.getScene().setCursor(Cursor.HAND);
        });

        circle.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown())
                circle.getScene().setCursor(Cursor.DEFAULT);
        });
    }*/

    class Delta { double x, y; } //record?

    public void moveCenter(double x, double y) {
        //TODO
    }

    public void moveArea() {
        //TODO
    }

    private void bindNodeX(CircleNode child, Circle parent) {
        child.getRepresentation().centerXProperty().bind(parent.centerXProperty()
                .add(parent.radiusProperty().divide(parent.getRadius())
                        .multiply(child.getCenter().getX() - parent.getCenterX())));
    }

    private void bindNodeY(CircleNode child, Circle parent) {
        child.getRepresentation().centerYProperty().bind(parent.centerYProperty()
                .add(parent.radiusProperty().divide(parent.getRadius())
                        .multiply(child.getCenter().getY() - parent.getCenterY())));
    }
}


