package com.proizvo.editor.data;

import java.util.Map;

public class Terms {

    private String[] basic;
    private String[] commands;
    private String[] params;
    private Map<String, String> messages;

    public void setBasic(String[] basic) {
        this.basic = basic;
    }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}
