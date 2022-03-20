package model.logicGates;

import model.Wire;
import model.logicGates.Gate;

public class XorGate extends Gate {
    public XorGate(Wire output, int delay, Wire... inputs) {
        super(output, delay, inputs);
    }

    @Override
    public void calculateOutput() {
        Wire outputWire = getOutput();
        boolean outputValue = false;
        for (int i = 0; i < getInputs().length; i++) {
            outputValue = (outputValue ^ getInputs()[i].isValue());
        }
        outputWire.setValue(outputValue);
    }

    @Override
    public String toString() {
        return "XOR";
    }
}
