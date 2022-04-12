package controller;

import controller.exception.simulatorException.InvalidTimeException;
import controller.exception.simulatorException.WireNotDeclaredException;
import model.CircuitGrath;
import model.Memory;
import model.Pair;
import model.Wire;
import model.logicGates.Gate;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.awt.*;
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

        if (startTime > endTime) {
            throw new InvalidTimeException();
        }
        Wire wire = memory.getNameWireMap().get(wireSt);
        if (wire == null) {
            throw new WireNotDeclaredException(wireSt);
        }


        Map<Integer, Boolean> timeline = wire.getSource().getGateTimeLine();
        List<Integer> times = new ArrayList<>(timeline.keySet());
        ArrayList<Pair<Integer, Integer>> dataTable = new ArrayList<>();
        for (int i = startTime; i < endTime; i += step) {
            int index = binarySearch(times, i);
            if (index < 0) dataTable.add(new Pair<>(i, -1));
            else {
                int parsedVal = timeline.get(times.get(index)) ? 1 : 0;
                dataTable.add(new Pair<>(i, parsedVal));
            }

        }
        return dataTable;
    }

    public XYChart drawWirePlot(String wireName) {
        XYChart chart = new XYChartBuilder().theme(Styler.ChartTheme.GGPlot2).build();
        styleChart(chart);
        createSingleSeries(wireName, chart);
        return chart;
    }

    private void styleChart(XYChart chart) {
        chart.getStyler().setPlotBackgroundColor(Color.WHITE);
        chart.getStyler().setYAxisTicksVisible(false);
        chart.getStyler().setXAxisTickMarkSpacingHint(10);
    }

    private void createSingleSeries(String wireName, XYChart chart) {
        Wire wire = memory.getNameWireMap().get(wireName);
        Gate gate = wire.getSource();

        Map<Integer, Boolean> timeLine = gate.getGateTimeLine();
        List<Integer> xAxis = new ArrayList<>();
        List<Integer> yAxis = new ArrayList<>();
        initScales(timeLine, xAxis, yAxis);
        chart.addSeries("wire " + wireName, xAxis, yAxis);
    }

    public XYChart drawCircuitPlot() {
        XYChart chart = new XYChartBuilder().theme(Styler.ChartTheme.GGPlot2).build();
        styleChart(chart);
        memory.getNameWireMap().forEach((k, v) -> {
            if (v.getSource() != null)
                createSingleSeries(k, chart);
        });
        return chart;
    }


    private void initScales(Map<Integer, Boolean> timeLine, List<Integer> xAxis, List<Integer> yAxis) {
        timeLine.forEach((k, v) -> {
            if (xAxis.size() != 0) {
                int value = v ? 1 : 0;
                if (value != yAxis.get(yAxis.size() - 1)) {
                    xAxis.add(k);
                    yAxis.add(yAxis.get(yAxis.size() - 1));
                    xAxis.add(k);
                    yAxis.add(v ? 1 : 0);
                }
            } else {
                xAxis.add(k);
                yAxis.add(v ? 1 : 0);
            }
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
