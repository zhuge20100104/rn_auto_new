package osvc.simulators.beans;

public class Msg {
    private String toUser;
    private String type;
    private String content;
    private String templateId;
    private String url;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getUrl() {
        return url;
    }
}
