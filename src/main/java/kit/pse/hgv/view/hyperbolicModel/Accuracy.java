package kit.pse.hgv.view.hyperbolicModel;

public enum Accuracy {
    DIRECT(1), LOW(10), MEDIUM(25), HIGH(50);

    private int accuracy;

    private Accuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getAccuracy() {
        return accuracy;
    }
}
