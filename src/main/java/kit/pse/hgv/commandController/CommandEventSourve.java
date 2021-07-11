package kit.pse.hgv.commandController;

import kit.pse.hgv.commandController.commands.Command;

public interface CommandEventSourve {
    public void notifyAll(Command c);
    public void register(CommandQListener listener);
}
