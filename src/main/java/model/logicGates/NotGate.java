package model.logicGates;

import model.Wire;
import model.logicGates.Gate;

public class NotGate extends Gate {

    public NotGate(Wire output, int delay, Wire... inputs) {
        super(output, delay, inputs);
    }

    @Override
    public void calculateOutput() {
        getOutput().setValue(!getOutput().isValue());
    }

    @Override
    public String toString() {
        return "NOT";
    }
}
