package controller;

import model.CircuitGrath;
import model.Memory;
import model.Pair;
import model.Wire;

import java.util.ArrayList;

public class SimulatorController implements Controller {
    private static final SimulatorController instance = new SimulatorController();
    private CircuitGrath grath;
    private Memory memory;


    public static SimulatorController getInstance() {
        return instance;
    }

    public static void config(Memory memory) {
        instance.memory = memory;
    }

    public ArrayList<Boolean> sim(String wireSt, String startTimeSt, String endTimeSt, String stepSt) {
        int startTime = Integer.parseInt(startTimeSt);
        int endTime = Integer.parseInt(endTimeSt);
        int step = Integer.parseInt(stepSt);
        Wire wire = memory.getNameWireMap().get(wireSt);

        ArrayList<Pair<Integer, Boolean>> timeline = wire.getSource().getGateTimeLine();
        ArrayList<Boolean> dataTable = new ArrayList<>();
        for (int i = startTime; i < endTime; i += step) {
            int index = binarySearch(timeline, i);
            dataTable.add(timeline.get(index).getValue());
        }
        return dataTable;
    }

    private int binarySearch(ArrayList<Pair<Integer, Boolean>> arr, int x) {
        int left = 0, right = arr.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr.get(mid).getKey() == x)
                return mid;

            if (arr.get(mid).getKey() < x)
                left = mid + 1;

            else
                right = mid - 1;
        }

        return right;
    }

    @Override
    public void track() {
        grath = new CircuitGrath();
        grath.initGraph(memory.getGates(), memory.getChangeStatusMap());
    }

    @Override
    public void unTrack() {

    }

    @Override
    public void undo() {

    }
}
