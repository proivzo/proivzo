package com.proizvo.editor.data;

public class Troop {

    private long id;
    private Member[] members;
    private String name;
    private Page[] pages;

    public void setId(long id) {
        this.id = id;
    }

    public void setMembers(Member... members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPages(Page... pages) {
        this.pages = pages;
    }
}
