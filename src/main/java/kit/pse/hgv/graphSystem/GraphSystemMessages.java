package kit.pse.hgv.graphSystem;

public enum GraphSystemMessages {
    TO_MANY_ELEMENTS("Es wurden zu viele GraphElemente erstellt!", "There are to much elements existing!"),
    EDGE_ONLY_WITH_NODES("Eine Kante kann nur mit Knoten verbunden werden!",
            "An edge can only be connected with nodes!"),
    MAX_NODES_EDGE("Eine Kante kann nur mit 2 Knoten verbunden werden!",
            "An edge can only be connected with two nodes!"),
    PATH_ERROR("Der Pfad ist ungültig oder das Format wurde nicht erkannt!",
            "The path is not existing or the format is not supportet!"),
    NODE_MISSING("Ein Knoten existiert nicht!",
            "One node is not existing!");

    private final String DE;
    private final String EN;

    private GraphSystemMessages(String de, String en) {
        EN = en;
        this.DE = de;
    }

    public String DE() {
        return DE;
    }

    public String EN() {
        return EN;
    }
}
