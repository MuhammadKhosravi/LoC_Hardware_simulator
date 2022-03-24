package model;

import model.logicGates.Gate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircuitNode {
    private final Gate nodeGate;
    private final List<CircuitNode> inputNodes;
    private final Map<Integer, Boolean> outputTimeMap;
    private boolean isVisited;
    private int totalDelay;

    protected CircuitNode(Gate nodeGate) {
        this.nodeGate = nodeGate;
        outputTimeMap = new HashMap<>();
        inputNodes = new ArrayList<>();
        totalDelay = -1;
        isVisited = false;
    }


    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    protected boolean isVisited() {
        return isVisited;
    }

    protected int getTotalDelay() {
        return totalDelay;
    }

    protected Map<Integer, Boolean> getOutputTimeMap() {
        return outputTimeMap;
    }

    protected void initTotalDelay() {
        inputNodes.forEach(in -> {
                    int nodeDelay;
                    if (in.getTotalDelay() != -1) {
                        in.initTotalDelay();
                    }
                }
        );
        int max = 0;
        for (CircuitNode inputNode : inputNodes) {
            if (inputNode.totalDelay > max) {
                max = inputNode.totalDelay;
            }
        }
        this.totalDelay = max + this.nodeGate.getDelay();
    }

    protected void simulateNode() {

    }
}
