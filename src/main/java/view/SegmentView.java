package view;

import model.HelpType;

import java.util.Scanner;

public class SegmentView implements View {
    private static final View segmentView = new SegmentView();

    private final View initView = InitView.getInstance();
    private final View modelingView = new ModelingView();
    private final View simulateVie = new SimulateView();

    public static View getInstance() {
        return segmentView;
    }

    public void run() {
        boolean finish = false;
        Statics.help(HelpType.SEGMENT_VIEW);

        Scanner scanner = Statics.getScanner();
        while (!finish) {
            String command = scanner.nextLine().trim();
            switch (command) {
                case Statics.INIT_START -> initView.run();
                case Statics.MODEL_START -> modelingView.run();
                case Statics.SIM_START -> simulateVie.run();
                case Statics.END -> finish = true;
                default -> Statics.help(HelpType.SEGMENT_VIEW);
            }
        }
    }
}
