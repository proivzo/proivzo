package com.proizvo.editor.cloud;

public class Credentials {

    private final String user;
    private final String password;

    public Credentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
