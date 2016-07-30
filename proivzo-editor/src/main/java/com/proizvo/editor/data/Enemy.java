package com.proizvo.editor.data;

public class Enemy {

    private long id;
    private Action[] actions;
    private int battlerHue;
    private String battlerName;
    private DropItem[] dropItems;
    private int exp;
    private Trait[] traits;
    private int gold;
    private String name;
    private String note;
    private int[] params;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setParams(int[] params) {
        this.params = params;
    }

    public void setTraits(Trait... traits) {
        this.traits = traits;
    }

    public void setActions(Action... actions) {
        this.actions = actions;
    }

    public void setBattlerName(String battlerName) {
        this.battlerName = battlerName;
    }

    public void setDropItems(DropItem... items) {
        this.dropItems = items;
    }
}
