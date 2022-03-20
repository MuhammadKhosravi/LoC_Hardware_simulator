/*
package controller;

import controller.exception.ModelingErrorException;
import model.*;
import model.logicGates.*;
import view.Statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class ProgramController {
    private final static ProgramController instance = new ProgramController();
    private final HashMap<String, Wire> nameWireHashmap = new HashMap<>();
    private List<RuntimeException> errors = new ArrayList<>();

    private boolean is_modeling = false;
    private boolean is_init = false;
    private boolean finish = false;

    public static ProgramController getInstance() {
        return instance;
    }

    public List<Gate> run(List<String> inputs) {
        final List<Gate> gates = new ArrayList<>();
        HashMap<String, Consumer<Matcher>> regexHashmap = getRegexHashmap();
        for (String input : inputs) {
            for (String command : regexHashmap.keySet()) {
                Matcher matcher = Statics.getInstance().getMatcher(input, command);
                if (matcher.find()) {
                    regexHashmap.get(command).accept(matcher);
                }
            }
        }
    }

    private HashMap<String, Consumer<Matcher>> getRegexHashmap() {
        HashMap<String, Consumer<Matcher>> regexHashmap = new HashMap<>();


        regexHashmap.put(Statics.getInstance().getGateRegex("and"), this::addAndGate);
        regexHashmap.put(Statics.getInstance().getGateRegex("or"), this::addOrGate);
        regexHashmap.put(Statics.getInstance().getGateRegex("xor"), this::addXorGate);
        regexHashmap.put(Statics.getInstance().getGateRegex("nand"), this::addNandGate);
        regexHashmap.put(Statics.getInstance().getGateRegex("nor"), this::addNorGate);
        regexHashmap.put(Statics.getInstance().getGateRegex("not"), this::addNotGate);

        regexHashmap.put(Statics.getInstance().DEF_WIRE, this::defineWire);

        regexHashmap.put(Statics.getInstance().MODEL_START, this::startModeling);
        regexHashmap.put(Statics.getInstance().MODEL_FINISH, this::finishModeling);

        regexHashmap.put(Statics.getInstance().INIT_START, this::startInit);
        regexHashmap.put(Statics.getInstance().INIT_FINISH, this::finishInit);
        regexHashmap.put(Statics.getInstance().INIT_VALUE, this::setInitValue);

        regexHashmap.put(Statics.getInstance().UPDATE, this::updateValue);

        regexHashmap.put(Statics.getInstance().END, this::end);

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

    private Gate createGate(Matcher matcher, String type) throws ModelingErrorException {
        if (!is_modeling) throw new ModelingErrorException();
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

    private Gate addNotGate(Matcher matcher) {
        try {
            return createGate(matcher, "not");
        } catch (ModelingErrorException e) {
            errors.add(e);
        }

    }

    private Gate addNorGate(Matcher matcher) {
        try {
            return createGate(matcher, "nor");
        } catch (ModelingErrorException e) {

        }

    }

    private Gate addNandGate(Matcher matcher) {
        try {
            return createGate(matcher, "nand");
        } catch (ModelingErrorException e) {

        }

    }

    private Gate addXorGate(Matcher matcher) {
        try {
            return createGate(matcher, "xor");
        } catch (ModelingErrorException e) {

        }

    }

    private Gate addAndGate(Matcher matcher) {
        try {
            return createGate(matcher, "and");
        } catch (ModelingErrorException e) {

        }
    }

    private Gate addOrGate(Matcher matcher, List<Gate> gates) {
        try {
            return createGate(matcher, "or");
        } catch (ModelingErrorException e) {

        }
    }


    private void defineWire(Matcher matcher) {
        String name = matcher.group("name");
        Wire wire = new Wire(name);
        nameWireHashmap.put(name, wire);
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
*/
