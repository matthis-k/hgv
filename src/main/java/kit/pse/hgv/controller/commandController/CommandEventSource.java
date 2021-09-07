package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.ICommand;

public interface CommandEventSource {

    /**
     * TODO
     *
     * @param c
     */
    void notifyAll(ICommand c);

    /**
     * TODO
     *
     * @param listener
     */
    void register(CommandQListener listener);
}
