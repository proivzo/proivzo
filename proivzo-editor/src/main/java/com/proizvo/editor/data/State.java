package com.proizvo.editor.data;

public class State {

    private long id;
    private int autoRemovalTiming;
    private int chanceByDamage;
    private String description;
    private int iconIndex;
    private int maxTurns;
    private String message1;
    private String message2;
    private String message3;
    private String message4;
    private int minTurns;
    private int motion;
    private String name;
    private String note;
    private int overlay;
    private int priority;
    private Boolean releaseByDamage;
    private boolean removeAtBattleEnd;
    private boolean removeByDamage;
    private boolean removeByRestriction;
    private boolean removeByWalking;
    private int restriction;
    private int stepsToRemove;
    private Trait[] traits;

    public State() {
    }

    public State(State s) {
        this.id = s.id;
        this.autoRemovalTiming = s.autoRemovalTiming;
        this.chanceByDamage = s.chanceByDamage;
        this.description = s.description;
        this.iconIndex = s.iconIndex;
        this.maxTurns = s.maxTurns;
        this.message1 = s.message1;
        this.message2 = s.message2;
        this.message3 = s.message3;
        this.message4 = s.message4;
        this.minTurns = s.minTurns;
        this.motion = s.motion;
        this.name = s.name;
        this.note = s.note;
        this.overlay = s.overlay;
        this.priority = s.priority;
        this.releaseByDamage = s.releaseByDamage;
        this.removeAtBattleEnd = s.removeAtBattleEnd;
        this.removeByDamage = s.removeByDamage;
        this.removeByRestriction = s.removeByRestriction;
        this.removeByWalking = s.removeByWalking;
        this.restriction = s.restriction;
        this.stepsToRemove = s.stepsToRemove;
        this.traits = s.traits;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setChanceByDamage(int chanceByDamage) {
        this.chanceByDamage = chanceByDamage;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public void setMinTurns(int minTurns) {
        this.minTurns = minTurns;
    }

    public void setMotion(int motion) {
        this.motion = motion;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTraits(Trait... traits) {
        this.traits = traits;
    }

    public void setRestriction(int restriction) {
        this.restriction = restriction;
    }

    public void setStepsToRemove(int stepsToRemove) {
        this.stepsToRemove = stepsToRemove;
    }

    public void setReleaseByDamage(Boolean releaseByDamage) {
        this.releaseByDamage = releaseByDamage;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public void setMessage3(String message3) {
        this.message3 = message3;
    }

    public void setMessage4(String message4) {
        this.message4 = message4;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRemoveByRestriction(boolean removeByRestriction) {
        this.removeByRestriction = removeByRestriction;
    }

    public void setRemoveAtBattleEnd(boolean removeAtBattleEnd) {
        this.removeAtBattleEnd = removeAtBattleEnd;
    }

    public void setAutoRemovalTiming(int autoRemovalTiming) {
        this.autoRemovalTiming = autoRemovalTiming;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOverlay(int overlay) {
        this.overlay = overlay;
    }

    public void setRemoveByDamage(boolean removeByDamage) {
        this.removeByDamage = removeByDamage;
    }
}
