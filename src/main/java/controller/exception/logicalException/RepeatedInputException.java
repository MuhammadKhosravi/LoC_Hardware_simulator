package controller.exception.logicalException;

public class RepeatedInputException extends LogicalException {

    public RepeatedInputException(int line) {
        super("logic error line " + line + ": wire was defined before");
    }
}
