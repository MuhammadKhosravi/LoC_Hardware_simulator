import view.SegmentView;

public class Main {
    public static void main(String[] args) {
        DIConfig context = new DIConfig();
        context.injectObjects();
        SegmentView.getInstance().run();
    }

}
