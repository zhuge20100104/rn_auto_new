package base.search;

import base.annotations.MobileOnly;
import base.annotations.WebAndMobile;
import base.annotations.WebOnly;
import base.search.agent.CommonSearchAgent;
import base.search.agent.MobileSearchAgent;
import base.search.agent.WebSearchAgent;
import base.search.engine.parser.JBy;
import base.search.utils.XPathMake;
import base.utils.ocr.beans.OneWord;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Search tools for base
 * osvc original
 * @author Fredric zhu
 * Created on Oct 2018
 */

public class SearchTools {

    /**expose web driver to sub class to support inheritance*/
    protected WebDriver driver;
    /**search agent can be applied to both web and mobile*/
    protected CommonSearchAgent commonSearchAgent;
    /**search agent can be applied to only mobile*/
    protected MobileSearchAgent mobileSearchAgent;
    /**search agent can be applied to only web*/
    protected WebSearchAgent webSearchAgent;


    /**
     * public constructor to construct a search tool
     * @param driver selenium web driver
     */
    public SearchTools(WebDriver driver) {
        this.driver = driver;
        commonSearchAgent = new CommonSearchAgent(driver);
        mobileSearchAgent = new MobileSearchAgent(driver);
        webSearchAgent = new WebSearchAgent(driver);
    }

    /**
     *  You can also get web driver in any where use this method
     *  @return The original selenium web driver
     */
    @WebAndMobile
    public WebDriver getDriver() {
        return driver;
    }


    /**
     * The find element method find a single element from Ui using specific locator
     * <br> Current supported locators are id, name,xpath,css,class, and ui
     * <br> The Ui locator is for mobile only
     * @param type The locator type, which should be id, name,xpath,css,class, or ui
     * @param value locator value.
     * @return  A selenium web element
     * @throws Exception  When the element is not shown, this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public WebElement findElement(String type,String value) throws Exception{
        return commonSearchAgent.findElement(type,value);
    }


    /**
     * The click element method click a single element from Ui using specific locator
     * <br> Current supported locators are id, name,xpath,css,class, and ui
     * <br> The Ui locator is for mobile only
     * @param type The locator type, which should be id, name,xpath,css,class, or ui
     * @param value locator value.
     * @throws Exception  When the element is not shown, this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public void clickElement(String type,String value) throws Exception{
        commonSearchAgent.clickElement(type,value);
    }



    /**
     * The wait and send keys element method clear a text field and then send specific keys to it
     * @param type  The locator type, which should be id, name,xpath,css,class, or ui
     * @param value locator value.
     * @param keys The keys you want to send to the text field.
     * @throws Exception When the element is not shown, this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public void waitAndSendKeysElement(String type,String value,String keys) throws Exception{
        commonSearchAgent.waitAndSendKeysElement(type,value,keys);
    }


    /**
     * The find all elements method find all elements using the same locator on the Ui,
     * <br> This method is typically used when you want to interact with a list of web elements
     * @param type   The locator type, which should be id, name,xpath,css,class, or ui
     * @param value locator value.
     * @return   A list of web element
     * @throws Exception   When the element are not shown, this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public List<WebElement> findAllElement(String type,String value) throws Exception {
        return commonSearchAgent.findAllElement(type,value);
    }


    /**
     * The find by index element method find the elements has the same locator on the Ui
     * <br> and get the nth element of them.
     * @param type   The locator type, which should be id, name,xpath,css,class, or ui
     * @param value  locator value.
     * @param index  Which one you want to get in the element collection
     * @return  a selenium web element
     * @throws Exception  When there is no such element,  this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public WebElement findByIndexElement(String type,String value, int index) throws Exception {
        return commonSearchAgent.findByIndexElement(type,value,index);
    }




    //region Xpath locators,for both web and mobile

    /**
     * Find an element by xpath
     * @param xpath  The xpath locator of the element
     * @return  A selenium web element
     * @throws Exception  When locating fails, this method will throw an exception
     */
    @WebAndMobile
    public WebElement findByXPath(String xpath) throws Exception {
        return this.findElement("xpath",xpath);
    }


    /**
     * Click an element by xpath
     * @param xpath  The xpath locator of the element
     * @throws Exception  When locating fails, this method will throw an exception
     */
    @WebAndMobile
    public void clickByXPath(String xpath) throws Exception {
        this.clickElement("xpath",xpath);
    }


    /**
     * Wait and send keys to a web element by xpath
     * @param xpath The xpath locator of the element
     * @param keys  The keys you want to send to a text field
     * @throws Exception When locating fails, this method will throw an exception
     */
    @WebAndMobile
    public void waitAndSendKeysXPath(String xpath,String keys) throws Exception {
        this.waitAndSendKeysElement("xpath",xpath,keys);
    }

    /**
     * Find element by index using xpath
     * <br>See also {@link base.search.SearchTools#findByIndexElement(String, String, int)}
     * @param value  xpath value for the web elements
     * @param index  which one you want to get
     * @return  A selenium web element
     * @throws Exception   When locating fails, this method may throw an exception
     */
    @WebAndMobile
    public WebElement findByIndexXPath(String value, int index) throws Exception {
        return this.findByIndexElement("xpath",value,index);
    }


    /**
     * Find a list of elements using xpath
     * @param value  Xpath value for the list of elements
     * @return  A list of web elements
     * @throws Exception  When locating fails, this method may throw an exception.
     */
    @WebAndMobile
    public List<WebElement> findAllXPath(String value) throws Exception {
        return this.findAll("xpath",value);
    }
    //endregion


