package com.proizvo.editor.data;

public class Timing {

    private Integer conditions;
    private int[] flashColor;
    private int flashDuration;
    private int flashScope;
    private int frame;
    private Sound se;

    public Timing(Integer conditions, int flashDuration, int flashScope,
            int frame, Sound se, int... flashColor) {
        this.conditions = conditions;
        this.flashColor = flashColor;
        this.flashDuration = flashDuration;
        this.flashScope = flashScope;
        this.frame = frame;
        this.se = se;
    }

    public Integer getConditions() {
        return conditions;
    }

    public int[] getFlashColor() {
        return flashColor;
    }

    public int getFlashDuration() {
        return flashDuration;
    }

    public int getFlashScope() {
        return flashScope;
    }

    public int getFrame() {
        return frame;
    }

    public Sound getSe() {
        return se;
    }

    public void setFlashColor(int... flashColor) {
        this.flashColor = flashColor;
    }

    public void setFlashDuration(int flashDuration) {
        this.flashDuration = flashDuration;
    }

    public void setFlashScope(int flashScope) {
        this.flashScope = flashScope;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setSe(Sound se) {
        this.se = se;
    }
}
