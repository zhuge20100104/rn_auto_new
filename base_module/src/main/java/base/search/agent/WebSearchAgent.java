package base.search.agent;

import base.search.engine.AbsSearchEngine;
import base.search.engine.parser.JBy;
import base.search.utils.XPathMake;
import base.search.wrapper.*;
import base.utils.ocr.OCRUtil;
import base.utils.ocr.beans.OneWord;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WebSearchAgent extends CommonSearchAgent {
    public WebSearchAgent(WebDriver driver) {
        super(driver);
    }

    public OneWord findWordsStartByOcr(String startStr) {
        OneWord oneWord = OCRUtil.getInstance().getWordStartWith(startStr,false);
        return oneWord;
    }

    public OneWord findWordsEndsByOcr(String endsStr) {
        OneWord oneWord = OCRUtil.getInstance().getWordEndsWith(endsStr,false);
        return oneWord;
    }

    public OneWord findWordsContainsByOcr(String containsStr) {
        OneWord oneWord = OCRUtil.getInstance().getWordContains(containsStr,false);
        return oneWord;
    }

    public OneWord findWordsExtractByOcr(String word) {
        OneWord oneWord = OCRUtil.getInstance().getExtractWord(word,false);
        return oneWord;
    }




    public WebElement findOrigin(String properties) {
        return findOrigin(null,properties);
    }

    public WebElement findOrigin(String eleType,String properties) {
        String xpathLocator = XPathMake.makeOwnXpath(eleType,properties);
        By by = By.xpath(xpathLocator);
        try {
            return driver.findElement(by);
        }catch (Exception ex) {
            return null;
        }
    }


    public WebElement findOriginByIndex(String properties,int nth) throws Exception {
        return findOriginByIndex(null,properties,nth);
    }

    public WebElement findOriginByIndex(String eleType,String properties,int nth) throws Exception {
        String xpathLocator =  XPathMake.makeNthXPath(eleType,properties,nth);
        By by = By.xpath(xpathLocator);
        try {
            return driver.findElement(by);
        }catch (Exception ex) {
            return null;
        }
    }

    public WebElement findOriginChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        String xpathLocator =  XPathMake.makeChildXPath(parentType,parentProperties,childType,childProperties,isDirectChild);
        By by = By.xpath(xpathLocator);
        try {
            return driver.findElement(by);
        }catch (Exception ex) {
            return null;
        }
    }

    public WebElement findOriginChild(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        return findOriginChild(parentType,parentProperties,childType,childProperties,true);
    }





    public WebElement find(String properties) throws Exception {
        return find(null,properties);
    }

    public WebElement find(String eleType,String properties)  throws Exception {
        String xpathLocator = XPathMake.makeOwnXpath(eleType,properties);
        return  findElement("xpath",xpathLocator);
    }


    public boolean wait(String properties) throws Exception {
        return wait(null,properties);
    }

    public boolean wait(String eleType,String properties)  throws Exception {
        String xpathLocator = XPathMake.makeOwnXpath(eleType,properties);
        return  waitElement("xpath",xpathLocator);
    }



    public boolean waitChild(String parentProperties, String childProperties) throws Exception {
        return waitChild(null,parentProperties,null,childProperties,true);
    }

    public boolean waitChild(String parentProperties, String childProperties,boolean isDirectChild) throws Exception {
        return waitChild(null,parentProperties,null,childProperties,isDirectChild);
    }


    public boolean waitChild(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        return waitChild(parentType,parentProperties,childType,childProperties,true);
    }

    public boolean waitChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        String xpathLocator =  XPathMake.makeChildXPath(parentType,parentProperties,childType,childProperties,isDirectChild);
        return waitElement("xpath",xpathLocator);
    }


    public boolean waitByIndex(String eleType,String properties,int nth) throws Exception {
        String xpathLocator =  XPathMake.makeNthXPath(eleType,properties,nth);
        return waitElement("xpath",xpathLocator);
    }

    public boolean waitByIndex(String properties,int nth) throws Exception {
        return waitByIndex(null,properties,nth);
    }


    public List<WebElement> findAll(String elementType, String properties) throws Exception {
        AbsSearchEngine searchEngine = this.prepareEngine("xpath");
        String xpathLocator = XPathMake.makeOwnXpath(elementType,properties);
        return searchEngine.findAll("xpath",xpathLocator);
    }


    public WebElement findByIndex(String properties,int nth) throws Exception {
        return findByIndex(null,properties,nth);
    }

    public WebElement findByIndex(String eleType,String properties,int nth) throws Exception {
        String xpathLocator =  XPathMake.makeNthXPath(eleType,properties,nth);
        return findElement("xpath",xpathLocator);
    }

    public WebElement findChild(String parentProperties, String childProperties) throws Exception{
        return findChild(null,parentProperties,null,childProperties,true);
    }

    public WebElement findChild(String parentProperties, String childProperties,boolean isDirectChild) throws Exception{
        return findChild(null,parentProperties,null,childProperties,isDirectChild);
    }

    public WebElement findChild(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        return findChild(parentType,parentProperties,childType,childProperties,true);
    }

    public WebElement findChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        String xpathLocator =  XPathMake.makeChildXPath(parentType,parentProperties,childType,childProperties,isDirectChild);
        return findElement("xpath",xpathLocator);
    }


    public void click(String properties) throws Exception {
        click(null,properties);
    }

    public void click(String eleType,String properties)  throws Exception {
        String xpathLocator = XPathMake.makeOwnXpath(eleType,properties);
        clickElement("xpath",xpathLocator);
    }

    public void clickByIndex(String properties,int nth) throws Exception {
        clickByIndex(null,properties,nth);
    }

    public void clickByIndex(String eleType,String properties,int nth) throws Exception {
        String xpathLocator =  XPathMake.makeNthXPath(eleType,properties,nth);
        clickElement("xpath",xpathLocator);
    }

    public void clickChild(String parentProperties, String childProperties) throws Exception{
        clickChild(null,parentProperties,null,childProperties,true);
    }

    public void clickChild(String parentProperties, String childProperties,boolean isDirectChild) throws Exception{
        clickChild(null,parentProperties,null,childProperties,isDirectChild);
    }

    public void clickChild(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        clickChild(parentType,parentProperties,childType,childProperties,true);
    }

    public void clickChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        String xpathLocator =  XPathMake.makeChildXPath(parentType,parentProperties,childType,childProperties,isDirectChild);
        clickElement("xpath",xpathLocator);
    }



    public void waitAndSendKeys(String properties,String keys) throws Exception {
        waitAndSendKeys(null,properties,keys);
    }

    public void waitAndSendKeys(String eleType,String properties,String keys)  throws Exception {
        String xpathLocator = XPathMake.makeOwnXpath(eleType,properties);
        this.waitAndSendKeysElement("xpath",xpathLocator,keys);
    }

    public void waitAndSendKeysByIndex(String properties,int nth,String keys) throws Exception {
        waitAndSendKeysByIndex(null,properties,nth,keys);
    }

    public void waitAndSendKeysByIndex(String eleType,String properties,int nth,String keys) throws Exception {
        String xpathLocator =  XPathMake.makeNthXPath(eleType,properties,nth);
        this.waitAndSendKeysElement("xpath",xpathLocator,keys);
    }

    public void waitAndSendKeysChild(String parentProperties, String childProperties,String keys) throws Exception{
        waitAndSendKeysChild(null,parentProperties,null,childProperties,true,keys);
    }

    public void waitAndSendKeysChild(String parentProperties, String childProperties,boolean isDirectChild,String keys) throws Exception{
        waitAndSendKeysChild(null,parentProperties,null,childProperties,isDirectChild,keys);
    }

    public void waitAndSendKeysChild(String parentType, String parentProperties, String childType,String childProperties,String keys) throws Exception{
        waitAndSendKeysChild(parentType,parentProperties,childType,childProperties,true,keys);
    }

    public void waitAndSendKeysChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild,String keys) throws Exception{
        String xpathLocator =  XPathMake.makeChildXPath(parentType,parentProperties,childType,childProperties,isDirectChild);
        this.waitAndSendKeysElement("xpath",xpathLocator,keys);
    }

    public boolean waitForText(String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine("id");
        return searchEngine.waitForText(value);
    }

    public boolean waitForTextDisappear(String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine("id");
        return searchEngine.waitForTextDisappear(value);
    }

    public void refreshPage() {
        this.driver.navigate().refresh();
    }


    public void selectByText(String type,String value,String text) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        WebElement ele = searchEngine.find(type,value);
        SelectWrapper wrapper = new SelectWrapper(driver,ele);
        wrapper.selectByText(text);
    }

    public void selectByIndex(String type,String value,int index) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        WebElement ele = searchEngine.find(type,value);
        SelectWrapper wrapper = new SelectWrapper(driver,ele);
        wrapper.selectByIndex(index);
    }



    public WebElement findElementInIFrameElement(WebElement frameEle,String type,String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        FrameWrapper fWrapper = new FrameWrapper(searchEngine,driver,frameEle);
        return fWrapper.findElementInIFrame(type,value);
    }


    public void waitAndSendKeysInIFrameElement(WebElement frameEle,String type,String value,String message) throws Exception {
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        FrameWrapper fWrapper = new FrameWrapper(searchEngine,driver,frameEle);
        fWrapper.waitAndSendKeysInIFrame(type,value,message);
    }

    public void waitAndSendKeysInIFrame(WebElement frameEle,String eleType,String properties,String message) throws Exception {
        String childXpath = XPathMake.makeOwnXpath(eleType,properties);
        this.waitAndSendKeysInIFrameElement(frameEle,"xpath",childXpath,message);
    }


    public void clickElementInIFrame(WebElement frameEle,String type,String value) throws Exception {
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        FrameWrapper fWrapper = new FrameWrapper(searchEngine,driver,frameEle);
        fWrapper.clickElementInIFrame(type,value);
    }


    public void selectListByIndex(String type,String value, int index) throws Exception{
        AbsSearchEngine listSearchEngine = this.prepareEngine(type);
        WebElement listEle = listSearchEngine.find(type,value);
        ListWrapper lWrapper = new ListWrapper(driver,listEle);
        lWrapper.selectListByIndex(index);
    }

    public List<WebElement> getListElements(String type,String value) throws Exception {
        AbsSearchEngine listSearchEngine = this.prepareEngine(type);
        WebElement listEle = listSearchEngine.find(type,value);
        ListWrapper lWrapper = new ListWrapper(driver,listEle);
        return lWrapper.getListElements();
    }

    public WebElement getListElementByIndex(String type,String value,int index) throws Exception{
        AbsSearchEngine listSearchEngine = this.prepareEngine(type);
        WebElement listEle = listSearchEngine.find(type,value);
        ListWrapper lWrapper = new ListWrapper(driver,listEle);
        return lWrapper.getListElementByIndex(index);
    }


    public void selectListByText(String type,String value,String text) throws Exception{
        AbsSearchEngine listSearchEngine = this.prepareEngine(type);
        WebElement listEle = listSearchEngine.find(type,value);
        ListWrapper lWrapper = new ListWrapper(driver,listEle);
        lWrapper.selectListByText(text);
    }


    public void selectListContainsText(String type,String value,String text) throws Exception{
        AbsSearchEngine listSearchEngine = this.prepareEngine(type);
        WebElement listEle = listSearchEngine.find(type,value);
        ListWrapper lWrapper = new ListWrapper(driver,listEle);
        lWrapper.selectListContainsText(text);
    }


    public int getListSize(String type,String value) throws Exception {
        AbsSearchEngine listSearchEngine = this.prepareEngine(type);
        WebElement listEle = listSearchEngine.find(type,value);
        ListWrapper lWrapper = new ListWrapper(driver,listEle);
        return lWrapper.getListSize();
    }


    public void handleAlert(int tryCount) {
        AlertWrapper aWrapper = new AlertWrapper(driver,null);
        aWrapper.handleAlert(tryCount);
    }

    public Alert getExistAlert(){
        AlertWrapper aWrapper = new AlertWrapper(driver,null);
        return aWrapper.getExistAlert();
    }

    public boolean isAlertPresent(){
        AlertWrapper aWrapper = new AlertWrapper(driver,null);
        return aWrapper.isAlertPresent();
    }


    public void navigateTo(String url) {
        NavigateWrapper navigateWrapper = new NavigateWrapper(driver);
        navigateWrapper.navigateTo(url);
    }

    public boolean isBrowserClosed() {
        NavigateWrapper navigateWrapper = new NavigateWrapper(driver);
        return navigateWrapper.isBrowserClosed();
    }


    public String getPageTitle() {
        NavigateWrapper navigateWrapper = new NavigateWrapper(driver);
        return navigateWrapper.getPageTitle();
    }

    public String getPageUrl() {
        NavigateWrapper navigateWrapper = new NavigateWrapper(driver);
        return navigateWrapper.getPageUrl();
    }

    public void scrollElementToView(WebElement ele) {
        WebWrapper webWrapper = new WebWrapper(driver,ele);
        webWrapper.scrollToView();
    }




    public boolean isPageTextVisible(String text) {
        PageWrapper pageWrapper = new PageWrapper(driver);
        return pageWrapper.isTextVisible(text);
    }

    public boolean doesPageTextExist(String text) {
        PageWrapper pageWrapper = new PageWrapper(driver);
        return pageWrapper.doesTextExist(text);
    }


    public String executeScript(String script) throws Exception {
        PageWrapper pageWrapper = new PageWrapper(driver);
        return pageWrapper.executeScript(script);
    }

    public String executeScriptIgnoreAlert(String script) throws Exception {
        PageWrapper pageWrapper = new PageWrapper(driver);
        return pageWrapper.executeScriptIgnoreAlert(script);
    }


    public String getPageAsXml() {
        PageWrapper pageWrapper = new PageWrapper(driver);
        return pageWrapper.getPageAsXml();
    }

    public void mouseOver(JBy by) throws Exception {
        PageWrapper pageWrapper = new PageWrapper(driver);
        pageWrapper.mouseOver(by);
    }

}
