package model;

public class OrGate extends Gate {

    public OrGate(Wire output, int delay, Wire... inputs) {
        super(output, delay, inputs);
    }

    @Override
    public void calculateOutput() {
        boolean outputValue = false;
        for (Wire input : getInputs()) {
            outputValue = (outputValue | input.isValue());
        }
        getOutput().setValue(outputValue);
    }

    @Override
    public String toString() {
        return "OR";
    }
}
