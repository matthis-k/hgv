package kit.pse.hgv.controller.commandController;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.concurrent.Worker;
import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandController.commands.LoadGraphCommand;
import kit.pse.hgv.representation.PolarCoordinate;

public class CommandController extends Thread implements CommandEventSource {
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
        synchronized (this) {
            Command c = commandQ.poll();
            if (c != null) {
                c.execute();
                notifyAll(c);
                System.out.println("command processed");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void queueCommand(Command c) {
        synchronized (this) {
            commandQ.add(c);
            System.out.println(commandQ.size());
        }
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

    public void dummy() {
        Command c = new LoadGraphCommand("src/main/resources/Vorlage.graphml");
        c.execute();
        notifyAll(c);
        c = new CreateNodeCommand(1, new PolarCoordinate(5, 2));
        c.execute();
        notifyAll(c);
    }
}