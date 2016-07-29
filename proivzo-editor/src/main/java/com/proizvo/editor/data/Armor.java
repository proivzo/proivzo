package com.proizvo.editor.data;

public class Armor {

    private long id;
    private int atypeId;
    private String description;
    private int etypeId;
    private Trait[] traits;
    private int iconIndex;
    private String name;
    private String note;
    private int[] params;
    private int price;

    public void setParams(int[] params) {
        this.params = params;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAtypeId(int atypeId) {
        this.atypeId = atypeId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEtypeId(int etypeId) {
        this.etypeId = etypeId;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTraits(Trait... traits) {
        this.traits = traits;
    }

    public void setName(String name) {
        this.name = name;
    }
}
