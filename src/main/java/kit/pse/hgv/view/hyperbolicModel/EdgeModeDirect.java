package kit.pse.hgv.view.hyperbolicModel;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.Edge;
import kit.pse.hgv.graphSystem.GraphElement;
import kit.pse.hgv.graphSystem.Node;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.LineStrip;

import java.util.ArrayList;
import java.util.List;

public class EdgeModeDirect implements EdgeMode{
    @Override
    public LineStrip calculateEdge(Edge edge) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(edge.getStart().getCoordinate());
        coordinates.add(edge.getEnd().getCoordinate());
        return new LineStrip(coordinates, edge.getId(), Color.valueOf(edge.getMetadata("Color")));
    }
}
