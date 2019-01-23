package osvc.pageobjects.web.SystemParameterConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SystemParameterPage extends BaseWebComponent {

    private SearchTools sTools;
    private Logger logger;
    public SystemParameterPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        this.logger = logger;
        this.sTools = new SearchTools(driver);
    }

    // Input Time Out
    private void chooseInputTimeOut(String mins) throws Exception {
        sTools.click("span", "text=" + mins + "mins");
    }

    public void chooseInputTimeOut_10mins() throws Exception {
        chooseInputTimeOut("10");
    }

    public void chooseInputTimeOut_15mins() throws Exception {
        chooseInputTimeOut("15");
    }

    public void chooseInputTimeOut_30mins() throws Exception {
        chooseInputTimeOut("30");
    }

    public void chooseInputTimeOut_45mins() throws Exception {
        chooseInputTimeOut("45");
    }

    public void chooseInputTimeOut_60mins() throws Exception {
        chooseInputTimeOut("60");
    }

    // Message Polling Frequency
    private void chooseMsgPollingFrequency(String second) throws Exception {
        sTools.click("span", "text=" + second + "s");
    }

    public void chooseMsgPollingFrequency_3s() throws Exception {
        chooseInputTimeOut("3");
    }

    public void chooseMsgPollingFrequency_5s() throws Exception {
        chooseInputTimeOut("5");
    }

    public void chooseMsgPollingFrequency_10s() throws Exception {
        chooseInputTimeOut("10");
    }

    // Chat Queue Status Update Frequency
    private void chooseChatQueueStatusUpdateFrequency(String second) throws Exception {
        sTools.click("span", "text=" + second + "s");
    }

    public void chooseChatQueueStatusUpdateFrequency_10s() throws Exception {
        chooseInputTimeOut("10");
    }

    public void chooseChatQueueStatusUpdateFrequency_30s() throws Exception {
        chooseInputTimeOut("30");
    }

    public void chooseChatQueueStatusUpdateFrequency_60s() throws Exception {
        chooseInputTimeOut("60");
    }

    public void chooseChatQueueStatusUpdateFrequency_90s() throws Exception {
        chooseInputTimeOut("90");
    }

    public void chooseIsCustomersAllowedToCreateIncidentInNBH(boolean isAllowed) throws Exception {
        if (isAllowed) {
            sTools.click("span", "text=allowed");
        } else {
            sTools.click("span", "text=not allowed");
        }
    }
}
