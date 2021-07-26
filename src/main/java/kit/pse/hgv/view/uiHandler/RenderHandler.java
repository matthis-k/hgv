package kit.pse.hgv.view.uiHandler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandProcessor.HyperModelCommandProcessor;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;
import kit.pse.hgv.view.RenderModel.DefaultRenderEngine;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;

import java.lang.reflect.Array;
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

        RenderEngine engine = new DefaultRenderEngine(1,1, new DrawManager(1, new NativeRepresentation()), this);
        CommandController.getInstance().register(engine);

    }

    @FXML
    public void renderGraph(List<Drawable> graph) {

        for (Drawable node : graph) {
            if(node.isNode()){
                CircleNode currentNode = (CircleNode) node;
                currentNode.getRepresentation().setCenterX(currentNode.getRepresentation().getCenterX() + START_CENTER_X);
                currentNode.getRepresentation().setCenterY(currentNode.getRepresentation().getCenterY() + START_CENTER_Y);
                bindNodeX(currentNode, renderCircle);
                bindNodeY(currentNode, renderCircle);
                bindRadius(currentNode, renderCircle);

                node.draw(renderPane);
            } else {
                LineStrip currentLine = (LineStrip) node;
                bindLines(currentLine.getLines());

                for (Line line : currentLine.getLines())
                    renderPane.getChildren().add(line);
            }
        }
        System.out.println(renderPane.getChildren().size());
    }

    private void zoom(double zoom) {
        renderCircle.setRadius(renderCircle.getRadius() + zoom);
    }

    private void bindLines(Vector<Line> lines) {
        for(Line line : lines) {
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

    private void bindNodeX(CircleNode child, Circle parent) {
        /*child.getRepresentation().centerXProperty().bind(parent.centerXProperty()
                .add(parent.radiusProperty().divide(START_RADIUS)
                        .multiply(child.getRepresentation().getCenterX() - parent.getCenterX())));*/
        //child.getRepresentation().centerXProperty().bind(parent.centerXProperty().add(child.getRepresentation().getCenterX() * 1000/START_RADIUS));

        //child.getRepresentation().centerXProperty().bind(parent.centerXProperty().multiply(parent.radiusProperty().divide(START_RADIUS)).add((child.getRepresentation().getCenterX() * 10 * parent.getRadius()/START_RADIUS)));
        //child.getRepresentation().centerXProperty().bind(parent.centerXProperty().multiply(parent.radiusProperty().divide(START_RADIUS)).add((child.getRepresentation().getCenterX() * 10)));
        child.getRepresentation().centerXProperty().bind(renderCircle.centerXProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS).multiply(10)
                        .multiply(child.getRepresentation().getCenterX() - renderCircle.getCenterX())));

    }

    private void bindNodeY(CircleNode child, Circle parent) {
        /*child.getRepresentation().centerYProperty().bind(parent.centerYProperty()
                .add(parent.radiusProperty().divide(START_RADIUS)
                        .multiply(child.getRepresentation().getCenterY() - parent.getCenterY())));*/
        //child.getRepresentation().centerYProperty().bind(parent.centerYProperty().add(child.getRepresentation().getCenterY() * 1000/START_RADIUS));
        child.getRepresentation().centerYProperty().bind(renderCircle.centerYProperty()
                .add(renderCircle.radiusProperty().divide(START_RADIUS)
                        .multiply(10).multiply(child.getRepresentation().getCenterY() - renderCircle.getCenterY())));
    }

    private void bindRadius(CircleNode child, Circle parent) {
        /*child.getRepresentation().centerYProperty().bind(parent.centerYProperty()
                .add(parent.radiusProperty().divide(parent.getRadius())
                        .multiply(child.getCenter().getY() - parent.getCenterY())));*/
        child.getRepresentation().radiusProperty().bind(parent.radiusProperty().divide(50));
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


