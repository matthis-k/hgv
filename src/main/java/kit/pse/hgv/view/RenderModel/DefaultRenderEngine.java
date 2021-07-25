package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.ArrayList;

public class DefaultRenderEngine extends RenderEngine {

    public DefaultRenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        super(tab, graph, drawManager, handler);
    }

    @Override
    public void firstRender() {
        //this.displayedGraph = drawManager.getRenderData();
        handler.renderGraph(this.displayedGraph);
    }

    //TODO Scheduler einbauen
    @Override
    public void rerender() {
        updateGraph();
        render();
        this.toBeUpdated.clear();
    }

    @Override
    public void receiveCommand(HyperModelCommand command) {
        //updateGraph();
        //drawManager.moveCenter();
    }

    @Override
    public void receiveCommand(MetaSystemCommand command) {
        //drawManager.getRenderData();
    }

    @Override
    public void receiveCommand(FileSystemCommand command) {
        //firstRender();
    }

    @Override
    public void receiveCommand(GraphElementCommand command) {
        //updateGraph();
        //rerender();
    }


    private void render() {
        handler.renderGraph(this.displayedGraph);
    }

    private void updateGraph() {
        this.displayedGraph = drawManager.getRenderData(toBeUpdated);
    }

}
