package com.proizvo.editor.data;

public class Weapon {

    private long id;
    private int animationId;
    private String description;
    private int etypeId;
    private Trait[] traits;
    private int iconIndex;
    private String name;
    private String note;
    private int[] params;
    private int price;
    private int wtypeId;

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setParams(int[] params) {
        this.params = params;
    }

    public void setWtypeId(int wtypeId) {
        this.wtypeId = wtypeId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTraits(Trait... traits) {
        this.traits = traits;
    }
}
