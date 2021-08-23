package kit.pse.hgv.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.CommandQListener;
import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.controller.commands.DummyCommand;

import java.util.Timer;

public class CommandControllerTest {
    private static CommandController ctl = CommandController.getInstance();

    @Test
    public void listener() throws InterruptedException {
        CommandQListener listener = new CommandQListener(){
            @Override
            public void onNotify(ICommand c) {
                assertTrue(c.getResponse().getBoolean("success"));
            }
        };
        ctl.queueCommand(new DummyCommand());
        ctl.start();
        synchronized (this) {
            wait(100);
        }
        assertTrue(ctl.getCommandQ().isEmpty());
    }

}
