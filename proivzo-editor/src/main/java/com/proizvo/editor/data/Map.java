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
}
