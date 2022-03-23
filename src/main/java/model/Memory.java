package model;

import model.logicGates.Gate;

import java.util.*;

public class Memory {
    private final Map<String, Wire> nameWireMap = new HashMap<>();
    private final Map<Integer, Pair<Wire, Boolean>> changeStatusMap = new TreeMap<>();
    private final List<Gate> gates = new ArrayList<>();
    private final Map<Wire, Gate> wireSrcMap = new HashMap<>();

    public Map<String, Wire> getNameWireMap() {
        return nameWireMap;
    }

    public Map<Integer, Pair<Wire, Boolean>> getChangeStatusMap() {
        return changeStatusMap;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public Map<Wire, Gate> getWireSrcMap() {
        return wireSrcMap;
    }
}
