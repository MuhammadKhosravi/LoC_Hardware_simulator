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
    private HashMap<Wire, Gate> wireSrcMap;


    public static void config(Memory memory) {
        instance.memory = memory;
    }

    public static ModelController getInstance() {
        return instance;
    }

    public void defineWire(String name, int line) {
        if (memory.getNameWireMap().containsKey(name)) {
            throw new RepeatedInputException(line);
        }
        Wire wire = new Wire(name, false);
        newInputs.put(name, wire);
    }

    public void createAndGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new AndGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
    }

    public void createOrGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new OrGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
    }

    public void createNandGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new NandGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
    }

    public void createXorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new XorGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
    }

    public void createNorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new NorGate(wireOutput, intDelay, wireInputs);
        newGates.add(gate);
    }

    public void updateValue(String wireName, String time, String value, int line) {

        Wire wire = memory.getNameWireMap().get(wireName);
        int intTime = Integer.parseInt(time);
        int intValue = Integer.parseInt(value);
        if (intValue > 1) {
            throw new InvalidInputException(line);
        }
        boolean boolVal = value.equals("1");
        memory.getChangeStatusMap().put(intTime, new Pair<Wire, Boolean>(wire, boolVal));
        //TODO add undo service
    }

    private Wire[] getWireInputs(String[] inputs) {
        Wire[] wireInputs = new Wire[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            wireInputs[i] = memory.getNameWireMap().get(inputs[i]);
        }
        return wireInputs;
    }

    @Override
    public void track() {
        newInputs = new HashMap<>();
        newGates = new ArrayList<>();
        wireSrcMap = new HashMap<>();
    }

    @Override
    public void unTrack() {
        memory.getNameWireMap().putAll(newInputs);
        memory.getGates().addAll(newGates);
        memory.getWireSrcMap().putAll(wireSrcMap);
    }

    @Override
    public void undo() {
        newInputs.clear();
        newGates.clear();
        wireSrcMap.clear();
    }
}
