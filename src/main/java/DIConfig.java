import controller.InitController;
import controller.ModelController;
import model.Memory;

public class DIConfig {
    public void injectObjects() {
        Memory memory = new Memory();

        InitController.config(memory);
        ModelController.config(memory);
    }
}
