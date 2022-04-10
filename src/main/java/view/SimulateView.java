package view;

import controller.SimulatorController;
import controller.exception.BadSyntaxException;
import model.HelpType;
import model.Instructions.SimulateInstruction;
import model.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SimulateView implements View {

    SimulatorController controller = SimulatorController.getInstance();

    @Override
    public void run() {
        Statics.help(HelpType.SIM_VIEW);
        controller.track();

        List<String> commands = new ArrayList<>();
        Scanner scanner = Statics.getScanner();
        getInputs(scanner, commands);
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
        List<RuntimeException> exceptions = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            boolean isValid = false;

            for (Pair<String, SimulateInstruction> simulateInstruction : Statics.SIMULATE_INSTRUCTIONS) {
                Matcher matcher = Statics.getMatcher(commands.get(i), simulateInstruction.getKey());
                if (matcher.find()) {
                    try {
                        if (simulateInstruction.getValue() == SimulateInstruction.SIMULATE_INSTRUCTION) {
                            ArrayList<Integer> answer = controller.sim(matcher.group("wire"),
                                    matcher.group("start"),
                                    matcher.group("finish"),
                                    matcher.group("step"));
                            System.out.println(answer);
                        }
                    } catch (RuntimeException e) {
                        exceptions.add(e);
                    }
                }
            }
            if (!isValid) exceptions.add(new BadSyntaxException(i + 1));
        }
    }
}
