package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.ICommand;

public interface CommandEventSource {
    public void notifyAll(ICommand c);
    public void register(CommandQListener listener);
}
