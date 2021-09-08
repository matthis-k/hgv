package kit.pse.hgv.controller.commandController.commands;

import org.json.JSONArray;

import java.util.Vector;

/**
 * This class handles the execution of various commands
 */
public class CommandComposite extends WorkingAreaCommand {
    private final Vector<ICommand> commands = new Vector<>();
    private static final String RESPONSES = "responses";
    private static final String SUBCOMMAND_FAILED = "Diese Aktion ist fehlgeschlagen.";

    /**
     * This method adds the command to be executed in a Vector class
     *
     * @param c command to be executed
     */
    public void addCommand(ICommand c) {
        commands.add(c);
    }

    /**
     * This method adds the command to be executed in a Vector class
     *
     * @param c command to be executed
     */
    public void addCommand(WorkingAreaCommand c) {
        for (Integer i : c.getWorkingArea()) {
            extendWorkingArea(i);
        }
        commands.add(c);
    }

    @Override
    public void execute() {
        JSONArray responses = new JSONArray();
        for (ICommand c : commands) {
            c.execute();
            modifiedIds.addAll(c.getModifiedIds());
            responses.put(c.getResponse());
            if (!c.succeeded()) {
                fail(SUBCOMMAND_FAILED);
            }
        }
        response.put(RESPONSES, responses);
    }
}