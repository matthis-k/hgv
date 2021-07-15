package kit.pse.hgv.graphSystem;

import java.util.HashMap;

public abstract class GraphElement {
    private int id = -1;
    // TODO: Metadata
    private HashMap<String, String> metadata = new HashMap<>();
    public void setMetadata(String key, String value) {
        metadata.put(key, value);
    }
    public String getMetadata(String key) {
        return metadata.get(key);
    }

    void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
