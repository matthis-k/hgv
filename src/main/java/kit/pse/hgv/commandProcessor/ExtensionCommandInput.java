package kit.pse.hgv.commandProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * This class processes the string command from the extension server and delegates the command to the command controller.
 */
public enum ExtensionCommandInput {

    /**
     * This enum handles the command string that creates nodes.
     */
    CREATE_NODE(ExtensionCommandInput.START + "CreateNode" + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            String coordinate = jsonExtensionInput.getString("coordinate");
            coordinate = coordinate.replaceAll("[\\p{Ps}\\p{Pe}]", "");
            //String[] eachCoordinate = coordinate.split(",");
            //Woher weiß man bei (2,1,0) welche x Koordinate zu welcher y Koordinate gehört? Sollte man das in (2,1;0) angeben oder so?
        }
    },


    /**
     * This enum handles the command string that creates edges.
     */
    CREATE_EDGE(ExtensionCommandInput.START + "CreateEdge" + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            int firstNode = jsonExtensionInput.getInt("id1");
            int secondNode = jsonExtensionInput.getInt("id2");
        }
    },

    /**
     * This enum handles the command string that deletes nodes.
     */
    DELETE_NODE(ExtensionCommandInput.START + "DeleteNode" + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            int nodeID = jsonExtensionInput.getInt("node_id");
        }
    },

    /**
     * This enum handles the command string that deletes edges.
     */
    DELETE_EDGE(ExtensionCommandInput.START + "DeleteEdge" + ExtensionCommandInput.END) {
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            int edgeID = jsonExtensionInput.getInt("edge_id");
        }
    },

    /**
     * This enum handles the command string that moves nodes.
     */
    MOVE_NODE(ExtensionCommandInput.START + "MoveNode" + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    },

    /**
     * This enum handles the command string that changes the color.
     */
    CHANGE_COLOR(ExtensionCommandInput.START + "ChangeColor" + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {
            int elementID = jsonExtensionInput.getInt("elementId");
            //Hexazahl
        }
    },

    /**
     * FRAGE: Machen wir einen ChangeMetadata Befehl, der nochmal einen extra Typ hat oder spalten wir das in einzelne Befehle auf?
     */
    CHANGE_METADATA(ExtensionCommandInput.START + "ChangeMetadata" + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    },

    /**
     * This enum handles the command string that moves the center of the presentation.
     */
    MOVE_CENTER(ExtensionCommandInput.START + "MoveCenter" + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(JSONObject jsonExtensionInput) {

        }
    },

    /**
     * This enum handles the command string that pauses the script.
     */
    PAUSE(ExtensionCommandInput.START + "Pause" + ExtensionCommandInput.END){
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
    ExtensionCommandInput(String s) {
        this.pattern = Pattern.compile(s);
    }

    /**
     * This method decides whether the command from the extension is in the correct format
     *
     * @param extensionInput the command as string from the extension
     * @return the command as enum
     */
    public static ExtensionCommandInput processCommandString(String extensionInput){
        boolean correctInput = false;
        ExtensionCommandInput enumInput = null;
        try {
            JSONObject jsonExtensionInput = new JSONObject(extensionInput);
            for (ExtensionCommandInput input : ExtensionCommandInput.values()) {
                Matcher matcher = input.pattern.matcher((String) jsonExtensionInput.get("type"));
                if(matcher.matches()) {
                    input.parseCommand(jsonExtensionInput);
                    enumInput = input;
                    correctInput = true;
                }
            }
            if (correctInput) {
                //AN CONTROLLER FEHLERNACHRICHT GEBEN UND STOPP DES SKRIPTS BEANTRAGEN
                return enumInput;
            } else {
                throw new IllegalArgumentException("Command Type is non-existent.");
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Command is not in the correct format.");
        }
    }

    /**
     * This method decides whether the parameters from the given string from the extension are correct
     *
     * @param jsonExtensionInput String as JSON Object
     */
    protected abstract void parseCommand(JSONObject jsonExtensionInput);
}
