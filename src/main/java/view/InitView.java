package view;

import controller.InitController;
import controller.exception.BadSyntaxException;
import controller.exception.logicalException.LogicalException;
import model.HelpType;
import model.InitInstruction;
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
        List<Exception> exceptions = new ArrayList<>();

        getInputs(scanner, commands);
        controller.track();
        readInitBlock(commands, exceptions);

    }

    private void readInitBlock(List<String> commands, List<Exception> exceptions) {
        for (int i = 0; i < commands.size(); i++) {
            boolean isValid = false;
            for (Pair<String, InitInstruction> initInstruction : Statics.getInitInstructions()) {
                Matcher matcher = Statics.getInstance().getMatcher(commands.get(i), initInstruction.getKey());
                if (matcher.find()) {
                    findCommand(exceptions, i, initInstruction, matcher);
                    isValid = true;
                    break;
                }
            }
            if (!isValid) exceptions.add(new BadSyntaxException(i + 1));
        }
    }

    private void getInputs(Scanner scanner, List<String> commands) {
        String input;
        boolean finish = false;
        while (!finish) {
            input = scanner.nextLine().trim();
            if (input.equals("finish init")) {
                finish = true;
            }
            commands.add(input);
        }
    }

    private void findCommand(List<Exception> exceptions, int line, Pair<String, InitInstruction> initInstruction, Matcher matcher) {
        switch (initInstruction.getValue()) {
            case INIT_VALUE -> {
                try {
                    controller.defineInput(matcher.group("name"), matcher.group("value") , line + 1);
                } catch (LogicalException e) {
                    exceptions.add(e);
                }
            }
            case INIT_FINISH -> {
                if (showErrors(exceptions)) {
                    controller.undo();
                } else {
                    controller.unTrack();
                }
            }
        }
    }

    private boolean showErrors(List<Exception> exceptions) {
        if (exceptions.size() == 0) return false;
        System.out.println("Execution failed!");
        for (Exception exception : exceptions) {
            System.out.println(exception.getMessage());
        }
        return true;
    }
}
