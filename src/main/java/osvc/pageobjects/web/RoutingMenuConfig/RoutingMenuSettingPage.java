package osvc.pageobjects.web.RoutingMenuConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RoutingMenuSettingPage extends BaseWebComponent {
    private static StringBuilder SetRoutingData = new StringBuilder();

    private SearchTools sTools;
    private Logger logger;
    public RoutingMenuSettingPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        this.sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public void clickSaveBtn() throws Exception {
        sTools.click("span", "text=Save");
    }

    private void clickCloseBtn() throws Exception {
        sTools.click("button", "title=Close");
    }

    private void clickEditOptionCodeBtn(String OptionCode) throws Exception {
        sTools.click("button", "id=selectOptionCodeBtn");
        sTools.click("span", "text=" + OptionCode);
        clickCloseBtn();
    }

    public void getInPage_1() throws Exception {
        sTools.click("span", "text=1st Level");
    }

    public void getInPage_2() throws Exception {
        sTools.click("span", "text=2nd Level");
    }

    public void getInPage_3() throws Exception {
        sTools.click("span", "text=3rd Level");
    }

    public void getInPage_4() throws Exception {
        sTools.click("span", "text=4th Level");
    }

    public void getInPage_5() throws Exception {
        sTools.click("span", "text=5th Level");
    }

    public void getInPage_HistoryIncidents() throws Exception {
        sTools.click("span", "text=History Incidents");
    }

    private void clickEnableOrDisableRoutingBtn() throws Exception {
        sTools.click("div", "aria-labelledby=enableLabel");
    }

    private boolean isRoutingMenuEnable() throws Exception {
        return Boolean.valueOf(sTools.find("div", "aria-labelledby=enableLabel").getAttribute("aria-checked"));
    }

    public void enableRoutingMenu() throws Exception {
        if (!isRoutingMenuEnable()) {
            clickEnableOrDisableRoutingBtn();
        }
    }

    public void disableRoutingMenu() throws Exception {
        if (isRoutingMenuEnable()) {
            clickEnableOrDisableRoutingBtn();
        }
    }

    // Level routing setting
    private void clickEnableOrDisableRoutingLevelBtn() throws Exception {
        sTools.clickByCss("#enableLevel + div div[role=\"switch checkbox\"].oj-switch-thumb");
    }

    private void clickEnableOrDisableHistoryIncidentLevelBtn() throws Exception {
        sTools.click("div", "aria-labelledby=enableIncidentMessageLabel");
    }

    private boolean isRoutingLevelMenuEnable() throws Exception {
        WebElement CheckButton = sTools.findByCss("#enableLevel + div div[role=\"switch checkbox\"].oj-switch-thumb");
//        String CheckBtnStat = CheckButton.getAttribute("innerHTML");
//        boolean flag = Boolean.valueOf(CheckBtnStat);
        return CheckButton.getAttribute("innerHTML").contains("aria-checked=\"true\"");
//        return Boolean.valueOf(sTools.find("div", "aria-labelledby=enableIncidentMessageLabel").getAttribute("aria-checked"));
    }

    private boolean isHistoryIncidentLevelMenuEnable() throws Exception {
        return Boolean.valueOf(sTools.find("div", "aria-labelledby=enableIncidentMessageLabel").getAttribute("aria-checked"));
    }

    public void enableRoutingLevelMenu() throws Exception {
        if (!isRoutingLevelMenuEnable()) {
            clickEnableOrDisableRoutingLevelBtn();
        }
    }

    public void disableRoutingLevelMenu() throws Exception {
        if (isRoutingLevelMenuEnable()) {
            clickEnableOrDisableRoutingLevelBtn();
        }
    }

    public void enableHistoryIncidentLevelMenu() throws Exception {
        if (!isHistoryIncidentLevelMenuEnable()) {
            clickEnableOrDisableHistoryIncidentLevelBtn();
        }
    }

    private void inputQuestionContent(String Content) throws Exception {
        sTools.find("textarea", "id=questionContent").clear();
        sTools.find("textarea", "id=questionContent").sendKeys(Content);
    }

    private void inputCustomizeField(String CustomizeField) throws Exception {
        sTools.find("input", "id=fieldName").clear();
        sTools.find("input", "id=fieldName").sendKeys(CustomizeField);
    }

    private void clickFieldName() throws Exception {
        sTools.click("span", "id=ojChoiceId_fieldNameSelect_selected");
    }

    private void chooseCategory() throws Exception {
        sTools.click("div", "text=Category");
    }

    private void chooseProduct() throws Exception {
        sTools.click("div", "text=Product");
    }

    private void chooseCustomizeField() throws Exception {
        sTools.click("div", "text=Customize Field");
    }

    private void clickSelectOptionCode(String OptionCode) throws Exception {
        sTools.click("span", "text=Select");
        sTools.click("span", "text=" + OptionCode);
        clickCloseBtn();
    }

    public void InputSelectOptionCode(String OptionCode) throws Exception {
        sTools.find("input", "id=optionCode").clear();
        sTools.find("input", "id=optionCode").sendKeys(OptionCode);
    }

    private void inputOptionorder(String OptionOrder) throws Exception {
        sTools.find("input", "id=optionOrder").clear();
        sTools.find("input", "id=optionOrder").sendKeys(OptionOrder);
    }

    private void clickAction() throws Exception {
        sTools.click("span", "id=ojChoiceId_optionAction_selected");
    }

    private void chooseActionRequestChat() throws Exception {
        sTools.click("div", "text=Request chat");
    }

    private void chooseActionCreateIncident() throws Exception {
        sTools.click("div", "text=Create incident");
    }

    private void InputDescriptionOfTheOption(String Desc) throws Exception {
        sTools.find("input", "id=optionContent").clear();
        sTools.find("input", "id=optionContent").sendKeys(Desc);
    }

    private void clickAdd() throws Exception {
        sTools.click("span", "text=Add");
    }

    //set routing workflow
    public void setNewRouting(boolean isCustomize, String QuestionContent, String FieldName, String CustomizedField, String OptionCode, String OptionOrder, String Action, String Desc) throws Exception {
        inputQuestionContent(QuestionContent);
        clickFieldName();

        if (!isCustomize && "".equals(FieldName)) {
            clickEditOptionCodeBtn(OptionCode);
            inputOptionorder(OptionOrder);
            clickAction();
            if ("Request chat".equals(Action)) {
                chooseActionRequestChat();
            } else if ("Create incident".equals(Action)) {
                chooseActionCreateIncident();
            }
            InputDescriptionOfTheOption(Desc);
            clickAdd();
            appendSetRoutingData(OptionCode, Action, OptionOrder, Desc);
        } else if (isCustomize && "".equals(FieldName)) {
            inputCustomizeField(CustomizedField);
            clickSelectOptionCode(OptionCode);
            inputOptionorder(OptionOrder);
            clickAction();
            if ("Request chat".equals(Action)) {
                chooseActionRequestChat();
            } else if ("Create incident".equals(Action)) {
                chooseActionCreateIncident();
            }
            InputDescriptionOfTheOption(Desc);
            clickAdd();
            appendSetRoutingData(OptionCode, Action, OptionOrder, Desc);
        }

        switch (FieldName) {
            case "Category":
                chooseCategory();
                clickSelectOptionCode(OptionCode);
                inputOptionorder(OptionOrder);
                clickAction();
                if ("Request chat".equals(Action)) {
                    chooseActionRequestChat();
                } else if ("Create incident".equals(Action)) {
                    chooseActionCreateIncident();
                }
                InputDescriptionOfTheOption(Desc);
                clickAdd();
                appendSetRoutingData(OptionCode, Action, OptionOrder, Desc);
                break;
            case "Product":
                chooseProduct();
                clickSelectOptionCode(OptionCode);
                inputOptionorder(OptionOrder);
                clickAction();
                if ("Request chat".equals(Action)) {
                    chooseActionRequestChat();
                } else if ("Create incident".equals(Action)) {
                    chooseActionCreateIncident();
                }
                InputDescriptionOfTheOption(Desc);
                clickAdd();
                appendSetRoutingData(OptionCode, Action, OptionOrder, Desc);
                break;
            case "Customize Field":
                chooseCustomizeField();
                inputCustomizeField(CustomizedField);
                clickSelectOptionCode(OptionCode);
                inputOptionorder(OptionOrder);
                clickAction();
                if ("Request chat".equals(Action)) {
                    chooseActionRequestChat();
                } else if ("Create incident".equals(Action)) {
                    chooseActionCreateIncident();
                }
                InputDescriptionOfTheOption(Desc);
                clickAdd();
                appendSetRoutingData(OptionCode, Action, OptionOrder, Desc);
                break;
        }
    }

    //check is routing menu saved successfully
    private void appendSetRoutingData(String... Datas) {
        for (String Data : Datas) {
            SetRoutingData.append(Data);
        }
    }

    private String getSetRoutingData(String Data) {
        return String.valueOf(SetRoutingData);
    }

    public boolean checkSavedRouting(String setRoutingData) throws Exception {
        WebElement OptionTable = sTools.find("table", "id=optionTable");
        List<WebElement> SavedOption = OptionTable.findElements(By.cssSelector("td span"));
        StringBuilder SavedRoutingData = new StringBuilder();
        for (WebElement eachSavedOption : SavedOption) {
            try {
                String getRoutingData = eachSavedOption.getText();
                SavedRoutingData.append(getRoutingData);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return setRoutingData.contentEquals(SetRoutingData);
    }

    //delete routingmenu
    public void clickDeleteBtn() throws Exception {
        sTools.click("button", "title=delete option");
    }

    // history incidents page
    private boolean isNotifyAgentOn() throws Exception {
//        WebElement checkbox = sTools.find("input", "id=agree");
        WebElement checkbox = driver.findElement(By.cssSelector("input#agree"));
        String checkbox_attr = checkbox.getAttribute("class");
        return checkbox_attr.contains("oj-selected");
    }

    public void turnOnNotifyAgent() throws Exception {
        if (!isNotifyAgentOn()) {
            sTools.click("input", "id=agree");
        }
    }

    public void turnOffNotifyAgent() throws Exception {
        if (isNotifyAgentOn()) {
            sTools.click("input", "id=agree");
        }
    }

    public String checkMessageDisplayedNum() throws Exception {
        String ret = "";
        List<WebElement> Nums = sTools.findAllElement("css", "#showNumberBar span");
//        List<WebElement> Nums = driver.findElements(By.cssSelector("#showNumberBar span"));
        for (WebElement eachNums : Nums) {
            if (eachNums.getAttribute("class").contains("oj-selected")) {
                ret = eachNums.findElement(By.cssSelector("span")).getText();
            }
        }
        return ret;
    }

    public String getRoutingNum() throws Exception {
        getInPage_1();
        List<WebElement> routing_lines = sTools.findAllElement("css", "tr.oj-table-body-row.oj-table-hgrid-lines");
        return String.valueOf(routing_lines.size());
    }

    private void chooseMaxMsgDisplayed(String Num) throws Exception {
        WebElement NumBar = sTools.find("div", "id=showNumberBar");
        NumBar.findElement(By.xpath("//div[@id='showNumberBar']//label//span[text()='" + Num + "']")).click();
    }

    public void clickMaxMsgDisplayed_1() throws Exception {
        chooseMaxMsgDisplayed("1");
    }

    public void clickMaxMsgDisplayed_2() throws Exception {
        chooseMaxMsgDisplayed("2");
    }

    public void clickMaxMsgDisplayed_3() throws Exception {
        chooseMaxMsgDisplayed("3");
    }

    public void clickMaxMsgDisplayed_4() throws Exception {
        chooseMaxMsgDisplayed("4");
    }

    public void clickMaxMsgDisplayed_5() throws Exception {
        chooseMaxMsgDisplayed("5");
    }

    private void resetIncidentRoutingMsg_1(String Language) throws Exception {
        sTools.click("button", "id=menuButton1");
        sTools.click("span", "in " + Language);
    }
    
    public void resetIncidentRoutingMsg_1_toEN() throws Exception {
        resetIncidentRoutingMsg_1("English");
    }

    public void resetIncidentRoutingMsg_1_toCH() throws Exception {
        resetIncidentRoutingMsg_1("Chinese");
    }

    private void resetIncidentRoutingMsg_2(String Language) throws Exception {
        sTools.click("button", "id=menuButton2");
        sTools.click("span", "in " + Language);
    }

    public void resetIncidentRoutingMsg_2_toEN() throws Exception {
        resetIncidentRoutingMsg_2("English");
    }

    public void resetIncidentRoutingMsg_2_toCH() throws Exception {
        resetIncidentRoutingMsg_2("Chinese");
    }
}
