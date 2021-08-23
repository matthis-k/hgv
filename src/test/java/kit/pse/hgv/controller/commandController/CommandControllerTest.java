package kit.pse.hgv.controller.commandController;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.controller.commandController.commands.DummyCommand;

public class CommandControllerTest {
    private static CommandController ctl;

    @Before
    public void setup() {
        CommandController.reset();
        ctl = CommandController.getInstance();
    }

    @Test
    public void queueNull() throws InterruptedException {
        CommandQListener listener = new CommandQListener(){
            @Override
            public void onNotify(ICommand c) {
                assertTrue(false);
            }
        };
        ctl.register(listener);
        ctl.queueCommand(null);
        ctl.start();
        synchronized (this) {
            wait(100);
        }
        assertTrue(ctl.getCommandQ().isEmpty());
        ctl.stopController();
    }
    @Test
    public void listener() throws InterruptedException {
        CommandQListener listener = new CommandQListener(){
            @Override
            public void onNotify(ICommand c) {
                assertTrue(c.getResponse().getBoolean("success"));
            }
        };
        ctl.register(listener);
        ctl.queueCommand(new DummyCommand());
        ctl.start();
        synchronized (this) {
            wait(100);
        }
        assertTrue(ctl.getCommandQ().isEmpty());
        ctl.stopController();
    }

    @After
    public void reset() {
        CommandController.reset();
    }

}
