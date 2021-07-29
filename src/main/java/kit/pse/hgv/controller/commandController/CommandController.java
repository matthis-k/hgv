package kit.pse.hgv.controller.commandController;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.PolarCoordinate;

public class CommandController extends Thread implements CommandEventSource {
    //TODO: undo/redo
    private static CommandController instance;

    private Vector<CommandQListener> listeners = new Vector<>();
    private ConcurrentLinkedQueue<ICommand> commandQ = new ConcurrentLinkedQueue<ICommand>();

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
        }
    }

    public void queueCommand(ICommand c) {
        synchronized (this) {
            commandQ.add(c);
        }
    }

    @Override
    public void notifyAll(ICommand c) {
        for (CommandQListener listener : listeners) {
            Task<Void> task = new Task<>() {
                @Override protected Void call() throws Exception {
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

    public void dummy() {
        ICommand c = new LoadGraphCommand("src/main/resources/Vorlage.graphml");
        c.execute();
        notifyAll(c);
        c = new CreateNodeCommand(1, new PolarCoordinate(5, 2));
        c.execute();
        notifyAll(c);
        int[] nodes = {3, 8};
        c = new CreateEdgeCommand(1, nodes);
        c.execute();
        notifyAll(c);
    }

    public void doSpiralGraph(int n) {
        ICommand cmd = new LoadGraphCommand("src/main/resources/empty.graphml");
        cmd.execute();
        notifyAll(cmd);

        int graph = 1;
        Vector<Integer> nodeIds = new Vector<>();
        for (int i = 0; i < n; i++) {
            CreateNodeCommand c = new CreateNodeCommand(graph, new PolarCoordinate(i, i));
            c.execute();
            nodeIds.addAll(c.getModifiedIds());
            int r = 150+(3*i*255/n)%100;
            int g = 100+(2*i*255/n)%100;
            int b = 50+(i*255/n)%100;
            GraphSystem.getInstance().getGraphElementByID(nodeIds.lastElement()).setMetadata("color", "rgb(" + r + ","+g+","+b+")");
            notifyAll(c);
            if (i>0) {
                int[] nodes = {nodeIds.get(i-1), nodeIds.get(i)};
                CreateEdgeCommand e = new CreateEdgeCommand(graph, nodes);
                e.execute();
                notifyAll(e);
            }
        }

    }
}