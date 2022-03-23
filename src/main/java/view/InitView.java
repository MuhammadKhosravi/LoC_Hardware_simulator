package view;

import controller.InitController;
import controller.exception.BadSyntaxException;
import controller.exception.logicalException.LogicalException;
import model.HelpType;
import model.Instructions.InitInstruction;
import model.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class InitView implements View {
    private static final InitView instance = new InitView();
    private final InitController controller = InitController.getInstance();


    public static InitView getInstance() {
        return instance;
    }

    public void run() {
        Statics.help(HelpType.INIT_VIEW);

        Scanner scanner = Statics.getScanner();
        List<String> commands = new ArrayList<>();
        getInputs(scanner, commands);

        controller.track();

        execute(commands);

        controller.unTrack();

    }

    public void getInputs(Scanner scanner, List<String> commands) {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.equals(Statics.INIT_FINISH)) {
                break;
            }
            commands.add(input);
        }
    }

    public void execute(List<String> commands) {
        List<Exception> exceptions = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            boolean isValid = false;
            for (Pair<String, InitInstruction> initInstruction : Statics.INIT_INSTRUCTIONS) {
                Matcher matcher = Statics.getMatcher(commands.get(i), initInstruction.getKey());
                if (matcher.find()) {
                    findCommand(exceptions, i, initInstruction, matcher);
                    isValid = true;
                    break;
                }
            }
            if (!isValid) exceptions.add(new BadSyntaxException(i + 1));
        }
        if (showErrors(exceptions)) controller.undo();
    }


    private void findCommand(List<Exception> exceptions, int line, Pair<String, InitInstruction> initInstruction, Matcher matcher) {
        if (initInstruction.getValue() == InitInstruction.INIT_VALUE) {
            try {
                controller.defineInput(matcher.group("name"), matcher.group("value"), line + 1);
            } catch (LogicalException e) {
                exceptions.add(e);
            }
        }
    }

    public boolean showErrors(List<Exception> exceptions) {
        if (exceptions.size() == 0) return false;
        System.out.println("initializing failed!");
        for (Exception exception : exceptions) {
            System.out.println(exception.getMessage());
        }
        return true;
    }
}
