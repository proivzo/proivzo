package com.proizvo.editor.data;

public class Damage {

    private boolean critical;
    private int elementId;
    private String formula;
    private int type;
    private int variance;

    public Damage(boolean critical, int elementId, String formula, int type, int variance) {
        this.critical = critical;
        this.elementId = elementId;
        this.formula = formula;
        this.type = type;
        this.variance = variance;
    }
}
