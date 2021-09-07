package kit.pse.hgv.graphSystem.exception;

import kit.pse.hgv.exception.HGVException;

/**
 * Exception occures, whenever an ovefflow is detected and not planed. Provides
 * detailt message how to fix the error.
 */
public class OverflowException extends HGVException {
    private final String mes;

    public OverflowException(String mes) {
        this.mes = mes;
    }
}
