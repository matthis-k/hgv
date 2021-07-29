package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.ICommand;

public interface CommandEventSource {

    /**
     * TODO
     * @param c
     */
    public void notifyAll(ICommand c);

    /**
     * TODO
     * @param listener
     */
    public void register(CommandQListener listener);
}
