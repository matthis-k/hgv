package kit.pse.hgv.view.uiHandler;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

/**
 * This class manages the graphic representation of the current graph.
 */
public class RenderHandler implements UIHandler {

    @FXML
    private Pane renderPane;
    @FXML
    private Circle renderCircle;
    @FXML
    private CheckBox centerCheckBox;

    /**
     * ID of the currently selected node/edge.
     */
    private int currentlySelected;
    private Circle center;

    private RenderEngine currentEngine;

    private static final int START_CENTER_X = 640;
    private static final int START_CENTER_Y = 360;
    private static final int START_RADIUS = 300;
    private static final int FIRST_GRAPH = 1;
    private static final double FACTOR_VIEW = 10;
    private int startRadiusForCenter = 300;
    private static final int NODE_SIZE = 5;
    private static final int CHECKBOX_X_OFFSET = 450;
    private static final int CHECKBOX_Y_OFFSET = 25;
    private Button zoomIn;
    private Button zoomOut;

    private ArrayList<RenderEngine> engines;
    private int currentID;

    private static RenderHandler instance;

    public int getCurrentID() {
        return currentID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        engines = new ArrayList<>();
        engines.add(new DefaultRenderEngine(FIRST_GRAPH, FIRST_GRAPH, this));
        setupCenter();

        renderCircle.setRadius(START_RADIUS);
        renderCircle.setCenterX(START_CENTER_X);
        renderCircle.setCenterY(START_CENTER_Y);

        centerCheckBox.layoutXProperty().bind(renderPane.prefWidthProperty().subtract(CHECKBOX_X_OFFSET));
        centerCheckBox.layoutYProperty().bind(renderPane.prefHeightProperty().subtract(CHECKBOX_Y_OFFSET));

        enableDragCC(renderCircle, center);

        currentEngine = findEngine(FIRST_GRAPH);
        currentID = currentEngine.getGraphID();

        CommandController.getInstance().register(currentEngine);
        renderPane.getChildren().add(center);

        setupZoom();
    }

    private RenderEngine findEngine(int ID) {
        for (RenderEngine engine : engines) {
            if(ID == engine.getGraphID())
                return engine;
        }
        return null;
    }

    /**
     * This method rerenders the displayed graph.
     * 
     * @param graph
     */
    @FXML
    public void renderGraph(List<Drawable> graph) {
        for(Drawable node : graph) {
            if(node.getID() == 9)
                System.out.println("erwischt");
        }
        // clear the renderPane
        renderPane.getChildren().clear();
        renderPane.getChildren().add(renderCircle);
        renderPane.getChildren().add(zoomIn);
        renderPane.getChildren().add(zoomOut);

        ArrayList<Circle> nodes = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        // setup and sort elements
        for (Drawable node : graph) {
            if (node.isNode()) {
                CircleNode currentNode = (CircleNode) node;

                if (!currentNode.isCentered())
                    setupNode(currentNode);

                nodes.add(currentNode.getRepresentation());
            } else {
                LineStrip currentStrip = (LineStrip) node;

                bindLines(currentStrip);
                selectEdge(currentStrip);

                for (Line line : currentStrip.getLines()) {
                    line.setStroke(currentStrip.getColor());
                    lines.add(line);
                }
            }
        }
        buildRenderPane(nodes, lines);
    }

    /**
     * This method manages whether the center is visible and draggable.
     */
    public void changeCenterVisibility() {
        center.setVisible(false);
        if (centerCheckBox.isSelected()) {
            center.centerYProperty().unbind();
            center.centerXProperty().unbind();
            center.setVisible(true);
        } else {
            bindCenter();
        }
    }

    /**
     * This method rebuilds the renderPane after a new graph is displayed.
     * 
     * @param nodes
     * @param lines
     */
    private void buildRenderPane(ArrayList<Circle> nodes, ArrayList<Line> lines) {
        renderPane.getChildren().addAll(lines);
        renderPane.getChildren().addAll(nodes);
        renderPane.getChildren().add(center);
        renderPane.getChildren().add(centerCheckBox);
    }

