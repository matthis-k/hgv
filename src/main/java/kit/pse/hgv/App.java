package kit.pse.hgv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;
import kit.pse.hgv.view.RenderModel.TabManager;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.RenderModel.DefaultRenderEngine;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.LoadGraphCommand;
import kit.pse.hgv.extensionServer.ExtensionServer;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        CommandController cmdController = CommandController.getInstance();
        cmdController.start();
        ExtensionServer server = new ExtensionServer(12345);
        server.start();
        scene = new Scene(loadFXML("MainView"), 1280, 720);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("HGV");
        stage.show();
        RenderHandler handler = new RenderHandler();
        RenderEngine engine = new DefaultRenderEngine(1, 1, new DrawManager(1, new NativeRepresentation()), handler);
        cmdController.register(engine);
        cmdController.dummy();
        // cmdController.queueCommand(new LoadGraphCommand("C:/Users/memph/Documents/seadiags/hgv/src/main/resources/Vorlage.graphml"));


    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}