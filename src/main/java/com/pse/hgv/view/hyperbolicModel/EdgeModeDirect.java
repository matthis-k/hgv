package com.pse.hgv.view.hyperbolicModel;

import com.pse.hgv.representation.Coordinate;
import com.pse.hgv.representation.LineStrip;

import java.util.ArrayList;
import java.util.List;

public class EdgeModeDirect implements EdgeMode{
    @Override
    public LineStrip calculateEdge(Edge edge) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(edge.getStart);
        coordinates.add(edge.getEnd);
        return new LineStrip(cordinates, edge.getId(),edge.getMetadata("Color"));
    }
}
