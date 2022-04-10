package model;

import model.logicGates.Gate;

import java.util.List;

public class CircuitNode {
    public final Gate nodeGate;
    protected int outputTime;
    protected boolean output;
    private List<CircuitNode> inputNodes;
    private boolean isVisited;

    public CircuitNode(Gate nodeGate) {
        this.nodeGate = nodeGate;
        outputTime = -1;
        isVisited = false;
    }

    protected boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    protected int getOutputTime() {
        return outputTime;
    }

    public List<CircuitNode> getInputNodes() {
        return inputNodes;
    }

    public void setInputNodes(List<CircuitNode> inputNodes) {
        this.inputNodes = inputNodes;
    }


    private void setNodeDelay(int base) {
        int max = base;
        for (CircuitNode inputNode : inputNodes) {
            if (inputNode.outputTime > max) {
                max = inputNode.outputTime;
            }
        }
        this.outputTime = max + this.nodeGate.getDelay();
    }


    protected void updateNodeProp(String trigger, int time) {
        updateNodeChildren(trigger, time);
        int base = 0;
        if (nodeGate.doesHaveDirectInput(trigger)) base = time;
        setNodeDelay(base);
        this.nodeGate.calculateOutput();
        this.output = this.nodeGate.getOutput().isValue();
        this.isVisited = true;
    }

    private void updateNodeChildren(String trigger, int time) {
        for (CircuitNode inputNode : inputNodes) {
            if (!inputNode.isVisited) {
                inputNode.updateNodeProp(trigger, time);
            }
        }
    }
}
