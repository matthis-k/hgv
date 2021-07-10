package com.pse.hgv.graphSystem;

import java.util.HashMap;

public class GraphSystem extends IdCreator {
    private HashMap<Integer, Graph> graphs = new HashMap<>();
    private static GraphSystem instance;

    private GraphSystem() {}

    public static GraphSystem getInstance() {
        if (instance == null) {
            instance = new GraphSystem();
        }
        return instance;
    }

    public Graph getGraphById(int graphId) {
        return graphs.get(graphId);
    }

    public void addGraph(Graph g) {
        if (g == null) {
            g = new Graph();
        }
        graphs.put(getNextId(), g);
    }
}
