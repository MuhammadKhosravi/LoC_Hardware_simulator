package controller.exception.logicalException;

public class InvalidInputException extends LogicalException {

    public InvalidInputException(int line) {
        super("logic error line " + line + ": Attributed value must be 1 or 0");
    }
}
