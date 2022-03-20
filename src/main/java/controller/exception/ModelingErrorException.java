package controller.exception;

public class ModelingErrorException extends RuntimeException {
    public ModelingErrorException(String gate, int line) {
        super("invalid modeling for" + gate + "command at line " + line);
    }
}
