package kit.pse.hgv.controller.commandProcessor;

import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CommandComposite;
import kit.pse.hgv.controller.commandController.commands.EditUserMetaCommand;
import org.junit.*;

import java.util.HashMap;

public class MetaDataProcessorTest {

    private static MetaDataProcessor metaDataProcessor;

    /**
     * Initializes the MetaDataProcessor
     */
    @BeforeClass
    public static void setup() {
        metaDataProcessor = new MetaDataProcessor();
    }

    /**
     * Clears the CommandQueue
     */
    @Before
    public void clearQBefore() {
        CommandController.getInstance().getCommandQ().clear();
    }

    /**
     * Tests if the EditUserMetaCommand is created and queued if the editMetaData method is called from the UI
     */
    @Test
    public void testChangeMetadata() {
        metaDataProcessor.editMetaData(1, "color", "red");
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof EditUserMetaCommand);
    }

    /**
     * Tests if the CompositeCommand is created and queued if the editmetadata method with a HashMap as input is called from the UI
     */
    @Test
    public void testChangeMetadataMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("color", "red");
        map.put("r", "2");
        metaDataProcessor.editMetaData(1, map);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof CommandComposite);
    }

    /**
     * Clears the CommandQueue
     */
    @After
    public void clearQAfter() {
        CommandController.getInstance().getCommandQ().clear();
    }

    /**
     * Clears the metaDataProcessor
     */
    @AfterClass
    public static void free() {
        metaDataProcessor = null;
    }
}
