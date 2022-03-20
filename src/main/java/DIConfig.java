import controller.InitController;
import model.Memory;

public class DIConfig {
    public void injectObjects() {
        Memory memory = new Memory();

        InitController.config(memory);
    }
}
