package com.hgv.graphSystem;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;

import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;

public class GraphTest {
    @Test
    public void getElementById() {
        Graph g = new Graph();
        Node a = new Node();
        Node b = new Node();
        Edge ab = new Edge(a, b);
        
        g.addGraphElement(a);
        g.addGraphElement(b);
        g.addGraphElement(ab);

        assertEquals(a, g.getElementById(a.getId()));
        assertEquals(b, g.getElementById(b.getId()));
        assertEquals(ab, g.getElementById(ab.getId()));
        assertEquals(g.getIds().size(), 3);
        assertEquals(null, g.getElementById(7));
    }    
    @Test
    public void metadata() {
        Graph g = new Graph();
        for (int i = 0; i < 300; i++) {
            g.addGraphElement(new Node());
        }
        Vector<Node> nodes = g.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            g.addGraphElement(new Edge(nodes.elementAt(i), nodes.elementAt((i+1)%nodes.size())));
        }
        assertEquals(g.getIds().size(), 600);

        for (GraphElement e : g.getGraphElements()) {
            e.setMetadata("metadata", Integer.toString(e.getId()));
        }

        for (GraphElement e : g.getGraphElements()) {
            String val = e.getMetadata("metadata");
            assertEquals(e.getId(), Integer.parseInt(val));
            assertEquals(null, e.getMetadata("unknown"));
        }
    }
}
