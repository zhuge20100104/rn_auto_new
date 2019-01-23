package utils;

import config.utils.ConfigUtil;

public class JConfigUtil extends ConfigUtil {

    //region admin console related configuration
    public static String getAdminConsoleIdentityDomain() {
        return getPropertyValue("testautomation.admin_console.identity_domain");
    }

    public static String getAdminConsoleServiceName() {
        return getPropertyValue("testautomation.admin_console.service_name");
    }

    public static String getAdminConsoleUsername() {
        return getPropertyValue("testautomation.admin_console.username");
    }

    public static String getAdminConsolePassword() {
        return getPropertyValue("testautomation.admin_console.password");
    }
    //endregion

    // osm adminui
    public static String getOSMAdminUIURL() {
        return getPropertyValue("testautomation.osm.admin_console.url");
    }

    public static String getOSMAdminUIUsername() {
        return getPropertyValue("testautomation.osm.admin_console.username");
    }

    public static String getOSMAdminUIPassword() {
        return getPropertyValue("testautomation.osm.admin_console.password");
    }

    public static String getOSMAdminUIOfficialAccount_1() {
        return getPropertyValue("testautomation.osm.account_1");
    }

    public static String getOSMAdminUIOfficialAccount_2() {
        return getPropertyValue("testautomation.osm.account_2");
    }
    //endregion

    //region BUI related configuration

    public static String getBUIUrl() {
        return getPropertyValue("testautomation.bui.url");
    }

    public static String getBUIRightNowName() {
        return getPropertyValue("testautomation.bui.rightnow.name");
    }

    public static String getBUIUserName(String account) {
        String userName="";
        switch (account) {
            case BUIAccount.ACCOUNT1:
                userName =  getPropertyValue("testautomation.bui.account1.username");
                break;
            case BUIAccount.ACCOUNT2:
                userName =  getPropertyValue("testautomation.bui.account2.username");
                break;
            case BUIAccount.ACCOUNT3:
                userName =  getPropertyValue("testautomation.bui.account3.username");
                break;
            case BUIAccount.ACCOUNT4:
                userName =  getPropertyValue("testautomation.bui.account4.username");
                break;
            case BUIAccount.ACCOUNT5:
                userName =  getPropertyValue("testautomation.bui.account5.username");
                break;
        }

        return userName;
    }



    public static String getBUIPassword(String account) {
        String password="";
        switch (account) {
            case BUIAccount.ACCOUNT1:
                password =  getPropertyValue("testautomation.bui.account1.password");
                break;
            case BUIAccount.ACCOUNT2:
                password =  getPropertyValue("testautomation.bui.account2.password");
                break;
            case BUIAccount.ACCOUNT3:
                password =  getPropertyValue("testautomation.bui.account3.password");
                break;
            case BUIAccount.ACCOUNT4:
                password =  getPropertyValue("testautomation.bui.account4.password");
                break;
            case BUIAccount.ACCOUNT5:
                password =  getPropertyValue("testautomation.bui.account5.password");
                break;
        }

        return password;
    }
    //endregion

    //
    public static String getSSORNUrl() {
        return getPropertyValue("testautomation.sso.osvc_redirect_url");
    }

    public static String getSSORNContactId() {
        return getPropertyValue("testautomation.sso.osvc_contact_id");
    }
    //endregion

    //

    public static String getSMSWebServiceUrl() {
        return getPropertyValue("testautomation.mock_service.real_sms_service_url");
    }

    public static String getSMSAccessAccount() {
        return getPropertyValue("testautomation.mock_service.real_sms_service_username");
    }

    public static String getSMSPassword() {
        return getPropertyValue("testautomation.mock_service.real_sms_service_password");
    }

    public static String getSMSMobileNumber() {
        return getPropertyValue("testautomation.mock_service.real_sms_mobile_number");
    }

    public static String getSMSBusyMobileNumber() {
        return getPropertyValue("testautomation.mock_service.real_sms_mobile_busy_number");
    }

    //endregion

    //region User register related configuration
    public static String getUserRegisterPhone() {
        return getPropertyValue("testautomation.user_register.phone");
    }

    public static String getUserRegisterOfficialAccount1() {
        return getPropertyValue("testautomation.user_register.official_account1");
    }


    public static String getUserRegisterOfficialAccount2() {
        return getPropertyValue("testautomation.user_register.official_account2");
    }

    public static String getUserRegisterUnregistrationURL() {
        return getPropertyValue("testautomation.user_register.unregistration_url");
    }

    public static String getUserRegisterGetContractsNumURL() {
        return getPropertyValue("testautomation.user_register.get_contacts_num_url");
    }

    public static String getUserRegisterGetRegisterCodeURL() {
        return getPropertyValue("testautomation.user_register.get_register_code_url");
    }
    //endregion


    //region Test account related configuration
    public static String getTestAccountAccount1Name() {
        return getPropertyValue("testautomation.test_account.account1.name");
    }

    public static String getTestAccountAccount1AppId() {
        return getPropertyValue("testautomation.test_account.account1.app_id");
    }

    public static String getTestAccountAccount1AppSecret() {
        return getPropertyValue("testautomation.test_account.account1.app_secret");
    }

    public static String getTestAccountAccount1Token() {
        return getPropertyValue("testautomation.test_account.account1.token");
    }

    public static String getTestAccountAccount1WechatId() {
        return getPropertyValue("testautomation.test_account.account1.wechat_id");
    }


    public static String getTestAccountAccount2AppId() {
        return getPropertyValue("testautomation.test_account.account2.app_id");
    }

    public static String getTestAccountAccount2AppSecret() {
        return getPropertyValue("testautomation.test_account.account2.app_secret");
    }

    public static String getTestAccountAccount2Token() {
        return getPropertyValue("testautomation.test_account.account2.token");
    }

    public static String getTestAccountAccount2WechatId() {
        return getPropertyValue("testautomation.test_account.account2.wechat_id");
    }
    //endregion


    //region Wechat layout related configuration
    public static String getWechatLayoutLeftMenu() {
        return getPropertyValue("testautomation.wechat.layout.left_menu");
    }

    public static String getWechatLayoutMiddleMenu(){
        return getPropertyValue("testautomation.wechat.layout.middle_menu");
    }

    public static String getWechatLayoutRightMenu(){
        return getPropertyValue("testautomation.wechat.layout.right_menu");
    }
    //endregion


    //region get wechat subscription number
    public static String getWechatSubscriptionNumber() {
        return getPropertyValue("testautomation.wechat.subscription");
    }

    public static String getWechatUserOpenId1() {
        return getPropertyValue("testautomation.wechat.user.openid1");
    }
    //enregion

    //merge contact info
    public static String getMergeContactFirstName() {
        return getPropertyValue("testautomation.bui.mergecontact.firstname");
    }
    public static String getMergeContactLasttName() {
        return getPropertyValue("testautomation.bui.mergecontact.lastname");
    }
    public static String getMergeContactPhone() {
        return getPropertyValue("testautomation.bui.mergecontact.phone");
    }
    public static String getMergeContactEmail() {
        return getPropertyValue("testautomation.bui.mergecontact.email");
    }
    //


    // work hour and holiday statue config
    public static String isAgentInWorkHour() {
        return getPropertyValue("testautomation.agent.workhour.statue");
    }

    public static String isAgentInHoliday() {
        return getPropertyValue("testautomation.agent.holiday.statue");
    }
    // end of work hour and holiday statue config


    public static void main(String[] args) {
        System.out.println(getPropertyValue("testautomation.admin_console.identity_domain"));
    }
}
