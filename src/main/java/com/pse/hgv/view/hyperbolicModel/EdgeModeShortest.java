package com.pse.hgv.view.hyperbolicModel;

import com.pse.hgv.representation.LineStrip;

public class EdgeModeShortest implements EdgeMode{
    private Representation representation;
    public EdgeModeShortest(Representation representation) {
        this.representation = representation;
    }

    @Override
    public LineStrip calculateEdge(Edge edge) {
        return representation.calculateEdge(edge);
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }
}
