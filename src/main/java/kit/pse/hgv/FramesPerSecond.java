package kit.pse.hgv;

import java.util.ArrayList;
import java.util.List;

public class FramesPerSecond {
    public static List<Long> milliSeconds = new ArrayList<>();
    public static long start = 0;

    public static void addMilliseconds(long milliSecond) {
        if(milliSeconds.size() == 10) {
            long delta = milliSecond - milliSeconds.get(0);
            double temp = ((double) 1000.0) / (double) delta;
            System.out.println("FPS: " + temp * 10);
            milliSeconds.clear();
        }
        milliSeconds.add(milliSecond);
    }

    public static void setStart(long milliSecond) {
        start = milliSecond;
    }

    public static void setEnd(long milliSecond) {
        System.out.println(milliSecond - start);
    }

    public static void printCurrent(long milliSecond) {
        milliSeconds.add(milliSecond);
        long delta = milliSecond - milliSeconds.get(0);
        double temp = ((double) 1000.0) / (double) delta;
        System.out.println("FPS: " + temp * milliSeconds.size());
        milliSeconds.clear();
    }

}
