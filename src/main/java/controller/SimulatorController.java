package controller;

import com.github.sh0nk.matplotlib4j.Plot;
import controller.exception.simulatorException.InvalidTimeException;
import controller.exception.simulatorException.WireNotDeclaredException;
import model.CircuitGrath;
import model.Memory;
import model.Pair;
import model.Wire;
import model.logicGates.Gate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public ArrayList<Pair<Integer, Integer>> sim(String wireSt, String startTimeSt, String endTimeSt, String stepSt) {
        int startTime = Integer.parseInt(startTimeSt);
        int endTime = Integer.parseInt(endTimeSt);
        int step = Integer.parseInt(stepSt);

        if (startTime > endTime){
            throw new InvalidTimeException();
        }
        Wire wire = memory.getNameWireMap().get(wireSt);

        if (wire == null) {
            throw new WireNotDeclaredException(wireSt);
        }

        List<Pair<Integer, Boolean>> timeline = wire.getSource().getGateTimeLine();
        ArrayList<Pair<Integer, Integer>> dataTable = new ArrayList<>();
        for (int i = startTime; i < endTime; i += step) {
            int index = binarySearch(timeline, i);
            if (index < 0) dataTable.add(new Pair<>(i, -1));
            else {
                int parsedVal = timeline.get(index).getValue() ? 1 : 0;
                dataTable.add(new Pair<>(i, parsedVal));
            }

        }
        return dataTable;
    }

    public Plot drawPlot(String wireName) {
        Wire wire = memory.getNameWireMap().get(wireName);
        Gate gate = wire.getSource();
        List<Pair<Integer, Boolean>> timeLine = gate.getGateTimeLine();
        List<Double> xAxis = new ArrayList<>();
        List<Double> yAxis = new ArrayList<>();
        xAxis.add(Double.valueOf(timeLine.get(0).getKey()));
        yAxis.add((double) (timeLine.get(0).getValue() ? 1 : 0));
        timeLine.stream().skip(1).forEach(p -> {
            xAxis.add(Double.valueOf(p.getKey()));
            xAxis.add(Double.valueOf(p.getKey()));
            double value = p.getValue() ? 1 : 0;
            yAxis.add(yAxis.get(yAxis.size() - 1));
            yAxis.add(value);
        });
        xAxis.add(xAxis.get(xAxis.size() - 1) + 1);
        yAxis.add(yAxis.get(yAxis.size() - 1));
        Plot plot = Plot.create();
        plot.title("Wire " + wireName);
        plot.legend().loc("upper right");
        plot.plot().add(xAxis, yAxis);
        plot.ylim(-0.4, 1.4);
        plot.plot().color("blue").linewidth(2.5).linestyle("-");
        return plot;
    }


    private int binarySearch(List<Pair<Integer, Boolean>> arr, int x) {
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
        memory.getNameWireMap().forEach((k, v) -> zeroTimeWiresVal.put(v, v.isValue()));

        grath = new CircuitGrath();
        grath.initGraph(memory.getGates(), memory.getChangeStatusMap());
    }

    @Override
    public void unTrack() {
        zeroTimeWiresVal.forEach(Wire::setValue);
        memory.getGates().forEach(Gate::clear);
        grath.clear();
    }

    @Override
    public void undo() {
        // NO ACTION IS NEEDED
    }
}
