package controller;

import model.Memory;

public class SimulatorController implements Controller{
    private static final SimulatorController instance = new SimulatorController();
    private Memory memory;


    public static SimulatorController getInstance() {
        return instance;
    }

    public static void config(Memory memory) {
        instance.memory = memory;
    }

    public void sim(String wireSt, String startTimeSt, String endTimeSt, String stepSt) {

    }

    @Override
    public void track() {

    }

    @Override
    public void unTrack() {

    }

    @Override
    public void undo() {

    }
}
