package kit.pse.hgv.controller.commandController;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import kit.pse.hgv.controller.commandController.commands.Command;

public class CommandController extends Thread implements CommandEventSourve {
    //TODO: undo/redo
    private static CommandController instance;

    private Vector<CommandQListener> listeners = new Vector<>();
    private ConcurrentLinkedQueue<Command> commandQ = new ConcurrentLinkedQueue<Command>();

    public static CommandController getInstance() {
        if (instance == null) {
            instance = new CommandController();
        }
        return instance;
    }

    private CommandController() {
    }

    public void run() {
        while (true) {
            executeNext();
        }
    }

    private void executeNext() {
        commandQ.poll().execute();
    }

    public void queueCommand(Command c) {
        commandQ.add(c);
    }

    @Override
    public void notifyAll(Command c) {
        for (CommandQListener listener : listeners) {
            listener.onNotify(c);
        }
    }

    @Override
    public void register(CommandQListener listener) {
        listeners.add(listener);
    }
}