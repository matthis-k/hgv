package kit.pse.hgv.commandController;

import kit.pse.hgv.commandController.commands.Command;

public interface CommandQListener {
    public void onNotify(Command c);
}
