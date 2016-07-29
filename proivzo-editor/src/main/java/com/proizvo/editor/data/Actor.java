package com.proizvo.editor.data;

public class Actor {

    private long id;
    private String battlerName;
    private int characterIndex;
    private String characterName;
    private int classId;
    private int[] equips;
    private int faceIndex;
    private String faceName;
    private String[] traits;
    private int initialLevel;
    private int maxLevel;
    private String name;
    private String nickname;
    private String note;
    private String profile;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBattlerName(String battlerName) {
        this.battlerName = battlerName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCharacterIndex(int characterIndex) {
        this.characterIndex = characterIndex;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setEquips(int[] equips) {
        this.equips = equips;
    }

    public void setFaceIndex(int faceIndex) {
        this.faceIndex = faceIndex;
    }

    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }

    public void setInitialLevel(int initialLevel) {
        this.initialLevel = initialLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setTraits(String[] traits) {
        this.traits = traits;
    }
}
