package controller.exception;

public class NotModelingTimeException extends Exception {
    public NotModelingTimeException() {
        super("You are not allowed to do this when it's not modeling time!");
    }
}
