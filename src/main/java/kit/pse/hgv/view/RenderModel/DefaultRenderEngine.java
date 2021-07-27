package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DefaultRenderEngine extends RenderEngine {

    public DefaultRenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        super(tab, graph, drawManager, handler);
    }

    @Override
    public void firstRender() {
        this.displayedGraph = drawManager.getRenderData();
        handler.renderGraph(this.displayedGraph);
    }

    //TODO Scheduler einbauen
    @Override
    public void rerender() {
        updateGraph();
        handler.renderGraph(this.displayedGraph);
        this.toBeUpdated.clear();
    }

    private void updateGraph() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(19);
        this.displayedGraph = drawManager.getRenderData(list);
    }


    @Override
    public void receiveCommand(Command command) {
        //TODO ERROR
        if (command instanceof LoadGraphCommand) {
            firstRender();
        } else if (command instanceof GraphSystemCommand) {
            rerender();
        }
    }

    @Override
    public void receiveCommand(MoveCenterCommand command) {
        drawManager.moveCenter(command.getTransform());
        rerender();
    }

    @Override
    public void receiveCommand(MetaSystemCommand command) {
        toBeUpdated.add(command.getID());
        rerender();
    }

    @Override
    public void receiveCommand(FileSystemCommand command) {
        firstRender();
    }

    @Override
    public void receiveCommand(GraphSystemCommand command) {
        rerender();
    }

    @Override
    public void receiveCommand(LoadGraphCommand command) {
        firstRender();
    }

    @Override
    public void onNotify(Command c) {
        receiveCommand(c);
    }
}
