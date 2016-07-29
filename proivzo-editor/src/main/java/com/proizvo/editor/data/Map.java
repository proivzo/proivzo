package com.proizvo.editor.data;

public class Map {

    private boolean autoplayBgm;
    private boolean autoplayBgs;
    private String battleback1Name;
    private String battleback2Name;
    private Bgm bgm;
    private Bgm bgs;
    private boolean disableDashing;
    private String displayName;
    private String[] encounterList;
    private int encounterStep;
    private int height;
    private String note;
    private boolean parallaxLoopX;
    private boolean parallaxLoopY;
    private String parallaxName;
    private boolean parallaxShow;
    private int parallaxSx;
    private int parallaxSy;
    private int scrollType;
    private boolean specifyBattleback;
    private int tilesetId;
    private int width;
    private int[] data;
    private String[] events;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[] getData() {
        return data;
    }

    public int getTilesetId() {
        return tilesetId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setBattleback1Name(String battleback1Name) {
        this.battleback1Name = battleback1Name;
    }

    public void setBattleback2Name(String battleback2Name) {
        this.battleback2Name = battleback2Name;
    }

    public void setEvents(String[] events) {
        this.events = events;
    }

    public void setBgm(Bgm bgm) {
        this.bgm = bgm;
    }

    public void setBgs(Bgm bgs) {
        this.bgs = bgs;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEncounterList(String[] encounterList) {
        this.encounterList = encounterList;
    }

    public void setEncounterStep(int encounterStep) {
        this.encounterStep = encounterStep;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setParallaxName(String parallaxName) {
        this.parallaxName = parallaxName;
    }

    public void setParallaxShow(boolean parallaxShow) {
        this.parallaxShow = parallaxShow;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTilesetId(int tilesetId) {
        this.tilesetId = tilesetId;
    }

    public void setData(int[] data) {
        this.data = data;
    }
}
