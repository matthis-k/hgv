package kit.pse.hgv.controller.commandProcessor;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExtensionCommandTypeTest {
    @Test
    public void testCreateNode() {
        assertEquals(ExtensionCommandType.CREATE_NODE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateNode\", \"graphId\": 1, \"coordinate\": {\"phi\": 3, \"r\": 5}}"
        ,1));
    }
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
}
