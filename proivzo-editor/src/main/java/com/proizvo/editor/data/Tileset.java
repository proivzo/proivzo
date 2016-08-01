package com.proizvo.editor.data;

public class Tileset {

    private long id;
    private int[] flags;
    private int mode;
    private String name;
    private String note;
    private String[] tilesetNames;

    public long getId() {
        return id;
    }

    public int[] getFlags() {
        return flags;
    }

    public int getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public String[] getTilesetNames() {
        return tilesetNames;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFlags(int... flags) {
        this.flags = flags;
    }

    public void setTilesetNames(String... tilesetNames) {
        this.tilesetNames = tilesetNames;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
