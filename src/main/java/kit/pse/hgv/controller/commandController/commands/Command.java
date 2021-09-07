package kit.pse.hgv.controller.commandController.commands;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * This class handles the execution of a command and the undoing of an execution
 */
public abstract class Command implements ICommand {
    protected final static String SUCCESS = "success";
    protected final static String REASON = "reason";
    protected static final String ID = "id";
    protected static final String NO_ELEMENT_WITH_ID = "Es existiert kein Element (in diesem Graphen) mit der gewünschten ID.";
    protected static final String NO_GRAPH_WITH_ID = "Es existiert kein Graph mit dieser ID.";
    protected static final String WRONG_GRAPH = "wrong graph";



    protected JSONObject response = new JSONObject();
    protected int client = 0;
    protected HashSet<Integer> modifiedIds = new HashSet<>();

    protected Command() {
        succeed();
    }
    protected void succeed() {
        response.put(SUCCESS, true);
    }
    protected void fail(String message) {
        response.put(SUCCESS, false);
        response.put(REASON, message);
    }

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
     * Checks if the Change is made by the user or the extension. If it's made by
     * the extension, it sends a response message to it
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

    @Override
    public boolean succeeded() {
        try {
            return response.getBoolean(SUCCESS);
        } catch (JSONException e) {
            return false;
        }
    }
}
