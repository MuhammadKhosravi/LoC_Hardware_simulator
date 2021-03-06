package model.logicGates;


import model.Wire;

import java.util.Map;
import java.util.TreeMap;

public abstract class Gate {
    private final Wire[] inputs;
    private final Wire output;

    // time is measured in milliseconds
    private final int delay;
    private final Map<Integer, Boolean> gateTimeLine;


    public Gate(Wire output, int delay, Wire... inputs) {
        this.output = output;
        this.delay = delay;
        this.inputs = inputs;
        this.gateTimeLine = new TreeMap<>();
    }

    public abstract void calculateOutput();

    @Override
    public abstract String toString();

    public int getDelay() {
        return delay;
    }

    public Wire getOutput() {
        return output;
    }

    public Wire[] getInputs() {
        return inputs;
    }

    public Map<Integer, Boolean> getGateTimeLine() {
        return gateTimeLine;
    }

    public boolean doesHaveDirectInput(String trigger) {
        for (Wire input : inputs) {
            if (input.toString().equals(trigger))
                return true;
        }
        return false;
    }

    public void clear() {
        gateTimeLine.clear();
    }
}
