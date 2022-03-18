package model;


public abstract class Gate {
    private final Wire[] inputs;
    private final Wire output;

    // time is measured in milliseconds
    private final int delay;

    public Gate(Wire output, int delay, Wire... inputs) {
        this.output = output;
        this.delay = delay;
        this.inputs = inputs;
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
}