    private void bindLines(LineStrip strip) {
        if (!strip.isCentered()){
            strip.setCentered();
            for (Line line : strip.getLines()) {
                setupLine(line);
            }
        }
    }

    /**
     * This method moves the center and rerenders accordingly.
     * 
     * @param coordinate the new coordinate of the center.
     */
    public void moveCenter(Coordinate coordinate) {
        MoveCenterCommand c = new MoveCenterCommand(coordinate);
        c.execute();

        List<Drawable> list = currentEngine.getDrawManager().getRenderData();
        renderGraph(list);
    }

    /**
     * This method adds drag events to the graph and the center.
     * 
     * @param circle
     * @param center
     */
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
            if (!center.isVisible()) {
                circle.setCenterX(mouseEvent.getX() + dragDeltaCircle.x);
                circle.setCenterY(mouseEvent.getY() + dragDeltaCircle.y);
            } else {
                center.setCenterX(mouseEvent.getX() + dragDeltaCenter.x);
                center.setCenterY(mouseEvent.getY() + dragDeltaCenter.y);
                double x = ((mouseEvent.getX() + dragDeltaCenter.x - renderCircle.getCenterX()) / FACTOR_VIEW);
                double y = ((mouseEvent.getY() + dragDeltaCenter.y - renderCircle.getCenterY()) / (-FACTOR_VIEW));
                Coordinate newCenterCoordinate = new CartesianCoordinate(x, y);
                moveCenter(newCenterCoordinate);
            }
        });
    }

    /**
     * This method sets up a line (which is a part of a LineStrip).
     * 
     * @param line
     */
    private void setupLine(Line line) {
        line.layoutXProperty().bind(renderCircle.centerXProperty());
        line.layoutYProperty().bind(renderCircle.centerYProperty());

        line.startXProperty().bind(renderCircle.layoutXProperty().add(
                renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW).multiply(line.getStartX())));
        line.startYProperty().bind(renderCircle.layoutYProperty().add(
                renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW).multiply(line.getStartY())));
        line.endXProperty().bind(renderCircle.layoutXProperty().add(
                renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW).multiply(line.getEndX())));
        line.endYProperty().bind(renderCircle.layoutYProperty().add(
                renderCircle.radiusProperty().divide(START_RADIUS).multiply(FACTOR_VIEW).multiply(line.getEndY())));
    }

    /**
     * This method sets up a node.
     * 
     * @param currentNode
     */
    private void setupNode(CircleNode currentNode) {
        currentNode.setCentered();
        currentNode.getRepresentation().setFill(currentNode.getColor());
        bindNodeX(currentNode);
        bindNodeY(currentNode);
        bindRadius(currentNode);
        selectNode(currentNode);
    }

    /**
     * This method sets up the center of the hyperbolic representation.
     */
    private void setupCenter() {
        center = new Circle();
        center.setRadius(NODE_SIZE);
        center.setFill(Color.CYAN);

        bindCenter();
        center.setVisible(false);
    }

    /**
     * This method implements the zoom function.
     * 
     * @param zoom
     */
    private void zoom(double zoom) {
        if (renderCircle.getRadius() + zoom >= 0 && startRadiusForCenter + zoom >= 0) {
            startRadiusForCenter += zoom;
            renderCircle.setRadius(renderCircle.getRadius() + zoom);
        }
    }

    /**
     * This method binds the center of the hyperbolic representation.
     */
    private void bindCenter() {
        center.centerXProperty().bind(renderCircle.centerXProperty().add(renderCircle.radiusProperty()
                .divide(startRadiusForCenter).multiply(center.getCenterX() - renderCircle.getCenterX())));
        center.centerYProperty().bind(renderCircle.centerYProperty().add(renderCircle.radiusProperty()
                .divide(startRadiusForCenter).multiply(center.getCenterY() - renderCircle.getCenterY())));
    }

    /**
     * This methods binds the y coordinate of a node to the renderCircle.
     * 
     * @param child
     */
    private void bindNodeX(CircleNode child) {
        child.getRepresentation().layoutXProperty().bind(renderCircle.centerXProperty());
        child.getRepresentation().centerXProperty()
                .bind(renderCircle.layoutXProperty().add(renderCircle.radiusProperty().divide(START_RADIUS)
                        .multiply(FACTOR_VIEW).multiply(child.getRepresentation().getCenterX())));

    }

    /**
     * This methods binds the y coordinate of a node to the renderCircle.
     * 
     * @param child
     */
    private void bindNodeY(CircleNode child) {
        child.getRepresentation().layoutYProperty().bind(renderCircle.centerYProperty());
        child.getRepresentation().centerYProperty()
                .bind(renderCircle.layoutYProperty().add(renderCircle.radiusProperty().divide(START_RADIUS)
                        .multiply(FACTOR_VIEW).multiply(child.getRepresentation().getCenterY())));
    }

    /**
     * This method binds the radius of a node of the graph.
     * 
     * @param child
     */
    private void bindRadius(CircleNode child) {
        child.getRepresentation().radiusProperty().bind(renderCircle.radiusProperty().divide(100));
    }

    /**
     * Add a mouseClick event to a node. This is used to display the meta data in
     * the DetailContainer.
     * 
     * @param node
     */
    private void selectNode(CircleNode node) {
        node.getRepresentation().setOnMouseClicked(mouseEvent -> {
            currentlySelected = node.getID();
            DetailHandler.getInstance().updateDisplayedData(currentlySelected, node.getColor(),
                    node.getCenter().toPolar());
        });
    }

    /**
     * Add a mouseClick event to an edge. This is used to display the meta data in
     * the DetailContainer.
     * 
     * @param strip
     */
    private void selectEdge(LineStrip strip) {
        for (Line line : strip.getLines()) {
            line.setOnMouseClicked(mouseEvent -> {
                currentlySelected = strip.getID();
                DetailHandler.getInstance().updateDisplayData(currentlySelected, strip.getColor());
            });
        }
    }

    private void setupZoom() {
        zoomIn = new Button("_+"); //mnemonic
        zoomIn.setVisible(true);
        zoomIn.layoutXProperty().bind(centerCheckBox.layoutXProperty().subtract(30));
        zoomIn.layoutYProperty().bind(centerCheckBox.layoutYProperty().subtract(5));

        zoomIn.setOnAction((event -> {
            zoom(15);
        }));
        renderPane.getChildren().add(zoomIn);

        zoomOut = new Button("_-"); //mnemonic
        zoomOut.setVisible(true);
        zoomOut.layoutXProperty().bind(centerCheckBox.layoutXProperty().subtract(60));
        zoomOut.layoutYProperty().bind(centerCheckBox.layoutYProperty().subtract(5));

        zoomOut.setOnAction((event -> {
            zoom(-15);
        }));
        renderPane.getChildren().add(zoomOut);
    }

    public void switchGraph(int id) {
        if(id != currentID) {
            currentEngine = new DefaultRenderEngine(id, id, this);
            engines.add(currentEngine);
            CommandController.getInstance().register(currentEngine);
            currentID = id;
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    renderGraph(currentEngine.getDrawManager().getRenderData());
                    return null;
                }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            Platform.runLater(th);
            try {
                th.join();
            } catch (InterruptedException e) {
            }

        }
    }

    public static RenderHandler getInstance() {
        return instance;
    }

    public void moveDrawManagerCenter(Coordinate transform) {
        currentEngine.getDrawManager().moveCenter(transform);
    }

    public void updateAccuracy(Accuracy accuracy) {
        currentEngine.getDrawManager().setAccuracy(accuracy);
    }

    public DrawManager getCurrentDrawManager() {
        return currentEngine.getDrawManager();
    }
}

/**
 * this is used to calculate the delta when a node is dragged.
 */
class Delta {
    double x, y;
}
