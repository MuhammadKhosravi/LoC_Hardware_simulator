package controller;

import controller.exception.NotModelingTimeException;
import model.*;
import view.Regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class ProgramController {
    private final static ProgramController instance = new ProgramController();
    private final HashMap<String, Wire> nameWireHashmap = new HashMap<>();
    private final ArrayList<Gate> gates = new ArrayList<>();
    private boolean is_modeling = false;
    private boolean is_init = false;
    private boolean finish = false;

    public static ProgramController getInstance() {
        return instance;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Consumer<Matcher>> regexHashmap = getRegexHashmap();
        while (!finish) {
            String inputText = scanner.nextLine();
            for (String command : regexHashmap.keySet()) {
                Matcher matcher = Regex.getInstance().getMatcher(inputText, command);
                if (matcher.find()) {
                    regexHashmap.get(command).accept(matcher);
                }
            }
            System.out.println(gates);
            System.out.println(nameWireHashmap.keySet());
        }

    }

    private HashMap<String, Consumer<Matcher>> getRegexHashmap() {
        HashMap<String, Consumer<Matcher>> regexHashmap = new HashMap<>();


        regexHashmap.put(Regex.getInstance().getGateRegex("and"), this::addAndGate);
        regexHashmap.put(Regex.getInstance().getGateRegex("or"), this::addOrGate);
        regexHashmap.put(Regex.getInstance().getGateRegex("xor"), this::addXorGate);
        regexHashmap.put(Regex.getInstance().getGateRegex("nand"), this::addNandGate);
        regexHashmap.put(Regex.getInstance().getGateRegex("nor"), this::addNorGate);
        regexHashmap.put(Regex.getInstance().getGateRegex("not"), this::addNotGate);

        regexHashmap.put(Regex.getInstance().DEF_WIRE, this::defineWire);

        regexHashmap.put(Regex.getInstance().START_MODEL, this::startModeling);
        regexHashmap.put(Regex.getInstance().FINISH_MODEL, this::finishModeling);

        regexHashmap.put(Regex.getInstance().INIT_START, this::startInit);
        regexHashmap.put(Regex.getInstance().INIT_FINISH, this::finishInit);
        regexHashmap.put(Regex.getInstance().INIT_VALUE, this::setInitValue);

        regexHashmap.put(Regex.getInstance().UPDATE, this::updateValue);

        regexHashmap.put(Regex.getInstance().END, this::end);

        return regexHashmap;
    }

    private void updateValue(Matcher matcher) {
        Wire wire = nameWireHashmap.get(matcher.group("name"));
        boolean value = matcher.group("value").equals("1");
        int time = Integer.parseInt(matcher.group("time"));
        // TODO: somehow change the value at given time!
    }

    private void end(Matcher matcher) {
        finish = true;
    }

    private void setInitValue(Matcher matcher) {
        if (!is_init) {
            // TODO: needs to be implemented
            System.out.println("Error");
            return;
        }
        Wire wire = nameWireHashmap.get(matcher.group("name"));
        boolean value = matcher.group("value").equals("1");
        wire.setValue(value);
    }

    private void finishInit(Matcher matcher) {
        is_init = false;
    }

    private void startInit(Matcher matcher) {
        is_init = true;
    }

    private Gate createGate(Matcher matcher, String type) throws NotModelingTimeException {
        if (!is_modeling) throw new NotModelingTimeException();
        Wire output = getOutputWire(matcher);
        Wire[] inputs = getInputWires(matcher);
        short delay = getDelay(matcher);
        switch (type) {
            case "and":
                return new AndGate(output, delay, inputs);
            case "or":
                return new OrGate(output, delay, inputs);
            case "xor":
                return new XorGate(output, delay, inputs);
            case "nand":
                return new NandGate(output, delay, inputs);
            case "nor":
                return new NorGate(output, delay, inputs);
            case "not":
                return new NotGate(output, delay, inputs);
            default:
                return null;
        }
    }

    private void addNotGate(Matcher matcher) {
        try {
            gates.add(createGate(matcher, "not"));
        } catch (NotModelingTimeException e) {
        }

    }

    private void addNorGate(Matcher matcher) {
        try {
            gates.add(createGate(matcher, "nor"));
        } catch (NotModelingTimeException e) {

        }

    }

    private void addNandGate(Matcher matcher) {
        try {
            gates.add(createGate(matcher, "nand"));
        } catch (NotModelingTimeException e) {

        }

    }

    private void addXorGate(Matcher matcher) {
        try {
            gates.add(createGate(matcher, "xor"));
        } catch (NotModelingTimeException e) {

        }

    }

    private void defineWire(Matcher matcher) {
        String name = matcher.group("name");
        Wire wire = new Wire(name);
        nameWireHashmap.put(name, wire);
    }

    private void addAndGate(Matcher matcher) {
        try {
            gates.add(createGate(matcher, "and"));
        } catch (NotModelingTimeException e) {

        }
    }

    private void addOrGate(Matcher matcher) {
        try {
            gates.add(createGate(matcher, "or"));
        } catch (NotModelingTimeException e) {

        }
    }

    private short getDelay(Matcher matcher) {
        return Short.parseShort(matcher.group("delay"));
    }

    private Wire[] getInputWires(Matcher matcher) {
        String[] inputsName = matcher.group("inputs").split(", ");
        Wire[] inputs = new Wire[inputsName.length];
        for (int i = 0; i < inputsName.length; i++) {
            inputs[i] = nameWireHashmap.get(inputsName[i]);
        }
        return inputs;
    }

    private Wire getOutputWire(Matcher matcher) {
        return nameWireHashmap.get(matcher.group("output"));
    }

    private void finishModeling(Matcher matcher) {
        is_modeling = false;
    }

    private void startModeling(Matcher matcher) {
        is_modeling = true;
    }
}
