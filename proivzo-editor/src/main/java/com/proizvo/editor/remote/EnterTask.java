package com.proizvo.editor.remote;

import java.util.TimerTask;

public class EnterTask extends TimerTask {

    private EnterClient client;

    @Override
    public void run() {
        if (client != null) {
            return;
        }
        client = new EnterClient();
        client.login();
    }

    public void close() {
        if (client == null) {
            return;
        }
        client.close();
        client = null;
    }
}
