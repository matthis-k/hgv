package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.controller.commandController.commands.WorkingAreaCommand;
import kit.pse.hgv.controller.commandController.scheduler.IScheduler;
import kit.pse.hgv.controller.commandController.scheduler.ParallelScheduler;
import kit.pse.hgv.view.RenderModel.DefaultRenderEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandController extends Thread implements CommandEventSource {
    // TODO: undo/redo
    private static CommandController instance;
    private static boolean manualEdit = true;

    private final IScheduler scheduler = new ParallelScheduler();

    private final Vector<CommandQListener> listeners = new Vector<>();
    private final ConcurrentLinkedQueue<ICommand> commandQ = new ConcurrentLinkedQueue<ICommand>();

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
                    }
                }
            }
        }
    }

    private List<ICommand> getNextCommands() {
        return null;
    }

    /**
     * Executes the next Commands in the CommandQ that are possible to execute parallel.
     */
    private void executeNext() {
        List<CommandThread> commandThreads = new ArrayList<>();

        //Thread creation.
        for (ICommand c : scheduler.getNextCommand(commandQ)) {
            if (!manualEdit && c.isUser() && c instanceof WorkingAreaCommand) {
                continue;
            }
            CommandThread th = new CommandThread(c);
            commandThreads.add(th);
            th.start();
        }

        //Thread joins.
        for (CommandThread th : commandThreads) {
            try {
                th.join();
            } catch (InterruptedException e) {
            }
        }

        //When thread finished, notifyAll.
        for (CommandThread th : commandThreads) {
            notifyAll(th.getCommand());
        }


        /*
        synchronized (this) {
            ICommand c = commandQ.poll();
            if (c != null) {
                c.execute();
                notifyAll(c);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    /**
     * Puts a Command in the commandQueue
     *
     * @param c Command that was build from the CommandProcessor
     */
    public void queueCommand(ICommand c) {
        synchronized (this) {
            commandQ.add(c);
            notifyAll();
        }
    }

    @Override
    public void notifyAll(ICommand c) {
        for (CommandQListener listener : listeners) {
            listener.onNotify(c);
        }
        DefaultRenderEngine.resetError();
    }

    @Override
    public void register(CommandQListener listener) {
        listeners.add(listener);
    }

    public ConcurrentLinkedQueue<ICommand> getCommandQ() {
        return commandQ;
    }

    public void stopController() {
        interrupt();
    }

    public void setManualEdit(boolean edit) {
        manualEdit = edit;
    }
}