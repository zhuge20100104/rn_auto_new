package base.utils.medias;

import base.utils.http.OkHttpUtil;
import base.utils.sqllite.dao.AccessTokenDao;
import config.utils.ConfigUtil;


/**
 * The access token manager class
 */
public class AccessTokenManager {

    /**
     * Get a new access token
     * <br> This method will get the current access token in sqlite database instead of get it online
     * @return The access token
     * @throws Exception  This method will throw an exception if getting access token failed
     */
    public static String getAccessToken() throws Exception {
        String appId = ConfigUtil.getAccount1AppId();
        String appSecret = ConfigUtil.getAccount1AppSecret();

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" +appSecret;
        AccessTokenDao accessTokenDao = new AccessTokenDao();
        String accessToken = accessTokenDao.getCurrentAccessToken();

        if(accessToken == null) {
            String token = (String) OkHttpUtil.httpGetField(url,"access_token");
            if(token == null) {
                throw new GetAccessTokenException("Get access token failed, invalid appid or secret!!");
            }
            accessTokenDao.setFirstAccessToken(token);
            accessToken = token;
        }

        return accessToken;
    }


    /**
     * Delete access token in the database, used when you check the access token is out of dated.
     * @throws Exception This method will throw an exception if deleting access token failed
     */
    public static void deleteAccessToken() throws Exception {
        AccessTokenDao accessTokenDao = new AccessTokenDao();
        accessTokenDao.deleteAccessToken();
    }


}
