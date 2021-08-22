package kit.pse.hgv.representation;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.Vector;

public class LineStrip extends Drawable {
    private List<CartesianCoordinate> coordinates;
    private Vector<Line> lines = new Vector<>();
    private int[] connecting = new int[2];

    private void addLine(int index) {
        Line line = new Line();
        line.setStartX(coordinates.get(index).toCartesian().getX());
        line.setStartY(coordinates.get(index).toCartesian().getY());
        line.setEndX(coordinates.get(index + 1).toCartesian().getX());
        line.setEndY(coordinates.get(index + 1).toCartesian().getY());
        line.setStroke(color);
        lines.add(line);
    }

    public LineStrip(List<CartesianCoordinate> coordinates, int id, Color color, int firstID, int secondID) {
        super(id, color, false);
        this.coordinates = coordinates;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            addLine(i);
        }
        connecting[0] = firstID;
        connecting[1] = secondID;
    }

    @Override
    public void draw(Pane pane) {
        for (Line line : lines) {
            pane.getChildren().add(line);
        }
    }

    @Override
    public Node getRepresentation() {
        return null;
    }

    public Vector<Line> getLines() {
        return this.lines;
    }

    public List<CartesianCoordinate> getCoords() {
        return this.coordinates;
    }

    public int[] getConnectedNodes() {
        return connecting;
    }
}
