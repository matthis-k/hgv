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

  /**
   * creates a new extensionCommandProcessor
   */
  @BeforeClass
  public static void setup() {
    extensionCommandProcessor = new ExtensionCommandProcessor();
  }

  /**
   * clears the CommandQueue
   */
  @Before
  public void clear() {
    CommandController.getInstance().getCommandQ().clear();
  }

  /**
   * Tests if the StartExtensionCommand is created and queued if the startExtension method is called from the UI
   */
  @Test
  public void testStartExtension() {
    String path = "src/resources/client.py";
    extensionCommandProcessor.startExtension(path);
    assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof StartExtensionCommand);
  }

  /**
   * Tests if the RegisterExtensionCommand is created and queued if the registerExtension method is called from the UI
   */
  @Test
  public void testRegisterExtension() {
    String path = "src/resources/client.py";
    extensionCommandProcessor.registerExtension(path);
    assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof RegisterExtensionCommand);
  }

  /**
   * Clears the CommandQueue
   */
  @After
  public void cleanup() {
    CommandController.getInstance().getCommandQ().clear();
  }

  /**
   * clears the extensionCommandProcessor
   */
  @AfterClass
  public static void free() {
    extensionCommandProcessor = null;
  }
}
