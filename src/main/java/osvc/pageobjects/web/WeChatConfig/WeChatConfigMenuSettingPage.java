package osvc.pageobjects.web.WeChatConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import base.search.engine.wait.base.ElementWait;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class WeChatConfigMenuSettingPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    private WebDriver driver;
    public WeChatConfigMenuSettingPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
        this.driver = driver;
    }

    public void clickEditOrPublishBtn() throws Exception {
        sTools.click("input", "id=editorpublish");
    }

    private List<String> getMainMenuName() {
        List<String> MainMenuName = new ArrayList<>();
        List<WebElement> MainMenuItems = driver.findElements(By.cssSelector("li.menu_item > a[data-bind*='data']"));
        for (WebElement eachMainMenuItem : MainMenuItems) {
            MainMenuName.add(eachMainMenuItem.getText());
        }
        return MainMenuName;
    }

    private List<String> getSubMenuName() {
        List<String> SubMenuName = new ArrayList<>();
        List<WebElement> SubMenuItems = driver.findElements(By.cssSelector("li.menu_item ul li a[data-bind*=value]"));
        for (WebElement eachSubMenuItem : SubMenuItems) {
            SubMenuName.add(eachSubMenuItem.getText());
        }
        return SubMenuName;
    }

    public void clickFirstMenu() throws Exception {
        driver.findElement(By.cssSelector("li.menu_item:nth-child(1)")).click();
    }

    public void clickSecondMenu() throws Exception {
        driver.findElement(By.cssSelector("li.menu_item:nth-child(2)")).click();
    }

    public void clickThirdMenu() throws Exception {
        driver.findElement(By.cssSelector("li.menu_item:nth-child(3)")).click();
    }

    public boolean isUnsupportLabelDisplayed() throws Exception {
        return sTools.find("span", "id=unsupported-label").isDisplayed();
    }

    public void clickAddMainMenuBtn() throws Exception {
        List<String> MainMenuName = getMainMenuName();
        if (MainMenuName.size() == 3) {
            return;
        }
        sTools.click("li", "class*=js_addMenuBox menu_item grid_item");
    }

    public void clickAddSubMenuBtn() throws Exception {
        List<String> SubMenuName = getSubMenuName();
        if (SubMenuName.size() == 5) {
            return;
        }
        sTools.clickChild("div", "class=sub_menu_box selected","i", "class$=menu_add", false);
    }

    private static void findElement_WaitAndClick (WebDriver driver, WebDriverWait wait, String byWhat, String Para) {
        try {
            if (byWhat == "id") {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Para)));
                driver.findElement(By.id(Para)).click();
            }
            else if (byWhat == "css") {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Para)));
                driver.findElement(By.cssSelector(Para)).click();
            }
            else if (byWhat == "xpath") {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Para)));
                driver.findElement(By.xpath(Para)).click();
            }
        } catch (Exception e) {
            System.out.println(e);;
        }
    }

    private static String getContentFromClipboard() throws UnsupportedFlavorException, IOException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable clipT = clipboard.getContents(null);
        String content = null;
        if (clipT != null) {
            // judge if the type is stringFlavor
            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                content = (String) clipT.getTransferData(DataFlavor.stringFlavor);
                return content;
            }
        }
        return content;
    }

    private static String getValueFromTextArea(WebDriver driver, WebDriverWait wait, String Para) throws UnsupportedFlavorException, IOException {
        findElement_WaitAndClick(driver, wait, "css", Para);
        driver.findElement(By.cssSelector(Para)).sendKeys(Keys.CONTROL,"a");
        driver.findElement(By.cssSelector(Para)).sendKeys(Keys.CONTROL,"c");
        return getContentFromClipboard();
    }
    
    public void addSubMenuAndCheck() throws IOException, UnsupportedFlavorException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        for (int j = 1; j < 4; j++) {
            findElement_WaitAndClick(driver, wait, "css", "#menuList li:nth-child("+j+").jslevel1");//click main menu
            for (int i = 0; i < 5; i++) {
                findElement_WaitAndClick(driver, wait, "css", "#menuList li:nth-child("+j+") li.js_addMenuBox");//click add button
                findElement_WaitAndClick(driver, wait, "css", "#menuList "+"li:nth-child("+j+") "+"li:nth-child("+(i+2)+").jslevel2");//click new sub menu
                driver.findElement(By.cssSelector("#menu-name-input")).clear();
                driver.findElement(By.cssSelector("#menu-name-input")).sendKeys("sub"+j+"-"+(i+1));;//name new sub menu
            }
        }

        //check if submenus disapear when switch
        for (int j = 1; j < 4; j++) {
            findElement_WaitAndClick(driver, wait, "css", "#menuList li:nth-child("+j+").jslevel1");//click main menu
            for (int i = 0; i < 5; i++) {
                findElement_WaitAndClick(driver, wait, "css", "#menuList "+"li:nth-child("+j+") "+"li:nth-child("+(i+2)+").jslevel2");
                String Val_true = getValueFromTextArea(driver, wait, "#menu-name-input");
                assertEquals(Val_true, "sub"+j+"-"+(i+1));
            }
        }
    }
    
    public void deleteSubMenuAdnCheck() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        for (int j = 1; j < 4; j++) {
            findElement_WaitAndClick(driver, wait, "css", "#menuList li:nth-child("+j+").jslevel1");//click main menu
            for (int i = 0; i < 5; i++) {
                findElement_WaitAndClick(driver, wait, "css", "#menuList "+"li:nth-child("+j+") "+"li:nth-child("+(6-i)+").jslevel2");
                findElement_WaitAndClick(driver, wait, "css", ".board-headup-recycle");
            }
        }
        findElement_WaitAndClick(driver, wait, "css", "#editorpublish");
        driver.navigate().refresh();
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        weChatConfigPage.chooseAccountWithAccountName("chenguang");
        weChatConfigPage.getInMenuSettingTab();
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,250)","");
        findElement_WaitAndClick(driver, wait, "css", "#editorpublish");
        for (int j = 1; j < 4; j++) {
            findElement_WaitAndClick(driver, wait, "css", "#menuList li:nth-child("+j+").jslevel1");//click main menu
            WebElement addBtn = driver.findElement(By.cssSelector("#menuList li:last-child"));
            String att = addBtn.getAttribute("class");
            assertEquals(att, "js_addMenuBox show");
        }
    }

    public boolean checkMenuSettingSuccessToast() throws Exception {
        String ToastMsg = sTools.find("span", "class=oj-message-detail messageDetail").getText();
        if ("Saving success".equals(ToastMsg)) {
            return true;
        } else {
            logger.error(" Menu setting is not saved successful, The Toast message is: " + ToastMsg);
            return false;
        }
    }

    public void deleteMainMenu() throws Exception {
        WebElement menuList= driver.findElement(By.id("menuList"));
        List<WebElement> menu = menuList.findElements(By.xpath(".//li/div"));
        int MENU_NUM = menu.size();
        try {
            for (int i = 0; i < MENU_NUM; i++) {
                driver.findElement(By.cssSelector("#menuList > li:nth-child(1)")).click();
                driver.findElement(By.className("board-headup-recycle")).click();
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void editMenuName(String MenuName) throws Exception {
        sTools.find("input", "id=menu-name-input").clear();
        sTools.find("input", "id=menu-name-input").sendKeys(MenuName);
    }

    public void chooseMenuType(String chooseMenuType) throws Exception {
        sTools.clickChild("div", "id=menu-sel-id", "span", "id^=ojCh", false);
        sTools.click("div", "aria-label=" + chooseMenuType);
    }

    public void chooseMenuFeature(String chooseMenuFeature) throws Exception {
        sTools.clickChild("div", "id=system-type-id1", "span", "id^=ojCh", false);
        sTools.click("div", "aria-label=" + chooseMenuFeature);
    }


    public void refreshPage() {
        sTools.refreshPage();
        ElementWait elementWait = new ElementWait(driver);
        elementWait.waitForPageReady();
    }

    public boolean isLeftMenuNotExist(String leftMenu)  {
        try {
            WebElement setting_container = sTools.find("div", "id=param-setting-container");
            return setting_container.getAttribute("innerHTML").contains(leftMenu);
        }catch (Exception ex) {
            return false;
        }
    }


    public boolean isLeftMenuExist(String leftMenu)  {
        try {
            return sTools.waitForTextWeb(leftMenu);
        }catch (Exception ex) {
            return false;
        }
    }


    public boolean checkSubMenu(String mainMenu,String subMenu) {
        try {
            if(sTools.waitForTextWeb(mainMenu)){
                sTools.click("a","text="+mainMenu);
                WebElement subM = sTools.find("a","text="+subMenu);
                return subM!=null;
            }else {
                return false;
            }
        }catch (Exception ex) {
            return false;
        }
    }

    public boolean checkSubMenuNotExist(String mainMenu,String subMenu) {
        try {
            if(sTools.waitForTextWeb(mainMenu)){
                sTools.click("a","text="+mainMenu);
                boolean isDisappear = sTools.waitForTextDisappearWeb(subMenu);
                return isDisappear;
            }else {
                return false;
            }
        }catch (Exception ex) {
            return false;
        }
    }
}
