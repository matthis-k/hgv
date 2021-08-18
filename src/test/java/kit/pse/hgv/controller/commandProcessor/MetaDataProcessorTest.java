package kit.pse.hgv.controller.commandProcessor;

import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.EditUserMetaCommand;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MetaDataProcessorTest {

    private static MetaDataProcessor metaDataProcessor;

    @BeforeClass
    public static void setup() {
        metaDataProcessor = new MetaDataProcessor();
    }

    @Test
    public void testChangeMetadata() {
        metaDataProcessor.editMetaData(1, "color", "red");
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof EditUserMetaCommand);
    }

    @AfterClass
    public static void free() {
        metaDataProcessor = null;
    }
}
