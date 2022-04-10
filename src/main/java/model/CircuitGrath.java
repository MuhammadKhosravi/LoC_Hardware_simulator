package model;

import model.logicGates.Gate;

import java.util.*;

public class CircuitGrath {
    public List<CircuitNode> headNodes;
    public List<CircuitNode> allNodes;

    public CircuitGrath() {
        headNodes = new ArrayList<>();
        allNodes = new ArrayList<>();
    }

    public void setGraphNodesProp(String trigger, int time) {
        headNodes.forEach(h -> h.updateNodeProp(trigger, time));
    }

    public void reset() {
        allNodes.forEach(n -> n.setVisited(false));
    }


    public void initGraph(List<Gate> allGates,
                          Map<Integer, Pair<Wire, Boolean>> changeStatusMap) {
        HashMap<Gate, CircuitNode> gateNodeHashMap = new HashMap<>();
        HashMap<CircuitNode, Gate> nodeGateHashMap = new HashMap<>();
        HashSet<CircuitNode> notHeadNodes = new HashSet<>();

        createNodes(allGates, gateNodeHashMap, nodeGateHashMap);
        initNodeChildren(gateNodeHashMap, notHeadNodes);
        findHeadNodes(notHeadNodes);

        setGraphNodesProp(null, 0);
        pushValues();
        reset();
        for (Map.Entry<Integer, Pair<Wire, Boolean>> entry : changeStatusMap.entrySet()) {
            int time = entry.getKey();
            Wire wire = entry.getValue().getKey();
            boolean value = entry.getValue().getValue();
            wire.setValue(value);
            setGraphNodesProp(wire.toString(), time);
            pushValues();
            reset();
        }
    }


    public void pushValues() {
        allNodes.forEach(n -> n.nodeGate.getGateTimeLine().add(new Pair<>(n.outputTime, n.output)));
    }


    private void findHeadNodes(HashSet<CircuitNode> notHeadNodes) {
        allNodes.forEach(g -> {
            if (!notHeadNodes.contains(g)) {
                headNodes.add(g);
            }
        });
    }

    private void initNodeChildren(HashMap<Gate, CircuitNode> gateNodeHashMap,
                                  HashSet<CircuitNode> notHeadNodes) {
        for (CircuitNode node : allNodes) {
            ArrayList<CircuitNode> inputNodes = new ArrayList<>();
            Gate gate = node.nodeGate;
            for (Wire input : gate.getInputs()) {
                Gate inputGate = input.getSource();
                if (inputGate != null) {
                    CircuitNode entryNode = gateNodeHashMap.get(inputGate);
                    inputNodes.add(entryNode);
                    notHeadNodes.add(entryNode);
                }
            }
            node.setInputNodes(inputNodes);
        }
    }

    private void createNodes(List<Gate> allGates, HashMap<Gate, CircuitNode> gateNodeHashMap,
                             HashMap<CircuitNode, Gate> nodeGateHashMap) {
        allGates.forEach(g -> {
            CircuitNode n = new CircuitNode(g);
            allNodes.add(n);
            gateNodeHashMap.put(g, n);
            nodeGateHashMap.put(n, g);
        });
    }
}
