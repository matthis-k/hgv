package kit.pse.hgv.view.uiHandler;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandProcessor.HyperModelCommandProcessor;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;
import kit.pse.hgv.view.RenderModel.DefaultRenderEngine;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;


import java.net.URL;
import java.util.*;

public class RenderHandler implements UIHandler{

    List<Integer> selectedNodes;

    @FXML
    private Pane renderPane;
    @FXML
    private Circle renderCircle;

    private static final int START_CENTER_X = 640;
    private static final int START_CENTER_Y = 360;
    private static final int START_RADIUS = 300;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedNodes = new ArrayList<>();
        renderCircle.setRadius(START_RADIUS);
        renderCircle.setCenterX(START_CENTER_X);
        renderCircle.setCenterY(START_CENTER_Y);
        enableDragMainCircle(renderCircle);

        renderPane.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            if(scrollEvent.isControlDown())
                zoom(scrollEvent.getDeltaY());
        });


        RenderEngine engine = new DefaultRenderEngine(1,1, new DrawManager(1, new NativeRepresentation(5, Accuracy.DIRECT)), this);
        CommandController.getInstance().register(engine);

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
                if(!currentNode.isCentered()) {
                    currentNode.getRepresentation().setCenterX(currentNode.getRepresentation().getCenterX() + START_CENTER_X);
                    currentNode.getRepresentation().setCenterY(currentNode.getRepresentation().getCenterY() + START_CENTER_Y);
                    currentNode.setCentered();
                    currentNode.getRepresentation().setFill(node.getColor());
                    bindNodeX(currentNode, renderCircle);
                    bindNodeY(currentNode, renderCircle);
                    bindRadius(currentNode, renderCircle);
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
    }

    private void zoom(double zoom) {
        if(renderCircle.getRadius() + zoom >= 0)
            renderCircle.setRadius(renderCircle.getRadius() + zoom);
    }

    private void bindLines(LineStrip strip) {
        for(Line line : strip.getLines()) {

            if(!strip.isCentered()) {
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

        }
        strip.setCentered();
    }

    private void bindNodeX(CircleNode child, Circle parent) {
        child.getRepresentation().centerXProperty().bind(renderCircle.centerXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(10)
                        .multiply(child.getRepresentation().getCenterX() - renderCircle.getCenterX())));

    }

    private void bindNodeY(CircleNode child, Circle parent) {
        child.getRepresentation().centerYProperty().bind(renderCircle.centerYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS)
                        .multiply(10).multiply(child.getRepresentation().getCenterY() - renderCircle.getCenterY())));
    }

    private void bindRadius(CircleNode child, Circle parent) {
        /*child.getRepresentation().centerYProperty().bind(parent.centerYProperty()
                .add(parent.radiusProperty().divide(parent.getRadius())
                        .multiply(child.getCenter().getY() - parent.getCenterY())));*/
        //child.getRepresentation().radiusProperty().bind(parent.radiusProperty().divide(50));
        child.getRepresentation().radiusProperty().bind(renderCircle.radiusProperty().divide(50));
    }


    private void enableDragMainCircle(final Circle circle) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getCenterX() - mouseEvent.getX();
            dragDelta.y = circle.getCenterY() - mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
        });
        circle.setOnMouseReleased(mouseEvent -> circle.getScene().setCursor(Cursor.HAND));
        circle.setOnMouseDragged(mouseEvent -> {
            if(!mouseEvent.isAltDown() && !mouseEvent.isControlDown()) {
                circle.setCenterX(mouseEvent.getX() + dragDelta.x);
                circle.setCenterY(mouseEvent.getY() + dragDelta.y);
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
    }


    //TODO moveCenter
    public void moveCenter(double x, double y) {
        new HyperModelCommandProcessor().moveCenter(x, y);
    }

}

class Delta { double x, y; }


