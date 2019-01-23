package config;

import base.utils.JsonUtil;
import config.beans.WebWatchObject;

import java.util.List;

public class WebWatcherConfig {
    private List<WebWatchObject> alertWatchObjectList;
    public WebWatcherConfig() throws Exception{
        this.alertWatchObjectList = JsonUtil.loadConfigType(this.getClass(),List.class,
                WebWatchObject.class);
    }

    public List<WebWatchObject> getAlertWatchObjectList() {
        return alertWatchObjectList;
    }
}
