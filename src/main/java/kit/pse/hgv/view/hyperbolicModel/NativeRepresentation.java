package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.graphSystem.Edge;
import kit.pse.hgv.graphSystem.GraphElement;
import kit.pse.hgv.graphSystem.Node;
import kit.pse.hgv.representation.*;

public class NativeRepresentation implements Representation{

    private EdgeMode edgeMode = new EdgeModeDirect();
    private Coordinate center;

    public NativeRepresentation() {
        center = new PolarCoordinate(0,0);
    }

    public NativeRepresentation(Coordinate center) {
        this.center = center;
    }

    @Override
    public LineStrip calculateEdge(Edge edge) {
        return null;
    }

    @Override
    public CircleNode calculate(Node node) {
        return null;
    }

    @Override
    public LineStrip calculate(Edge edge) {
        System.out.println("Edge");
        return edgeMode.calculateEdge(edge);
    }


    @Override
    public LineStrip calculate(GraphElement graphElement) {
        System.out.println("Edge");
        return null;
    }

    @Override
    public void setEdgeMode(EdgeMode edgeMode) {
        this.edgeMode = edgeMode;
    }
}
