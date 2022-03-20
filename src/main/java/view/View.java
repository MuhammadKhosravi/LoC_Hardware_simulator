package view;

import java.util.List;
import java.util.Scanner;

public interface View {
    void run();

    boolean showErrors(List<Exception> exceptions);

    public void getInputs(Scanner scanner, List<String> commands);

    void execute(List<String> commands);
}
