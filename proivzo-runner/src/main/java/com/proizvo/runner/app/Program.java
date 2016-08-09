package com.proizvo.runner.app;

import com.xafero.sfs.Hoster;
import com.xafero.sfs.impl.PopupPlugin;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Program {

    public static void main2(String[] args) throws IOException, InterruptedException {
        int width = 816;
        int height = 624;
        String title = "Proizvo";
        File root = (new File("")).getAbsoluteFile();
        Hoster host = new Hoster(root, new PopupPlugin(title, "index.html", title, width, height));
        System.out.format("Publishing '%s' under '%s'... %n", root, host.getEndpoint());
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(host);
        Thread.sleep(2 * 1000);
        Desktop.getDesktop().browse(host.getEndpoint());
        System.out.println();
        System.out.println("Press any key to exit...");
        System.in.read();
        pool.shutdown();
        System.exit(0);
    }
}
