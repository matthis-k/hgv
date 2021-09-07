package kit.pse.hgv.graphSystem;

public class MetadataDefinition {

    private String name;
    private String defaultValue;
    private final String dataType;
    private MetadataType metadataType;

    public MetadataDefinition(String name, String defaultValue, MetadataType metadataType, String dataType) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.metadataType = metadataType;
        this.dataType = dataType;
    }

    public MetadataDefinition(String name, MetadataType metadataType, String dataType) {
        this.name = name;
        this.metadataType = metadataType;
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public MetadataType getMetadataType() {
        return metadataType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMetadataType(MetadataType metadataType) {
        this.metadataType = metadataType;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
