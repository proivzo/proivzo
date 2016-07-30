package com.proizvo.editor.data;

public class Member {

    private int enemyId;
    private int x;
    private int y;
    private boolean hidden;

    public Member(int enemyId, int x, int y, boolean hidden) {
        this.enemyId = enemyId;
        this.x = x;
        this.y = y;
        this.hidden = hidden;
    }
}
