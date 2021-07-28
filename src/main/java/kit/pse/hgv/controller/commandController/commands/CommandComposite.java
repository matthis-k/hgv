package kit.pse.hgv.controller.commandController.commands;

import java.util.Vector;

/**
 * This class handles the execution of various commands
 */
public class CommandComposite implements Command {
    private Vector<Command> commands = new Vector<>();

    /**
     * This method adds the command to be executed in a Vector class
     * @param c command to be executed
     */
    public void addCommand(Command c) {
        commands.add(c);
    }

    @Override
    public void execute() {
        for (Command c : commands) {
            c.execute();
        }
    }

    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.elementAt(i).undo();
        }
    }
}