package model;

import java.util.HashMap;

public class Memory {
    private final HashMap<String, Wire> nameWireMap = new HashMap<>();

    public HashMap<String, Wire> getNameWireMap() {
        return nameWireMap;
    }
}
