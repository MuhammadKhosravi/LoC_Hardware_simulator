package controller;

import controller.exception.logicalException.InvalidInputException;
import model.Memory;
import model.Wire;

import java.util.HashMap;

public class InitController implements Controller {
    private static final InitController instance = new InitController();
    private Memory memory;
    private HashMap<String, Wire> newInputs;

    public static void config(Memory memory) {
        instance.memory = memory;
    }

    public static InitController getInstance() {
        return instance;
    }

    public void defineInput(String name, String value, int line) {
        int intValue = Integer.parseInt(value);
        if (intValue > 1) {
            throw new InvalidInputException(line);
        }
        Wire wire = new Wire(name, true);
        wire.setValue(intValue == 1);
        newInputs.put(name, wire);
    }


    @Override
    public void track() {
        newInputs = new HashMap<>();
    }

    @Override
    public void unTrack() {
        memory.getNameWireMap().putAll(newInputs);
        newInputs = null;
    }

    @Override
    public void undo() {
        newInputs.clear();
    }
}
