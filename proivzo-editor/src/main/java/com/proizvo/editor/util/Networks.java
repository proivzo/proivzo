package com.proizvo.editor.util;

import java.io.File;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class Networks {

    private Networks() {
    }

    public static boolean checkFlag(Class<?> type) {
        ProtectionDomain domain = type.getProtectionDomain();
        CodeSource source = domain.getCodeSource();
        String file = source.getLocation().getFile();
        return (new File(file)).isDirectory();
    }

    public static String getPartner(boolean flag) {
        String proto = flag ? "http" : "https";
        String host = flag ? "localhost" : "proivzo.herokuapp.com";
        int port = flag ? 8084 : 443;
        return String.format("%s://%s:%s/api/", proto, host, port);
    }
}
