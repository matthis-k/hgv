package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles the execution of a command and the undoing of an execution
 */
public abstract class Command implements ICommand {
    protected int client = 0;
    protected Command() {}
    protected Command(int client) {
        this.client = client;
    }
    public boolean isUser() {
        return client == 0;
    }
    public int getClientId() {
        return client;
    }
    public void setClientId(int id) {
        client = id;
    }
}
