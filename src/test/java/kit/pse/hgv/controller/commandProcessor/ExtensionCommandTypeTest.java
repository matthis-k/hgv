package kit.pse.hgv.controller.commandProcessor;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExtensionCommandTypeTest {
    @Test
    public void testCreateNode() {
        assertEquals(ExtensionCommandType.CREATE_NODE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateNode\", \"coordinate\": \"12,23\"}"
        ));
    }
    @Test
    public void testCreateEdge() {
        assertEquals(ExtensionCommandType.CREATE_EDGE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateEdge\", \"id1\": \"2333\", \"id2\": \"22\"}"
        ));
    }
    @Test
    public void testDeleteElement() {
        assertEquals(ExtensionCommandType.DELETE_ELEMENT, ExtensionCommandType.processCommandString(
            "{ \"type\": \"DeleteElement\", \"id\": \"2333\"}"
        ));
    }
    @Test
    public void testMove() {
        assertEquals(ExtensionCommandType.MOVE_NODE, ExtensionCommandType.processCommandString(
            "{ \"type\": \"MoveNode\", \"coordiante\": \"-2.333,1434\"}"
        ));
    }
    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testMissingField() {
        ExtensionCommandType.processCommandString(
            "{ \"type\": \"CreateNode\" }"
        );
    }
}
