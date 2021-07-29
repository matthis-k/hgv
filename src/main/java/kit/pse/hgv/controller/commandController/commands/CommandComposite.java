package kit.pse.hgv.controller.commandController.commands;

import org.json.JSONArray;

import java.util.Vector;

/**
 * This class handles the execution of various commands
 */
public class CommandComposite extends Command {
    private Vector<ICommand> commands = new Vector<>();

    /**
     * This method adds the command to be executed in a Vector class
     * @param c command to be executed
     */
    public void addCommand(ICommand c) {
        commands.add(c);
    }

    @Override
    public void execute() {
        JSONArray responses = new JSONArray();
        boolean success = true;
        for (ICommand c : commands) {
            c.execute();
            modifiedIds.addAll(c.getModifiedIds());
            responses.put(c.getResponse());
            if (!c.getResponse().getBoolean("success")) {
                success = false;
            }
        }
        response.put("success", success);
        response.put("responses", responses);
        if (!success) {
            response.put("reason", "subcommand failed");
        }
    }

    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.elementAt(i).undo();
        }
    }
}