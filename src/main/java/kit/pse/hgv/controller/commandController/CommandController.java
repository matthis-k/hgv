package kit.pse.hgv.controller.commandController;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.PolarCoordinate;

public class CommandController extends Thread implements CommandEventSource {
    // TODO: undo/redo
    private static CommandController instance;

    private Vector<CommandQListener> listeners = new Vector<>();
    private ConcurrentLinkedQueue<ICommand> commandQ = new ConcurrentLinkedQueue<ICommand>();

    /**
     * returns the instance of the CommandController
     *
     * @return CommandController
     */
    public static CommandController getInstance() {
        if (instance == null) {
            instance = new CommandController();
        }
        return instance;
    }

    /**
     * The constructor creates an element of this class
     */
    private CommandController() {
        setName("CommandController");
    }

    /**
     * Starts the CommandController
     */
    public void run() {
        while (true) {
            synchronized (this) {
                if (!commandQ.isEmpty()) {
                    executeNext();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Executes the next Command in the CommandQ
     */
    private void executeNext() {
        synchronized (this) {
            ICommand c = commandQ.poll();
            if (c != null) {
                c.execute();
                notifyAll(c);
            }
        }
    }

    /**
     * Puts a Command in the commandQueue
     *
     * @param c Command that was build from the CommandProcessor
     */
    public void queueCommand(ICommand c) {
        synchronized (this) {
            commandQ.add(c);
            notify();
        }
    }

    @Override
    public void notifyAll(ICommand c) {
        for (CommandQListener listener : listeners) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    listener.onNotify(c);
                    return null;
                }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            Platform.runLater(th);
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void register(CommandQListener listener) {
        listeners.add(listener);
    }

    public ConcurrentLinkedQueue<ICommand> getCommandQ(){
        return commandQ;
    }

    public void stopController() {
        interrupt();
    }
}