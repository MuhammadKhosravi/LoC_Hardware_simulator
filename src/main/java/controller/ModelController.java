package controller;

import controller.exception.logicalException.InvalidInputException;
import controller.exception.logicalException.RepeatedInputException;
import model.Memory;
import model.Pair;
import model.Wire;
import model.logicGates.*;

import java.util.ArrayList;
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
        if (tryGetWire(name) != null) {
            throw new RepeatedInputException(line);
        }
        Wire wire = new Wire(name, false);
        newInputs.put(name, wire);
    }

    //TODO implement exceptions
    public void createAndGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);

        Gate gate = new AndGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    //TODO implement exceptions
    public void createOrGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);

        Gate gate = new OrGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    //TODO implement exceptions
    public void createNandGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);

        Gate gate = new NandGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    //TODO implement exceptions
    public void createXorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);

        Gate gate = new XorGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    //TODO implement exceptions
    public void createNorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = tryGetWire(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);

        Gate gate = new NorGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
        wireOutput.setSource(gate);
    }

    //TODO implement exceptions
    public void updateValue(String wireName, String time, String value, int line) {
        int intTime = Integer.parseInt(time);
        int intValue = Integer.parseInt(value);
        if (intValue > 1) {
            throw new InvalidInputException(line);
        }
        boolean boolVal = value.equals("1");

        Wire wire = tryGetWire(wireName);
        memory.getChangeStatusMap().put(intTime, new Pair<>(wire, boolVal));
    }

    private Wire[] getWireInputs(String[] inputs) {
        Wire[] wireInputs = new Wire[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            wireInputs[i] = tryGetWire(inputs[i]);
        }
        return wireInputs;
    }

    private Wire tryGetWire(String wireName) {
        Wire wire = memory.getNameWireMap().get(wireName);
        if (wire == null) wire = newInputs.get(wireName);
        //ToDO throw execution for not found wire
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
