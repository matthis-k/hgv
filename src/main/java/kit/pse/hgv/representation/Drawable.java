package kit.pse.hgv.representation;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Drawable extends Node {
    protected int id;
    protected Color color;
    protected final boolean isNode;

    protected Drawable(int id, Color color, boolean isNode) {
        this.id = id;
        this.color = color;
        this.isNode = isNode;
    }


    public int getID() {
        return this.id;
    }

    public Color getColor() {
        return this.color;
    }

    public abstract void draw(Pane pane);

    public boolean isNode() {
        return this.isNode;
    }

    public abstract Node getRepresentation();
}
