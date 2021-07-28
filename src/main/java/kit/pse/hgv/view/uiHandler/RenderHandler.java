package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.MoveCenterCommand;
import kit.pse.hgv.representation.*;
import kit.pse.hgv.view.RenderModel.DefaultRenderEngine;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;


import java.net.URL;
import java.util.*;

public class RenderHandler implements UIHandler{

    @FXML
    private Pane renderPane;
    @FXML
    private Circle renderCircle;
    @FXML
    private CheckBox centerCheckBox;

    private int currentlySelected;
    private Circle center;

    private DrawManager manager;

    private static final int START_CENTER_X = 640;
    private static final int START_CENTER_Y = 360;
    private static final int START_RADIUS = 300;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCenter();

        renderCircle.setFill(Color.DARKGREY);

        renderCircle.setRadius(START_RADIUS);
        renderCircle.setCenterX(START_CENTER_X);
        renderCircle.setCenterY(START_CENTER_Y);

        centerCheckBox.layoutXProperty().bind(renderPane.prefWidthProperty().subtract(450));
        centerCheckBox.layoutYProperty().bind(renderPane.prefHeightProperty().subtract(25));

        enableDragCC(renderCircle, center);

        renderPane.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            if(scrollEvent.isControlDown())
                zoom(scrollEvent.getDeltaY());
        });

        manager = new DrawManager(1, new NativeRepresentation(5, Accuracy.DIRECT));

        RenderEngine engine = new DefaultRenderEngine(1,1, manager, this);
        CommandController.getInstance().register(engine);
        renderPane.getChildren().add(center);
    }

    @FXML
    public void renderGraph(List<Drawable> graph) {
        ///TODO nicht immer clearen
        renderPane.getChildren().clear();
        renderPane.getChildren().add(renderCircle);

        ArrayList<Circle> nodes = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        for (Drawable node : graph) {
            if(node.isNode()){
                CircleNode currentNode = (CircleNode) node;
                if(!currentNode.isCentered()) {
                    currentNode.setCentered();
                    currentNode.getRepresentation().setFill(node.getColor());
                    bindNodeX(currentNode);
                    bindNodeY(currentNode);
                    bindRadius(currentNode);
                    selectNode(currentNode);
                }

                nodes.add(currentNode.getRepresentation());
            } else {
                LineStrip currentStrip = (LineStrip) node;
                bindLines(currentStrip);

                for (Line line : currentStrip.getLines()) {
                    line.setStroke(currentStrip.getColor());
                    lines.add(line);
                }
            }
        }

        renderPane.getChildren().addAll(lines);
        renderPane.getChildren().addAll(nodes);
        renderPane.getChildren().add(center);
        renderPane.getChildren().add(centerCheckBox);
    }

    public void changeCenterVisibility() {
        center.setVisible(false);
        if(centerCheckBox.isSelected())
            center.setVisible(true);
        else {
            Coordinate newCenterCoordinate = new CartesianCoordinate(center.getCenterX(), center.getCenterY());
            moveCenter(newCenterCoordinate);
        }
    }


    private void bindLines(LineStrip strip) {
        for(Line line : strip.getLines()) {
            if(!strip.isCentered()) {
                setupLine(line);
            }
        }
        strip.setCentered();
    }

    //TODO moveCenter
    public void moveCenter(Coordinate coordinate) {
        MoveCenterCommand c = new MoveCenterCommand(manager, coordinate);
        c.execute();

        List<Drawable> list = manager.getRenderData();
        renderGraph(list);
    }

    private void enableDragCC(final Circle circle, Circle center) {
        final Delta dragDeltaCircle = new Delta();
        final Delta dragDeltaCenter = new Delta();
        renderPane.setOnMousePressed(mouseEvent -> {
            dragDeltaCircle.x = circle.getCenterX() - mouseEvent.getX();
            dragDeltaCircle.y = circle.getCenterY() - mouseEvent.getY();
            dragDeltaCenter.x = center.getCenterX() - mouseEvent.getX();
            dragDeltaCenter.y = center.getCenterY() - mouseEvent.getY();
        });

        renderPane.setOnMouseDragged(mouseEvent -> {
            if(!mouseEvent.isShiftDown()) {
                circle.setCenterX(mouseEvent.getX() + dragDeltaCircle.x);
                circle.setCenterY(mouseEvent.getY() + dragDeltaCircle.y);
            } else {
                double x = mouseEvent.getX() + dragDeltaCenter.x;
                double y = mouseEvent.getY() + dragDeltaCenter.y;
                center.setCenterX(x);
                center.setCenterY(y);
            }
        });
    }

    private void setupLine(Line line) {
        /*line.startYProperty().unbind();
        line.startXProperty().unbind();
        line.endXProperty().unbind();
        line.endYProperty().unbind();*/

        line.setStartY(line.getStartY() + START_CENTER_Y);
        line.setEndY(line.getEndY() + START_CENTER_Y);
        line.setStartX(line.getStartX() + START_CENTER_X);
        line.setEndX(line.getEndX() + START_CENTER_X);

        line.startXProperty().bind(renderCircle.centerXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(10)
                        .multiply(line.getStartX() - renderCircle.getCenterX())));
        line.startYProperty().bind(renderCircle.centerYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(10)
                        .multiply(line.getStartY() - renderCircle.getCenterY())));
        line.endXProperty().bind(renderCircle.centerXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(10)
                        .multiply(line.getEndX() - renderCircle.getCenterX())));
        line.endYProperty().bind(renderCircle.centerYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(10)
                        .multiply(line.getEndY() - renderCircle.getCenterY())));
    }

    private void setupCenter() {
        center = new Circle();
        center.setRadius(5);
        center.setFill(Color.CYAN);
        center.layoutXProperty().bind(renderCircle.centerXProperty());
        center.layoutYProperty().bind(renderCircle.centerYProperty());
        center.setVisible(false);
    }

    private void zoom(double zoom) {
        if(renderCircle.getRadius() + zoom >= 0)
            renderCircle.setRadius(renderCircle.getRadius() + zoom);
    }

    private void bindNodeX(CircleNode child) {
        child.getRepresentation().layoutXProperty().bind(renderCircle.centerXProperty());
        child.getRepresentation().centerXProperty().bind(renderCircle.layoutXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(10)
                        .multiply(child.getRepresentation().getCenterX())));

    }

    private void bindNodeY(CircleNode child) {
        child.getRepresentation().layoutYProperty().bind(renderCircle.centerYProperty());
        child.getRepresentation().centerYProperty().bind(renderCircle.layoutYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS)
                        .multiply(10).multiply(child.getRepresentation().getCenterY())));
    }

    private void bindRadius(CircleNode child) {
        child.getRepresentation().radiusProperty().bind(renderCircle.radiusProperty().divide(100));
    }

    private void selectNode(CircleNode node) {
        node.getRepresentation().setOnMouseClicked(mouseEvent -> {
            currentlySelected = node.getID();
            DetailHandler.getInstance().updateDisplayedDate(currentlySelected, node.getColor(), node.getCenter().toPolar());
        });
    }

}

class Delta { double x, y; }


