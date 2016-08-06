package com.proizvo.pkg.app;

import static com.proizvo.pkg.util.IOHelper.build;
import static com.proizvo.pkg.util.IOHelper.find;
import static com.proizvo.pkg.util.IOHelper.getFilePart;
import static com.proizvo.pkg.util.IOHelper.replace;
import static com.proizvo.pkg.util.JsonHelper.asMap;
import static com.proizvo.pkg.util.JsonHelper.getJsonResource;
import static com.proizvo.pkg.util.JsonHelper.getPropResource;
import static com.proizvo.pkg.util.OSHelper.detectOS;
import static com.proizvo.pkg.util.ZipHelper.unpack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.proizvo.pkg.net.Download;

public class Program {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Properties cfg = getPropResource("internal/defaults.cfg");
		Map<String, String> sysVars = build("home", SystemUtils.USER_HOME);
		File workdir = new File(replace(cfg.getProperty("workdir"), sysVars));
		List<String> osKeys = detectOS();
		osKeys.add("unknown");
		JsonObject recipes = getJsonResource("internal/sources.json")
				.getAsJsonObject();
		for (Entry<String, JsonElement> e : recipes.entrySet()) {
			String key = e.getKey();
			JsonObject val = e.getValue().getAsJsonObject();

			System.out.format("Recipe '%s'... %n", key);
			Map<String, String> vars = asMap(val.get("vars"));

			Map<String, String> urls = asMap(val.get("download"));
			String url = replace(find(urls, osKeys), vars);

			if (url != null) {
				Download dl = new Download(workdir, url, getFilePart(url));
				System.out.println(" * " + dl);
				dl.getLatch().await();
				unpack(workdir, dl.getFile());
			}

			Map<String, String> cmds = asMap(val.get("script"));
			String cmd = replace(find(cmds, osKeys), vars);

			if (cmd != null)
				System.out.println(cmd);
		}

		System.out.println("Waiting...");
		System.in.read();
		System.out.println("Done.");
		System.exit(0);
	}
}