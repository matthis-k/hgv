package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.controller.commandController.commands.MoveCenterCommand;
import kit.pse.hgv.controller.commandController.commands.SetAccuracyCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;
import kit.pse.hgv.view.RenderModel.DefaultRenderEngine;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;
import kit.pse.hgv.view.hyperbolicModel.NativeRepresentation;
import kit.pse.hgv.view.hyperbolicModel.Representation;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import org.junit.*;

public class HyperModelCommandTest {

    static Representation representation;
    static DrawManager drawManager;
    static RenderHandler renderHandler;
    static RenderEngine renderEngine;

    @BeforeClass
    public static void setup() {
        representation = new NativeRepresentation(0.1, Accuracy.DIRECT);
        CreateNewGraphCommand createNewGraphCommand = new CreateNewGraphCommand();
        createNewGraphCommand.execute();
        drawManager = new DrawManager(1, representation);
        renderHandler = RenderHandler.getInstance();
        renderEngine = new DefaultRenderEngine(1, 1, renderHandler);
    }

    @Ignore
    @Test
    public void testAccuracy() {
        SetAccuracyCommand setAccuracyCommand = new SetAccuracyCommand("high");
        setAccuracyCommand.execute();
        Assert.assertTrue(setAccuracyCommand.getResponse().getBoolean("success") && (representation.getAccuracy().equals(Accuracy.HIGH)));
    }

    @Ignore
    @Test
    public void testMoveCenter() {
        Coordinate coordinate = new PolarCoordinate(1, 1);
        MoveCenterCommand moveCenterCommand = new MoveCenterCommand(coordinate);
        moveCenterCommand.execute();
        Assert.assertTrue(moveCenterCommand.getResponse().getBoolean("success") && (representation.getCenter().equals(coordinate)));
    }

    @AfterClass
    public static void free() {
        GraphSystem.getInstance().removeGraph(1);
        drawManager = null;
        representation = null;
        renderHandler = null;
        renderEngine = null;
    }
}