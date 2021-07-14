package com.pse.hgv.view.hyperbolicModel;

import com.pse.hgv.representation.CircleNode;
import com.pse.hgv.representation.Coordinate;
import com.pse.hgv.representation.LineStrip;
import javafx.scene.paint.Color;

public class NativeRepresentation implements Representation{

    private EdgeMode edgeMode;
    private Coordinate center;

    public NativeRepresentation(EdgeMode edgeMode) {
        this.edgeMode = edgeMode;
    }

    public NativeRepresentation(EdgeMode edgeMode, Coordinate center) {
        this.edgeMode = edgeMode;
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
        return edgeMode.calculateEdge(edge);
    }

    @Override
    public LineStrip calculate(Circle circle) {
        return null;
    }
}
