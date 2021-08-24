package kit.pse.hgv.graphSystem.exception;

/**
 * Exception occures, whenever an ovefflow is detected and not planed. Provides
 * detailt message how to fix the error.
 */
public class OverflowException extends Exception {
    private String mes;

    public OverflowException(String mes) {
        this.mes = mes;
    }
}
