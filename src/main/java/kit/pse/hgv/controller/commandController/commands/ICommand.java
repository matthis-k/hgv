package kit.pse.hgv.controller.commandController.commands;

import org.json.JSONObject;

import java.util.Set;

public interface ICommand {
    /**
     * executes the given command
     */
    void execute();

    /**
     * Returns all changed IDs
     *
     * @return all changed IDs
     */
    Set<Integer> getModifiedIds();

    /**
     * Returns if the user or the extension wants to change the graph
     *
     * @return true if the user wants to change the graph
     */
    boolean isUser();

    /**
     * Checks if the Change is made by the user or the extension. If it's made by
     * the extension, it sends a response message to it
     */
    JSONObject getResponse();

    /**
     * Returns the Client id of the extension
     *
     * @return client-id
     */
    int getClientId();

    /**
     * Sets the Client id of the extension
     *
     * @param id Client-Id
     */
    void setClientId(int id);

    /**
     * returns, if the execution of the command was successful
     * @return if the execution was successful
     */
    boolean succeeded();
}
