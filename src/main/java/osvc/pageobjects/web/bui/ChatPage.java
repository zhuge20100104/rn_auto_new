package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;
import base.search.engine.wait.utils.Sleeper;
import config.utils.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ChatPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public ChatPage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    private boolean isProductExist() throws Exception {
        String ProductContains = sTools.findByCss("input[id^='select-box-input-Incident_ProdId']+div").getText();
        return !ProductContains.contains("No Value");
    }

    private boolean isCategoryExist() throws Exception {
        String ProductContains = sTools.findByCss("input[id^='select-box-input-Incident_CatId']+div").getText();
        return !ProductContains.contains("No Value");
    }

    private void chooseProductIfNotExist() throws Exception {
        if (!isProductExist()) {
            sTools.clickByCss("input[id^='select-box-input-Incident_ProdId']");
            sTools.click("div", "text*=Maestro Smart Thermostat");
        }
    }

    private void chooseCategoryIfNotExist() throws Exception {
        if (!isCategoryExist()) {
            sTools.clickByCss("input[id^='select-box-input-Incident_CatId']");
            sTools.click("div", "text*=Wechat");
        }
    }

    public void makeIncidentInChatTobeSolved() throws Exception {
        sTools.clickByCss("span.workgroup-tab-container span.incident-icon");
        chooseProductIfNotExist();
        chooseCategoryIfNotExist();
        sTools.clickByCss("input[id^='select-box-input-Incident_Status_Id']");
        sTools.click("div", "class=select-box-item-label|text=Solved");
        sTools.clickByCss("button[id$='IncidentSave']");
    }

    public void sendMessageToMobile(String message) throws Exception {
        WebElement sendMessageFrame = sTools.findChild("div","class*=OracleChatPanel|class*=focus",null,"class*=cke_wysiwyg_frame",false);
        sTools.waitAndSendKeysInIFrame(sendMessageFrame,"body",null,message);
        sTools.click("button","class*=OracleChatInputSendButton");
    }

    public void transferToAnotherAgent(String AgentName) throws Exception {

        sTools.click("button","class=OracleChatTransferButton");


        //Wait for there is an agent
        WebElement tranferToAgent = sTools.find("span","id^=transfer2Agent");
        while(tranferToAgent.getAttribute("innerHTML").equals("Transfer to Agent (0)")) {
            sTools.click("button","class=OracleChatTransferButton");
            Sleeper.sleep(1);
            sTools.click("button","class=OracleChatTransferButton");
        }




        WebElement agentSelectDiv = sTools.find("div","id$=agentSelect");
        if(agentSelectDiv == null) {
            sTools.click("span","id^=transfer2Agent");
        }

        sTools.click("span","id^=tranAgent|text=Bot3");

        // choose rpoduct or category if they are "no value".


        try {
            chooseProductIfNotExist();
            chooseCategoryIfNotExist();

            sTools.click("button", "class=OracleChatTransferButton");
            sTools.click("span", "id^=tranAgent|text=Bot3");
        }catch (Exception ex) {

        }

    }


    public void startConference(String AgentName) throws Exception {
        sTools.clickByCss("div[id$='actionArea'] button[id$='conference']");
        sTools.clickByCss("span[id^='confAgent'][title*='" + AgentName + "']");

        // choose rpoduct or category if they are "no value".
        chooseProductIfNotExist();
        chooseCategoryIfNotExist();

        sTools.clickByCss("div[id$='actionArea'] button[id$='conference']");
        sTools.clickByCss("span[id^='confAgent'][title*='" + AgentName + "']");
    }



    public void terminateChat() throws Exception {
//        sTools.click(get("terminateChatBtn"));
    }

    public void checkLastMessageExists() throws Exception {
//        WebElement lastMessageSpan = sTools.find(get("lastMessageSpan"));
//        Assert.assertTrue(lastMessageSpan!=null, "Get last message span failure!!");
    }


    public void terminateIfHasChats() throws Exception {
        WebElement ele = sTools.find("div","class*=OracleChatPanel|class*=focus");

        if(ele!=null) {
            this.terminateTwoChats();
        }
    }

    public void terminateTwoChats() throws Exception{

        sTools.clickChild("div","class*=OracleChatPanel|class*=focus",null,"class*=OracleChatTerminateButton",false);

        Sleeper.sleep(8);

        sTools.click("a","title=Close Tab|class=closeButton");
        sTools.click("span","class=oj-button-text|text=No");


//        //Terminate each chat in the past
//        List<WebElement> eles = sTools.findAll(get("hiddenChatPane"));
//        for(WebElement ele:eles) {
//            if(ele.getAttribute("aria-expanded").equals("false")) {
//                ele.click();
//                Sleeper.sleep(4);
//                // Terminate the second chat session
//                sTools.getElementInParent(get("focusChatPaneTerminate")).click();
//            }
//        }

    }

    public String getChatEmail() throws Exception {
        WebElement ele = sTools.find("div", "id^=chatPanelContents|id$=info");
        return ele.findElement(By.cssSelector("li:nth-child(2)")).getText();
    }
}
