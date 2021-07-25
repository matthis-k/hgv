package kit.pse.hgv.controller.commandProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.paint.Color;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import kit.pse.hgv.controller.commandController.commands.*;

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
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            int graphId = inputAsJson.getInt("graphId");
            String coordinate = inputAsJson.getString("coordinate");
            String[] eachCoordinate = coordinate.split(",");
            double coord1 = Double.valueOf(eachCoordinate[0]);
            double coord2 = Double.valueOf(eachCoordinate[1]);
            Coordinate coord = new CartesianCoordinate(coord1, coord2);
            CreateNodeCommand command = new CreateNodeCommand(graphId, coord);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that creates edges.
     */
    CREATE_EDGE(ExtensionCommandType.START + "CreateEdge" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            int graphId = inputAsJson.getInt("graphId");
            int firstNode = inputAsJson.getInt("id1");
            int secondNode = inputAsJson.getInt("id2");
            int[] nodeIds = {firstNode, secondNode};
            CreateEdgeCommand command = new CreateEdgeCommand(graphId, nodeIds);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that deletes nodes.
     */
    DELETE_ELEMENT(ExtensionCommandType.START + "DeleteElement" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            int elementId = inputAsJson.getInt("id");
            GraphElementDeleteCommand command = new GraphElementDeleteCommand(elementId);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that moves nodes.
     */
    MOVE_NODE(ExtensionCommandType.START + "MoveNode" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            int id = inputAsJson.getInt("id");
            String coordinate = inputAsJson.getString("coordinate");
            String[] eachCoordinate = coordinate.split(",");
            double coord1 = Double.valueOf(eachCoordinate[0]);
            double coord2 = Double.valueOf(eachCoordinate[1]);
            MoveNodeCommand command = new MoveNodeCommand(id);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that changes the color.
     */
    CHANGE_COLOR(ExtensionCommandType.START + "ChangeColor" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            int elementId = inputAsJson.getInt("elementId");
            // Hexazahl
            String hexcolor = inputAsJson.getString("color");
            Color color = Color.web(hexcolor);
            EditColorCommand command = new EditColorCommand(elementId, color);
            return new ParseResult(command, this);
        }
    },

    /**
     * FRAGE: Machen wir einen ChangeMetadata Befehl, der nochmal einen extra Typ
     * hat oder spalten wir das in einzelne Befehle auf?
     */
    CHANGE_METADATA(ExtensionCommandType.START + "ChangeMetadata" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            String metaVal = inputAsJson.getString("TODO");
            String metaKey = inputAsJson.getString("TODO");
            int elementId = inputAsJson.getInt("id");
            EditUserMetaCommand command = new EditUserMetaCommand(metaKey, metaVal, elementId);
            return new ParseResult(command, this);
        }
    },

    COMMAND_COMPOSITE(ExtensionCommandType.START + "CommandComposite" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException {
            CommandComposite command = new CommandComposite();
            JSONArray commandsAsArray = inputAsJson.getJSONArray("commands");
            for (int i = 0; i < commandsAsArray.length();i++) {
                Command c = ExtensionCommandType.parseJson(commandsAsArray.getJSONObject(i)).cmd;
                command.addComamnd(c);
            }            
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that moves the center of the
     * presentation.
     */
    MOVE_CENTER(ExtensionCommandType.START + "MoveCenter" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException {
            MoveCenterCommand command = new MoveCenterCommand();
            return new ParseResult(command, this);
        }
    },
    /**
     * This command type can enable or disable the manual editing of a graph.
     */
    SET_MANUAL_EDIT(ExtensionCommandType.START + "SetManualEdit" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException {
            SetManualEditCommand command = new SetManualEditCommand();
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that pauses the script.
     */
    PAUSE(ExtensionCommandType.START + "Pause" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            int id = inputAsJson.getInt("id");
            PauseExtensionCommand command = new PauseExtensionCommand(id);
            return new ParseResult(command, this);
        }
    },

    STOP(ExtensionCommandType.START + "Stop" + ExtensionCommandType.END) {

        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            int id = inputAsJson.getInt("id");
            StopExtensionCommand command = new StopExtensionCommand(id);
            return new ParseResult(command, this);
        }

    };

    private static final String START = "^";
    private static final String END = "$";

    private final Pattern pattern;

    private class ParseResult {
        private Command cmd = null;
        private ExtensionCommandType type = null;

        public ParseResult(Command cmd, ExtensionCommandType type) {
            this.cmd = cmd;
            this.type = type;
        }

    }

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
        try {
            JSONObject inputAsJson = new JSONObject(extensionInput);
            ParseResult res = parseJson(inputAsJson);
            CommandController.getInstance().queueCommand(res.cmd);
            return res.type;
        } catch (JSONException | NumberFormatException e) {
            throw new IllegalArgumentException("Command is not in the correct format.");
        }
    }

    private static ParseResult parseJson(JSONObject json) throws JSONException, NumberFormatException {
        for (ExtensionCommandType input : ExtensionCommandType.values()) {
            Matcher matcher = input.pattern.matcher(json.getString("type"));
            if (matcher.matches()) {
                return input.parseCommand(json);
            }
        }
        return null;
    }

    /**
     * This method decides whether the parameters from the given string from the
     * extension are correct
     *
     * @param inputAsJson String as JSON Object
     */
    protected abstract ParseResult parseCommand(JSONObject inputAsJson) throws JSONException;
}
