package osvc.tests.integrator.web.SSOConfig;

import base.BaseWebTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.SSOPage.SSOPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class SSOSetupTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToSSOPage();
    }

    @Test(groups = {"paas", "setup", "p1"})
    public void setSSOAndCertificateTest() throws Exception {
        SSOPage ssoPage = new SSOPage(driver, logger);
        String CurrentHandle = ssoPage.getCurrentHandle();
        ssoPage.clickEditPriavteKeyAndCertificateBtn();
        ssoPage.inputDefaultPrivateKey();
        ssoPage.inputDefaultCertificate();
        ssoPage.clickPrivateKeyAndCertificateSaveBtn();
        ssoPage.inputRNSSOLink(JConfigUtil.getSSORNUrl());
        ssoPage.clickTestOSvCSSO();
        ssoPage.inputContactId(JConfigUtil.getSSORNContactId());
        ssoPage.clickContactIdTestBtn();
        ssoPage.switchAnotherWindow(CurrentHandle);
        Assert.assertTrue(ssoPage.checkAuthorizeSuccess(), "SSO Successfully");
    }

    @Test(groups = {"paas", "p1"})
    public void wrongPrivateKeyTest() throws Exception {
        SSOPage ssoPage = new SSOPage(driver, logger);
        String CurrentHandle = ssoPage.getCurrentHandle();
        ssoPage.clickEditPriavteKeyAndCertificateBtn();
        ssoPage.inputPrivateKey("123"); //wrong private key
        ssoPage.inputDefaultCertificate();
        ssoPage.clickPrivateKeyAndCertificateSaveBtn();
        ssoPage.inputRNSSOLink(JConfigUtil.getSSORNUrl());
        ssoPage.clickTestOSvCSSO();
        ssoPage.inputContactId(JConfigUtil.getSSORNContactId());
        ssoPage.clickContactIdTestBtn();
        ssoPage.switchAnotherWindow(CurrentHandle);
        Assert.assertTrue(ssoPage.checkAuthroizeWrongPriOrCer(), "SSO wrong private key test Successfully");
    }

    @Test(groups = {"paas", "p1"})
    public void wrongCertificateTest() throws Exception {
        SSOPage ssoPage = new SSOPage(driver, logger);
        String CurrentHandle = ssoPage.getCurrentHandle();
        ssoPage.clickEditPriavteKeyAndCertificateBtn();
        ssoPage.inputDefaultPrivateKey();
        ssoPage.inputCertificate("123"); //wrong certificate
        ssoPage.clickPrivateKeyAndCertificateSaveBtn();
        ssoPage.inputRNSSOLink(JConfigUtil.getSSORNUrl());
        ssoPage.clickTestOSvCSSO();
        ssoPage.inputContactId(JConfigUtil.getSSORNContactId());
        ssoPage.clickContactIdTestBtn();
        ssoPage.switchAnotherWindow(CurrentHandle);
        Assert.assertTrue(ssoPage.checkAuthroizeWrongPriOrCer(), "SSO wrong certificate test Successfully");
    }

    @Test(groups = {"paas", "p1"})
    public void wrongContactIdTest() throws Exception {
        SSOPage ssoPage = new SSOPage(driver, logger);
        String CurrentHandle = ssoPage.getCurrentHandle();
        ssoPage.clickEditPriavteKeyAndCertificateBtn();
        ssoPage.inputDefaultPrivateKey();
        ssoPage.inputDefaultCertificate();
        ssoPage.clickPrivateKeyAndCertificateSaveBtn();
        ssoPage.inputRNSSOLink(JConfigUtil.getSSORNUrl());
        ssoPage.clickTestOSvCSSO();
        ssoPage.inputContactId("000");// wrong contact id
        ssoPage.clickContactIdTestBtn();
        ssoPage.switchAnotherWindow(CurrentHandle);
        Assert.assertTrue(ssoPage.checkAuthorizeFailed(), "SSO Wrong Contact id test Successfully");
    }

    @Test(groups = {"paas", "p1"})
    public void DiscardTest() throws Exception {
        SSOPage ssoPage = new SSOPage(driver, logger);
        ssoPage.clickEditPriavteKeyAndCertificateBtn();
        ssoPage.inputDefaultPrivateKey();
        ssoPage.inputDefaultCertificate();
        ssoPage.clickPrivateKeyAndCertificateSaveBtn();

        String OldValue = ssoPage.getSSORNUrlInTextArea();  // todo  2 OldValue
        ssoPage.inputRNSSOLink("123");
        ssoPage.clickAnotherpage();
        ssoPage.clickDiscardBtn();
        ssoPage.clickbackToSSOpage();
        ssoPage.clickEditSSOLinkBtn();
        String NewValue = ssoPage.getSSORNUrlInTextArea();
        Assert.assertTrue(OldValue.contains("00147"), "Discard Success"); //todo 3
        Assert.assertTrue(NewValue.contains("00147"), "Discard Success"); //todo 4
        // TODO OCR is not stable
    }
}
