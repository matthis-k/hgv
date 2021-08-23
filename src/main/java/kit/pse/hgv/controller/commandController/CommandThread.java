package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.ICommand;

public class CommandThread extends Thread {

    private final ICommand c;

    public CommandThread(ICommand c) {
        this.c = c;
    }

    public void run() {
        c.execute();
    }
    public ICommand getCommand() {
        return c;
    }

}
