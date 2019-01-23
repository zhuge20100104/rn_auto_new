package config;

import base.utils.JsonUtil;
import config.beans.MobileWatchObject;

import java.util.List;

public class MobileWatcherConfig {
    private List<MobileWatchObject> watchObjectList;
    public MobileWatcherConfig() throws Exception{
        this.watchObjectList = JsonUtil.loadConfigType(this.getClass(),List.class,
                                                                            MobileWatchObject.class);
    }

    public List<MobileWatchObject> getWatchObjectList() {
        return watchObjectList;
    }
}
