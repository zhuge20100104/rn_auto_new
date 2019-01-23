package osvc.pageobjects.mobile.wechat;

import base.search.engine.wait.utils.Sleeper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import base.BaseMobileComponent;
import base.search.SearchTools;

public class HomeActivity extends BaseMobileComponent {
    private SearchTools sTools;
    private Logger logger;
    private WebDriver driver;
    public HomeActivity(WebDriver driver,Logger logger) throws Exception{
        sTools = new SearchTools(driver);
        this.driver = driver;
        this.logger = logger;
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","scanQRCode",
                        "chooseFromAlbum",
                        "allImages",
                        "follow",
                        "chatInfo",
                        "unFollow",
                        "confirmUnfollow"

                },

                {"zh_CN","扫一扫",
                        "从相册选取二维码",
                        "所有图片",
                        "关注公众号",
                        "聊天信息",
                        "取消关注",
                        "不再关注"
                },

                {"en_US","Scan QR Code",
                        "Choose QR Code from Album",
                        "All Images",
                        "Follow",
                        "Chat Info",
                        "Unfollow",
                        "Unfollow"
                }
        };
        return languages;
    }




    public void scanQRCode(int index) throws Exception{
        logger.info("Click the plus button on the right corner of wechat");
        sTools.clickByUi("res=com.tencent.mm:id/hu");
        logger.info("Click the scan button");
        sTools.clickByUi("text="+getProp("scanQRCode"));
        logger.info("Click the more button on scan page");
        sTools.clickByUi("res=com.tencent.mm:id/j1");
        logger.info("Click choose from album button on the bottom");
        sTools.clickByUi("text="+getProp("chooseFromAlbum"));
        logger.info("Click all images to select an album");
        sTools.clickByUi("text="+getProp("allImages"));
        logger.info("Go into the test album");
        sTools.clickByUi("text=test");
        logger.info("Select a qr code to scan");
        sTools.findAllElement("ui","res=com.tencent.mm:id/d9t").get(index).click();

    }


    public void unfollowAndFollow(int index) throws Exception{
        scanQRCode(index);
        Sleeper.sleep(20);
        boolean isFollowExist = sTools.waitForTextMobile(getProp("follow")+"");

        if(isFollowExist) {
            sTools.clickByUi("text="+getProp("follow"));
        }else {
            sTools.clickByUi("desc="+getProp("chatInfo"));
            sTools.clickByUi("text="+getProp("unFollow"));
            sTools.clickByUi("text="+getProp("confirmUnfollow"));
            scanQRCode(index);
            //Sleep for follow button to be clickable
            Sleeper.sleep(3);
            isFollowExist = sTools.waitForTextMobile(getProp("follow")+"");
            if(isFollowExist) {
                sTools.click("text="+getProp("follow"));
            }
        }
    }
}
