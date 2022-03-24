package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircuitGrath {
    List<CircuitNode> headNodes;
    List<CircuitNode> allNodes;

    public CircuitGrath() {
        headNodes = new ArrayList<>();
        allNodes = new ArrayList<>();
    }

    public void initGrathDelays() {
        HashMap<CircuitNode, Boolean> nodesVisitStateMap = new HashMap<>();
        int maxDelay = 0;
        headNodes.forEach(CircuitNode::initTotalDelay);
    }

    public void reset() {
        allNodes.forEach(n -> n.setVisited(false));
    }

}
