package controller.exception.modelingException;

public class InputInOutputException extends RuntimeException {
    public InputInOutputException(String gate, String terminal, int line) {
        super("Input " + terminal + " appears in output of gate " + gate + " at line " + line);
    }

}
