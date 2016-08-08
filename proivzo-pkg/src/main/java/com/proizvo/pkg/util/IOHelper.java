package com.proizvo.pkg.util;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

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

	public static File findExe(File root, String exe, boolean fail) {
		for (File file : FileUtils.listFiles(root, null, true))
			if (file.getName().equalsIgnoreCase(exe) && file.length() >= 1)
				return file;
		if (!fail)
			return null;
		throw new UnsupportedOperationException("Couldn't find '" + exe + "'!");
	}

	public static File like(File root, String term) {
		IOFileFilter ff = TrueFileFilter.INSTANCE;
		IOFileFilter df = TrueFileFilter.INSTANCE;
		for (File file : FileUtils.listFilesAndDirs(root, ff, df))
			if (like(file.getName(), term))
				return file;
		throw new UnsupportedOperationException("Couldn't find '" + term + "'!");
	}

	public static boolean like(String name, String term) {
		if (!term.contains("%"))
			return term.equalsIgnoreCase(name);
		String regex = term.replace("%", "\\w+");
		boolean result = name.matches(regex);
		return result;
	}

	public static void makeExecutable(File dir) {
		IOFileFilter ff = TrueFileFilter.INSTANCE;
		IOFileFilter df = TrueFileFilter.INSTANCE;
		for (File file : FileUtils.listFilesAndDirs(dir, ff, df))
			if (!file.canExecute())
				file.setExecutable(true, false);
	}
}