    //region id locators, for both web and mobile

    /**
     * Find a web element by id
     * <br> See also {@link base.search.SearchTools#findElement(String, String)}
     * @param id  The id locator of the current web element
     * @return  A selenium web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @Deprecated
    @WebOnly
    public WebElement findById(String id) throws Exception {
        return this.findElement("id",id);
    }



    /**
     * Click a web element by id
     * <br> See also {@link base.search.SearchTools#clickElement(String, String)}
     * @param id  The id locator of the current web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @Deprecated
    @WebOnly
    public void clickById(String id) throws Exception {
        this.clickElement("id",id);
    }

    /**
     * Wait and send keys to a web element by id
     * <br> See also {@link base.search.SearchTools#waitAndSendKeysElement(String, String, String)}
     * @param id The id locator of the current web element
     * @param keys The keys you want to send to the web element
     * @throws Exception   When locating fails, this method may throw an exception
     */
    @Deprecated
    @WebOnly
    public void waitAndSendKeysId(String id,String keys) throws Exception {
        this.waitAndSendKeysElement("id",id,keys);
    }

    //endregion


    //region name locators,for web only

    /**
     * Find a web element by name
     * <br>See also {@link base.search.SearchTools#findElement(String, String)}
     * @param name  The web element's name
     * @return A selenium web element
     * @throws Exception  When locating fails, this method will throw an exception
     */
    @WebOnly
    public WebElement findByName(String name) throws Exception {
        return this.findElement("name",name);
    }


    /**
     * Click a web element by name
     * <br> See also {@link base.search.SearchTools#clickElement(String, String)}
     * @param name The web element's name
     * @throws Exception When locating fails, this method will throw an exception
     */
    @Deprecated
    @WebOnly
    public void clickByName(String name) throws Exception {
        this.clickElement("name",name);
    }


    /**
     * wait and send keys to a web element by name
     * <br> See also {@link base.search.SearchTools#waitAndSendKeysElement(String, String, String)}
     * @param name The web element's name
     * @param keys The keys you want to send to the web element
     * @throws Exception When locating fails, this method will throw an exception
     */
    @Deprecated
    @WebOnly
    public void waitAndSendKeysName(String name,String keys) throws Exception {
        this.waitAndSendKeysElement("name",name,keys);
    }


    /**
     * Find a web element from element list by name
     * <br> See also {@link base.search.SearchTools#findByIndexElement(String, String, int)}
     * @param value  The web elements' name
     * @param index The index for which one you want to get
     * @return A selenium web element
     * @throws Exception When locating fails, this method will throw an exception
     */
    @Deprecated
    @WebOnly
    public WebElement findByIndexName(String value, int index) throws Exception {
        return this.findByIndexElement("name",value,index);
    }

    /**
     * Find web element list by name
     * <br> {@link base.search.SearchTools#findAllElement(String, String)}
     * @param value The web elements' name
     * @return a list of web elements
     * @throws Exception  When locating fails, this method will throw an exception
     */
    @Deprecated
    @WebOnly
    public List<WebElement> findAllName(String value) throws Exception {
        return this.findAllElement("name",value);
    }
    //endregion


    //region css locators, for both web and mobile

    /**
     * Find web element by css selector
     * @param css Css selector of the web element
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebAndMobile
    public WebElement findByCss(String css) throws Exception {
        return this.findElement("css",css);
    }


    /**
     * Click web element by css selector
     * <br> See also {@link base.search.SearchTools#clickElement(String, String)}
     * @param css Css selector of the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebAndMobile
    public void clickByCss(String css) throws Exception {
        this.clickElement("css",css);
    }

    /**
     * Wait and send keys to a web element using css
     * <br> See also {@link base.search.SearchTools#waitAndSendKeysElement(String, String, String)}
     * @param css  Css selector of the web element
     * @param keys Keys you want to send to the web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public void waitAndSendKeysCss(String css,String keys) throws Exception {
        this.waitAndSendKeysElement("css",css,keys);
    }


    /**
     * Find web element from web element list by css selector
     * <br> See also {@link base.search.SearchTools#findByIndexElement(String, String, int)}
     * @param value  Css selector of the web elements
     * @param index  Index of which one you want to interact with
     * @return A selenium web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public WebElement findByIndexCss(String value, int index) throws Exception {
        return this.findByIndexElement("css",value,index);
    }


    /**
     * Find  web element list by css selector
     * <br> See also {@link base.search.SearchTools#findAllElement(String, String)}
     * @param value Css value of the web elements
     * @return A list of web elements
     * @throws Exception When locating fails, this method may throw an exception
     */
    @Deprecated
    @WebAndMobile
    public List<WebElement> findAllCss(String value) throws Exception {
        return this.findAllElement("css",value);
    }
    //endregion


    //region class name locators, for web only

