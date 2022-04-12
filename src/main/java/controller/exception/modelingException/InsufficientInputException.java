package controller.exception.modelingException;

public class InsufficientInputException extends RuntimeException {
    public InsufficientInputException(String gate, int line) {
        super("Insufficient number of inputs for " + gate + " gate at line " + line);
    }

}
