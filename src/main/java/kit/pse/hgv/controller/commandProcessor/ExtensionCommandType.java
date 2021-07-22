package kit.pse.hgv.controller.commandProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * This class processes the string command from the extension server and
 * delegates the command to the command controller.
 */
public enum ExtensionCommandType {

    /**
     * This enum handles the command string that creates nodes.
     */
    CREATE_NODE(ExtensionCommandType.START + "CreateNode" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {
            String coordinate = inputAsJson.getString("coordinate");
            String[] eachCoordinate = coordinate.split(",");
            double coord1 = Double.valueOf(eachCoordinate[0]);
            double coord2 = Double.valueOf(eachCoordinate[1]);
        }
    },

    /**
     * This enum handles the command string that creates edges.
     */
    CREATE_EDGE(ExtensionCommandType.START + "CreateEdge" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {
            int firstNode = inputAsJson.getInt("id1");
            int secondNode = inputAsJson.getInt("id2");
        }
    },

    /**
     * This enum handles the command string that deletes nodes.
     */
    DELETE_ELEMENT(ExtensionCommandType.START + "DeleteElement" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {
            int elementId = inputAsJson.getInt("id");
        }
    },

    /**
     * This enum handles the command string that moves nodes.
     */
    MOVE_NODE(ExtensionCommandType.START + "MoveNode" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {
            int id = inputAsJson.getInt("id");
            String coordinate = inputAsJson.getString("coordinate");
            String[] eachCoordinate = coordinate.split(",");
            double coord1 = Double.valueOf(eachCoordinate[0]);
            double coord2 = Double.valueOf(eachCoordinate[1]);
        }
    },

    /**
     * This enum handles the command string that changes the color.
     */
    CHANGE_COLOR(ExtensionCommandType.START + "ChangeColor" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {
            int elementID = inputAsJson.getInt("elementId");
            // Hexazahl
        }
    },

    /**
     * FRAGE: Machen wir einen ChangeMetadata Befehl, der nochmal einen extra Typ
     * hat oder spalten wir das in einzelne Befehle auf?
     */
    CHANGE_METADATA(ExtensionCommandType.START + "ChangeMetadata" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {

        }
    },

    /**
     * This enum handles the command string that moves the center of the
     * presentation.
     */
    MOVE_CENTER(ExtensionCommandType.START + "MoveCenter" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {

        }
    },
    /**
     * This command type can enable or disable the manual editing of a graph.
     */
    SET_MANUAL_EDIT(ExtensionCommandType.START + "SetManualEdit" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {

        }
    },

    /**
     * This enum handles the command string that pauses the script.
     */
    PAUSE(ExtensionCommandType.START + "Pause" + ExtensionCommandType.END) {
        @Override
        protected void parseCommand(JSONObject inputAsJson) {

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
    ExtensionCommandType(String s) {
        this.pattern = Pattern.compile(s);
    }

    /**
     * This method decides whether the command from the extension is in the correct
     * format
     *
     * @param extensionInput the command as string from the extension
     * @return the command as enum
     */
    public static ExtensionCommandType processCommandString(String extensionInput) {
        boolean isInputValid = false;
        ExtensionCommandType enumInput = null;
        try {
            JSONObject inputAsJson = new JSONObject(extensionInput);
            for (ExtensionCommandType input : ExtensionCommandType.values()) {
                Matcher matcher = input.pattern.matcher((String) inputAsJson.get("type"));
                if (matcher.matches()) {
                    input.parseCommand(inputAsJson);
                    enumInput = input;
                    isInputValid = true;
                }
            }
            if (isInputValid) {
                // TODO: AN CONTROLLER FEHLERNACHRICHT GEBEN UND STOPP DES SKRIPTS BEANTRAGEN
                return enumInput;
            } else {
                throw new IllegalArgumentException("Command Type is non-existent.");
            }
        } catch (JSONException | NumberFormatException e) {
            throw new IllegalArgumentException("Command is not in the correct format.");
        }
    }

    /**
     * This method decides whether the parameters from the given string from the
     * extension are correct
     *
     * @param inputAsJson String as JSON Object
     */
    protected abstract void parseCommand(JSONObject inputAsJson) throws JSONException;
}
