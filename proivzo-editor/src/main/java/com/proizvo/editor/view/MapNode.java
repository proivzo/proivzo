package com.proizvo.editor.view;

import com.proizvo.editor.data.Map;

public class MapNode {

    private final Map map;
    private final String name;

    public MapNode(Map map, String name) {
        this.map = map;
        this.name = name;
    }

    public Map getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
