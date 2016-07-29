package com.proizvo.editor.data;

public class CommonEvent {

    private long id;
    private Event[] list;
    private String name;
    private int switchId;
    private int trigger;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSwitchId(int switchId) {
        this.switchId = switchId;
    }

    public void setList(Event... list) {
        this.list = list;
    }
}
