package com.proizvo.pkg.net;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncByteConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;

public class Download implements FutureCallback<HttpResponse>, Closeable,
		AutoCloseable {

	private final File file;
	private final URI url;
	private final CloseableHttpAsyncClient client;
	private final HttpGet req;
	private final HttpAsyncRequestProducer prod;
	private final DownloadConsumer cons;
	private final CountDownLatch latch;

	private FileOutputStream out;
	private Future<HttpResponse> fut;

	public Download(File root, String raw, String path) throws FileNotFoundException {
		this.file = new File(root, path);
		this.url = URI.create(raw);
		this.client = HttpAsyncClients.createDefault();
		this.req = new HttpGet(url);
		this.prod = HttpAsyncMethods.create(req);
		this.cons = new DownloadConsumer();
		this.latch = new CountDownLatch(1);
		if (file.exists() && file.canRead()) {
			completed(new BasicHttpResponse(Defaults.HTTP11, 304,
					"Not Modified"));
			return;
		}
		file.getParentFile().mkdirs();
		this.out = new FileOutputStream(file);
		client.start();
		this.fut = client.execute(prod, cons, this);
	}

	public File getFile() {
		return file;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	@Override
	public void close() throws IOException {
		fut.cancel(true);
		out.flush();
		out.close();
		cons.close();
		prod.close();
		client.close();
	}

	@Override
	public String toString() {
		return "Download [url=" + url + "]";
	}

	class DownloadConsumer extends AsyncByteConsumer<HttpResponse> {

		@Override
		protected void onByteReceived(ByteBuffer buf, IOControl ioctrl)
				throws IOException {
			int size = buf.limit();
			byte[] array = new byte[size];
			buf.get(array, 0, size);
			out.write(array);
			out.flush();
		}

		@Override
		protected void onResponseReceived(HttpResponse rsp)
				throws HttpException, IOException {
			System.out.println("TODO: received = " + rsp);
		}

		@Override
		protected HttpResponse buildResult(HttpContext ctx) throws Exception {
			return new BasicHttpResponse(Defaults.HTTP11, 200, "OK");
		}
	}

	@Override
	public void completed(HttpResponse rsp) {
		latch.countDown();
		System.out.println("TODO: ->" + rsp.getStatusLine());
	}

	@Override
	public void failed(Exception ex) {
		latch.countDown();
		System.out.println("TODO: ->" + ex);
	}

	@Override
	public void cancelled() {
		latch.countDown();
		System.out.println("TODO: cancelled");
	}
}