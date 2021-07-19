package kit.pse.hgv.controller.commandProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * This class processes the string command from the extension server and
 * delegates the command to the command controller.
 */
public enum ExtensionCommand {

    /**
     * This enum handles the command string that creates nodes.
     */
    CREATE_NODE(ExtensionCommand.START + "CreateNode" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            try {
                String coordinate = jsonExtensionInput.getString("coordinate");
                String[] eachCoordinate = coordinate.split(",");
                double coord1 = Double.valueOf(eachCoordinate[0]);
                double coord2 = Double.valueOf(eachCoordinate[1]);
            } catch (NumberFormatException | JSONException e) {
                // TODO: show error message
            }
        }
    },

    /**
     * This enum handles the command string that creates edges.
     */
    CREATE_EDGE(ExtensionCommand.START + "CreateEdge" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            try {
                int firstNode = jsonExtensionInput.getInt("id1");
                int secondNode = jsonExtensionInput.getInt("id2");
            } catch (JSONException e) {
                // TODO: show error message
            }
        }
    },

    /**
     * This enum handles the command string that deletes nodes.
     */
    DELETE_NODE(ExtensionCommand.START + "DeleteNode" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            try {
                int nodeID = jsonExtensionInput.getInt("node_id");
            } catch (JSONException e) {
                // TODO: show error message
            }
        }
    },

    /**
     * This enum handles the command string that deletes edges.
     */
    DELETE_EDGE(ExtensionCommand.START + "DeleteEdge" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            try {
                int edgeID = jsonExtensionInput.getInt("edge_id");
            } catch (JSONException e) {
                // TODO: show error message
            }
        }
    },

    /**
     * This enum handles the command string that moves nodes.
     */
    MOVE_NODE(ExtensionCommand.START + "MoveNode" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    },

    /**
     * This enum handles the command string that changes the color.
     */
    CHANGE_COLOR(ExtensionCommand.START + "ChangeColor" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            try {
                int elementID = jsonExtensionInput.getInt("elementId");
                // Hexazahl
            } catch (JSONException e) {
                // TODO: show error message
            }

        }
    },

    /**
     * FRAGE: Machen wir einen ChangeMetadata Befehl, der nochmal einen extra Typ
     * hat oder spalten wir das in einzelne Befehle auf?
     */
    CHANGE_METADATA(ExtensionCommand.START + "ChangeMetadata" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    },

    /**
     * This enum handles the command string that moves the center of the
     * presentation.
     */
    MOVE_CENTER(ExtensionCommand.START + "MoveCenter" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    },

    SET_MANUAL_EDIT(ExtensionCommand.START + "SetManualEdit" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    },

    /**
     * This enum handles the command string that pauses the script.
     */
    PAUSE(ExtensionCommand.START + "Pause" + ExtensionCommand.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    };

    private static final String START = "^";
    private static final String END = "$";

    private final Pattern pattern;

    /**
     * The constructor creates an element of this class
     *
     * @param s the pattern associated to the input
     */
    ExtensionCommand(String s) {
        this.pattern = Pattern.compile(s);
    }

    /**
     * This method decides whether the command from the extension is in the correct
     * format
     *
     * @param extensionInput the command as string from the extension
     * @return the command as enum
     */
    public static ExtensionCommand processCommandString(String extensionInput) {
        boolean correctInput = false;
        ExtensionCommand enumInput = null;
        try {
            JSONObject jsonExtensionInput = new JSONObject(extensionInput);
            for (ExtensionCommand input : ExtensionCommand.values()) {
                Matcher matcher = input.pattern.matcher((String) jsonExtensionInput.get("type"));
                if (matcher.matches()) {
                    input.parseCommand(jsonExtensionInput);
                    enumInput = input;
                    correctInput = true;
                }
            }
            if (correctInput) {
                // TODO: AN CONTROLLER FEHLERNACHRICHT GEBEN UND STOPP DES SKRIPTS BEANTRAGEN
                return enumInput;
            } else {
                throw new IllegalArgumentException("Command Type is non-existent.");
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Command is not in the correct format.");
        }
    }

    /**
     * This method decides whether the parameters from the given string from the
     * extension are correct
     *
     * @param jsonExtensionInput String as JSON Object
     */
    protected abstract void parseCommand(JSONObject jsonExtensionInput);
}
