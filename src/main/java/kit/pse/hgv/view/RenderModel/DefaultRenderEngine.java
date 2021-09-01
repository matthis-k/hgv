package kit.pse.hgv.view.RenderModel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;
import kit.pse.hgv.view.uiHandler.DetailHandler;
import kit.pse.hgv.view.uiHandler.EditHandler;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.*;

/**
 * This class manages the RenderSystem. It decides when to rerender.
 */
public class DefaultRenderEngine extends RenderEngine {

    HashMap<Integer, Set<Integer>> updatedMap;

    public DefaultRenderEngine(int tab, int graph, RenderHandler handler) {
        super(tab, graph, new DrawManager(graph, new NativeRepresentation(3, DetailHandler.getCurrentAccuracy())), handler);
        updatedMap = new HashMap<>();
    }

    @Override
    public void render() {
        updateGraph();
        handler.renderGraph(displayedGraph);
        updatedMap.get(RenderHandler.getInstance().getCurrentID()).clear();
    }

    private void updateGraph() {
        this.displayedGraph = drawManager.getRenderData(updatedMap.get(RenderHandler.getInstance().getCurrentID()));
    }

    @Override
    public void onNotify(ICommand c) {
        if (updatedMap.get(RenderHandler.getInstance().getCurrentID()) == null) {
            updatedMap.put(RenderHandler.getInstance().getCurrentID(), new HashSet<>());
        }
        updatedMap.get(RenderHandler.getInstance().getCurrentID()).addAll(c.getModifiedIds());

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                render();
                return null;
            }
        };
        Thread runThread = new Thread(task);
        runThread.setDaemon(true);

        if (c.isUser()) {
            if (!c.succeeded()) {
                //TODO: Error message c.getResponse().get("reason");
            }
            Platform.runLater(runThread);
        } else {
            if(c instanceof RenderCommand) {
                Platform.runLater(runThread);
            }
        }

        try {
            runThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
