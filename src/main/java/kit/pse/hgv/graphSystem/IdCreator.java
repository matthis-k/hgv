package kit.pse.hgv.graphSystem;

public class IdCreator {
    protected int nextId = 0;
    protected Integer getNextId() {
        // CONSTRAINT: assume there will be no overflow
        // TODO: manage overflows
        // check if already an overflow ocuured in the past???
        return ++nextId;
    }
}
