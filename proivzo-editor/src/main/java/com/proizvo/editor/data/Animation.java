package com.proizvo.editor.data;

public class Animation {

    private long id;
    private int animation1Hue;
    private String animation1Name;
    private int animation2Hue;
    private String animation2Name;
    private float[][][] frames;
    private String name;
    private int position;
    private Timing[] timings;

    public void setId(long id) {
        this.id = id;
    }

    public void setAnimation1Hue(int animation1Hue) {
        this.animation1Hue = animation1Hue;
    }

    public void setAnimation2Hue(int animation2Hue) {
        this.animation2Hue = animation2Hue;
    }

    public void setAnimation1Name(String animation1Name) {
        this.animation1Name = animation1Name;
    }

    public void setAnimation2Name(String animation2Name) {
        this.animation2Name = animation2Name;
    }

    public void setFrames(float[][]... frames) {
        this.frames = frames;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimings(Timing... timings) {
        this.timings = timings;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAnimation1Hue() {
        return animation1Hue;
    }

    public String getAnimation1Name() {
        return animation1Name;
    }

    public int getAnimation2Hue() {
        return animation2Hue;
    }

    public String getAnimation2Name() {
        return animation2Name;
    }

    public float[][][] getFrames() {
        return frames;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public Timing[] getTimings() {
        return timings;
    }
}
