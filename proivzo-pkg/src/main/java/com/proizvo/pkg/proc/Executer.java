package com.proizvo.pkg.proc;

import static com.proizvo.pkg.util.IOHelper.build;
import static com.proizvo.pkg.util.IOHelper.findExe;
import static com.proizvo.pkg.util.IOHelper.replace;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Executer implements Closeable, AutoCloseable {

	private final String cmd;
	private final CountDownLatch latch;
	private final ProcessBuilder builder;

	public Executer(File root, String cmd) throws IOException {
		this.cmd = cmd;
		this.latch = new CountDownLatch(1);
		String[] pts = cmd.split(" ");
		String exeName = pts[0];
		String[] args = Arrays.copyOfRange(pts, 1, pts.length);
		File exe = findExe(root, exeName);
		if (!exe.canExecute())
			exe.setExecutable(true, true);
		List<String> cmds = new LinkedList<>();
		cmds.add(exe.getAbsolutePath());
		Map<String, String> vars = build("exepdir", exe.getParent());
		for (String arg : args)
			cmds.add(replace(arg, vars));
		this.builder = new ProcessBuilder(cmds);
		Map<String, String> env = builder.environment();
		builder.redirectErrorStream(true);
		final Process proc = builder.start();
		try (InputStream in = proc.getInputStream()) {
			try (InputStreamReader inr = new InputStreamReader(in)) {
				try (BufferedReader inbr = new BufferedReader(inr)) {
					String line;
					while ((line = inbr.readLine()) != null) {
						System.out.println(" !!! '" + line + "'");
					}
				}
			}
		}
		System.out.println(" ??? (" + exe.getName() + ") Program terminated with code=" + proc.exitValue() + "!");
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public String toString() {
		return "Executer [cmd=" + cmd + "]";
	}
}