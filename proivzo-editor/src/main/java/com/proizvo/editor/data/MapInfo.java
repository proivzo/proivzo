package com.proizvo.editor.data;

public class MapInfo {

    private final long id;
    private final boolean expanded;
    private final String name;
    private final long order;
    private final long parentId;
    private final double scrollX;
    private final double scrollY;

    public MapInfo(long id, boolean expanded, String name, long order,
            long parentId, double scrollX, double scrollY) {
        this.id = id;
        this.expanded = expanded;
        this.name = name;
        this.order = order;
        this.parentId = parentId;
        this.scrollX = scrollX;
        this.scrollY = scrollY;
    }

    public long getId() {
        return id;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getName() {
        return name;
    }

    public long getOrder() {
        return order;
    }

    public long getParentId() {
        return parentId;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    @Override
    public String toString() {
        return "MapInfo{" + "id=" + id + ", expanded=" + expanded
                + ", name=" + name + ", order=" + order + ", parentId=" + parentId
                + ", scrollX=" + scrollX + ", scrollY=" + scrollY + '}';
    }
}