    /**
     * Find web element by class Name
     * See also {@link base.search.SearchTools#findElement(String, String)}
     * @param className The class name of the web element
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @Deprecated
    @WebOnly
    public WebElement findByClass(String className) throws Exception {
        return this.findElement("class",className);
    }


    /**
     * Click web element by class Name
     * See also {@link base.search.SearchTools#clickElement(String, String)}
     * @param className The class name of the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickByClass(String className) throws Exception {
        this.clickElement("class",className);
    }


    /**
     * Wait and send keys to a web element by class name
     * See also {@link base.search.SearchTools#waitAndSendKeysElement(String, String, String)}
     * @param className  The class name of the web element
     * @param keys The keys you want to send to the web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysClass(String className,String keys) throws Exception {
        this.waitAndSendKeysElement("class",className,keys);
    }


    /**
     * Find a web element from element list by class name
     * See also {@link base.search.SearchTools#findByIndexElement(String, String, int)}
     * @param value The class name of element list
     * @param index Which one you want to interact with
     * @return A selenium web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findByIndexClass(String value, int index) throws Exception {
        return this.findByIndexElement("class",value,index);
    }

    /**
     * Find web element list by class name
     * @param value The class name of element list
     * @return A list of selenium web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public List<WebElement> findAllClass(String value) throws Exception {
        return this.findAllElement("class",value);
    }
    //endregion



    //region For mobile Only, find by android ui automator

    /**
     * This method is for mobile only
     * <br>Find a mobile element by android ui automator selector
     * @param property Ui automator selector property,support
     *                 <br> res=[Resource ID], text=[text value],desc=[content description]
     * @return An Appium mobile element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @MobileOnly
    public WebElement findByUi(String property) throws Exception {
        return mobileSearchAgent.findByUi(property);
    }

    /**
     * This method is for mobile only
     * <br> Click a mobile element by android ui automator selector
     * @param property Ui automator selector property,support
     *                <br> res=[Resource ID], text=[text value],desc=[content description]
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @MobileOnly
    public void clickByUi(String property) throws Exception {
        mobileSearchAgent.clickByUi(property);
    }


    /**
     * This method is for mobile only.
     * <br> Wait and send keys to a mobile element
     * @param property Ui automator selector property,support
     *                <br> res=[Resource ID], text=[text value],desc=[content description]
     * @param keys  The keys you want to send to the mobile element
     * @throws Exception   When locating fails, this method may throw an exception
     */
    @MobileOnly
    public void waitAndSendKeysUi(String property,String keys) throws Exception {
        mobileSearchAgent.waitAndSendKeysUi(property,keys);
    }
    //endregion


    //region For web Only,  ocr related method

    /**
     * This method is for web only.
     * Find words starts with a specific string using OCR
     * @param startStr The string you want to match
     * @return  The word as an object.
     *          <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @WebOnly
    public OneWord findWordsStartByOcrWeb(String startStr) {
        return webSearchAgent.findWordsStartByOcr(startStr);
    }

    /**
     * This method is for web only
     * Find words End with a specific string using OCR
     * @param endsStr The string you want to match
     * @return The word as an object.
     *         <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @WebOnly
    public OneWord findWordsEndsByOcrWeb(String endsStr) {
        return webSearchAgent.findWordsEndsByOcr(endsStr);
    }


    /**
     * This method is for web only
     * Find words contains a specific string using OCR
     * @param containsStr The string you want to match
     * @return The word as an object.
     *         <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @WebOnly
    public OneWord findWordsContainsByOcrWeb(String containsStr) {
        return webSearchAgent.findWordsContainsByOcr(containsStr);
    }


    /**
     *  This method is for web only
     *  Find words exactly matched  using OCR
     * @param word  The string you want to match
     * @return The word as an object.
     *         <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @WebOnly
    public OneWord findWordsExtractByOcrWeb(String word) {
        return webSearchAgent.findWordsExtractByOcr(word);
    }
    //endregion



    //region For mobile Only,  ocr related method

    /**
     *  This method is for mobile only.
     *  Find words starts with a specific string using OCR
     * @param startStr The words you want to match
     * @return  The word as an object.
     *          <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @MobileOnly
    public OneWord findWordsStartByOcrMobile(String startStr) {
        return mobileSearchAgent.findWordsStartByOcr(startStr);
    }


    /**
     *  This method is for mobile only.
     *  Find words ends with a specific string using OCR
     * @param endsStr The words you want to match
     * @return  The word as an object.
     *          <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @MobileOnly
    public OneWord findWordsEndsByOcr(String endsStr) {
        return mobileSearchAgent.findWordsEndsByOcr(endsStr);
    }


    /**
     *  This method is for mobile only.
     *  Find words contains a specific string using OCR
     * @param containsStr The words you want to match
     * @return  The word as an object.
     *          <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @MobileOnly
    public OneWord findWordsContainsByOcr(String containsStr) {
        return mobileSearchAgent.findWordsContainsByOcr(containsStr);
    }



    /**
     *  This method is for mobile only.
     *  Find words exactly matched  using OCR
     * @param word The words you want to match
     * @return  The word as an object.
     *          <br> See also {@link base.utils.ocr.beans.OneWord}
     */
    @MobileOnly
    public OneWord findWordsExtractByOcr(String word) {
        return mobileSearchAgent.findWordsExtractByOcr(word);
    }


    /**
     * This method is for mobile only.
     * Tap a location on the mobile
     * @param centralX  The x axis you want to tap
     * @param centralY  The y axis you want to tap
     */
    @MobileOnly
    public void tapMobile(int centralX, int centralY) {
        mobileSearchAgent.tap(centralX,centralY);
    }

    /**
     * This method is for mobile only.
     * Click a text string using OCR on mobile, The string starts with {startStr}
     * @param startStr The string you want to match
     */
    @MobileOnly
    public void clickWordsStartByOcrMobile(String startStr) {
        mobileSearchAgent.clickWordsStartByOcr(startStr);
    }


    /**
     * This method is for mobile only.
     * Click a text string using OCR on mobile, The string ends with {endsStr}
     * @param endsStr The string you want to match
     */
    @MobileOnly
    public void clickWordsEndsByOcrMobile(String endsStr) {
        mobileSearchAgent.clickWordsEndsByOcr(endsStr);
    }


