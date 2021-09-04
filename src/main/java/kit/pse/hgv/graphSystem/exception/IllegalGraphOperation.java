package kit.pse.hgv.graphSystem.exception;

import kit.pse.hgv.exception.HGVException;

/**
 * This Exception is thrown when there is a try to execute an illegal operation on the graphsystem.
 */
public class IllegalGraphOperation extends HGVException {
    private String mes;

    public IllegalGraphOperation(String mes) {
        this.mes = mes;
    }
}
