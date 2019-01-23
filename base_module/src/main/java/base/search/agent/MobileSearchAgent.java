package base.search.agent;

import base.search.engine.AbsSearchEngine;
import base.search.utils.MobileUtil;
import base.search.wrapper.MobileScrollWrapper;
import base.search.wrapper.WaitWrapper;
import base.utils.ocr.OCRUtil;
import base.utils.ocr.beans.OneWord;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MobileSearchAgent extends CommonSearchAgent {

    public MobileSearchAgent(WebDriver driver) {
        super(driver);
    }

    public WebElement findByUi(String property) throws Exception {
        return this.findElement("ui",property);
    }

    public void clickByUi(String property) throws Exception {
        this.clickElement("ui",property);
    }


    public void waitAndSendKeysUi(String property,String keys) throws Exception {
        this.waitAndSendKeysElement("ui",property,keys);
    }


    public OneWord findWordsStartByOcr(String startStr) {
        OneWord oneWord = OCRUtil.getInstance().getWordStartWith(startStr,true);
        return oneWord;
    }

    public OneWord findWordsEndsByOcr(String endsStr) {
        OneWord oneWord = OCRUtil.getInstance().getWordEndsWith(endsStr,true);
        return oneWord;
    }

    public OneWord findWordsContainsByOcr(String containsStr) {
        OneWord oneWord = OCRUtil.getInstance().getWordContains(containsStr,true);
        return oneWord;
    }

    public OneWord findWordsExtractByOcr(String word) {
        OneWord oneWord = OCRUtil.getInstance().getExtractWord(word,true);
        return oneWord;
    }

    public void tap(int centralX, int centralY) {
        new TouchAction((MobileDriver) driver).tap(centralX, centralY).perform();
    }


    public void clickWordsStartByOcr(String startStr) {
        OneWord oneWord = findWordsStartByOcr(startStr);
        int centralX = oneWord.getLocation().getCentralX();
        int centralY = oneWord.getLocation().getCentralY();
        this.tap(centralX, centralY);
    }


    public void clickWordsEndsByOcr(String endsStr) {
        OneWord oneWord = findWordsEndsByOcr(endsStr);
        int centralX = oneWord.getLocation().getCentralX();
        int centralY = oneWord.getLocation().getCentralY();
        this.tap(centralX, centralY);
    }

    public void clickWordsContainsByOcr(String containsStr) {
        OneWord oneWord = findWordsContainsByOcr(containsStr);
        int centralX = oneWord.getLocation().getCentralX();
        int centralY = oneWord.getLocation().getCentralY();
        this.tap(centralX, centralY);
    }


    public void clickWordsExtractByOcr(String word) {
        OneWord oneWord = findWordsExtractByOcr(word);
        int centralX = oneWord.getLocation().getCentralX();
        int centralY = oneWord.getLocation().getCentralY();
        this.tap(centralX, centralY);
    }

    public boolean waitForText(String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine("ui");
        return searchEngine.waitForText(value);
    }

    public String activity(AppiumDriver driver){
        return MobileUtil.activity(driver);
    }


    //region Scroll mobile related element methods
    public boolean scrollMobileUp() {
        MobileScrollWrapper mobileScrollWrapper = new MobileScrollWrapper(driver);
        return mobileScrollWrapper.scrollUp();
    }

    public boolean scrollMobileDown() {
        MobileScrollWrapper mobileScrollWrapper = new MobileScrollWrapper(driver);
        return mobileScrollWrapper.scrollDown();
    }


    public boolean scrollMobileLeft() {
        MobileScrollWrapper mobileScrollWrapper = new MobileScrollWrapper(driver);
        return mobileScrollWrapper.scrollLeft();
    }


    public boolean scrollMobileRight() {
        MobileScrollWrapper mobileScrollWrapper = new MobileScrollWrapper(driver);
        return mobileScrollWrapper.scrollRight();
    }


    public boolean mobileScroll(int startX, int startY, int endX, int endY,float durationSecs) {
        MobileScrollWrapper mobileScrollWrapper = new MobileScrollWrapper(driver);
        return mobileScrollWrapper.scroll(startX,startY,endX,endY,durationSecs);
    }
    //endregion



    public boolean waitForActivity(String activity,int interval, int timeout) {
        WaitWrapper wWrapper = new WaitWrapper(driver,null,null);
        return wWrapper.waitForActivity(activity,interval,timeout);
    }

    public void backToLastActivity() {
        MobileUtil.back((AppiumDriver) driver);
    }

    public void backToHome() {
        MobileUtil.home((AppiumDriver) driver);
    }

    public boolean waitForTextDisappear(String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine("ui");
        return searchEngine.waitForTextDisappear(value);
    }
}
