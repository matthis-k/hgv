package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.RegisterExtensionCommand;
import kit.pse.hgv.controller.commandController.commands.StartExtensionCommand;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtensionCommandProcessorTest {

    private static ExtensionCommandProcessor extensionCommandProcessor;

    @BeforeClass
    public static void setup() {
        extensionCommandProcessor = new ExtensionCommandProcessor();
    }

    @Test
    public void testStartExtension(){
        String path = "C:";
        extensionCommandProcessor.startExtension(path);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof StartExtensionCommand);
    }

    @Test
    public void testRegisterExtension() {
        String path = "C:";
        extensionCommandProcessor.registerExtension(path);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof RegisterExtensionCommand);
    }

    @AfterClass
    public static void free() {
        extensionCommandProcessor = null;
    }
}
