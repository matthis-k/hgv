package kit.pse.hgv.controller.commandProcessor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandProcessor.*;

import org.junit.Test;

public class ExtensionCommandTypeTest {

    /*@Test
    public void testCreateNode() {
        ExtensionCommandType.processCommandString("{ \"type\": \"CreateNode\", \"graphId\": 1, \"coordinate\": {\"phi\": 3, \"r\": 5}}", 1);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof CreateNodeCommand);
    }*/

    @Test
    public void testCreateEdge() {
        assertEquals(ExtensionCommandType.CREATE_EDGE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateEdge\", \"graphId\": 1, \"id1\": \"2333\", \"id2\": \"22\"}"
        ,1));
    }
    @Test
    public void testDeleteElement() {
        assertEquals(ExtensionCommandType.DELETE_ELEMENT, ExtensionCommandType.processCommandString(
            "{ \"type\": \"DeleteElement\", \"id\": \"2333\"}"
        ,1));
    }
    @Test
    public void testMove() {
        assertEquals(ExtensionCommandType.MOVE_NODE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"MoveNode\", \"id\": \"11234\", \"coordinate\": {\"phi\": 3, \"r\": 5}}"
        ,1));
    }
    @Test
    public void testComposite() {
        assertEquals(ExtensionCommandType.COMMAND_COMPOSITE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"CommandComposite\", \"commands\": [ { \"type\": \"DeleteElement\", \"id\": \"2333\"} ] }"
        ,1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingField() {
        ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateNode\" }"
        ,1);
    }

    @Test
    public void testChangeMetadata() {
        assertEquals(ExtensionCommandType.CHANGE_METADATA, ExtensionCommandType.processCommandString("{\"type\": \"ChangeMetadata\", \"id\": 1, \"value\": red, \"key\": color}", 1));
    }

    @Test
    public void testMoveCenter() {
        assertEquals(ExtensionCommandType.MOVE_CENTER, ExtensionCommandType.processCommandString("{\"type\": \"MoveCenter\", \"coordinate\": {\"phi\": 2.5, \"r\": 1} }", 1));
    }

    @Test
    public void testGetGraph() {
        assertEquals(ExtensionCommandType.GET_GRAPH, ExtensionCommandType.processCommandString("{\"type\": \"GetGraph\", \"graphId\": 1}", 1));
    }

    @Test
    public void testManualEditEnable() {
        assertEquals(ExtensionCommandType.SET_MANUAL_EDIT, ExtensionCommandType.processCommandString("{\"type\": \"SetManualEdit\", \"manualEdit\": true}", 1));
    }

    @Test
    public void testManualEditDisable() {
        assertEquals(ExtensionCommandType.SET_MANUAL_EDIT, ExtensionCommandType.processCommandString("{\"type\": \"SetManualEdit\", \"manualEdit\": false}", 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testManualEditFailure() {
        ExtensionCommandType.processCommandString("{\"type\": \"SetManualEdit\", \"manualEdit\": \"other\"}",1);
    }

    @Test
    public void testPause() {
        assertEquals(ExtensionCommandType.PAUSE, ExtensionCommandType.processCommandString("{\"type\": \"Pause\"}", 1));
    }

    @Test
    public void testStop() {
        assertEquals(ExtensionCommandType.STOP, ExtensionCommandType.processCommandString("{\"type\": \"Stop\"}", 1));
    }

    @Test
    public void testSaveGraph() {
        assertEquals(ExtensionCommandType.SAVE_GRAPH, ExtensionCommandType.processCommandString("{\"type\": \"SaveGraph\", \"path\": \"./out.graphml\", \"graphId\": 1}", 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveGraphFailure() {
        ExtensionCommandType.processCommandString("{\"type\": \"SaveGraph\", \"path\": \"./noexistingdir/out\", \"graphId\": 1}", 1);
    }
}
