package kit.pse.hgv.view.RenderModel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import kit.pse.hgv.App;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;
import kit.pse.hgv.view.uiHandler.DetailHandler;
import kit.pse.hgv.view.uiHandler.EditHandler;
import kit.pse.hgv.view.uiHandler.ErrorPopupHandler;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.io.IOException;
import java.util.*;

/**
 * This class manages the RenderSystem. It decides when to rerender.
 */
public class DefaultRenderEngine extends RenderEngine {

    private HashMap<Integer, Set<Integer>> updatedMap;
    private static ICommand command;

    public DefaultRenderEngine(int tab, int graph, RenderHandler handler) {
        super(tab, graph, new DrawManager(graph, new NativeRepresentation(3, DetailHandler.getCurrentAccuracy())), handler);
        updatedMap = new HashMap<>();
        command = null;
    }

    public static String getErrorMessage() {
        if(command != null)
            return command.getResponse().get("reason").toString();
        else
            return "No command detected";
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
        command = c;
        if(updatedMap.get(RenderHandler.getInstance().getCurrentID()) != null) {
            updatedMap.get(RenderHandler.getInstance().getCurrentID()).addAll(c.getModifiedIds());
        } else {
            updatedMap.put(RenderHandler.getInstance().getCurrentID(), new HashSet<>());
            updatedMap.get(RenderHandler.getInstance().getCurrentID()).addAll(c.getModifiedIds());
        }
        if (c.isUser()) {
           renderTask();
        } else {
            if(c instanceof RenderCommand) {
                renderTask();
            }
        }
        if (!c.succeeded()) {
                popupTask(c);
        }
    }

    private void popupTask(ICommand c){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                System.out.println(c.getResponse());
                Stage popupStage = new Stage();
                Parent root = FXMLLoader.load(App.class.getResource("ErrorPopup.fxml"));
                popupStage.setScene(new Scene(root));
                popupStage.setTitle("Fehlermeldung");
                popupStage.setResizable(false);
                popupStage.getIcons().add(new Image(App.class.getResourceAsStream("/hyperbolicthomas.png")));
                popupStage.show();
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        Platform.setImplicitExit(false);
        Platform.runLater(th);
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void renderTask() {
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
