package kit.pse.hgv.controller.commandProcessor;

import static org.junit.Assert.assertTrue;

import org.junit.After;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.MoveCenterCommand;
import kit.pse.hgv.controller.commandController.commands.SetAccuracyCommand;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HyperModelCommandProcessorTest {

    private static HyperModelCommandProcessor hyperModelCommandProcessor;

    /**
     * Initializes the CommandProcessor
     */
    @BeforeClass
    public static void setupClass() {
        hyperModelCommandProcessor = new HyperModelCommandProcessor();
    }

    /**
     * Clears the CommandQueue
     */
    @Before
    public void setup() {
        CommandController.getInstance().getCommandQ().clear();
    }

    /**
     * Tests if the MoveCenterCommand is created and queued if the moveCenter method is called from the UI
     */
    @Test
    public void testMoveCenter() {
        Coordinate coordinate = new PolarCoordinate(1, 2);
        hyperModelCommandProcessor.moveCenter(coordinate);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof MoveCenterCommand);
    }

    /**
     * Tests if the SetAccuracyCommand is created and queued if the setAccuracy method is called from the UI
     */
    @Test
    public void testSetAccuracy() {
        hyperModelCommandProcessor.setAccuracy("high");
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof SetAccuracyCommand);
    }

    /**
     * Clears the CommandQueue
     */
    @After
    public void cleanup() {
        CommandController.getInstance().getCommandQ().clear();
    }

    /**
     * Clears the Processor
     */
    @AfterClass
    public static void free() {
        hyperModelCommandProcessor = null;
    }
}
