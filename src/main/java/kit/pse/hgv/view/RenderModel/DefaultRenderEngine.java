package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

/**
 * This class manages the RenderSystem. It decides when to rerender.
 */
public class DefaultRenderEngine extends RenderEngine {


    public DefaultRenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        super(tab, graph, drawManager, handler);
    }

    @Override
    public void render() {
        updateGraph();
        handler.renderGraph(this.displayedGraph);
        this.toBeUpdated.clear();
    }

    private void updateGraph() {
        this.displayedGraph = drawManager.getRenderData();
    }

    @Override
    public void onNotify(ICommand c) {
        if(c.isUser()) {
            if(!c.getResponse().getBoolean("success")){
                c.getResponse().get("reason");
            }
            toBeUpdated.addAll(c.getModifiedIds());
            render();
        } else {
            toBeUpdated.addAll(c.getModifiedIds());
        }
    }


}
