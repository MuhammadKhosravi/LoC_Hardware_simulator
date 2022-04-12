package controller;

import model.CircuitGrath;
import model.Memory;
import model.Pair;
import model.Wire;
import model.logicGates.Gate;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

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
        Wire wire = memory.getNameWireMap().get(wireSt);

        Map<Integer, Boolean> timeline = wire.getSource().getGateTimeLine();
        List<Integer> times = new ArrayList<>(timeline.keySet());
        ArrayList<Pair<Integer, Integer>> dataTable = new ArrayList<>();
        for (int i = startTime; i < endTime; i += step) {
            int index = binarySearch(times, i);
            if (index < 0) dataTable.add(new Pair<>(i, -1));
            else {
                int parsedVal = timeline.get(index) ? 1 : 0;
                dataTable.add(new Pair<>(i, parsedVal));
            }

        }
        return dataTable;
    }

    public XYChart drawPlot(String wireName) {
        Wire wire = memory.getNameWireMap().get(wireName);
        Gate gate = wire.getSource();

        Map<Integer, Boolean> timeLine = gate.getGateTimeLine();
        List<Integer> xAxis = new ArrayList<>();
        List<Integer> yAxis = new ArrayList<>();
        initScales(timeLine, xAxis, yAxis);


        XYChart chart = QuickChart.getChart(wireName + " plot", "Time", "Value", "Oscillation", xAxis, yAxis);
        chart.getStyler().setYAxisTicksVisible(false);
        return chart;
    }

    private void initScales(Map<Integer, Boolean> timeLine, List<Integer> xAxis, List<Integer> yAxis) {
        timeLine.forEach((k, v) -> {
            if (xAxis.size() != 0) {
                xAxis.add(k);
                yAxis.add(yAxis.get(yAxis.size() - 1));
            }
            xAxis.add(k);
            yAxis.add(v ? 1 : 0);
        });

        xAxis.add(xAxis.get(xAxis.size() - 1) + 1);
        yAxis.add(yAxis.get(yAxis.size() - 1));
    }


    private int binarySearch(List<Integer> arr, int x) {
        int left = 0, right = arr.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr.get(mid) == x)
                return mid;

            if (arr.get(mid) < x)
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
