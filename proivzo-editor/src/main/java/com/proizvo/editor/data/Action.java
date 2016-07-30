package com.proizvo.editor.data;

public class Action {

    private int conditionParam1;
    private int conditionParam2;
    private int conditionType;
    private int rating;
    private int skillId;

    public Action(int conditionParam1, int conditionParam2, int conditionType, int rating, int skillId) {
        this.conditionParam1 = conditionParam1;
        this.conditionParam2 = conditionParam2;
        this.conditionType = conditionType;
        this.rating = rating;
        this.skillId = skillId;
    }
}
