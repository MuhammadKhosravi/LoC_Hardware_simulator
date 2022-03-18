package model;

public class NandGate extends Gate {

    public NandGate(Wire output, int delay, Wire... inputs) {
        super(output, delay, inputs);
    }

    @Override
    public void calculateOutput() {
        Wire outputWire = getOutput();
        boolean outputValue = true;
        for (int i = 0; i < getInputs().length; i++) {
            outputValue = (outputValue & getInputs()[i].isValue());
        }
        outputWire.setValue(!outputValue);
    }

    @Override
    public String toString() {
        return "NAND";
    }
}
