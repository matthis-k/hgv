package kit.pse.hgv.view.hyperbolicModel;



import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.Drawable;

import java.util.*;

public class DrawManager {
    private Coordinate center;
    private GraphSystem graphSystem = GraphSystem.getInstance();
    private int graphId;
    private HashMap<Integer, Drawable> rendered = new HashMap<>();
    private Representation representation;

    public DrawManager(int graphId, Coordinate center, Representation representation) {
        this.graphId = graphId;
        this.center = center;
        this.representation = representation;
    }

    public DrawManager(int graphId, Representation representation) {
        this.graphId = graphId;
        this.center = new CartesianCoordinate(0,0);
        this.representation = representation;
    }

    public List<Drawable> getRenderData(List<Integer> changedElements) {
        Set<Integer> toCalculate = addConnectedEdges(changedElements);
        for(Integer id : toCalculate) {
            Drawable drawable = changeElement(id);
            rendered.put(drawable.getID(), drawable);
        }
        List<Drawable> res = new ArrayList<>();
        res.addAll(rendered.values());
        return res;
    }

    public List<Drawable> getRenderData() {
        rendered.clear();
        for(Integer id : graphSystem.getIDs(graphId)) {
            changeElement(id);
        }
        List<Drawable> res = new ArrayList<>();
        res.addAll(rendered.values());
        return res;
    }

    private Drawable changeElement(int id) {

        Node node = graphSystem.getNodeByID(graphId, id);
        if(node != null) {
            return getRepresentation().calculate(node);
        } else {

            Edge edge = graphSystem.getEdgeByID(graphId, id);
            return getRepresentation().calculate(edge);
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
            //TODO Philipp
            if(graphSystem.getNodeByID(graphId, id) != null) {
                for(Edge edge : graphSystem.getGraphByID(graphId).getEdgesOfNode(graphSystem.getNodeByID(graphId, id))) {
                    allChangedElements.add(edge.getId());
                }
            }
        }

        return allChangedElements;
    }

    public List<Drawable> moveCenter(Coordinate center) {
        List<Drawable> res = new ArrayList<>();
        getRepresentation().setCenter(center);
        //clear the list of rendered Elements, because every Element has to be rendered newly
        rendered.clear();
        for(Integer id: graphSystem.getIDs(graphId)) {
            Drawable drawable = changeElement(id);
            rendered.put(drawable.getID(), drawable);
        }
        res.addAll(rendered.values());
        return res;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {

        this.representation = representation;

    }

    public void setAccuracy(Accuracy accuracy) {
        representation.setAccuracy(accuracy);
    }
}
