package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.ICommand;

public interface CommandQListener {
    void onNotify(ICommand c);
}
