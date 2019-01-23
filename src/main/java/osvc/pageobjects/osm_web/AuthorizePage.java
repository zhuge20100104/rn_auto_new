package osvc.pageobjects.osm_web;

import base.BaseWebComponent;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AuthorizePage extends BaseWebComponent {

    private OSMSearchTools sTools;
    private Logger logger;

    public AuthorizePage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        this.logger = logger;
        this.sTools = new OSMSearchTools(driver);

        if (driver.getPageSource().contains("iframe")) {
            getInToMainFrame();
        }
    }

    private void getInToMainFrame() throws Exception {
        driver.switchTo().frame(sTools.find("iframe", "id=osm1|src=about:blank"));
//      another method:  driver.switchTo().frame((WebElement) driver.findElement(By.cssSelector("iframe#oms1[src='about:blank']")));
    }

    public void checkNameOrder(String AccountName) throws Exception {
        WebElement Account_Ele = chooseAccount(AccountName);
        Assert.assertTrue(sTools.getElementInWebElementParent(Account_Ele, By.className("name")).isDisplayed(), "WeChat name is displayed");
    }

    public void checkUpdateTime(String AccountName) throws Exception {
        WebElement Account_Ele = chooseAccount(AccountName);
        Assert.assertTrue(sTools.getElementInWebElementParent(Account_Ele, By.className("time")).isDisplayed(), "WeChat update time is displayed");
    }

    private void clickAddWeChatBtn() throws Exception {
        sTools.click("class=content add-account");
    }

    private void clickWeChatTypeBtn() throws Exception {
        sTools.click("button", "class=channel-button wechat");
    }

    private void clickNextBtn() throws Exception {
        sTools.click("oj-button", "class*=next-button");
    }

    private void clickAgreeTermsAndConditionsBtn() throws Exception {
        sTools.click("input", "id=agreement");
    }

    private void clickAuthorizeBtn() throws Exception {
        sTools.click("oj-button", "on-oj-action=[[authorizeWeChatAccount]]");
    }

    private boolean isNextBtnEnabled() throws Exception {
        WebElement nextBtn = sTools.find("oj-button", "class*=next-button");
        return nextBtn.getAttribute("class").contains("oj-enabled");
    }

    public void addWeChatType() throws Exception {
        clickAddWeChatBtn();
        clickWeChatTypeBtn();
        clickNextBtn();
        clickAgreeTermsAndConditionsBtn();
        clickNextBtn();
        clickAuthorizeBtn();
    }

    public void addWeChatTypeWithoutAgree() throws Exception {
        clickAddWeChatBtn();
        addWeChatType();
        clickNextBtn();
        isNextBtnEnabled();
    }

    public void addAccoutntType() throws Exception {
        clickAddWeChatBtn();
        addWeChatType();
        clickNextBtn();
    }

    public String getUpdateTime(String AccountName) throws Exception {
        WebElement Account = chooseAccount(AccountName); // TODO not finish
        return Account.findElement(By.cssSelector("#oms-account-0 > div.WeChat > div.account-type div.time")).getText();
    }

    private WebElement chooseAccount(String AccountName) throws Exception {
        WebElement ret = null;
        List<WebElement> WeChat_Accounts = driver.findElements(By.cssSelector("div.account-item"));
        for (WebElement eachAccount : WeChat_Accounts) {
            if (AccountName.equals(eachAccount.findElement(By.cssSelector("div[title='Account Name']")).getText())) {
                ret = eachAccount;
            }
        }
        return ret;
    }

    private List<String> getAccountsNames() {
        List<String> ret = new ArrayList<>();
        List<WebElement> AccountNames_ele = driver.findElements(By.cssSelector("div[title='Account Name']"));
        for (WebElement eachAccountNames_ele : AccountNames_ele) {
            ret.add(eachAccountNames_ele.getText());
        }
        return ret;
    }

    private boolean isExitAccount(String AccountName) throws Exception {
        boolean flag = false;
        List<String> ExistNames = getAccountsNames();
        for (String eachName : ExistNames) {
            flag = AccountName.equals(eachName);
        }
        return flag;
    }

    public String getCurrentHandle() {
        return driver.getWindowHandle();
    }

    public void getIntoAnotherHandles(String CurrentHandle) {
        for (String handles : driver.getWindowHandles()) {
            if (handles.equals(CurrentHandle))
                continue;
            driver.switchTo().window(handles);
        }
    }

    public boolean isExpectHandle(String ExpectHandle) {
        return ExpectHandle.equals(getCurrentHandle());
    }

    public void switchToExpectHandle(String ExpectHandle) throws Exception {
        if (!isExpectHandle(ExpectHandle)){
            driver.switchTo().window(ExpectHandle);
        }
    }

    public void getCapture() throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //利用FileUtils工具类的copyFile()方法保存getScreenshotAs()返回的文件对象。
        FileUtils.copyFile(srcFile, new File(String.format("./src/main/resources/screenshot/screenshot%s.png", Long.toString(System.currentTimeMillis()))));
    }

    public void waitAndGetCapture() throws Exception {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        new WebDriverWait(driver, 3).until(
//                driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        getCapture();
    }

//    public void getQRCode() throws Exception {
//        sTools.find(get("qrcode"));
//        URL url = new URL("url");
//        InputStream in = url.openStream();
//        FileOutputStream out = new FileOutputStream(file);
//        Streams.copy(in, out);
//        FileUtils.copyFile(qrcode, new File(String.format("./src/main/resources/screenshot/screenshot%s.png", Long.toString(System.currentTimeMillis()))));
//    }

    public void getqrcode(){
        driver.findElements(By.cssSelector("img.qrcode"));
    }
}
