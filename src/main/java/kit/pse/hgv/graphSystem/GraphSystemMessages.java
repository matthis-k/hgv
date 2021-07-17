package kit.pse.hgv.graphSystem;

public enum GraphSystemMessages {
    TO_MANY_ELEMENTS("Es wurden zu viele GraphElemente erstellt");

    private String mes;
    private GraphSystemMessages(String mes) {
        this.mes = mes;
    }
    public String getMessage() {
        return mes;
    }
}
