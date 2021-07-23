package kit.pse.hgv.representation;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Drawable extends Node {
    private int id;
    private Color color;

    protected Drawable(int id, Color color) {
        this.id = id;
        this.color = color;
    }


    public int getID() {
        return this.id;
    }

    public Color getColor() {
        return this.color;
    }

    public abstract void draw(Pane pane);
}
