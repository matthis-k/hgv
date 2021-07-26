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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import kit.pse.hgv.controller.commandProcessor.HyperModelCommandProcessor;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;

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

    }

    @FXML
    public void renderGraph(List<Drawable> graph) {

        for (Drawable node : graph) {
            if(node.isNode()){
                CircleNode currentNode = (CircleNode) node;
                bindNodeX(currentNode, renderCircle);
                bindNodeY(currentNode, renderCircle);
            }
            node.draw(renderPane);
        }

    }

    private void zoom(double zoom) {
        renderCircle.setRadius(renderCircle.getRadius() + zoom);
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


