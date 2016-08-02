package com.proizvo.editor.data;

public class Sound {

    private String name;
    private int pan;
    private int pitch;
    private int volume;

    public Sound(String name, int pan, int pitch, int volume) {
        this.name = name;
        this.pan = pan;
        this.pitch = pitch;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public int getPan() {
        return pan;
    }

    public int getPitch() {
        return pitch;
    }

    public int getVolume() {
        return volume;
    }
}
