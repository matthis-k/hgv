package kit.pse.hgv.controller.commandProcessor;

import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.EditUserMetaCommand;
import org.junit.*;

public class MetaDataProcessorTest {

    private static MetaDataProcessor metaDataProcessor;

    @BeforeClass
    public static void setup() {
        metaDataProcessor = new MetaDataProcessor();
    }

    @Before
    public void clearQBefore() {
        CommandController.getInstance().getCommandQ().clear();
    }

    @Test
    public void testChangeMetadata() {
        metaDataProcessor.editMetaData(1, "color", "red");
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof EditUserMetaCommand);
    }

    @After
    public void clearQAfter() {
        CommandController.getInstance().getCommandQ().clear();
    }

    @AfterClass
    public static void free() {
        metaDataProcessor = null;
    }
}