    /**
     * This method is for mobile only.
     * Click a text string using OCR on mobile, The string contains {containsStr}
     * @param containsStr The string you want to match
     */
    @MobileOnly
    public void clickWordsContainsByOcrMobile(String containsStr) {
        mobileSearchAgent.clickWordsContainsByOcr(containsStr);
    }

    /**
     * This method is for mobile only.
     * Click a text string using OCR on mobile, The string exactly matched {word}
     * @param word The string you want to match
     */
    @MobileOnly
    public void clickWordsExtractByOcrMobile(String word) {
        mobileSearchAgent.clickWordsExtractByOcr(word);
    }
    //endregion


    //region  Find using origin selenium methods with no wait wrapper,for web only

    /**
     * This method is for web only.
     * <br> Find a web element using properties selector without wait, not safe
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @return A selenium web element
     */
    @WebOnly
    public WebElement findOriginWeb(String properties) {
        return webSearchAgent.findOrigin(properties);
    }

    /**
     * This method is for web only.
     * <br> Find a web element using properties selector without wait, not safe
     * @param eleType The element type you want to match with, eg. [div]  [span] or [a]
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @return A selenium web element
     */
    @WebOnly
    public WebElement findOriginWeb(String eleType,String properties) {
        return webSearchAgent.findOrigin(eleType,properties);
    }

