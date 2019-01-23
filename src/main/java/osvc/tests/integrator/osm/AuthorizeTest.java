package osvc.tests.integrator.osm;

import base.BaseWebTestCase;
import base.search.SearchTools;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.osm_web.AuthorizePage;
import utils.JConfigUtil;
import utils.OSMLoginUtil;


public class AuthorizeTest extends BaseWebTestCase {

    @BeforeMethod
    void setup() throws Exception{
        String url = JConfigUtil.getOSMAdminUIURL();
        setupCase(url);

        //Read Case Data
//        cData = new CaseData(CaseUtil.getCaseDataPath(this.getClass()));
//
        OSMLoginUtil loginUtil = new OSMLoginUtil(driver, logger);
        loginUtil.login();

        SearchTools sTools = new SearchTools(driver);
    }

    @Test
    public void addWeChatAccountTest() throws Exception {
        AuthorizePage authorizePage = new AuthorizePage(driver, logger);
        authorizePage.checkNameOrder(JConfigUtil.getOSMAdminUIOfficialAccount_2());
        authorizePage.checkUpdateTime(JConfigUtil.getOSMAdminUIOfficialAccount_2());
        authorizePage.addWeChatType();
    }

    @Test
    public void addExitWeChatAccountTest() throws Exception {
        AuthorizePage authorizePage = new AuthorizePage(driver, logger);
        String OSM_Handle = authorizePage.getCurrentHandle();
        authorizePage.addWeChatType();
        authorizePage.getIntoAnotherHandles(OSM_Handle);
        authorizePage.waitAndGetCapture();
     }

    @Test
    public void cancelAuthorizeTest() throws Exception {
        AuthorizePage authorizePage = new AuthorizePage(driver, logger);
        authorizePage.addWeChatType();
        // TODO saoma
        authorizePage.addWeChatTypeWithoutAgree();
        // TODO saoma
    }

    @Test
    public void checkPreviewsButton() throws Exception {
        AuthorizePage authorizePage = new AuthorizePage(driver, logger);
        authorizePage.addAccoutntType();
    }
}
