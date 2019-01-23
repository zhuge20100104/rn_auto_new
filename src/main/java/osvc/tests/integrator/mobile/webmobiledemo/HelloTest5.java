package osvc.tests.integrator.mobile.webmobiledemo;



import base.BaseWebTestCase;
import base.search.engine.wait.utils.Sleeper;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class HelloTest5 extends BaseWebTestCase{

    private void testCoreSearch() throws Exception {
        LoginPage page = new LoginPage(driver,logger);
        page.Login();
    }

    @BeforeMethod
    public void setup() throws Exception {
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin";
        setupCase(url);
    }


    public  void testCoreSync() throws Exception{
        System.out.println(SignalCore.waitForSignal(packageKey,"Test3"));
        SignalCore.sendSignal(packageKey,"Test4");
    }



    @Test
    public  void test() throws Exception{
        testCoreSearch();
        Thread.sleep(20 * 1000);
        SignalCore.sendSignal(packageKey,"shouldStartMobile");

        SignalCore.waitForSignal(packageKey, "shouldStartWeb",1000,300*1000);

        logger.info("Start web signal has been accepted!!");

        Sleeper.sleep(10);

//        testCoreSearch();
    }

}