    /**
     * This method is for web only.
     * <br> Find a web element in a element list using index without wait, no safe
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @param nth Which element you want interact with,specify the index
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findOriginByIndexWeb(String properties,int nth) throws Exception {
        return webSearchAgent.findOriginByIndex(properties,nth);
    }

    /**
     * This method is for web only.
     * <br> Find a web element in a element list using index without wait, no safe
     * @param eleType The element type you want to match with, eg. [div]  [span] or [a]
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @param nth Which element you want interact with,specify the index
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findOriginByIndexWeb(String eleType,String properties,int nth) throws Exception {
        return webSearchAgent.findOriginByIndex(eleType,properties,nth);
    }

    /**
     * This method is for web only.
     * <br> Find a child element in a parent element without wait, no safe
     * @param parentType  The parent element type, eg. [div]  [span] or [a]
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType  The child element type, eg. [div]  [span] or [a]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild  Whether the child is directly child
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    public WebElement findOriginChildWeb(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        return webSearchAgent.findOriginChild(parentType,parentProperties,childType,childProperties,isDirectChild);
    }

    /**
     * This method is for web only.
     * <br> Find a child element in a parent element, This method assume the child is a directly child.
     * <br> No wait for this method, not safe.
     * @param parentType  The parent element type, eg. [div]  [span] or [a]
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType  The child element type, eg. [div]  [span] or [a]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    public WebElement findOriginChildWeb(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        return webSearchAgent.findOriginChild(parentType,parentProperties,childType,childProperties);
    }
    //endregion find origin methods region




    //region Recommend --> find Web element related methods


    /**
     * This method is for web only.
     * <br> Find a web element using properties selector
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement find(String properties) throws Exception {
        return webSearchAgent.find(properties);
    }


    /**
     * This method is for web only.
     * <br> Find a web element using properties selector
     * @param eleType The element type you want to match with, eg. [div] [span]
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement find(String eleType,String properties)  throws Exception {
        return webSearchAgent.find(eleType,properties);
    }



    /**
     * This method is for web only.
     * <br> Find a web element in a element list using index
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @param nth Which element you want interact with,specify the index
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findByIndex(String properties,int nth) throws Exception {
        return webSearchAgent.findByIndex(properties,nth);
    }



    /**
     * This method is for web only.
     * <br> Find a web element in a element list using index
     * @param eleType The element type you want to match with, eg. [div],[span]
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @param nth Which element you want interact with,specify the index
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findByIndex(String eleType,String properties,int nth) throws Exception {
        return webSearchAgent.findByIndex(eleType,properties,nth);
    }


    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * <br> This method assume the child is directly child
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findChild(String parentProperties, String childProperties) throws Exception{
        return webSearchAgent.findChild(parentProperties,childProperties);
    }

    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild Whether the child is directly child of the parent
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findChild(String parentProperties, String childProperties,boolean isDirectChild) throws Exception{
        return webSearchAgent.findChild(parentProperties,childProperties,isDirectChild);
    }

    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * @param parentType The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findChild(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        return webSearchAgent.findChild(parentType,parentProperties,childType,childProperties);
    }


    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * @param parentType The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild  Whether the child is directly child of the parent
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        return webSearchAgent.findChild(parentType,parentProperties,childType,childProperties,isDirectChild);
    }


    /**
     * This method is for web only.
     * <br> Find web element list using properties selector
     * @param elementType  The element type you want to match with, eg. [div],[span]
     * @param properties  The element properties selector, eg. [id=xx|class=xx]
     * @return A list of web elements
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public List<WebElement> findAll(String elementType, String properties) throws Exception {
        return webSearchAgent.findAll(elementType,properties);
    }
    //endregion


    //region click Web element related methods

    /**
     * This method is for web only.
     * Click a web element using properties locator
     * @param properties  The element properties selector, eg. [id=xx|class=xx]
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void click(String properties) throws Exception {
        webSearchAgent.click(properties);
    }

    /**
     * This method is for web only.
     * <br> Click a web element using properties locator
     * @param eleType  The element type you want to match with, eg. [div],[span]
     * @param properties The element properties selector, eg. [id=xx|class=xx]
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void click(String eleType,String properties)  throws Exception {
        webSearchAgent.click(eleType,properties);
    }


    /**
     * This method is for web only.
     * <br> Click a web element of web element list using properties locator
     * @param properties  The element properties selector, eg. [id=xx|class=xx]
     * @param nth Which one you want to interact with
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickByIndex(String properties,int nth) throws Exception {
        webSearchAgent.clickByIndex(properties,nth);
    }

    /**
     * This method is for web only.
     * <br> Click a web element of web element list using properties locator
     * @param eleType The element type you want to match with, eg. [div],[span]
     * @param properties The element properties selector, eg. [id=xx|class=xx]
     * @param nth Which one you want to interact with
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickByIndex(String eleType,String properties,int nth) throws Exception {
        webSearchAgent.clickByIndex(eleType,properties,nth);
    }


    /**
     * This method is for web only.
     * <br> Click a child web element in a parent web element
     * @param parentProperties The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties The child element properties selector, eg. [id=xx|class=xx]
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickChild(String parentProperties, String childProperties) throws Exception{
        webSearchAgent.clickChild(parentProperties,childProperties);
    }

    /**
     * This method is for web only.
     * <br> Click a child web element in a parent web element
     * @param parentProperties The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild Whether the child element is directly child
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickChild(String parentProperties, String childProperties,boolean isDirectChild) throws Exception{
        webSearchAgent.clickChild(parentProperties,childProperties,isDirectChild);
    }


    /**
     * This method is for web only.
     * <br> Click a child web element in a parent web element
     * @param parentType The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties The child element properties selector, eg. [id=xx|class=xx]
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickChild(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        webSearchAgent.clickChild(parentType,parentProperties,childType,childProperties);
    }

    /**
     * This method is for web only.
     * <br> Click a child web element in a parent web element
     * @param parentType  The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild Whether the child element is directly child
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        webSearchAgent.clickChild(parentType,parentProperties,childType,childProperties,isDirectChild);
    }
    //endregion


    //region wait and send keys Web element related methods

    /**
     * This method is for web only.
     * <br> Wait and send keys to a web element using properties selector
     * @param properties  The element properties selector, eg. [id=xx|class=xx]
     * @param keys The keys you want to send to the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeys(String properties,String keys) throws Exception {
        webSearchAgent.waitAndSendKeys(properties,keys);
    }

    /**
     * This method is for web only.
     * <br> Wait and send keys to a web element using properties selector
     * @param eleType The element type you want to match with, eg. [div],[span]
     * @param properties The element properties selector, eg. [id=xx|class=xx]
     * @param keys The keys you want to send to the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeys(String eleType,String properties,String keys)  throws Exception {
        webSearchAgent.waitAndSendKeys(eleType,properties,keys);
    }

    /**
     * This method is for web only
     * Wait and send keys to a web element in web element list
     * @param properties  The element properties selector, eg. [id=xx|class=xx]
     * @param nth Which one you want to interact with in the list
     * @param keys The keys you want to send to the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysByIndex(String properties,int nth,String keys) throws Exception {
        webSearchAgent.waitAndSendKeysByIndex(properties,nth,keys);
    }

    /**
     * This method is for web only
     * Wait and send keys to a web element in web element list
     * @param eleType The element type you want to match with, eg. [div],[span]
     * @param properties  The element properties selector, eg. [id=xx|class=xx]
     * @param nth Which one you want to interact with in the list
     * @param keys The keys you want to send to the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysByIndex(String eleType,String properties,int nth,String keys) throws Exception {
        webSearchAgent.waitAndSendKeysByIndex(eleType,properties,nth,keys);
    }

    /**
     * This method is for web only
     * <br> Wait and send keys to a child web element in a parent web element
     * @param parentProperties   The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @param keys The keys you want to send to the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysChild(String parentProperties, String childProperties,String keys) throws Exception{
        webSearchAgent.waitAndSendKeysChild(parentProperties,childProperties,keys);
    }

    /**
     *  This method is for web only
     *  <br> Wait and send keys to a child web element in a parent web element
     * @param parentProperties The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild Whether the child element is directly child of the parent
     * @param keys  The keys you want to send to the web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysChild(String parentProperties, String childProperties,boolean isDirectChild,String keys) throws Exception{
        webSearchAgent.waitAndSendKeysChild(parentProperties,childProperties,isDirectChild,keys);
    }

    /**
     * This method is for web only
     * <br> Wait and send keys to a child web element in a parent web element
     * @param parentType The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties The child element properties selector, eg. [id=xx|class=xx]
     * @param keys  The keys you want to send to the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysChild(String parentType, String parentProperties, String childType,String childProperties,String keys) throws Exception{
        webSearchAgent.waitAndSendKeysChild(parentType,parentProperties,childType,childProperties,keys);
    }

    /**
     * This method is for web only
     * <br> Wait and send keys to a child web element in a parent web element
     * @param parentType  The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild Whether the child element is directly child of the parent
     * @param keys  The keys you want to send to the web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild,String keys) throws Exception{
        webSearchAgent.waitAndSendKeysChild(parentType,parentProperties,childType,childProperties,isDirectChild,keys);
    }
    //endregion


    /**
     * Wait for an selenium element to be interacted
     * @param type The web element locator type ,can be one of id,name,css,class,xpath and ui
     * @param value The locator value
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebAndMobile
    public boolean waitElement(String type,String value) throws Exception{
        return commonSearchAgent.waitElement(type,value);
    }



    @WebOnly
    public boolean wait(String properties) throws Exception{
       // return commonSearchAgent.wait(type,value);
        return  webSearchAgent.wait(properties);
    }

    public boolean wait(String eleType,String properties)  throws Exception {
        return webSearchAgent.wait(eleType,properties);
    }



    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * <br> This method assume the child is directly child
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitChild(String parentProperties, String childProperties) throws Exception{
        return webSearchAgent.waitChild(parentProperties,childProperties);
    }

    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild Whether the child is directly child of the parent
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitChild(String parentProperties, String childProperties,boolean isDirectChild) throws Exception{
        return webSearchAgent.waitChild(parentProperties,childProperties,isDirectChild);
    }

    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * @param parentType The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitChild(String parentType, String parentProperties, String childType,String childProperties) throws Exception{
        return webSearchAgent.waitChild(parentType,parentProperties,childType,childProperties);
    }


    /**
     * This method is for web only.
     * <br> Find a child element in a parent element
     * @param parentType The parent element type you want to match with, eg. [div],[span]
     * @param parentProperties  The parent element properties selector, eg. [id=xx|class=xx]
     * @param childType The child element type you want to match with, eg. [div],[span]
     * @param childProperties  The child element properties selector, eg. [id=xx|class=xx]
     * @param isDirectChild  Whether the child is directly child of the parent
     * @return The child web element
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitChild(String parentType, String parentProperties, String childType,String childProperties,boolean isDirectChild) throws Exception{
        return webSearchAgent.waitChild(parentType,parentProperties,childType,childProperties,isDirectChild);
    }




    /**
     * This method is for web only.
     * <br> Find a web element in a element list using index
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @param nth Which element you want interact with,specify the index
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitByIndex(String properties,int nth) throws Exception {
        return webSearchAgent.waitByIndex(properties,nth);
    }



    /**
     * This method is for web only.
     * <br> Find a web element in a element list using index
     * @param eleType The element type you want to match with, eg. [div],[span]
     * @param properties  The properties selector you want to use,eg. [id=xx|class=xx]
     * @param nth Which element you want interact with,specify the index
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitByIndex(String eleType,String properties,int nth) throws Exception {
        return webSearchAgent.waitByIndex(eleType,properties,nth);
    }




    /**
     * This method is for web only
     * <br> Wait for a text to be visible in a web page
     * @param value The text value
     * @return Whether the text is visible at last
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitForTextWeb(String value) throws Exception{
        return webSearchAgent.waitForText(value);
    }

    /**
     * This method is for mobile only
     * <br> Wait for a text to be visible on a mobile page
     * @param value The text value
     * @return Whether the text is visible at last
     * @throws Exception When locating fails, this method may throw an exception
     */
    @MobileOnly
    public boolean waitForTextMobile(String value) throws Exception{
        return mobileSearchAgent.waitForText(value);
    }


