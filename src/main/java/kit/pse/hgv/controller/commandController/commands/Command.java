package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles the execution of a command and the undoing of an execution
 */
public interface Command {
    public void execute();
    public void undo();
}
