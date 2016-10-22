package com.proizvo.editor.remote;

import com.proizvo.editor.cfg.Configuration;
import com.proizvo.editor.cloud.Credentials;
import com.proizvo.editor.cloud.DialogCallback;
import com.proizvo.editor.cloud.LoginDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EnterTask implements ActionListener {

    private JFrame frame;
    private EnterClient client;

    public <T extends JFrame> T parent(T frame) {
        this.frame = frame;
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (client != null) {
            return;
        }
        client = new EnterClient();
        boolean modal = true;
        final LoginDialog dialog = new LoginDialog(frame, modal);
        dialog.setLocationRelativeTo(frame);
        dialog.callback(new DialogCallback<String, char[], Boolean>() {
            @Override
            public void onDialogResult(int result, String user, char[] pass, Boolean store) {
                if (result == LoginDialog.RET_CANCEL) {
                    System.exit(0);
                    return;
                }
                Credentials creds = new Credentials(user, new String(pass));
                if (!client.login(creds)) {
                    JOptionPane.showMessageDialog(dialog, "User or password incorrect!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    dialog.setVisible(true);
                    return;
                }
                if (store) {
                    Configuration cfg = Configuration.getInstance();
                    cfg.saveLastCredentials(creds);
                }
                dialog.setVisible(false);
                dialog.dispose();
            }
        }).setVisible(true);
    }

    public void close() {
        if (client == null) {
            return;
        }
        client.close();
        client = null;
    }
}
