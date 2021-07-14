package com.pse.hgv.view.hyperbolicModel;

import com.pse.hgv.representation.*;

import java.util.ArrayList;
import java.util.List;

public class DrawManager {
    private Coordinate center;
    private Graph graph;
    private List<Drawable> rendered;
    EdgeMode edgeMode;
    Representation representation;

    public DrawManager(int id, Coordinate center) {
        this.center = center;
    }

    public DrawManager(int id) {
        this.center = new CartesianCoordinate(0,0);

    }

    public List<Drawable> getRenderData(List<Integer> changedElements) {
        List<Drawable> drawables = new ArrayList<Drawable>();
        for(Integer id : changedElements) {
            GraphElement graphElement = GraphSystem.getElementByID(id);
            drawables.add(representation.calculate(graphElement));
        }
        return drawables;
    }

    public List<Drawable> moveCenter(Coordinate) {
        return null;
    }

}
