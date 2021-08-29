package kit.pse.hgv.view.RenderModel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.HashSet;
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
        this.displayedGraph = drawManager.getRenderData(toBeUpdated);
        //this.displayedGraph = drawManager.getRenderData();
    }

    @Override
    public void onNotify(ICommand c) {
        if (c.isUser()) {
            if (!c.succeeded()) {
                //TODO: Error message c.getResponse().get("reason");
            }
            toBeUpdated.addAll(c.getModifiedIds());
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    render();
                    return null;
                }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            Platform.runLater(th);
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            toBeUpdated.addAll(c.getModifiedIds());
        }
    }

}
