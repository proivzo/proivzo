package com.proizvo.editor.util;

import java.util.LinkedList;
import java.util.List;

public class Strings {

    private Strings() {
    }

    public static boolean isEmpty(String txt) {
        return txt == null || txt.isEmpty() || txt.trim().isEmpty();
    }

    public static List<String> asList(String... txts) {
        List<String> list = new LinkedList<>();
        for (String txt : txts) {
            list.add(txt);
        }
        return list;
    }
}
