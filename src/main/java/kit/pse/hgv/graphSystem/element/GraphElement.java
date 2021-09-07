package kit.pse.hgv.graphSystem.element;

import kit.pse.hgv.graphSystem.GraphSystemMessages;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;

/**
 * This is an superclass fo an element of a graph. It manages id and metadata.
 * <p>
 * Every element has a unique integer id which cannot be changed.
 */
public abstract class GraphElement {

    private static int idCounter = 1;
    private final int id;

    private final HashMap<String, String> metadata = new HashMap<>();

    /**
     * Constructor enshures that the element has an unique id. Id overflow will
     * throw exception.
     *
     * @throws OverflowException is thrown when the id is reaching MAX_INT value and
     *                           is overflowing.
     */
    public GraphElement() throws OverflowException {
        if (idCounter == Integer.MIN_VALUE) {
            throw new OverflowException(GraphSystemMessages.TO_MANY_ELEMENTS.DE());
        }
        id = idCounter++;
    }

    /**
     * this gets u the unique id of the element.
     *
     * @return Returns an integer id of the element.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the metadata String at the key.
     *
     * @param key  is the key where you want to store your metadata String.
     * @param meta is the Data you want to store at the key.
     */
    public void setMetadata(String key, String meta) {
        metadata.put(key, meta);
    }

    /**
     * Gets u the metadata stored at the key.
     *
     * @param key is the String key of the metadata you want to get.
     * @return Returns the String stored at the key. Could be null i no key or no
     * String stored.
     */
    public String getMetadata(String key) {
        return metadata.get(key);
    }

    public Collection<String> getAllMetadata() {
        return metadata.keySet();
    }

    public JSONObject getAllMetaDataAsJSON() {
        JSONObject meta = new JSONObject();
        for (String key : metadata.keySet()) {
            if (!(key.equals("r") || key.equals("phi"))) {
                meta.put(key, getMetadata(key));
            }
        }
        return meta;
    }
}
