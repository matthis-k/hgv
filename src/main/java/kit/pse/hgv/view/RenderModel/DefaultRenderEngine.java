package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;


public class DefaultRenderEngine extends RenderEngine {

    private boolean hasBeenStarted;

    public DefaultRenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        super(tab, graph, drawManager, handler);
        hasBeenStarted = false;
    }

    @Override
    public void render() {
        updateGraph();
        handler.renderGraph(this.displayedGraph);
        this.toBeUpdated.clear();
    }

    private void updateGraph() {
        //ändern mit >0?
        this.displayedGraph = drawManager.getRenderData();
    }

    @Override
    public void onNotify(ICommand c) {
        if(c.isUser()) {
            if(!c.getResponse().getBoolean("success")){
                c.getResponse().get("reason");
                //Popup fehler
            }
            System.out.println("bin da " + c.getModifiedIds());
            toBeUpdated.addAll(c.getModifiedIds());
            render();
        } else {
            toBeUpdated.addAll(c.getModifiedIds());
        }
    }


}
