package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.Vector;

/**
 * This class manages the RenderSystem. It decides when to rerender.
 */
public class DefaultRenderEngine extends RenderEngine {

    public DefaultRenderEngine(int tab, int graph, RenderHandler handler) {
        super(tab, graph, new DrawManager(graph, new NativeRepresentation(3, Accuracy.DIRECT)), handler);
    }

    @Override
    public void render() {
        updateGraph();
        handler.renderGraph(this.displayedGraph);
        this.toBeUpdated.clear();
    }

    private void updateGraph() {
        //TODO
        //Vector<Integer> update = new Vector<>();
        //update.addAll(toBeUpdated);
        //this.displayedGraph = drawManager.getRenderData(update);
        this.displayedGraph = drawManager.getRenderData();
    }

    @Override
    public void onNotify(ICommand c) {
        if (c.isUser()) {
            if (!c.getResponse().getBoolean("success")) {
                c.getResponse().get("reason");
            }
            toBeUpdated.addAll(c.getModifiedIds());
            render();
        } else {
            toBeUpdated.addAll(c.getModifiedIds());
        }
    }

}
