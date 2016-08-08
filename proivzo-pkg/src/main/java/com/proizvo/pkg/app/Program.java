package com.proizvo.pkg.app;

import static com.proizvo.pkg.util.IOHelper.build;
import static com.proizvo.pkg.util.IOHelper.find;
import static com.proizvo.pkg.util.IOHelper.findExe;
import static com.proizvo.pkg.util.IOHelper.getFilePart;
import static com.proizvo.pkg.util.IOHelper.like;
import static com.proizvo.pkg.util.IOHelper.makeExecutable;
import static com.proizvo.pkg.util.IOHelper.replace;
import static com.proizvo.pkg.util.JsonHelper.asMap;
import static com.proizvo.pkg.util.JsonHelper.getJsonResource;
import static com.proizvo.pkg.util.JsonHelper.getPropResource;
import static com.proizvo.pkg.util.NetHelper.getAddresses;
import static com.proizvo.pkg.util.OSHelper.detectOS;
import static com.proizvo.pkg.util.ZipHelper.unpack;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.proizvo.pkg.api.IEnvCustomizer;
import com.proizvo.pkg.net.Download;
import com.proizvo.pkg.proc.Executer;
import com.proizvo.pkg.web.ServePlugin;
import com.xafero.sfs.Hoster;

public class Program {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Properties cfg = getPropResource("internal/defaults.cfg");
		Map<String, String> sysVars = build("home", SystemUtils.USER_HOME);
		final File workdir = new File(replace(cfg.getProperty("workdir"),
				sysVars));
		workdir.mkdirs();
		System.out.println("Work directory = " + workdir);
		File tempdir = new File(replace(cfg.getProperty("tempdir"), sysVars));
		FileUtils.deleteQuietly(tempdir);
		tempdir.mkdirs();
		System.out.println("Temp directory = " + tempdir);
		File srcdir = (new File("")).getAbsoluteFile();
		System.out.println("Source directory = " + srcdir);
		File cwd = tempdir;
		List<String> osKeys = detectOS();
		osKeys.add("unknown");
		Map<String, File> hostedFiles = new LinkedHashMap<>();
		JsonObject recipes = getJsonResource("internal/sources.json")
				.getAsJsonObject();
		for (Entry<String, JsonElement> e : recipes.entrySet()) {
			String key = e.getKey();
			JsonObject val = e.getValue().getAsJsonObject();

			System.out.format("Recipe '%s'... %n", key);
			Map<String, String> vars = asMap(val.get("vars"));
			final Map<String, String> envs = asMap(val.get("env"));

			Map<String, String> skip = asMap(val.get("skip"));
			String skipFilePath = skip.get("file");
			if (skipFilePath != null) {
				File skipFile = findExe(workdir, skipFilePath, false);
				if (skipFile != null && skipFile.exists()) {
					System.out.println(" skip because '" + skipFile.getName()
							+ "' already exists!");
					continue;
				}
			}

			Map<String, String> chmods = asMap(val.get("chmod"));
			for (Entry<String, String> x : chmods.entrySet()) {
				String xkey = x.getKey();
				int xval = Integer.parseInt(x.getValue());
				if (xval == 777)
					makeExecutable(like(workdir, xkey));
				else
					throw new UnsupportedOperationException("chmod " + xval
							+ " ?!");
			}

			Map<String, String> tmpFiles = asMap(val.get("files"));
			File inputTmpFile = null;
			for (Entry<String, String> y : tmpFiles.entrySet()) {
				String ykey = y.getKey();
				File thef = File.createTempFile(ykey, "rov");
				String yval = y.getValue();
				if (ykey.equalsIgnoreCase("input"))
					inputTmpFile = thef;
				String txt = yval;
				FileUtils.write(thef, txt, "UTF8");
			}

			Map<String, String> urls = asMap(val.get("download"));
			String url = replace(find(urls, osKeys), vars);

			if (url != null) {
				Download dl = new Download(workdir, url, getFilePart(url));
				System.out.println(" * " + dl);
				dl.getLatch().await();
				unpack(workdir, dl.getFile(), true);
			}

			Map<String, String> cmds = asMap(val.get("script"));
			String cmd = replace(find(cmds, osKeys), vars);

			if (cmd != null) {
				IEnvCustomizer ec = new IEnvCustomizer() {
					@Override
					public void customize(Map<String, String> env) {
						for (Entry<String, String> e : envs.entrySet()) {
							String key = e.getKey();
							String val = e.getValue();
							env.put(key, like(workdir, val) + "");
						}
					}
				};
				Executer ex = new Executer(workdir, cmd, cwd, ec, inputTmpFile);
				System.out.println(" * " + ex);
				ex.getLatch().await();
				String newCwd = vars.get("scwd");
				if (newCwd != null) {
					cwd = new File(cwd, newCwd);
					System.out.println("Next working directory = " + cwd);
				}
			}

			Map<String, String> hosts = asMap(val.get("serve"));
			for (Entry<String, String> z : hosts.entrySet()) {
				String zkey = z.getKey();
				String zval = z.getValue();
				File zfile = new File(cwd, zval);
				hostedFiles.put(zkey, zfile);
			}

			Map<String, String> copies = asMap(val.get("copy"));
			Map<String, String> cvars = build("twd", srcdir + "", "cwd", cwd
					+ "");
			for (Entry<String, String> w : copies.entrySet()) {
				String wkey = replace(w.getKey(), cvars);
				String wval = replace(w.getValue(), cvars);
				File src = new File(wkey);
				File dst = new File(wval);
				System.out.printf(" Copying '%s' to '%s'... %n", src, dst);
				FileUtils.copyDirectory(src, dst);
			}
		}

		int port = 8080;
		String allNet = "0.0.0.0";
		Hoster hoster = new Hoster(allNet, port, new File(""), new ServePlugin(
				hostedFiles));
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.submit(hoster);

		for (InetAddress addr : getAddresses()) {
			String endpoint = (hoster.getEndpoint() + "").replace(allNet,
					addr.getHostAddress());
			URI uri = URI.create(endpoint);
			System.out.println(" Endpoint = " + uri);
			Desktop.getDesktop().browse(uri);
		}

		System.out.println("Press any key to quit...");
		System.in.read();
		pool.shutdown();
		System.exit(0);
		System.out.println("Done.");
	}
}