    /**
     * Wait for a selenium element to be disappeared
     * @param type  The web element locator type ,can be one of id,name,css,class,xpath and ui
     * @param value The locator value
     * @return Whether the element is disappeared at last
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebAndMobile
    public boolean waitForDisappear(String type,String value) throws Exception{
        return commonSearchAgent.waitForDisappear(type,value);
    }

    /**
     * The method is for web only
     * <br> Wait for a text disappeared on a web page
     * @param value  The text value
     * @return   Whether the text is disappeared at last
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public boolean waitForTextDisappearWeb(String value) throws Exception{
        return webSearchAgent.waitForTextDisappear(value);
    }


    /**
     * The method is for mobile only
     * <br> Wait for a text disappeared on a mobile page
     * @param value The text value
     * @return  Whether the text is disappeared at last
     * @throws Exception When locating fails, this method may throw an exception
     */
    @MobileOnly
    public boolean waitForTextDisappearMobile(String value) throws Exception{
        return mobileSearchAgent.waitForTextDisappear(value);
    }






    //region return the current activity name,for mobile only

    /**
     * This method is for mobile only
     * <br> Get the current activity of the app
     * @param driver The mobile driver
     * @return The name of the current activity
     */
    @MobileOnly
    public String activity(AppiumDriver driver){
        return mobileSearchAgent.activity(driver);
    }
    //endregion

    //Refresh the current web page

    /**
     * This method is for web only.
     * <br> Refresh the current web page.
     */
    @WebOnly
    public void refreshPage() {
        webSearchAgent.refreshPage();
    }


    /**
     * Wait for the current selenium element enabled
     * @param type  The locator type, which should be id, name,xpath,css,class, or ui
     * @param value the locator value
     * @return Whether the web element is enabled at last
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebAndMobile
    public boolean waitForEnabled(String type,String value) throws Exception {
        return commonSearchAgent.waitForEnabled(type,value);
    }

    //Method for selectors

    /**
     * This method is for web only
     * <br> Select a specific value in a selector control
     * @param type   The locator type, which should be id, name,xpath,css or class,can't be ui
     * @param value   The locator value
     * @param text    The value you want to select
     * @throws Exception    When locating fails, this method may throw an exception
     */
    @WebOnly
    public void selectByText(String type,String value,String text) throws Exception{
        webSearchAgent.selectByText(type,value,text);
    }


    /**
     * This method is for web only
     * <br> Select a specific index in a selector control
     * @param type   The locator type, which should be id, name,xpath,css or class,can't be ui
     * @param value   The locator value
     * @param index    The index you want to select
     * @throws Exception    When locating fails, this method may throw an exception
     */
    @WebOnly
    public void selectByIndex(String type,String value,int index) throws Exception{
        webSearchAgent.selectByIndex(type,value,index);
    }
    //End Method for selectors




    //Method for find Element in IFrame

