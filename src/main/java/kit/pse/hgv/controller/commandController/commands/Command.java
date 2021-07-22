package kit.pse.hgv.controller.commandController.commands;

public interface Command {
    public void execute();
    public void undo();
}
