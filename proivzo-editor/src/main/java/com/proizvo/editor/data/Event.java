package com.proizvo.editor.data;

public class Event {

    private int code;
    private int indent;
    private int[] parameters;

    public Event(int code, int indent, int[] parameters) {
        this.code = code;
        this.indent = indent;
        this.parameters = parameters;
    }
}
