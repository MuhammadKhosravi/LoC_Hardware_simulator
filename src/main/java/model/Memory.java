package model;

import model.logicGates.Gate;

import java.util.*;

public class Memory {
    private final Map<String, Wire> nameWireMap = new HashMap<>();
    private final Map<Integer, Pair<Wire, Boolean>> changeStatusMap = new TreeMap<>();
    private final List<Gate> gates = new ArrayList<>();

    public Map<String, Wire> getNameWireMap() {
        return nameWireMap;
    }

    public Map<Integer, Pair<Wire, Boolean>> getChangeStatusMap() {
        return changeStatusMap;
    }

    public List<Gate> getGates() {
        return gates;
    }
}
