package model;

import model.logicGates.Gate;

public class Wire {
    private final String name;
    private boolean value;
    private boolean isInput;
    private Gate source;

    public Wire(String name , boolean isInput) {
        this.name = name;
        this.isInput = isInput;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void setSource(Gate source) {
        this.source = source;
    }

    public Gate getSource() {
        return source;
    }

    @Override
    public String toString() {
        return name;
    }
}
