package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.extensionServer.ExtensionServer;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * This class handles the execution of a command and the undoing of an execution
 */
public abstract class Command implements ICommand {
    protected JSONObject response = new JSONObject();
    protected int client = 0;
    protected HashSet<Integer> modifiedIds = new HashSet<>();
    protected Command() {}

    /**
     * Returns if the user or the extension wants to change the graph
     *
     * @return true if the user wants to change the graph
     */
    public boolean isUser() {
        return client == 0;
    }

    /**
     * Returns the Client id of the extension
     *
     * @return client-id
     */
    public int getClientId() {
        return client;
    }

    /**
     * Sets the Client id of the extension
     *
     * @param id Client-Id
     */
    public void setClientId(int id) {
        client = id;
    }

    /**
     * Checks if the Change is made by the user or the extension. If it's made by the extension, it sends
     * a response message to it
     *
     */
    public JSONObject getResponse() {
        return response;
    }

    /**
     * Returns all changed IDs
     *
     * @return all changed IDs
     */
    public Set<Integer> getModifiedIds() {
        return modifiedIds;
    }
}
