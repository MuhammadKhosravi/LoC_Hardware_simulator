package controller;

import controller.exception.logicalException.InvalidInputException;
import controller.exception.logicalException.RepeatedInputException;
import model.Memory;
import model.Wire;

import java.util.ArrayList;
import java.util.List;

public class InitController implements Controller {
    private static final InitController instance = new InitController();
    private Memory memory;
    private List<String> newInputs;

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
        if (memory.getNameWireMap().containsKey(name)) {
            throw new RepeatedInputException(line);
        }
        Wire wire = new Wire(name, true);
        memory.getNameWireMap().put(name, wire);
        wire.setValue(intValue == 1);
        newInputs.add(name);
    }


    @Override
    public void track() {
        newInputs = new ArrayList<>();
    }

    @Override
    public void unTrack() {
        newInputs = null;
    }

    @Override
    public void undo() {
        newInputs.forEach(in -> memory.getNameWireMap().remove(in));
    }
}
