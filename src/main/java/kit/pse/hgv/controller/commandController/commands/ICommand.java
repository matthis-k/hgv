package kit.pse.hgv.controller.commandController.commands;

public interface ICommand {
    public void execute();
    public void undo();
    public boolean isUser();
    public int getClient();
    public void setId(int id);
}
