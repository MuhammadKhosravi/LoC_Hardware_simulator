package controller.exception;

public class BadSyntaxException extends RuntimeException{
    public BadSyntaxException(int line) {
        super("syntax error line " + line + ": no such command");
    }
}
