package com.proizvo.editor.util;

import java.util.List;

public class Lists {

    public static <T extends List<I>, I> T insert(T list, I... items) {
        for (I item : items) {
            list.add(item);
        }
        return list;
    }
}