    /**
     * This method is for web only
     * <br> Find a web element in a frame element
     * @param frameEle  The frame element you want to locator sub element in
     * @param type  Sub element type, can be id, name,xpath,css or class,can't be ui
     * @param value  Sub element locator value
     * @return The sub element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement findElementInIFrameElement(WebElement frameEle,String type,String value) throws Exception{
        return webSearchAgent.findElementInIFrameElement(frameEle,type,value);
    }


    /**
     * This method is for web only
     * <br> Wait and send keys to a sub element in frame element
     * @param frameEle The frame element you want to locator sub element in
     * @param type Sub element type, can be id, name,xpath,css or class,can't be ui
     * @param value Sub element locator value
     * @param message The keys you want to send to sub element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysInIFrameElement(WebElement frameEle,String type,String value,String message) throws Exception {
        webSearchAgent.waitAndSendKeysInIFrameElement(frameEle,type,value,message);
    }


    /**
     * This method is for web only
     * <br> Wait and send keys to a sub element in frame element
     * @param frameEle The frame element you want to locator sub element in
     * @param eleType The sub element type, eg. [span] [div]
     * @param properties  The sub element properties selector, eg. [id=xx|class=xx]
     * @param message   The keys you want to send to sub element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void waitAndSendKeysInIFrame(WebElement frameEle,String eleType,String properties,String message) throws Exception {
        webSearchAgent.waitAndSendKeysInIFrame(frameEle,eleType,properties,message);
    }


    /**
     * This method is for web only
     * <br> Click a sub web element in frame element
     * @param frameEle The frame element you want to locator sub element in
     * @param type Sub element type, can be id, name,xpath,css or class,can't be ui
     * @param value Sub element locator value
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void clickElementInIFrame(WebElement frameEle,String type,String value) throws Exception {
        webSearchAgent.clickElementInIFrame(frameEle,type,value);
    }


    //End Method for find Element in IFrame

    //Method for Web List operations

    /**
     * This method is for web only
     * <br>Select a list element by index
     * @param type  The locator type, which should be id, name,xpath,css,class, or ui
     * @param value The locator value
     * @param index Which one you want to select in the list
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void selectListByIndex(String type,String value, int index) throws Exception{
        webSearchAgent.selectListByIndex(type,value,index);
    }

    /**
     * This method is for web only
     * <br> Get all of the web elements in the list control
     * @param type The locator type, which should be id, name,xpath,css,class, or ui
     * @param value The locator value
     * @return A list of selenium web elements
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public List<WebElement> getListElements(String type,String value) throws Exception {
        return  webSearchAgent.getListElements(type,value);
    }


    /**
     * This method is for web only
     * <br> Get one of the web element in a list control by index
     * @param type  The locator type, which should be id, name,xpath,css,class, or ui
     * @param value The locator value
     * @param index Which element you want to get
     * @return A selenium web element
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public WebElement getListElementByIndex(String type,String value,int index) throws Exception{
        return webSearchAgent.getListElementByIndex(type,value,index);
    }


    /**
     * This method is for web only
     * <br> Select a sub web element in a list control by text
     * @param type    The locator type, which should be id, name,xpath,css,class, or ui
     * @param value   The locator value
     * @param text   The text you want to select
     * @throws Exception When locating fails, this method may throw an exception
     */
    @WebOnly
    public void selectListByText(String type,String value,String text) throws Exception{
        webSearchAgent.selectListByText(type,value,text);
    }


    /**
     * This method is for web only
     * <br> Select a sub web element in a list control by text, not full text, partial text
     * @param type The locator type, which should be id, name,xpath,css,class, or ui
     * @param value The locator value
     * @param text The partial text you want to match with
     * @throws Exception  When locating fails, this method may throw an exception
     */
    @WebOnly
    public void selectListContainsText(String type,String value,String text) throws Exception{
        webSearchAgent.selectListContainsText(type,value,text);
    }


    /**
     * This method is for web only
     * <br> Get the size of the web list control
     * @param type  The locator type, which should be id, name,xpath,css,class, or ui
     * @param value The locator value
     * @return  The size of the list control
     * @throws Exception   When locating fails, this method may throw an exception
     */
    @WebOnly
    public int getListSize(String type,String value) throws Exception {
        return webSearchAgent.getListSize(type,value);
    }
    //End Method for Web List operations
    


    // Method for operating with web alert

    /**
     * This method is for web only
     * <br> Handle a web alert explicitly
     * @param tryCount  How many times you want to retry
     */
    @WebOnly
    public void handleAlert(int tryCount) {
        webSearchAgent.handleAlert(tryCount);
    }

    /**
     * This method is for web only
     * <br> Get an exist alert in the current web page
     * @return The alert
     */
    @WebOnly
    public Alert getExistAlert(){
        return webSearchAgent.getExistAlert();
    }

    /**
     * This method is for web only
     * <br> Check whether there is an alert present in the current web page
     * @return Whether there is an alert present
     */
    @WebOnly
    public boolean isAlertPresent(){
        return webSearchAgent.isAlertPresent();
    }
    // End Method for operating with alert


    //region  Methods for web browser set urls

    /**
     * This method is for web only
     * <br> Navigate to a specific url
     * @param url The url you want to navigate to
     */
    @WebOnly
    public void navigateTo(String url) {
        webSearchAgent.navigateTo(url);
    }

    /**
     * This method is for web only
     * <br> Check whether the current browser is closed
     * @return  whether the current browser is closed
     */
    @WebOnly
    public boolean isBrowserClosed() {
        return webSearchAgent.isBrowserClosed();
    }

