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

/**
 * This class manages the graphic representation of the current graph.
 */
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
    private static final int ONLY_GRAPH = 1;
    private static final double FACTOR_VIEW = 10;
    private int startRadiusForCenter = 300;


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

        manager = new DrawManager(ONLY_GRAPH, new NativeRepresentation(5, Accuracy.DIRECT));

        RenderEngine engine = new DefaultRenderEngine(ONLY_GRAPH,ONLY_GRAPH, manager, this);
        CommandController.getInstance().register(engine);
        renderPane.getChildren().add(center);
    }

    @FXML
    public void renderGraph(List<Drawable> graph) {
        renderPane.getChildren().clear();
        renderPane.getChildren().add(renderCircle);

        ArrayList<Circle> nodes = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        for (Drawable node : graph) {
            if(node.isNode()){
                CircleNode currentNode = (CircleNode) node;

                if(!currentNode.isCentered())
                    setupNode(currentNode);

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
        buildRenderPane(nodes, lines);
    }

    public void changeCenterVisibility() {
        center.setVisible(false);
        if(centerCheckBox.isSelected()){
            center.centerYProperty().unbind();
            center.centerXProperty().unbind();
            center.setVisible(true);
        } else {
            double x = ((center.getCenterX() - renderCircle.getCenterX())/FACTOR_VIEW);
            double y = ((center.getCenterY() - renderCircle.getCenterY())/(-FACTOR_VIEW));
            Coordinate newCenterCoordinate = new CartesianCoordinate(x ,y);
            moveCenter(newCenterCoordinate);
            bindCenter();
        }
    }

    private void buildRenderPane(ArrayList<Circle> nodes, ArrayList<Line> lines) {
        renderPane.getChildren().addAll(lines);
        renderPane.getChildren().addAll(nodes);
        renderPane.getChildren().add(center);
        renderPane.getChildren().add(centerCheckBox);
    }

    private void bindLines(LineStrip strip) {
        for(Line line : strip.getLines())
            setupLine(line);
    }

    //TODO moveCenter
    public void moveCenter(Coordinate coordinate) {
        MoveCenterCommand c = new MoveCenterCommand(coordinate);
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
                center.setCenterX(mouseEvent.getX() + dragDeltaCenter.x);
                center.setCenterY(mouseEvent.getY() + dragDeltaCenter.y);
            }
        });
    }

    private void setupLine(Line line) {
        line.layoutXProperty().bind(renderCircle.centerXProperty());
        line.layoutYProperty().bind(renderCircle.centerYProperty());

        line.startXProperty().bind(renderCircle.layoutXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW)
                        .multiply(line.getStartX())));
        line.startYProperty().bind(renderCircle.layoutYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW)
                        .multiply(line.getStartY())));
        line.endXProperty().bind(renderCircle.layoutXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW)
                        .multiply(line.getEndX())));
        line.endYProperty().bind(renderCircle.layoutYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW)
                        .multiply(line.getEndY())));
    }

    private void setupNode(CircleNode currentNode) {
        currentNode.setCentered();
        currentNode.getRepresentation().setFill(currentNode.getColor());
        bindNodeX(currentNode);
        bindNodeY(currentNode);
        bindRadius(currentNode);
        selectNode(currentNode);
    }

    private void setupCenter() {
        center = new Circle();
        center.setRadius(5);
        center.setFill(Color.CYAN);

        bindCenter();
        center.setVisible(false);
    }

    private void zoom(double zoom) {
        if(renderCircle.getRadius() + zoom >= 0 && startRadiusForCenter + zoom >= 0) {
            startRadiusForCenter += zoom;
            renderCircle.setRadius(renderCircle.getRadius() + zoom);
        }
    }

    private void bindCenter() {
        center.centerXProperty().bind(renderCircle.centerXProperty()
                .add(renderCircle.radiusProperty().divide(startRadiusForCenter)
                        .multiply(center.getCenterX() - renderCircle.getCenterX())));
        center.centerYProperty().bind(renderCircle.centerYProperty()
                .add(renderCircle.radiusProperty().divide(startRadiusForCenter)
                        .multiply(center.getCenterY() - renderCircle.getCenterY())));
    }

    private void bindNodeX(CircleNode child) {
        child.getRepresentation().layoutXProperty().bind(renderCircle.centerXProperty());
        child.getRepresentation().centerXProperty().bind(renderCircle.layoutXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW)
                        .multiply(child.getRepresentation().getCenterX())));

    }

    /**
     * This methods binds the y coordinate of a node to the renderCircle.
     * @param child
     */
    private void bindNodeY(CircleNode child) {
        child.getRepresentation().layoutYProperty().bind(renderCircle.centerYProperty());
        child.getRepresentation().centerYProperty().bind(renderCircle.layoutYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW)
                        .multiply(child.getRepresentation().getCenterY())));
    }

    /**
     * This method binds the radius of a node of the graph.
     * @param child
     */
    private void bindRadius(CircleNode child) {
        child.getRepresentation().radiusProperty().bind(renderCircle.radiusProperty().divide(100));
    }

    /**
     * Add a mouseClick event to a node. This is used to display the meta data in the DetailContainer.
     * @param node
     */
    private void selectNode(CircleNode node) {
        node.getRepresentation().setOnMouseClicked(mouseEvent -> {
            currentlySelected = node.getID();
            DetailHandler.getInstance().updateDisplayedDate(currentlySelected, node.getColor(), node.getCenter().toPolar());
        });
    }

}

class Delta { double x, y; }


