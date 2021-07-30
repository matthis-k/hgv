package kit.pse.hgv.view.hyperbolicModel;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.*;

import java.util.*;

public class DrawManager {
    private static Color DEFAULT_NODE_COLOR = Color.RED;
    private static Color DEFAULT_EDGE_COLOR = Color.BLACK;
    private GraphSystem graphSystem = GraphSystem.getInstance();
    private int graphId;
    private HashMap<Integer, Drawable> rendered = new HashMap<>();
    private Representation representation;


    /**
     * Constructor to create a new DrawManager with given Center
     * @param graphId
     * @param center
     * @param representation
     */
    public DrawManager(int graphId, Coordinate center, Representation representation) {
        this.graphId = graphId;
        this.representation = representation;
        representation.setCenter(center);
    }

    /**
     * Constructor to create a new DrawManager
     * @param graphId the ID of the graph to be represented
     * @param representation the type of representation the graph should be shown in
     */
    public DrawManager(int graphId, Representation representation) {
        this.graphId = graphId;
        this.representation = representation;
    }

    /**
     * Method to get the Rendered elements
     * @param changedElements
     * @return
     */
    public List<Drawable> getRenderData(List<Integer> changedElements) {
        Set<Integer> toCalculate = addConnectedEdges(changedElements);
        for(Integer id : toCalculate) {
            if (graphSystem.isInGraph(graphId, id)) {
                Drawable drawable = calculateElement(id);
                rendered.put(drawable.getID(), drawable);
            } else {
                rendered.remove(id);
            }
        }
        List<Drawable> res = new Vector<>();
        res.addAll(rendered.values());
        return res;
    }

    public List<Drawable> getRenderData() {
        rendered.clear();
        for(Integer id : graphSystem.getIDs(graphId)) {
            Drawable drawable = calculateElement(id);
            rendered.put(drawable.getID(), drawable);
        }
        List<Drawable> res = new Vector<>();
        res.addAll(rendered.values());
        return res;
    }

    private Drawable calculateElement(int id) {
        Drawable d;
        Node node = graphSystem.getNodeByID(graphId, id);
        if(node != null) {
            d = getRepresentation().calculate(node);
        } else {
            Edge edge = graphSystem.getEdgeByID(graphId, id);
            d = getRepresentation().calculate(edge);
        }
        return d.setColor(getColorOfId(id));
    }

    private Color getColorOfId(int id) {
        GraphElement el = GraphSystem.getInstance().getGraphElementByID(id);
        try {
            return Color.web(el.getMetadata("color"));
        } catch (NullPointerException | IllegalArgumentException e) {
            return el instanceof Node ? DEFAULT_NODE_COLOR : DEFAULT_EDGE_COLOR;
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
            if(graphSystem.getNodeByID(graphId, id) != null) {
                for(Edge edge : graphSystem.getGraphByID(graphId).getEdgesOfNode(graphSystem.getNodeByID(graphId, id))) {
                    allChangedElements.add(edge.getId());
                }
            }
        }

        return allChangedElements;
    }

    public List<Drawable> moveCenter(Coordinate center) {
        representation.setCenter(center);
        //clear the list of rendered Elements, because every Element has to be rendered newly
        rendered.clear();
        for(Integer id: graphSystem.getIDs(graphId)) {
            Drawable drawable = calculateElement(id);
            rendered.put(drawable.getID(), drawable);
        }
        List<Drawable> res = new Vector<>();
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
