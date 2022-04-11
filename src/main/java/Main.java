import view.SegmentView;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DIConfig context = new DIConfig();
        context.injectObjects();
        SegmentView.getInstance().run();
    }
}
