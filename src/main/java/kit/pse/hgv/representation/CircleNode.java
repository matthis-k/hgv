package kit.pse.hgv.representation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleNode extends Drawable {
    private CartesianCoordinate center;
    private CartesianCoordinate viewCenter;
    private double radius;
    private Circle representation;

    /**
     * Creates an element of CircleNode
     *
     * @param center the Coordinate of the Node
     * @param radius the Radius of the node
     * @param id     id of the node
     * @param color  the Color of the node
     */
    public CircleNode(CartesianCoordinate center, double radius, int id, Color color) {
        super(id, color, true);
        this.center = center;
        this.viewCenter = center.mirroredY().toCartesian();
        this.radius = radius;
        representation = new Circle(viewCenter.getX(), viewCenter.getY(), radius, color);
        representation.setStroke(color); // nur um rand zu togglen
    }

    @Override
    public void draw(Pane pane) {
        pane.getChildren().add(representation);
    }

    @Override
    public boolean isNode() {
        return super.isNode();
    }

    /**
     * Returns the Representation of the Node
     *
     * @return Representation of the Node
     */
    public Circle getRepresentation() {
        return this.representation;
    }

    /**
     * Returns the Cartesian Coordinate of the Node
     *
     * @return CartesianCoordinate of the Node
     */
    public CartesianCoordinate getCenter() {
        return this.center;
    }

    public void setCenter (Coordinate coordinate) {
        this.center = coordinate.toCartesian();
        this.viewCenter = coordinate.mirroredY().toCartesian();
        //TODO
        /*representation.centerXProperty().unbind();
        representation.centerYProperty().unbind();
        representation.setCenterX(viewCenter.getX());
        representation.setCenterY(viewCenter.getY());*/
    }

    /**
     * Returns the Cartesian Coordinate of the Node
     *
     * @return CartesianCoordinate of the Node
     */
    public CartesianCoordinate getVisualCenter() {
        return this.viewCenter;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof CircleNode)) return false;
        CircleNode circleNode = (CircleNode) o;
        boolean equals = getCenter().equals(circleNode.getCenter());
        equals &= id == circleNode.getID();
        return equals;
    }
}
