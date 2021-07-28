package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.ICommand;

public interface CommandQListener {
    public void onNotify(ICommand c);
}
