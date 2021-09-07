package kit.pse.hgv.view.uiHandler;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import junit.framework.TestCase;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.ShutdownCommand;
import kit.pse.hgv.extensionServer.ExtensionServer;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import java.util.ArrayList;

@Ignore
public class RenderHandlerTest extends TestCase {

    /*
    private RenderHandler handler;
    private ArrayList<Drawable> graph;
    private static Scene scene;

    @Before
    public void setUp() throws Exception {
        commandController cmdController = commandController.getInstance();
        cmdController.start();
        ExtensionServer server = ExtensionServer.getInstance();
        server.start();
        scene = new Scene(loadFXML("MainView"), 1280, 720);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("HGV");
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            commandController.getInstance().queueCommand(new ShutdownCommand());
        });


        this.handler = new RenderHandler();
        graph = new ArrayList<>();
        for(int i=0; i < 10; i++) {
            graph.add(new CircleNode(new CartesianCoordinate(1, 1), 1, i, Color.CYAN));
        }
        for(int i = 0; i <= 9; i++) {
            ArrayList<CartesianCoordinate> coords = new ArrayList<>();
            coords.add(new CartesianCoordinate(1, 1));
            graph.add(new LineStrip(coords, i, Color.CYAN, i, i+1));
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    public void testRenderGraph() {
        handler.renderGraph(graph);
    }

    public void testChangeCenterVisibility() {
    }

    public void testMoveCenter() {
    } */
}