package view;

import controller.SimulatorController;
import model.HelpType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimulateView implements View {

    SimulatorController controller = SimulatorController.getInstance();

    @Override
    public void run() {
        Statics.help(HelpType.SIM_VIEW);
        List<String> commands = new ArrayList<>();
        Scanner scanner = Statics.getScanner();
        getInputs(scanner, commands);

        controller.track();

        execute(commands);

        controller.unTrack();
    }

    @Override
    public boolean showErrors(List<Exception> exceptions) {
        return false;
    }

    @Override
    public void getInputs(Scanner scanner, List<String> commands) {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.equals(Statics.SIM_FINISH)) {
                break;
            }
            commands.add(input);
        }
    }

    @Override
    public void execute(List<String> commands) {

    }
}
