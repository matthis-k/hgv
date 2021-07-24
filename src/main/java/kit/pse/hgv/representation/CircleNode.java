package kit.pse.hgv.representation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleNode extends Drawable{
    private CartesianCoordinate center;
    private double radius;
    private Circle representation;

    public CircleNode(CartesianCoordinate center, double radius, int id, Color color) {
        super(id, color, true);
        this.center = center;
        this.radius = radius;
        representation = new Circle(center.getX(), center.getY(), radius, color);
        representation.setStroke(color); //nur um rand zu togglen
    }

    @Override
    public void draw(Pane pane) {
        pane.getChildren().add(representation);
    }

    @Override
    public boolean isNode() {
        return super.isNode();
    }

    public Circle getRepresentation() {
        return this.representation;
    }

    public CartesianCoordinate getCenter() { return this.center;}
}
