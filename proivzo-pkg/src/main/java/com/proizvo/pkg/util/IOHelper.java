package com.proizvo.pkg.util;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class IOHelper {

	private IOHelper() {
	}

	public static InputStream getResource(String path) {
		return getResource(IOHelper.class, path);
	}

	public static InputStream getResource(Class<?> clazz, String path) {
		return getResource(clazz.getClassLoader(), path);
	}

	public static InputStream getResource(ClassLoader loader, String path) {
		return loader.getResourceAsStream(path);
	}

	public static String getFilePart(String url) {
		String[] pts = url.split("/");
		return pts[pts.length - 1];
	}

	public static String find(Map<String, String> map, List<String> keys) {
		for (String key : keys) {
			String url = map.get(key);
			if (url == null)
				continue;
			return url;
		}
		return null;
	}

	public static String replace(String text, Map<String, String> vars) {
		if (text == null)
			return text;
		for (String raw : vars.keySet()) {
			String key = "$" + raw.toUpperCase() + "$";
			String val = vars.get(raw);
			while (text.contains(key))
				text = text.replace(key, val);
		}
		return text;
	}

	public static Map<String, String> build(String... texts) {
		Map<String, String> map = new LinkedHashMap<>();
		for (int i = 0; i < texts.length; i = i + 2) {
			String key = texts[i];
			String val = texts[i + 1];
			map.put(key, val);
		}
		return map;
	}

	public static File findExe(File root, String exe) {
		for (File file : FileUtils.listFiles(root, null, true))
			if (file.getName().equalsIgnoreCase(exe) && file.length() >= 1)
				return file;
		throw new UnsupportedOperationException("Couldn't find '" + exe + "'!");
	}
}