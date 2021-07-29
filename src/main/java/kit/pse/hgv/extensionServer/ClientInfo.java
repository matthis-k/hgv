package kit.pse.hgv.extensionServer;
public class ClientInfo {
    private String name = new String();
    private String desc = new String();

    void setName(String name) {
        this.name = name;
    }
    void setDescription(String desc) {
        this.desc = desc;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return desc;
    }
}
