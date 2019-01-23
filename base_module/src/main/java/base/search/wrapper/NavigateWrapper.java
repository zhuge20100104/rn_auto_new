package base.search.wrapper;

import com.google.common.collect.ImmutableSet;
import config.utils.ConfigUtil;
import config.utils.StringUtils;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class NavigateWrapper {

    private static final int  PAGE_NAVIGATION_ALERT_TRY_COUNT = 8;

    private WebDriver webDriver;

    public NavigateWrapper(WebDriver driver) {
        this.webDriver = driver;
    }

    public boolean isBrowserClosed() {
        try {
            return this.webDriver == null || StringUtils.isEmpty(this.webDriver.getWindowHandle());
        } catch (Exception ex) {
            return true;
        }
    }


    public String getRelativeBaseUrl() {
        String baseUrl = "";
        if(this.isBrowserClosed()) {
            baseUrl = ConfigUtil.getAppHost();
        }else {
            String pageUrl = this.webDriver.getCurrentUrl();
            String[] pageUrlPart = pageUrl.split("/");
            Set<String> parts = ImmutableSet.copyOf(pageUrlPart);
            if (parts.contains("home.shtml") && parts.contains("portal")) {
                baseUrl = "https://" + pageUrlPart[2];
            } else {
                baseUrl = pageUrlPart[0] + "//" + pageUrlPart[2];
            }
        }
        return baseUrl;
    }




    public void setBaseUrl(String url) {
        this.webDriver.get(url);
        AlertWrapper wrapper = new AlertWrapper(webDriver,null);
        wrapper.handleAlert(PAGE_NAVIGATION_ALERT_TRY_COUNT);
    }

    public void navigateTo(String url) {
        //Partial URL, /app/hosting/scripting.nl
        if (url.startsWith("/")) {
            url = this.getRelativeBaseUrl() + url;
        }
        setBaseUrl(url);
    }


    public String getPageTitle() {
        return this.webDriver == null ? null : this.webDriver.getTitle();
    }


    public String getPageUrl() {
        return this.webDriver == null ? null : this.webDriver.getCurrentUrl();
    }
}
