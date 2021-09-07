package kit.pse.hgv.extensionServer;

/**
 * Represents a state of a client.
 */
public interface ClientState {
    /**
     * @return the next state
     */
    ClientState nextState();

    /**
     * Decides what the handler should do in that state.
     *
     * @param handler is the handler
     */
    void work(ClientHandler handler);
}
