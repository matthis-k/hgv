package kit.pse.hgv.commandProcessor;

import kit.pse.hgv.commandController.commands.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ExtensionCommandInput {

    CREATE_NODE(ExtensionCommandInput.START + "create node " + ExtensionCommandInput.GRAPHID + ExtensionCommandInput.COORDINATE + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(Matcher matcher, Command command) {
            int GraphID;

        }
    },

    CREATE_EDGE(ExtensionCommandInput.START + "create edge " + ExtensionCommandInput.GRAPHID + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(Matcher matcher, Command command) {
            int GraphID;
        }
    },

    DELETE_NODE(ExtensionCommandInput.START + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(Matcher matcher, Command command) {

        }
    },

    DELETE_EDGE(ExtensionCommandInput.START + ExtensionCommandInput.END) {
        @Override
        protected void parseCommand(Matcher matcher, Command command) {

        }
    },

    MOVE_NODE(ExtensionCommandInput.START + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(Matcher matcher, Command command) {

        }
    },

    CHANGE_COLOR(ExtensionCommandInput.START + ExtensionCommandInput.COLOR + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(Matcher matcher, Command command) {

        }
    },

    MOVE_CENTER(ExtensionCommandInput.START + ExtensionCommandInput.END){
        @Override
        protected void parseCommand(Matcher matcher, Command command) {

        }
    }

    ;

    private static final String START = "^";
    private static final String END = "$";
    private static final String GRAPHID = "[0-9]+";
    private static final String COORDINATE = "\\([0-9]+\\,[0-9]+\\)";
    private static final String COLOR = "#([a-fA-F0-9]{6})";

    private final Pattern pattern;

    ExtensionCommandInput(String s) {
        this.pattern = Pattern.compile(s);
    }

    public static void processCommandString(String extensionInput){
        boolean correctInput = false;
        for (ExtensionCommandInput input : ExtensionCommandInput.values()) {
            Matcher matcher = input.pattern.matcher(extensionInput);
            correctInput = true;
        }
        if (!correctInput) {
            //AN CONTROLLER FEHLERNACHRICHT GEBEN UND STOPP DES SKRIPTS BEANTRAGEN
            throw new IllegalArgumentException("Command is not in the correct format.");
        }
    }

    protected abstract void parseCommand(Matcher matcher, Command command);
}
