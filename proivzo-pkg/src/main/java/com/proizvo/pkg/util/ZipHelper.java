package com.proizvo.pkg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ZipHelper {

    public static void unpack(File root, File file, boolean fastMode) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            try (InputStream pack = detect(file, in)) {
                try (ArchiveInputStream ark = detectArk(file, pack)) {
                    int i = 0;
                    ArchiveEntry entry;
                    while ((entry = ark.getNextEntry()) != null) {
                        File name = new File(root, entry.getName());
                        i++;
                        if (name.exists() && name.canRead()) {
                            if (i == 1 && fastMode) {
                                System.out.println(" skip extraction because '"+name.getName()+"' exists!");
                                return;
                            }
                            continue;
                        }
                        if (entry.isDirectory()) {
                            name.mkdir();
                            continue;
                        }
                        try (FileOutputStream out = new FileOutputStream(name)) {
                            IOUtils.copy(ark, out);
                        }
                    }
                }
            }
        }
    }

    private static ArchiveInputStream detectArk(File file, InputStream in) {
        String name = file.getName().toLowerCase();
        if (name.contains(".tar.") || name.contains(".t")) {
            return new TarArchiveInputStream(in);
        }
        if (name.endsWith("zip")) {
            return new ZipArchiveInputStream(in);
        }
        throw new UnsupportedOperationException(file + "?!");
    }

    private static InputStream detect(File file, InputStream in)
            throws IOException {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".tar.xz")) {
            return new XZCompressorInputStream(in);
        }
        if (name.endsWith(".tgz")) {
            return new GzipCompressorInputStream(in);
        }
        if (name.endsWith("zip")) {
            return in;
        }
        throw new UnsupportedOperationException(file + "?!");
    }
}
