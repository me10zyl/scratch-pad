package logic;

/**
 * Created by ZyL on 2016/6/3.
 */
public class IndexManager {
    private static int index = 0;
    private static final String name = "标签";

    public static String next() {
        return name + index++;
    }

    public static int getIndex() {
        return index;
    }
    public static void setIndex(int index) {
        IndexManager.index = index;
    }
}
