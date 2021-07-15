package kit.pse.hgv.view.hyperbolicModel;



import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.GraphElement;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.Drawable;

import java.util.*;

public class DrawManager {
    private Coordinate center;
    private Graph graph;
    private HashMap<Integer, Drawable> rendered;
    private Representation representation;

    public DrawManager(int id, Coordinate center, Representation representation) {
        this.center = center;
        this.representation = representation;
    }

    public DrawManager(int id) {
        this.center = new CartesianCoordinate(0,0);

    }

    public List<Drawable> getRenderData(List<Integer> changedElements) {
        for(Integer id : changedElements) {
            GraphElement graphElement = graph.getElementById(id);
            rendered.put(id, representation.calculate(graphElement));
        }
        List<Drawable> output = new ArrayList<Drawable>();
        output.addAll(rendered.values());
        return output;
    }

    private Set<Integer> addConnectedEdges(List<Integer> changedElements) {
        Set<Integer> allChangedElements = new HashSet<>();
        allChangedElements.addAll(changedElements);
        return allChangedElements;
    }

    public List<Drawable> moveCenter(Coordinate coordinate) {
        return null;
    }

    public void setEdgeMode(EdgeMode edgeMode) {
        representation.setEdgeMode(edgeMode);
    }

    public Representation getRepresentation() {
        return representation;
    }

}
