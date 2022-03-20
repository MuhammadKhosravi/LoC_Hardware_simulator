package model;

import java.util.HashMap;
import java.util.TreeMap;

public class Memory {
    private final HashMap<String, Wire> nameWireMap = new HashMap<>();
    private final TreeMap<Integer, Pair<Wire , Boolean>> changeStatusMap = new TreeMap<>();

    public HashMap<String, Wire> getNameWireMap() {
        return nameWireMap;
    }

    public TreeMap<Integer, Pair<Wire, Boolean>> getChangeStatusMap() {
        return changeStatusMap;
    }
}
