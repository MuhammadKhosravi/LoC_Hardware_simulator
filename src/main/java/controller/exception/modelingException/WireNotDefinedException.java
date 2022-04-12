package controller.exception.modelingException;

public class WireNotDefinedException extends RuntimeException {
    public WireNotDefinedException(String wire, int line) {
        super("Undefined wire " + wire + " at line " + line);
    }
}
