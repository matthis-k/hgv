package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.graphSystem.Edge;
import kit.pse.hgv.representation.LineStrip;

public interface EdgeMode {
    LineStrip calculateEdge(Edge edge);
}
