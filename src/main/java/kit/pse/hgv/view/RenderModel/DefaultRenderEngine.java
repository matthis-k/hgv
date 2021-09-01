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
        this.displayedGraph = drawManager.getRenderData( updatedMap.get(RenderHandler.getInstance().getCurrentID()));
        //this.displayedGraph = drawManager.getRenderData();
    }

    @Override
    public void onNotify(ICommand c) {
        if(updatedMap.get(RenderHandler.getInstance().getCurrentID()) != null) {
            updatedMap.get(RenderHandler.getInstance().getCurrentID()).addAll(c.getModifiedIds());
        } else {
            updatedMap.put(RenderHandler.getInstance().getCurrentID(), new HashSet<>());
            updatedMap.get(RenderHandler.getInstance().getCurrentID()).addAll(c.getModifiedIds());
        }
        if (c.isUser()) {
            System.out.println(c.isUser());
            if (!c.succeeded()) {
                //TODO: Error message c.getResponse().get("reason");
            }
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
            if(c instanceof RenderCommand) {
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
            }
        }
    }

}
