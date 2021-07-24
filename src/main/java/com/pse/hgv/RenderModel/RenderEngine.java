package com.pse.hgv.RenderModel;

import com.pse.hgv.uiHandler.RenderHandler;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderEngine {

    private final RenderHandler handler;
    private DrawManager drawManager;
    private List<Drawable> displayedGraph;
    private final int tabID;
    private final int graphID;

    public RenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        this.tabID = tab;
        this.graphID = graph;
        this.handler = handler;
        this.drawManager = drawManager;
        displayedGraph = drawManager.getRenderData(new ArrayList<Integer>());
    }

    public abstract void firstRender();
    public abstract void rerender();
    public abstract void updateGraph();

    public int getGraphID() {
        return this.graphID;
    }

    public int getTabID() {
        return this.tabID;
    }

    public DrawManager getDrawManager() {
        return this.drawManager;
    }

    public RenderHandler getHandler() {
        return this.handler;
    }

    public List<Drawable> getDisplayedGraph() {
        return this.displayedGraph;
    }
}