package com.proizvo.editor.data;

public class Airship {

    private Bgm bgm;
    private int characterIndex;
    private String characterName;
    private int startMapId;
    private int startX;
    private int startY;

    public Airship(Bgm bgm, int characterIndex, String characterName, int startMapId, int startX, int startY) {
        this.bgm = bgm;
        this.characterIndex = characterIndex;
        this.characterName = characterName;
        this.startMapId = startMapId;
        this.startX = startX;
        this.startY = startY;
    }
}
