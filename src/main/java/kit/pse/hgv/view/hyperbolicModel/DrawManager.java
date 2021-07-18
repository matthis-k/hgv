package kit.pse.hgv.view.hyperbolicModel;



import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.GraphElement;
import kit.pse.hgv.graphSystem.GraphSystem;
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
        graph = GraphSystem.getInstance().getGraphById(id);
        this.center = center;
        this.representation = representation;
    }

    public DrawManager(int id, Representation representation) {
        graph = GraphSystem.getInstance().getGraphById(id);
        this.center = new CartesianCoordinate(0,0);
        this.representation = representation;
    }

    public List<Drawable> getRenderData(List<Integer> changedElements) {
        Set<Integer> toCalculate = addConnectedEdges(changedElements);
        for(Integer id : toCalculate) {
            GraphElement graphElement = graph.getElementById(id);

        }
        List<Drawable> res = new ArrayList<>();
        res.addAll(rendered.values());
        return res;
    }

    private void changeElement(GraphElement graphElement) {
        if(graphElement != null) {
            rendered.put(graphElement.getId(), representation.calculate(graphElement));
        } else {
            rendered.remove(graphElement.getId());
        }
    }

    /**
     * Methode that checks the List of changed Elements for changed Nodes and searches all Edges that are based in this
     * Node
     * @param changedElements The List of Elements which have to be newly rendered
     * @return A Set of all Elements that also need to be newly rendered
     */
    private Set<Integer> addConnectedEdges(List<Integer> changedElements) {
        Set<Integer> allChangedElements = new HashSet<>();
        allChangedElements.addAll(changedElements);
        for(Integer id: allChangedElements) {
            allChangedElements.addAll(graph.getEdges(id));
        }
        return allChangedElements;
    }

    public List<Drawable> moveCenter(Coordinate center) {
        List<Drawable> res = new ArrayList<>();
        representation.setCenter(center);
        //clear the list of rendered Elements, because every Element has to be rendered newly
        rendered.clear();
        for(GraphElement graphElement: graph.getGraphElements()) {
            changeElement(graphElement);
        }
        res.addAll(rendered.values());
        return res;
    }

    public void setEdgeMode(EdgeMode edgeMode) {
        representation.setEdgeMode(edgeMode);
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        EdgeMode edgeMode = representation.getEdgeMode();
        this.representation = representation;
        representation.setEdgeMode(edgeMode);
    }

}
