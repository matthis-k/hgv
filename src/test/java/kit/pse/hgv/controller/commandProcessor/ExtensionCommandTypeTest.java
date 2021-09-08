package kit.pse.hgv.controller.commandProcessor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandProcessor.*;

import org.junit.Test;

public class ExtensionCommandTypeTest {

    /**
     * Tests if the right ENUM is chosen when the command type is CreateNode
     */
    @Test
    public void testCreateNode() {
        assertEquals(ExtensionCommandType.CREATE_NODE,ExtensionCommandType.processCommandString("{ \"type\": \"CreateNode\", \"graphId\": 1, \"coordinate\": {\"phi\": 3, \"r\": 5}}", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is CreateEdge
     */
    @Test
    public void testCreateEdge() {
        assertEquals(ExtensionCommandType.CREATE_EDGE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateEdge\", \"graphId\": 1, \"id1\": \"2333\", \"id2\": \"22\"}"
        ,1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is DeleteElement
     */
    @Test
    public void testDeleteElement() {
        assertEquals(ExtensionCommandType.DELETE_ELEMENT, ExtensionCommandType.processCommandString(
            "{ \"type\": \"DeleteElement\", \"id\": \"2333\"}"
        ,1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is MoveNode
     */
    @Test
    public void testMove() {
        assertEquals(ExtensionCommandType.MOVE_NODE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"MoveNode\", \"id\": \"11234\", \"coordinate\": {\"phi\": 3, \"r\": 5}}"
        ,1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is CommandComposite
     */
    @Test
    public void testComposite() {
        assertEquals(ExtensionCommandType.COMMAND_COMPOSITE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"CommandComposite\", \"commands\": [ { \"type\": \"DeleteElement\", \"id\": \"2333\"} ] }"
        ,1));
    }

    /**
     * Tests if an IllegalArgumenException is thrown when the Command doesn't have the correct format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMissingField() {
        ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateNode\" }"
        ,1);
    }

    /**
     * Tests if the right ENUM is chosen when the command type is ChangeMetadata
     */
    @Test
    public void testChangeMetadata() {
        assertEquals(ExtensionCommandType.CHANGE_METADATA, ExtensionCommandType.processCommandString("{\"type\": \"ChangeMetadata\", \"id\": 1, \"value\": red, \"key\": color}", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is Render
     */
    @Test
    public void testRender() {
        assertEquals(ExtensionCommandType.RENDER, ExtensionCommandType.processCommandString("{\"type\": \"Render\"}", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is MoveCenter
     */
    @Test
    public void testMoveCenter() {
        assertEquals(ExtensionCommandType.MOVE_CENTER, ExtensionCommandType.processCommandString("{\"type\": \"MoveCenter\", \"coordinate\": {\"phi\": 2.5, \"r\": 1} }", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is GetGraph
     */
    @Test
    public void testGetGraph() {
        assertEquals(ExtensionCommandType.GET_GRAPH, ExtensionCommandType.processCommandString("{\"type\": \"GetGraph\", \"graphId\": 1}", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is SetManualEdit
     */
    @Test
    public void testManualEditEnable() {
        assertEquals(ExtensionCommandType.SET_MANUAL_EDIT, ExtensionCommandType.processCommandString("{\"type\": \"SetManualEdit\", \"manualEdit\": true}", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is SetManualEdit
     */
    @Test
    public void testManualEditDisable() {
        assertEquals(ExtensionCommandType.SET_MANUAL_EDIT, ExtensionCommandType.processCommandString("{\"type\": \"SetManualEdit\", \"manualEdit\": false}", 1));
    }

    /**
     * Tests if an IllegalArgumentException is thrown then the SetManualEdit Command doesn't have the right format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testManualEditFailure() {
        ExtensionCommandType.processCommandString("{\"type\": \"SetManualEdit\", \"manualEdit\": \"other\"}",1);
    }

    /**
     * Tests if the right ENUM is chosen when the command type is Pause
     */
    @Test
    public void testPause() {
        assertEquals(ExtensionCommandType.PAUSE, ExtensionCommandType.processCommandString("{\"type\": \"Pause\"}", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is Stop
     */
    @Test
    public void testStop() {
        assertEquals(ExtensionCommandType.STOP, ExtensionCommandType.processCommandString("{\"type\": \"Stop\"}", 1));
    }

    /**
     * Tests if the right ENUM is chosen when the command type is SaveGraph
     */
    @Test
    public void testSaveGraph() {
        assertEquals(ExtensionCommandType.SAVE_GRAPH, ExtensionCommandType.processCommandString("{\"type\": \"SaveGraph\", \"path\": \"./out\", \"graphId\": 1}", 1));
    }

    /**
     * Tests if an IllegalArgumentException is thrown when the file doesn't have the .graphml format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveGraphFailure() {
        ExtensionCommandType.processCommandString("{\"type\": \"SaveGraph\", \"path\": \"./noexistingdir/out\", \"graphId\": 1}", 1);
    }

    /**
     * Tests if an IllegalArgumentException is thrown then the command type is not valid
     */
    @Test(expected = IllegalArgumentException.class)
    public void testProcessCommandFailure() {
        ExtensionCommandType.processCommandString("{\"type\": \"notValid\"}", 1);
    }
}
