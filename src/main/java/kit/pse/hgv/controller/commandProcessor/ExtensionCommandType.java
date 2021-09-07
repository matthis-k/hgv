package kit.pse.hgv.controller.commandProcessor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.paint.Color;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;

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
            JSONObject coordinate = inputAsJson.getJSONObject("coordinate");
            double phi = coordinate.getDouble("phi");
            double r = coordinate.getDouble("r");
            Coordinate coord = new PolarCoordinate(phi, r);
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
            int[] nodeIds = { firstNode, secondNode };
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
            JSONObject coordinate = inputAsJson.getJSONObject("coordinate");
            double phi = coordinate.getDouble("phi");
            double r = coordinate.getDouble("r");
            PolarCoordinate coord = new PolarCoordinate(phi, r);
            MoveNodeCommand command = new MoveNodeCommand(id, coord);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command that changes the metadata of an element
     */
    CHANGE_METADATA(ExtensionCommandType.START + "ChangeMetadata" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            String meta = inputAsJson.getString("value");
            String key = inputAsJson.getString("key");
            int elementId = inputAsJson.getInt("id");
            EditUserMetaCommand command = new EditUserMetaCommand(elementId, key, meta);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles various commands coming as composite from the extension
     */
    COMMAND_COMPOSITE(ExtensionCommandType.START + "CommandComposite" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException {
            CommandComposite command = new CommandComposite();
            JSONArray commandsAsArray = inputAsJson.getJSONArray("commands");
            for (int i = 0; i < commandsAsArray.length(); i++) {
                ICommand c = ExtensionCommandType.parseJson(commandsAsArray.getJSONObject(i)).cmd;
                command.addCommand(c);
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
            JSONObject coordinate = inputAsJson.getJSONObject("coordinate");
            double phi = coordinate.getDouble("phi");
            double r = coordinate.getDouble("r");
            Coordinate coord = new PolarCoordinate(phi, r);
            MoveCenterCommand command = new MoveCenterCommand(coord);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that returns the graph
     */
    GET_GRAPH(ExtensionCommandType.START + "GetGraph" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException {
            int graphId = inputAsJson.getInt("graphId");
            SendGraphCommand command = new SendGraphCommand(graphId);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the render command coming from the extension
     */
    RENDER(ExtensionCommandType.START + "Render" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException {
            RenderCommand command = new RenderCommand();
            return new ParseResult(command, this);
        }
    },

    /**
     * This command type can enable or disable the manual editing of a graph.
     */
    SET_MANUAL_EDIT(ExtensionCommandType.START + "SetManualEdit" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException {
            boolean manualEdit = inputAsJson.getBoolean("manualEdit");
            SetManualEditCommand command = new SetManualEditCommand(manualEdit);
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that pauses the script.
     */
    PAUSE(ExtensionCommandType.START + "Pause" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            PauseExtensionCommand command = new PauseExtensionCommand();
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that stops the script
     */
    STOP(ExtensionCommandType.START + "Stop" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, NumberFormatException {
            ICommand command = new StopExtensionCommand();
            return new ParseResult(command, this);
        }
    },

    /**
     * This enum handles the command string that saves the graph
     */
    SAVE_GRAPH(ExtensionCommandType.START + "SaveGraph" + ExtensionCommandType.END) {
        @Override
        protected ParseResult parseCommand(JSONObject inputAsJson) throws JSONException, IllegalArgumentException {
            String path = inputAsJson.getString("path");
            Path file = new File(path).toPath();
            if (Files.isDirectory(file.getParent())) {
                int id = inputAsJson.getInt("graphId");
                SaveGraphCommand command = new SaveGraphCommand(id, path);
                return new ParseResult(command, this);
            } else {
                throw new IllegalArgumentException("This Directory is non-existent.");
            }
        }
    };

    private static final String START = "^";
    private static final String END = "$";

    private final Pattern pattern;

    /**
     * This class unites the command its enum type
     */
    private class ParseResult {
        private ICommand cmd = null;
        private ExtensionCommandType type = null;

        public ParseResult(ICommand cmd, ExtensionCommandType type) {
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
    public static ExtensionCommandType processCommandString(String extensionInput, int clientId) {
        try {
            JSONObject inputAsJson = new JSONObject(extensionInput);
            ParseResult res = parseJson(inputAsJson);
            if (res == null) {
                throw new IllegalArgumentException("Not a valid extension command");
            }
            if (res.cmd != null) {
                res.cmd.setClientId(clientId);
                CommandController.getInstance().queueCommand(res.cmd);
            }
            return res.type;
        } catch (JSONException | NumberFormatException e) {
            throw new IllegalArgumentException("Command is not in the correct format.");
        }
    }

    /**
     * This method decides which command should be executed and calls the
     * parseCommand Method
     * 
     * @param json the command as JSONObject
     * @return the command and the enum united in one class
     * @throws JSONException         if the JSONObject doesn't contain the correct
     *                               format
     * @throws NumberFormatException if other parameters are incorrect
     */
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
