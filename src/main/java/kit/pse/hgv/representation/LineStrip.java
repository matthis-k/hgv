package kit.pse.hgv.representation;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.Vector;

public class LineStrip extends Drawable {
    private List<Coordinate> coordinates;
    private Vector<Line> lines = new Vector<>();

    /*public LineStrip(CircleNode start, CircleNode end, int id, Color color) {
        super(id, color, false);
        this.start = start;
        this.end = end;
        representation = new Line();
        representation.startXProperty().bind(start.getRepresentation().centerXProperty());
        representation.startYProperty().bind(start.getRepresentation().centerYProperty());

        representation.endXProperty().bind(end.getRepresentation().centerXProperty());
        representation.endYProperty().bind(end.getRepresentation().centerYProperty());
    }*/

    private void addLine(int index) {
        Line line = new Line();
        line.setStartX(coordinates.get(index).toCartesian().getX());
        line.setStartY(coordinates.get(index).toCartesian().getY());
        line.setEndX(coordinates.get(index+1).toCartesian().getX());
        line.setEndY(coordinates.get(index+1).toCartesian().getY());
        lines.add(line);
    }

    public LineStrip(List<Coordinate> coordinates, int id, Color color) {
        super(id, color, false);
        this.coordinates = coordinates;
        for (int i = 0; i < coordinates.size()-1; i++) {
            addLine(i);
        }
    }

    @Override
    public void draw(Pane pane) {
        for (Line line : lines){
            pane.getChildren().add(line);
        }
    }

    @Override
    public boolean isNode() {
        return super.isNode();
    }

    public Vector<Line> getLines() {
        return this.lines;
    }
    @Override
    public Node getRepresentation() {
        return null;
    }
}
