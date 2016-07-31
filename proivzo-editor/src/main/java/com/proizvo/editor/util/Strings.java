package com.proizvo.editor.util;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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

    public static String[] asArray(ResourceBundle bundle, String key) {
        String val = bundle.getString(key);
        String[] array = val.split(Pattern.quote(";"));
        for (int i = 0; i < array.length; i++) {
            String item = array[i];
            if (item.equalsIgnoreCase("null")) {
                array[i] = null;
            }
        }
        return array;
    }

    public static String[] repeat(int count, String value) {
        String[] array = new String[count];
        for (int i = 0; i < count; i++) {
            array[i] = value;
        }
        return array;
    }

    public static boolean[] repeat(int count, boolean value) {
        boolean[] array = new boolean[count];
        for (int i = 0; i < count; i++) {
            array[i] = value;
        }
        return array;
    }
}
