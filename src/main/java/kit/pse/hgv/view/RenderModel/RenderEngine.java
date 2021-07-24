package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderEngine {

    protected final RenderHandler handler;
    protected DrawManager drawManager;
    protected List<Drawable> displayedGraph;
    protected final int tabID;
    protected final int graphID;

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

    /*public int getGraphID() {
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
    }*/
}
