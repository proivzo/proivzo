package com.proizvo.editor.util;

import java.io.File;

public class Files {

    public static File find(File root, String path) {
        File file = new File(root, path);
        if (!file.exists()) {
            File dir = file.getParentFile();
            for (File item : dir.listFiles()) {
                if ((item + "").equalsIgnoreCase(file + "")) {
                    return item;
                }
            }
        }
        return file;
    }
}
