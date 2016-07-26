package com.proizvo.editor.util;

import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class Threads {

    public static Timer executeOnce(int delayMs, ActionListener task) {
        Timer timer = new Timer(delayMs, task);
        timer.setRepeats(false);
        timer.start();
        return timer;
    }

    public static interface Action<V extends Object> {

        public void call(V obj) throws Exception;
    }

    public static <T> SwingWorker executeSwing(final Callable<T> background, final Action<T> after) {
        SwingWorker worker = new SwingWorker<T, T>() {
            @Override
            public T doInBackground() {
                try {
                    return background.call();
                } catch (Exception e) {
                    Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, e);
                    return null;
                }
            }

            @Override
            public void done() {
                try {
                    after.call(get());
                } catch (Exception e) {
                    Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, e);
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
        return worker;
    }
}
