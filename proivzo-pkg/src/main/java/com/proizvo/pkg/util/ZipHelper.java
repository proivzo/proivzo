package com.proizvo.pkg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ZipHelper {

	public static void unpack(File file) throws IOException {
		try (FileInputStream in = new FileInputStream(file)) {
			try (CompressorInputStream pack = detect(file, in)) {
				try (ArchiveInputStream ark = new TarArchiveInputStream(pack)) {
					ArchiveEntry entry;
					while ((entry = ark.getNextEntry()) != null) {
						File name = new File(entry.getName());
						if (name.exists() && name.canRead())
							continue;
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

	private static CompressorInputStream detect(File file, InputStream in)
			throws IOException {
		String name = file.getName().toLowerCase();
		if (name.endsWith(".tar.xz"))
			return new XZCompressorInputStream(in);
		if (name.endsWith(".tgz"))
			return new GzipCompressorInputStream(in);
		throw new UnsupportedOperationException(file + "?!");
	}
}