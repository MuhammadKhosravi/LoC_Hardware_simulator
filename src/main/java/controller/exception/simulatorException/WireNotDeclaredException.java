package controller.exception.simulatorException;

public class WireNotDeclaredException extends RuntimeException {
    public WireNotDeclaredException(String wire) {
        super("Simulator exception: wire " + wire + " is not declared");
    }
}
