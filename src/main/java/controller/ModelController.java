package controller;

import controller.exception.logicalException.InvalidInputException;
import controller.exception.logicalException.RepeatedInputException;
import controller.exception.modelingException.InputInOutputException;
import controller.exception.modelingException.InsufficientInputException;
import controller.exception.modelingException.WireNotDefinedException;
import model.Memory;
import model.Pair;
import model.Wire;
import model.logicGates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModelController implements Controller {
    private static final ModelController instance = new ModelController();
    private Memory memory;

    private List<Gate> newGates;
    private HashMap<String, Wire> newInputs;


    public static void config(Memory memory) {
        instance.memory = memory;
    }

    public static ModelController getInstance() {
        return instance;
    }

    public void defineWire(String name, int line) {
        try {
            tryGetWire(name, line);
            throw new RepeatedInputException(line);
        } catch (WireNotDefinedException wireNotDefinedException){
            Wire wire = new Wire(name, false);
            newInputs.put(name, wire);
        }
    }

    public void createAndGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output, line);
        Wire[] wireInputs = getWireInputs(inputs, "AND",line);
        int intDelay = Integer.parseInt(delay);

        checkForInputInOutputException(inputs, output, "AND", line);

        Gate gate = new AndGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    public void createOrGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output, line);
        Wire[] wireInputs = getWireInputs(inputs, "OR", line);
        int intDelay = Integer.parseInt(delay);

        checkForInputInOutputException(inputs, output, "OR", line);

        Gate gate = new OrGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    private void checkForInputInOutputException(String[] inputs, String output, String gate, int line) {
        if (Arrays.asList(inputs).contains(output)) {
            throw new InputInOutputException(gate, output, line);
        }
    }

    public void createNandGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output, line);
        Wire[] wireInputs = getWireInputs(inputs, "NAND", line);
        int intDelay = Integer.parseInt(delay);

        checkForInputInOutputException(inputs, output, "NAND", line);

        Gate gate = new NandGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    public void createXorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output, line);
        Wire[] wireInputs = getWireInputs(inputs, "XOR", line);
        int intDelay = Integer.parseInt(delay);

        checkForInputInOutputException(inputs, output, "XOR", line);

        Gate gate = new XorGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    public void createNorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output, line);
        Wire[] wireInputs = getWireInputs(inputs, "NOR", line);
        int intDelay = Integer.parseInt(delay);

        checkForInputInOutputException(inputs, output, "NOR", line);

        Gate gate = new NorGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    public void updateValue(String wireName, String time, String value, int line) {
        int intTime = Integer.parseInt(time);
        int intValue = Integer.parseInt(value);
        if (intValue > 1) {
            throw new InvalidInputException(line);
        }
        boolean boolVal = value.equals("1");

        Wire wire = tryGetWire(wireName, line);
        memory.getChangeStatusMap().put(intTime, new Pair<>(wire, boolVal));
    }

    private Wire[] getWireInputs(String[] inputs, String gate, int line) {
        Wire[] wireInputs = new Wire[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            wireInputs[i] = tryGetWire(inputs[i], line);
        }
        if (inputs.length < 2) {
            throw new InsufficientInputException(gate, line);
        }
        return wireInputs;
    }

    private Wire tryGetWire(String wireName, int line) {
        Wire wire = memory.getNameWireMap().get(wireName);
        if (wire == null) wire = newInputs.get(wireName);
        if (wire == null) {
            throw new WireNotDefinedException(wireName, line);
        }
        return wire;
    }

    @Override
    public void track() {
        newInputs = new HashMap<>();
        newGates = new ArrayList<>();
    }

    @Override
    public void unTrack() {
        memory.getNameWireMap().putAll(newInputs);
        memory.getGates().addAll(newGates);
    }

    @Override
    public void undo() {
        newInputs.clear();
        newGates.clear();
    }
}
