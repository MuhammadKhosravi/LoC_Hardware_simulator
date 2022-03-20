package controller;

import controller.exception.logicalException.InvalidInputException;
import controller.exception.logicalException.RepeatedInputException;
import model.Memory;
import model.Pair;
import model.Wire;
import model.logicGates.*;

import java.util.ArrayList;
import java.util.List;

public class ModelController implements Controller {
    private static final ModelController instance = new ModelController();
    private List<Gate> buffer;
    private List<String> newInputs;

    private Memory memory;

    public static void config(Memory memory) {
        instance.memory = memory;
    }

    public static ModelController getInstance() {
        return instance;
    }

    private void defineWire(String name, int line) {
        if (memory.getNameWireMap().containsKey(name)) {
            throw new RepeatedInputException(line);
        }
        Wire wire = new Wire(name, false);
        memory.getNameWireMap().put(name, wire);
        newInputs.add(name);
    }

    public void createAndGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new AndGate(wireOutput, intDelay, wireInputs);
        buffer.add(gate);
    }

    public void createOrGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new OrGate(wireOutput, intDelay, wireInputs);
        buffer.add(gate);
    }

    public void createNandGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new NandGate(wireOutput, intDelay, wireInputs);
        buffer.add(gate);
    }

    public void createXorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new XorGate(wireOutput, intDelay, wireInputs);
        buffer.add(gate);
    }

    public void createNorGate(String output, String delay, String[] inputs, int line) {
        Wire wireOutput = memory.getNameWireMap().get(output);
        Wire[] wireInputs = getWireInputs(inputs);
        int intDelay = Integer.parseInt(delay);
        Gate gate = new NorGate(wireOutput, intDelay, wireInputs);
        buffer.add(gate);
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
        newInputs = new ArrayList<>();
        buffer = new ArrayList<>();
    }

    @Override
    public void unTrack() {

    }

    @Override
    public void undo() {

    }
}
