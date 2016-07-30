package com.proizvo.editor.data;

public class Item {

    private long id;
    private int animationId;
    private boolean consumable;
    private Damage damage;
    private String description;
    private Effect[] effects;
    private int hitType;
    private int iconIndex;
    private int itypeId;
    private String name;
    private String note;
    private int occasion;
    private int price;
    private int repeats;
    private int scope;
    private int speed;
    private int successRate;
    private int tpGain;

    public void setId(long id) {
        this.id = id;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public void setEffects(Effect... effects) {
        this.effects = effects;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public void setItypeId(int itypeId) {
        this.itypeId = itypeId;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
