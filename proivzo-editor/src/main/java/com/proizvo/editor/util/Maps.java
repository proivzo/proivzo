package com.proizvo.editor.util;

import java.util.Map;

public class Maps {

    private Maps() {
    }

    public static <K, V> V getOrOther(Map<K, V> map, K key, V defaultValue) {
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }
}
