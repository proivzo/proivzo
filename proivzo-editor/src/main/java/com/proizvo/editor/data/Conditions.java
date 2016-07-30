package com.proizvo.editor.data;

public class Conditions {

    private int actorHp;
    private int actorId;
    private boolean actorValid;
    private int enemyHp;
    private int enemyIndex;
    private boolean enemyValid;
    private int switchId;
    private boolean switchValid;
    private int turnA;
    private int turnB;
    private boolean turnEnding;
    private boolean turnValid;

    public Conditions(int actorHp, int actorId, boolean actorValid, int enemyHp,
            int enemyIndex, boolean enemyValid, int switchId, boolean switchValid,
            int turnA, int turnB, boolean turnEnding, boolean turnValid) {
        this.actorHp = actorHp;
        this.actorId = actorId;
        this.actorValid = actorValid;
        this.enemyHp = enemyHp;
        this.enemyIndex = enemyIndex;
        this.enemyValid = enemyValid;
        this.switchId = switchId;
        this.switchValid = switchValid;
        this.turnA = turnA;
        this.turnB = turnB;
        this.turnEnding = turnEnding;
        this.turnValid = turnValid;
    }
}
