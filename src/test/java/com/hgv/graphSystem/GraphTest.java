package com.hgv.graphSystem;

import java.util.Vector;

import kit.pse.hgv.graphSystem.Coordinate;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;

import static org.junit.Assert.*;

public class GraphTest {

    private GraphSystem g;
    @Before
    public void init() {
        g = GraphSystem.getInstance();
    }


    @Test
    public void accessGraph() {
        g.loadGraph("");
        assertNotNull(g.getGraphElementByID(1));
    }

    @Test
    public void addNode() throws OverflowException {
        g.loadGraph("");
        g.addElement(1, new Coordinate());
        assertNotNull(g.getGraphElementByID(1));
    }

    @Test
    public void addEdge() throws OverflowException {
        g.loadGraph("");
        g.addElement(1, new Coordinate());
        g.addElement(1, new Coordinate());
        int[] nodes = {1,2};
        g.addElement(1, nodes);

        assertNotNull(g.getGraphElementByID(3));
    }

    @Test
    public void removeElement() throws OverflowException {
        g.loadGraph("");
        g.addElement(1, new Coordinate());
        g.removeElement(1);
        assertNull(g.getGraphElementByID(1));
    }

    @Test
    public void removeNodeDelEdges() throws OverflowException {
        g.loadGraph("");
        g.addElement(1, new Coordinate());
        g.addElement(1, new Coordinate());
        System.out.println(g.getNodeByID(1));
        System.out.println(g.getNodeByID(2));
        int[] nodes = {1, 2};
        g.addElement(1, nodes);
        System.out.println(g.getEdgeByID(3));
        g.removeElement(3);
        assertNull(g.getGraphElementByID(3));
    }

    @After
    public void destroy() {
        g.removeGraph(0);
    }
}
