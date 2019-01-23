package osvc.simulators.beans;

import java.util.ArrayList;
import java.util.List;

public class ToRNMsg {
    private String fromOpenId;
    private String toUserName;
    private String rightNowName;
    private String contactId;
    private List<String> msgs = new ArrayList<>();

    public String getFromOpenId() {
        return fromOpenId;
    }

    public void setFromOpenId(String fromOpenId) {
        this.fromOpenId = fromOpenId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getRightNowName() {
        return rightNowName;
    }

    public void setRightNowName(String rightNowName) {
        this.rightNowName = rightNowName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }
}
