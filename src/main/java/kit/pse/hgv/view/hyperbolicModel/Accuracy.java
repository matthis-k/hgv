package kit.pse.hgv.view.hyperbolicModel;

public enum Accuracy {
    DIRECT(1),
    LOW(10),
    MEDIUM(50),
    HIGH(100);
    private int accuracy;
    private Accuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getAccuracy() {
        return accuracy;
    }
}
