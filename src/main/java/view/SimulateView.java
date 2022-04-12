package view;

import controller.SimulatorController;
import controller.exception.BadSyntaxException;
import model.HelpType;
import model.Instructions.SimulateInstruction;
import model.Pair;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SimulateView implements View {

    SimulatorController controller = SimulatorController.getInstance();

    @Override
    public void run() {
        Statics.help(HelpType.SIM_VIEW);
        controller.track();

        List<String> commands = new ArrayList<>();
        Scanner scanner = Statics.getScanner();
        getInputs(scanner, commands);
        execute(commands);

        controller.unTrack();
    }

    @Override
    public boolean showErrors(List<Exception> exceptions) {
        return false;
    }

    @Override
    public void getInputs(Scanner scanner, List<String> commands) {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.equals(Statics.SIM_FINISH)) {
                break;
            }
            commands.add(input);
        }
    }

    @Override
    public void execute(List<String> commands) {
        List<Exception> exceptions = new ArrayList<>();
        ArrayList<List<Pair<Integer, Integer>>> outputs = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            boolean isValid = false;

            for (Pair<String, SimulateInstruction> simulateInstruction : Statics.SIMULATE_INSTRUCTIONS) {
                Matcher matcher = Statics.getMatcher(commands.get(i), simulateInstruction.getKey());
                if (matcher.find()) {
                    try {
                        switch (simulateInstruction.getValue()) {
                            case SIMULATE_INSTRUCTION -> {
                                ArrayList<Pair<Integer, Integer>> output = controller.sim(matcher.group("wire"),
                                        matcher.group("start"),
                                        matcher.group("finish"),
                                        matcher.group("step"));
                                outputs.add(output);
                            }
                            case SIMULATE_WIRE_PLOT -> {
                                XYChart chart = controller.drawWirePlot(matcher.group("wire"));
                                createJframe(chart);
                            }
                            case SIMULATE_CIRCUIT_PLOT -> {
                                XYChart chart = controller.drawCircuitPlot();
                                createJframe(chart);
                            }
                        }
                    } catch (Exception e) {
                        exceptions.add(e);
                    }
                }
            }
            if (!isValid) exceptions.add(new BadSyntaxException(i + 1));
        }

        if (!showErrors(exceptions)) {
            displayOutputs(outputs);
        }
    }

    private void createJframe(XYChart chart) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Wire");
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel chartPanel = new XChartPanel<>(chart);
            frame.add(chartPanel, BorderLayout.CENTER);
            chartPanel.setBackground(Color.BLACK);
            frame.pack();
            frame.setVisible(true);
        });
    }

    private void displayOutputs(List<List<Pair<Integer, Integer>>> outputs) {
        for (List<Pair<Integer, Integer>> output : outputs) {
            for (Pair<Integer, Integer> integerIntegerPair : output) {
                System.out.println("TIME : " + integerIntegerPair.getKey() + " VALUE : " + integerIntegerPair.getValue());
            }
            System.out.println("------------------------------------");
        }
    }


}
