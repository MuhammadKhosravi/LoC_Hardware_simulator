package model.logicGates;

import model.Wire;
import model.logicGates.Gate;

public class NorGate extends Gate {

    public NorGate(Wire output, int delay, Wire... inputs) {
        super(output, delay, inputs);
    }

    @Override
    public void calculateOutput() {
        boolean outputValue = false;
        for (Wire input : getInputs()) {
            outputValue = (outputValue | input.isValue());
        }
        getOutput().setValue(!outputValue);
    }

    @Override
    public String toString() {
        return "NOR";
    }
}
