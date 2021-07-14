package com.pse.hgv.view.hyperbolicModel;

import com.pse.hgv.representation.Coordinate;
import com.pse.hgv.representation.LineStrip;

public interface EdgeMode {
    LineStrip calculateEdge(Edge edge);
}
