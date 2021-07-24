package kit.pse.hgv.controller.commandController.commands;

import java.util.Vector;

public class CommandComposite implements Command {
    private Vector<Command> commands = new Vector<>();

    public void addComamnd(Command c) {
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