package controller.exception.simulatorException;

public class InvalidTimeException extends RuntimeException {
    public InvalidTimeException(){
        super("Simulator exception: end time is earlier than start time");
    }
}
