package kit.pse.hgv.graphSystem;

import java.util.HashMap;

public class GraphSystem extends IdCreator {
    /** Singleton instance */
    private static GraphSystem instance;

    /** List of graphs that are loaded. */
    private HashMap<Integer, Graph> graphs = new HashMap<>();

    private GraphSystem() {}

    /**
     * Creates or gets the only existing GraphSystem instance.
     * @return Returns the only instance of the graphsystem.
     */
    public static GraphSystem getInstance() {
        if (instance == null) {
            instance = new GraphSystem();
        }
        return instance;
    }


    public Graph getGraphById(int graphId) {
        return graphs.get(graphId);
    }

    //Wird das so gebraucht?
    public void addGraph(Graph g) {
        if (g == null) {
            g = new Graph();
        }
        graphs.put(getNextId(), g);
    }

    /**
     * Removes graph with the given id.
     * @param id is the id of the graph that should be removed.
     */
    public void removeGraph(int graphID) {
        graphs.remove(graphID);
    }

    public void addElement(int graphID, int nodeAID, int nodeBID) {

    }
    public void addElement(int graphID, Coordinate coord) {

    }


}
