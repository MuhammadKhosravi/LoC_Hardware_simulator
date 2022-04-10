package view;

import controller.ModelController;
import controller.exception.BadSyntaxException;
import model.HelpType;
import model.Instructions.ModelingInstruction;
import model.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ModelingView implements View {

    private static final ModelingView instance = new ModelingView();
    private final ModelController controller = ModelController.getInstance();


    public static ModelingView getInstance() {
        return instance;
    }

    @Override
    public void run() {
        Statics.help(HelpType.MODEL_VIEW);
        controller.track();

        List<String> commands = new ArrayList<>();
        Scanner scanner = Statics.getScanner();
        getInputs(scanner, commands);
        execute(commands);

        controller.unTrack();
    }

    public void getInputs(Scanner scanner, List<String> commands) {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.equals(Statics.MODEL_FINISH)) {
                break;
            }
            commands.add(input);
        }
    }

    @Override
    public void execute(List<String> commands) {
        List<Exception> exceptions = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            boolean isValid = false;
            for (Pair<String, ModelingInstruction> modelingInstruction : Statics.MODELING_INSTRUCTIONS) {
                Matcher matcher = Statics.getMatcher(commands.get(i), modelingInstruction.getKey());
                if (matcher.find()) {
                    findCommand(exceptions, i, modelingInstruction, matcher);
                    isValid = true;
                    break;
                }
            }
            if (!isValid) exceptions.add(new BadSyntaxException(i + 1));
        }
    }

    private void findCommand(List<Exception> exceptions, int line, Pair<String, ModelingInstruction> modelingInstruction,
                             Matcher matcher) {
        try {
            switch (modelingInstruction.getValue()) {
                case DEF_WIRE -> controller.defineWire(matcher.group("name"),
                        line);
                case AND -> controller.createAndGate(matcher.group("output"),
                        matcher.group("delay"),
                        matcher.group("inputs").split("\\s*,\\s*"), line);


                case OR -> controller.createOrGate(matcher.group("output"),
                        matcher.group("delay"),
                        matcher.group("inputs").split("\\s*,\\s*"), line);

                case NOR -> controller.createNorGate(matcher.group("output"),
                        matcher.group("delay"),
                        matcher.group("inputs").split("\\s*,\\s*"), line);

                case NAND -> controller.createNandGate(matcher.group("output"),
                        matcher.group("delay"),
                        matcher.group("inputs").split("\\s*,\\s*"), line);

                case XOR -> controller.createXorGate(matcher.group("output"),
                        matcher.group("delay"),
                        matcher.group("inputs").split("\\s*,\\s*"), line);

                case UPDATE -> controller.updateValue(matcher.group("name"),
                        matcher.group("time"),
                        matcher.group("value"), line);
            }
        } catch (Exception e) {
            exceptions.add(e);
        }
    }

    @Override
    public boolean showErrors(List<Exception> exceptions) {
        if (exceptions.size() == 0) return false;
        System.out.println("modeling failed!");
        for (Exception exception : exceptions) {
            System.out.println(exception.getMessage());
        }
        return true;
    }
}
