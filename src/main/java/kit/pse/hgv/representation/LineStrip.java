package kit.pse.hgv.representation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.Vector;

public class LineStrip extends Drawable {
    private final List<CartesianCoordinate> coordinates;
    private final Vector<Line> lines = new Vector<>();
    private final int[] connecting = new int[2];

    private void addLine(int index) {
        Line line = new Line();
        line.setStartX(coordinates.get(index).toCartesian().getX());
        line.setStartY(coordinates.get(index).toCartesian().getY());
        line.setEndX(coordinates.get(index + 1).toCartesian().getX());
        line.setEndY(coordinates.get(index + 1).toCartesian().getY());
        Color usedColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.5);
        line.setStroke(usedColor);
        line.setFill(usedColor);
        lines.add(line);
    }

    public LineStrip(List<CartesianCoordinate> coordinates, int id, Color color, int firstID, int secondID) {
        super(id, new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.5), false);
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
