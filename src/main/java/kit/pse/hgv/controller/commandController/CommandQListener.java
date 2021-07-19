package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.Command;

public interface CommandQListener {
    public void onNotify(Command c);
}
