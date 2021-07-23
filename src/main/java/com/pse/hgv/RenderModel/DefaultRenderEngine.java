package com.pse.hgv.RenderModel;

import com.pse.hgv.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

public class DefaultRenderEngine extends RenderEngine{

    public DefaultRenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        super(tab, graph, drawManager, handler);
    }

    @Override
    public void firstRender() {
        getHandler().drawGraph(getDisplayedGraph());
    }

    @Override
    public void rerender() {

    }

    @Override
    public void updateGraph() {

    }
}
