package com.proizvo.pkg.web;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.xafero.sfs.api.IPlugin;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class ServePlugin implements IPlugin {

	private final Map<String, File> files;

	public ServePlugin(Map<String, File> files) {
		this.files = files;
	}

	@Override
	public boolean canHandle(String uri, String query) {
		return true;
	}

	@Override
	public Response serve(IHTTPSession session) {
		String uri = session.getUri();
		try {
			if (uri.equalsIgnoreCase("/")) {
				try (StringWriter str = new StringWriter()) {
					try (PrintWriter out = new PrintWriter(str)) {
						out.printf("<html>");
						out.printf("<head>");
						out.printf("<title>%s</title>", files.size() + " files");
						out.printf("</head>");
						out.printf("<body>");
						for (Entry<String, File> e : files.entrySet()) {
							String key = e.getKey();
							File val = e.getValue();
							out.printf("<a href='%s'>%s</a>", key,
									val.getName());
						}
						out.printf("</body>");
						out.printf("</html>");
						out.flush();
					}
					return newFixedLengthResponse(Status.OK,
							NanoHTTPD.MIME_HTML, str + "");
				}
			}
			File file = files.get(uri.substring(1));
			if (file != null && file.exists() && file.canRead()) {
				FileInputStream data = FileUtils.openInputStream(file);
				long length = file.length();
				return newFixedLengthResponse(Status.OK,
						"application/octet-stream", data, length);
			}
		} catch (Exception e) {
			return newFixedLengthResponse(Status.INTERNAL_ERROR,
					NanoHTTPD.MIME_PLAINTEXT,
					e.getClass().getName() + ": " + e.getMessage());
		}
		return newFixedLengthResponse(Status.NOT_FOUND,
				NanoHTTPD.MIME_PLAINTEXT, "NOT_FOUND: " + uri);
	}
}