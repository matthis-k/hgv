package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.Command;

public interface CommandEventSource {
    public void notifyAll(Command c);
    public void register(CommandQListener listener);
}
