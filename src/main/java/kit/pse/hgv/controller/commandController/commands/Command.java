package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.ExtensionServer;

import java.util.HashSet;
import java.util.Set;

/**
 * This class handles the execution of a command and the undoing of an execution
 */
public abstract class Command implements ICommand {
    protected int client = 0;
    protected HashSet<Integer> modifiedIds = new HashSet<>();
    protected Command() {}
    public boolean isUser() {
        return client == 0;
    }
    public int getClientId() {
        return client;
    }
    public void setClientId(int id) {
        client = id;
    }
    protected void respond(String msg) {
        if (isUser()) { return; }
        ExtensionServer.getInstance().send(client, msg);
    }
    public Set<Integer> getModifiedIds() {
        return modifiedIds;
    }
}
