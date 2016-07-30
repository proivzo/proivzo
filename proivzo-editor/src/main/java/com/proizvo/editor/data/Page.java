package com.proizvo.editor.data;

public class Page {

    private Conditions conditions;
    private Event[] list;
    private int span;

    public Page(Conditions conditions, int span, Event... events) {
        this.conditions = conditions;
        this.list = events;
        this.span = span;
    }
}
