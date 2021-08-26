package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.RegisterExtensionCommand;
import kit.pse.hgv.controller.commandController.commands.StartExtensionCommand;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtensionCommandProcessorTest {

  private static ExtensionCommandProcessor extensionCommandProcessor;

  @BeforeClass
  public static void setup() {
    extensionCommandProcessor = new ExtensionCommandProcessor();
  }

  @Before
  public void clear() {
    CommandController.getInstance().getCommandQ().clear();
  }

  @Test
  public void testStartExtension() {
    String path = "src/resources/client.py";
    extensionCommandProcessor.startExtension(path);
    assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof StartExtensionCommand);
  }

  @Test
  public void testRegisterExtension() {
    String path = "src/resources/client.py";
    extensionCommandProcessor.registerExtension(path);
    assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof RegisterExtensionCommand);
  }

  @After
  public void cleanup() {
    CommandController.getInstance().getCommandQ().clear();
  }

  @AfterClass
  public static void free() {
    extensionCommandProcessor = null;
  }
}
