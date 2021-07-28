package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;


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

    @Override
    public void receiveCommand(ICommand command) {
        //TODO ERROR
        if (command instanceof LoadGraphCommand) {
            render();
        } else if (command instanceof CreateElementCommand) {
            toBeUpdated.add(((CreateElementCommand) command).getAddedId());
            render();
        }
    }

    @Override
    public void receiveCommand(MoveCenterCommand command) {
        drawManager.moveCenter(command.getTransform());
        render();
    }

    @Override
    public void receiveCommand(MetaSystemCommand command) {
        toBeUpdated.add(command.getID());
        render();
    }

    @Override
    public void receiveCommand(FileSystemCommand command) {
        render();
    }

    @Override
    public void receiveCommand(GraphSystemCommand command) {
        render();
    }

    @Override
    public void receiveCommand(LoadGraphCommand command) {
        render();
    }

    @Override
    public void onNotify(ICommand c) {
        receiveCommand(c);
    }

    private void updateGraph() {
        if(toBeUpdated.size() > 0)
            this.displayedGraph = drawManager.getRenderData(toBeUpdated);
        else
            this.displayedGraph = drawManager.getRenderData();
    }
}
