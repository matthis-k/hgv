package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.graphSystem.Edge;
import kit.pse.hgv.graphSystem.GraphElement;
import kit.pse.hgv.graphSystem.Node;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.LineStrip;

public interface Representation {
    LineStrip calculateEdge(Edge edge);

    CircleNode calculate(Node node);
    LineStrip calculate(Edge edge);
    LineStrip calculate(GraphElement graphElement);
    void setEdgeMode(EdgeMode edgeMode);
}
