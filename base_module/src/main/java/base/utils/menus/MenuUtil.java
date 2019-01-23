package base.utils.menus;

import base.utils.http.OkHttpUtil;
import base.utils.medias.AccessTokenManager;
import base.utils.sqllite.dao.AccessTokenDao;


/**
 * Menu util used to create menu for public wechat account
 */
public class MenuUtil {
    /** The single menu util instance*/
    private static MenuUtil instance = new MenuUtil();

    /**
     * Private constructor
     */
    private MenuUtil(){
    }


    /**
     * Public static method to get menu util instance
     * @return  The single menu util instance
     */
    public static MenuUtil getInstance() {
        synchronized (instance) {
            if (instance == null) {
                instance = new MenuUtil();
            }
        }
        return instance;
    }


    /***
     * Method to create menu for public wechat account
     * @param menuStr The json string used to create menu
     * @return  Whether the creation process is successful
     * @throws Exception  This method will throw an exception if create menu failed
     */
    public  boolean createMenu(String menuStr) throws Exception {

        String accessToken = AccessTokenManager.getAccessToken();
        System.out.println(accessToken);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;

        int errCode = (int)OkHttpUtil.httpPostField(url,menuStr,"errcode");
        if(errCode != 0) {
            //Reset accessToken
            AccessTokenDao accessTokenDao = new AccessTokenDao();
            accessTokenDao.deleteAccessToken();
            accessToken = AccessTokenManager.getAccessToken();


            url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;
            errCode = (int)OkHttpUtil.httpPostField(url,menuStr,"errcode");
            System.out.println(errCode);
            if(errCode == 0) {
                return true;
            }else {
                return false;
            }


        }else {
            return true;
        }
    }


    /**
     * Delete menu for public wechat account
     * @return  Whether deleting is successful
     * @throws Exception This method will throw an exception if delete menu failed
     */
    public boolean deleteMenu() throws Exception {
        String accessToken = AccessTokenManager.getAccessToken();
        System.out.println(accessToken);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accessToken;

        int errCode = (int)OkHttpUtil.httpGetField(url,"errcode");
        if(errCode != 0) {
            //Reset accessToken
            AccessTokenDao accessTokenDao = new AccessTokenDao();
            accessTokenDao.deleteAccessToken();
            accessToken = AccessTokenManager.getAccessToken();


            url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accessToken;
            errCode = (int)OkHttpUtil.httpGetField(url,"errcode");
            System.out.println(errCode);
            if(errCode == 0) {
                return true;
            }else {
                return false;
            }


        }else {
            return true;
        }

    }

    public static void main(String[] args) throws Exception{
        String menu = "{\"button\":[{\"type\":\"scancode_push\",\"name\":\"TestEdit\",\"key\":\"1\"},{\"type\":\"click\",\"name\":\"WeChatMenu\",\"key\":\"2\"},{\"type\":\"click\",\"name\":\"InWeChat\",\"key\":\"3\"}]}";
        boolean ret = MenuUtil.getInstance().createMenu(menu);

//        boolean ret = MenuUtil.getInstance().deleteMenu();
        System.out.println(ret);
    }
}
