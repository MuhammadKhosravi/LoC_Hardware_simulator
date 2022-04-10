package controller;

import model.CircuitGrath;
import model.Memory;
import model.Pair;
import model.Wire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimulatorController implements Controller {
    private static final SimulatorController instance = new SimulatorController();
    private CircuitGrath grath;
    private Memory memory;
    private Map<Wire, Boolean> zeroTimeWiresVal;

    public static SimulatorController getInstance() {
        return instance;
    }

    public static void config(Memory memory) {
        instance.memory = memory;
    }

    public ArrayList<Integer> sim(String wireSt, String startTimeSt, String endTimeSt, String stepSt) {
        int startTime = Integer.parseInt(startTimeSt);
        int endTime = Integer.parseInt(endTimeSt);
        int step = Integer.parseInt(stepSt);
        Wire wire = memory.getNameWireMap().get(wireSt);

        ArrayList<Pair<Integer, Boolean>> timeline = wire.getSource().getGateTimeLine();
        ArrayList<Integer> dataTable = new ArrayList<>();
        for (int i = startTime; i < endTime; i += step) {
            int index = binarySearch(timeline, i);
            if (index < 0) dataTable.add(-1);
            else {
                int parsedVal = timeline.get(index).getValue() ? 1 : 0;
                dataTable.add(parsedVal);
            }

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
        zeroTimeWiresVal = new HashMap<>();
        memory.getNameWireMap().forEach((k, v) -> {
            zeroTimeWiresVal.put(v, v.isValue());
        });

        grath = new CircuitGrath();
        grath.initGraph(memory.getGates(), memory.getChangeStatusMap());
    }

    @Override
    public void unTrack() {
        grath.clear();
    }

    @Override
    public void undo() {
        zeroTimeWiresVal.forEach(Wire::setValue);
    }
}
