package osvc.pageobjects.web.LogConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LogPage extends BaseWebComponent {

    private SearchTools sTools;
    private Logger logger;
    /**
     * Construct of base web component
     *
     * @param driver The selenium web driver
     * @throws Exception This method may throws exceptions
     */
    public LogPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        this.sTools = new SearchTools(driver);
        this.logger = logger;
    }

    private List<String> getSendTimeInPage() throws Exception {
        List<String> time = new ArrayList<>();
        List<WebElement> times = sTools.findAllCss("div[id='templateMsgLogDataGrid:databody'] div.oj-datagrid-row div:nth-child(3)");
        for (WebElement eachTime : times) {
            time.add(eachTime.getText());
        }

        return time;
    }

    public boolean isContainsCurrentTime(long CreateTime) throws Exception {
        List<String> time = getSendTimeInPage();
        logger.info("log time:\n" + time);
        logger.info("create time:\n" + CreateTime);
        for (String eachTime : time) {
            Timestamp ts = java.sql.Timestamp.valueOf(eachTime);
            long lEachTime = ts.getTime();
            logger.info(CreateTime+"-"+lEachTime+" =\n" + Math.abs(CreateTime - lEachTime) + "\n"+Math.abs(CreateTime - (lEachTime - 8*3600*1000)));
            if (Math.abs(CreateTime - lEachTime) < 600*1000 || Math.abs(CreateTime - (lEachTime + 8*3600*1000)) < 600*1000) {
                return true;
            }
        }
        return false;
    }
}
