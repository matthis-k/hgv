package kit.pse.hgv.controller.commandController.commands;

import org.json.JSONObject;

import java.util.Set;

public interface ICommand {
    public void execute();

    public void undo();

    /**
     * Returns all changed IDs
     *
     * @return all changed IDs
     */
    public Set<Integer> getModifiedIds();

    /**
     * Returns if the user or the extension wants to change the graph
     *
     * @return true if the user wants to change the graph
     */
    public boolean isUser();

    /**
     * Checks if the Change is made by the user or the extension. If it's made by
     * the extension, it sends a response message to it
     *
     */
    public JSONObject getResponse();

    /**
     * Returns the Client id of the extension
     *
     * @return client-id
     */
    public int getClientId();

    /**
     * Sets the Client id of the extension
     *
     * @param id Client-Id
     */
    public void setClientId(int id);
}
