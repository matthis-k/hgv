package kit.hgv.view.hyperbolicModel;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.Edge;
import kit.pse.hgv.graphSystem.Node;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;
import kit.pse.hgv.view.hyperbolicModel.Representation;
import org.junit.Test;

public class HyperbolicModelTest {
    @Test
    public void createDrawManager() {

    }

    @Test
    public void drawEdge() {
        Representation representation = new NativeRepresentation();
        Coordinate coord1 = new PolarCoordinate(0,0);
        Coordinate coord2 = new PolarCoordinate(1,1);
        Node node1 = new Node();
        Node node2 = new Node();
        Color color = new Color(0,0,0,0);
        Edge edge = new Edge(node1, node2);
        representation.calculate(edge);
    }
}