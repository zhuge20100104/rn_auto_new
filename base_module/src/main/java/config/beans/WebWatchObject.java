package config.beans;


import base.search.engine.parser.JBy;

public class WebWatchObject {
    private String text;
    private JBy by;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JBy getBy() {
        return by;
    }

    public void setBy(JBy by) {
        this.by = by;
    }
}
