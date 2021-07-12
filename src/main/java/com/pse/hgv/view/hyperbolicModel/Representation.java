package com.pse.hgv.view.hyperbolicModel;

import com.pse.hgv.representation.*;

public interface Representation {
    LineStrip calculateEdge(Coordinate start, Coordinate end);
    LineStrip calculateCircle(Coordinate center,int radius);
    CircleNode calculateNode(Coordinate node);
}
