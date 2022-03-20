package model;

public class Wire {
    private final String name;
    private boolean value;
    private boolean isInput;

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

    public String getName() {
        return name;
    }
}