    /**
     * This method is for web only
     * <br> Get the current page title
     * @return The current page title
     */
    @WebOnly
    public String getPageTitle() {
        return webSearchAgent.getPageTitle();
    }

    /**
     * This method is for web only
     * <br> Get the current page url
     * @return The current page url
     */
    @WebOnly
    public String getPageUrl() {
        return webSearchAgent.getPageUrl();
    }
    //endregion


    //region page related methods configurations

    /**
     * This method is for web only
     * <br> Check whether a text string is visible in the current web page
     * @param text The text string you want to find
     * @return Whether the text string is visible
     */
    @WebOnly
    public boolean isPageTextVisible(String text) {
        return webSearchAgent.isPageTextVisible(text);
    }

    /**
     * This method is for web only
     * <br> Check whether a text string exists in the current web page
     * @param text The text string you want to find
     * @return Whether the text string exists
     */
    @WebOnly
    public boolean doesPageTextExist(String text) {
        return webSearchAgent.doesPageTextExist(text);
    }


    /**
     * This method is for web only
     * <br> Execute Javascript in current web page
     * @param script  The script to execute
     * @return Script execution results
     * @throws Exception When execution fails, this method may throws an exception
     */
    @WebOnly
    public String executeScript(String script) throws Exception {
        return webSearchAgent.executeScript(script);
    }

    /**
     * This method is for web only
     * <br> Execute Javascript in current web page, and auto handle alerts
     * @param script The script to execute
     * @return Script execution results
     * @throws Exception When execution fails, this method may throws an exception
     */
    @WebOnly
    public String executeScriptIgnoreAlert(String script) throws Exception {
        return webSearchAgent.executeScriptIgnoreAlert(script);
    }


    /**
     * This method is for web only
     * <br>Get the current web page as xml
     * @return The current web page as xml string
     */
    @WebOnly
    public String getPageAsXml() {
        return webSearchAgent.getPageAsXml();
    }

    /**
     * Put your mouse over on a web element
     * @param by The web element locator
     * @throws Exception When locating fails, this method may throws an exception
     *                  <br> See also {@link base.search.engine.parser.JBy}
     */
    @WebAndMobile
    public void mouseOver(JBy by) throws Exception {
        webSearchAgent.mouseOver(by);
    }


    /**
     * Put your mouse over on a web element
     * @param type   The locator type, which should be id, name,xpath,css,class, or ui
     * @param value  The locator value
     * @throws Exception When locating fails, this method may throws an exception
     */
    @WebAndMobile
    public void mouseOver(String type,String value) throws Exception {
        JBy by = new JBy();
        by.setType(type);
        by.setValue(value);
        webSearchAgent.mouseOver(by);
    }
    //endregion



    //Wait for activity related methods, for mobile only

    /**
     * This method is for mobile only.
     * <br> Wait for an activity to show
     * @param activity  The activity name
     * @param interval  The wait interval
     * @param timeout   The wait timeout
     * @return Whether the activity is shown at last
     */
    @MobileOnly
    public boolean waitForActivity(String activity,int interval, int timeout) {
        return mobileSearchAgent.waitForActivity(activity,interval,timeout);
    }

    /**
     * This method is for mobile only.
     * <br> Back to the last activity on the mobile, tap the back button
     */
    @MobileOnly
    public void backToLastActivity() {
        mobileSearchAgent.backToLastActivity();
    }

    /**
     * This method is for mobile only.
     * <br> Tap the home button on a mobile.
     */
    @MobileOnly
    public void backToHome() {
        mobileSearchAgent.backToHome();
    }

    //End wait for activity related methods


    //region Web element related wrapper

    /**
     * This method is for web only.
     * <br> Scroll a web element to be visible.
     * @param ele  The element you want to scroll.
     */
    @WebOnly
    public void scrollElementToView(WebElement ele) {
        webSearchAgent.scrollElementToView(ele);
    }
    //endregion


    //region Scroll mobile related element methods

    /**
     * This method is for mobile only.
     * <br> Scroll current mobile interface up.
     * @return Whether scroll is successful.
     */
    @MobileOnly
    public boolean scrollMobileUp() {
        return  mobileSearchAgent.scrollMobileUp();
    }

    /**
     * This method is for mobile only.
     * <br> Scroll current mobile interface down.
     * @return Whether scroll is successful.
     */
    @MobileOnly
    public boolean scrollMobileDown() {
        return  mobileSearchAgent.scrollMobileDown();
    }


    /**
     * This method is for mobile only.
     * <br> Scroll current mobile interface left.
     * @return Whether scroll is successful.
     */
    @MobileOnly
    public boolean scrollMobileLeft() {
        return  mobileSearchAgent.scrollMobileLeft();
    }


    /**
     * This method is for mobile only.
     * <br> Scroll current mobile interface right.
     * @return Whether scroll is successful.
     */
    @MobileOnly
    public boolean scrollMobileRight() {
        return mobileSearchAgent.scrollMobileRight();
    }


    /**
     *  This method is for mobile only.
     *  <br> Scroll current mobile interface
     * @param startX  The x axis  of the start point
     * @param startY  The y axis  of the start point
     * @param endX   The x axis  of the end point
     * @param endY   The y axis  of the end point
     * @param durationSecs  The scroll duration.
     * @return  Whether scroll is successful.
     */
    public boolean mobileScroll(int startX, int startY, int endX, int endY,float durationSecs) {
        return mobileSearchAgent.mobileScroll(startX,startY,endX,endY,durationSecs);
    }
    //endregion

}
