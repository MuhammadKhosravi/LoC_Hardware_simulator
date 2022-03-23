package view;

import model.HelpType;

import java.util.List;
import java.util.Scanner;

public class SimulateView implements View {
    @Override
    public void run() {
        Statics.help(HelpType.SIM_VIEW);
    }

    @Override
    public boolean showErrors(List<Exception> exceptions) {
        return false;
    }

    @Override
    public void getInputs(Scanner scanner, List<String> commands) {

    }

    @Override
    public void execute(List<String> commands) {

    }
}
