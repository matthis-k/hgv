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
    private boolean hideEdges = false;

    /**
     * Constructor to create a new DrawManager with given Center
     *
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
     *
     * @param graphId        the ID of the graph to be represented
     * @param representation the type of representation the graph should be shown in
     */
    public DrawManager(int graphId, Representation representation) {
        this.graphId = graphId;
        this.representation = representation;
    }

    /**
     * Method to get the Rendered elements
     *
     * @param changedElements
     * @return
     */
    public List<Drawable> getRenderData(Set<Integer> changedElements) {
        calculateMixedIds(changedElements);
        List<Drawable> res = new Vector<>();
        res.addAll(rendered.values());
        return res;
    }

    private void calculateMixedIds(Set<Integer> ids) {
        if (ids.size() == 0) return;
        Set<Integer> nodes = new HashSet<>();
        Set<Integer> edges = new HashSet<>();
        for (Integer id : ids) {
            if (rendered.get(id) == null) {
                if (graphSystem.getNodeByID(id) != null) {
                    nodes.add(id);
                } else {
                    edges.add(id);
                }
            } else if (rendered.get(id).isNode()) {
                nodes.add(id);
            } else if (!hideEdges) {
                edges.add(id);
            }
        }
        calculateIds(nodes);
        calculateIds(edges);
    }

    private void calculateIds(Set<Integer> ids) {
        final int numThreads = 16;
        HashSet<Integer>[] chunks = new HashSet[numThreads];
        List<Calculator> calculators = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            chunks[i] = new HashSet<>();
        }
        int i = 0;
        for (Integer id : ids) {
            chunks[i % numThreads].add(id);
            i++;
        }
        for (HashSet<Integer> chunk : chunks) {
            if (!chunk.isEmpty()) {
                Calculator calculator = new Calculator(this, chunk, graphId);
                calculators.add(calculator);
            }

        }
        for (Calculator calculator : calculators) {
            calculator.start();
        }
        for (Calculator calculator : calculators) {
            try {
                calculator.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Drawable> getRenderData() {
        HashSet<Integer> ids = graphSystem.getIDs(graphId);
        calculateMixedIds(ids);
        List<Drawable> res = new Vector<>();
        res.addAll(rendered.values());
        return res;
    }

    /**
     * Methode that checks the List of changed Elements for changed Nodes and
     * searches all Edges that are based in this Node
     *
     * @param changedElements The List of Elements which have to be newly rendered
     * @return A Set of all Elements that also need to be newly rendered
     */
    private Set<Integer> addConnectedEdges(List<Integer> changedElements) {
        Set<Integer> allChangedElements = new HashSet<>();
        allChangedElements.addAll(changedElements);
        for (Integer id : changedElements) {
            if (graphSystem.getNodeByID(graphId, id) != null) {
                for (Edge edge : graphSystem.getGraphByID(graphId)
                        .getEdgesOfNode(graphSystem.getNodeByID(graphId, id))) {
                    allChangedElements.add(edge.getId());
                }
            }
        }

        return allChangedElements;
    }

    public List<Drawable> moveCenter(Coordinate center) {
        representation.setCenter(center);
        return getRenderData();
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {

        this.representation = representation;

    }

    public Coordinate getCenter() {
        return representation.getCenter();
    }

    public void setAccuracy(Accuracy accuracy) {
        representation.setAccuracy(accuracy);
    }

    synchronized protected void setRendered(Drawable drawable) {
        rendered.put(drawable.getID(), drawable);
    }

    synchronized protected Drawable getDrawable(int id) {
        return rendered.get(id);
    }

    public void setHideEdges(boolean hideEdges) {
        this.hideEdges = hideEdges;
    }

    synchronized protected void removeDrawable(int id) {
        rendered.remove(id);
    }
}
