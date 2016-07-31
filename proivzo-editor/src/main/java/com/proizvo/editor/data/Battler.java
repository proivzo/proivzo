package com.proizvo.editor.data;

public class Battler {

    private int actorId;
    private int[] equips;
    private int level;

    public Battler(int actorId, int[] equips, int level) {
        this.actorId = actorId;
        this.equips = equips;
        this.level = level;
    }
}
