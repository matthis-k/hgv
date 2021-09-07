package kit.pse.hgv.representation;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Drawable extends Node {
    protected int id;
    protected Color color;
    protected final boolean isNode;
    protected boolean isCentered;

    protected Drawable(int id, Color color, boolean isNode) {
        this.id = id;
        this.color = color;
        this.isNode = isNode;
        isCentered = false;
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


    public Drawable setColor(Color c) {
        this.color = c;
        return this;
    }

    public boolean isCentered() {
        return this.isCentered;
    }

    public void setCentered() {
        isCentered = true;
    }
}
