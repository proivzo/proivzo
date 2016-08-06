package com.proizvo.pkg.util;

import static com.proizvo.pkg.util.IOHelper.getResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {

	public static JsonElement getJsonResource(String path) throws IOException {
		JsonParser parser = new JsonParser();
		try (InputStream in = getResource(path)) {
			try (Reader reader = new InputStreamReader(in, "UTF8")) {
				return parser.parse(reader);
			}
		}
	}

	public static Properties getPropResource(String path) throws IOException {
		Properties props = new Properties();
		try (InputStream in = getResource(path)) {
			try (Reader reader = new InputStreamReader(in, "UTF8")) {
				props.load(reader);
				return props;
			}
		}
	}

	public static Map<String, String> asMap(JsonElement item) {
		Map<String, String> map = new LinkedHashMap<>();
		if (item == null)
			return map;
		JsonObject obj = item.getAsJsonObject();
		for (Entry<String, JsonElement> e : obj.entrySet())
			map.put(e.getKey(), e.getValue().getAsString());
		return map;
	}
}