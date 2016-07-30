package com.proizvo.editor.data;

public class Skill {

    private long id;
    private int animationId;
    private Damage damage;
    private String description;
    private Effect[] effects;
    private int hitType;
    private int iconIndex;
    private String message1;
    private String message2;
    private int mpCost;
    private String name;
    private String note;
    private int occasion;
    private int repeats;
    private int requiredWtypeId1;
    private int requiredWtypeId2;
    private int scope;
    private int speed;
    private int stypeId;
    private int successRate;
    private int tpCost;
    private int tpGain;

    public Skill() {
    }

    public Skill(Skill s) {
        this.id = s.id;
        this.animationId = s.animationId;
        this.damage = s.damage;
        this.description = s.description;
        this.effects = s.effects;
        this.hitType = s.hitType;
        this.iconIndex = s.iconIndex;
        this.message1 = s.message1;
        this.message2 = s.message2;
        this.mpCost = s.mpCost;
        this.name = s.name;
        this.note = s.note;
        this.occasion = s.occasion;
        this.repeats = s.repeats;
        this.requiredWtypeId1 = s.requiredWtypeId1;
        this.requiredWtypeId2 = s.requiredWtypeId2;
        this.scope = s.scope;
        this.speed = s.speed;
        this.stypeId = s.stypeId;
        this.successRate = s.successRate;
        this.tpCost = s.tpCost;
        this.tpGain = s.tpGain;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEffects(Effect... effects) {
        this.effects = effects;
    }

    public void setHitType(int hitType) {
        this.hitType = hitType;
    }

    public void setMpCost(int mpCost) {
        this.mpCost = mpCost;
    }

    public void setStypeId(int stypeId) {
        this.stypeId = stypeId;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public void setOccasion(int occasion) {
        this.occasion = occasion;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }

    public void setTpGain(int tpGain) {
        this.tpGain = tpGain;
    }

    public void setTpCost(int tpCost) {
        this.tpCost = tpCost;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
