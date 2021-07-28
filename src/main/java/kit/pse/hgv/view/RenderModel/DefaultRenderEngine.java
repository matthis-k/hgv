package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

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
        this.displayedGraph = drawManager.getRenderData(toBeUpdated);
    }


    @Override
    public void receiveCommand(ICommand command) {
        //TODO ERROR
        if (command instanceof LoadGraphCommand) {
            if (command.isUser()) {
                firstRender();
            }
        } else if (command instanceof CreateElementCommand) {
            toBeUpdated.add(((CreateElementCommand) command).getAddedId());
            if (command.isUser()) {
                rerender();
            }
        }else if (command instanceof RenderCommand) {
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
    public void onNotify(ICommand c) {
        receiveCommand(c);
    }
}
