package com.proizvo.editor.data;

public class Class {

    private long id;
    private int[] expParams;
    private Trait[] traits;
    private Learning[] learnings;
    private String name;
    private String note;
    private int[][] params;

    public Class() {
    }

    public Class(Class c) {
        this.id = c.id;
        this.expParams = c.expParams;
        this.traits = c.traits;
        this.learnings = c.learnings;
        this.name = c.name;
        this.note = c.note;
        this.params = c.params;
    }

    public int[][] getParams() {
        return params;
    }

    public void setParams(int[]... params) {
        this.params = params;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExpParams(int... expParams) {
        this.expParams = expParams;
    }

    public void setLearnings(Learning... learnings) {
        this.learnings = learnings;
    }

    public void setTraits(Trait... traits) {
        this.traits = traits;
    }
}
