package config.beans;

import base.search.engine.parser.JBy;

public class MobileWatchObject {
    private String matchedActivity;
    private JBy eleToClick;
    private JBy whenElement;
    private String comment;

    public String getMatchedActivity() {
        return matchedActivity;
    }

    public void setMatchedActivity(String matchedActivity) {
        this.matchedActivity = matchedActivity;
    }

    public JBy getEleToClick() {
        return eleToClick;
    }

    public void setEleToClick(JBy eleToClick) {
        this.eleToClick = eleToClick;
    }

    public JBy getWhenElement() {
        return whenElement;
    }

    public void setWhenElement(JBy whenElement) {
        this.whenElement = whenElement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
